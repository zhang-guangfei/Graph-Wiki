package com.sales.ops.serviceimpl.mdm;

import com.sales.ops.db.dao.OpsCoordinateMapper;
import com.sales.ops.db.entity.OpsCoordinate;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.po.MdmRequest;
import com.sales.ops.dto.po.MdmResponseDto;
import com.sales.ops.dto.po.MdmTokenResponseDto;
import com.sales.ops.service.mdm.MdmService;
import com.smc.smccloud.core.utils.DateUtil;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/30 13:44
 */
@Slf4j
@Service
public class MdmServiceImpl implements MdmService {

    @Value("${mdm.url}")
    private String mdmUrl;

    @Value("${mdm.oauth.grant_type}")
    private String grantType;

    @Value("${mdm.oauth.client_id}")
    private String clientId;

    @Value("${mdm.oauth.client_secret}")
    private String clientSecret;

    // 缓存Token
    private String currentToken;
    private long tokenExpireTime;

    @Autowired
    private OpsCoordinateMapper opsCoordinateMapper;


    @Autowired
    private MdmFeignApi mdmFeignApi;

    /**
     * 获取Token（带缓存）
     */
    @Override
    public String getToken() {
        // 如果Token存在且未过期，直接返回
        if (currentToken != null && System.currentTimeMillis() < tokenExpireTime) {
            return currentToken;
        }
        // 否则重新获取Token
        MdmTokenResponseDto tokenResponse = mdmFeignApi.getToken(grantType, clientId, clientSecret);
        currentToken = "Bearer " + tokenResponse.getAccess_token();
        // 提前5秒过期，避免边界问题
        tokenExpireTime = System.currentTimeMillis() + (tokenResponse.getExpires_in() - 5) * 1000;
        return currentToken;
    }

    @Override
    public void sendMdmData(Map<String, List<OpsRequestpurchase>> modelMap){
        // 1.初始化数据
        ArrayList<MdmRequest> requests = new ArrayList<>();
        modelMap.entrySet().forEach(a -> {
            Map<String, Boolean> orderno = new HashMap<String, Boolean>();
            for (OpsRequestpurchase o : a.getValue()) {
                orderno.put((o.getOrderno() + "-" + o.getItemno().toString()
                        + (o.getSplititemno() == null ? "" : ("-" + o.getSplititemno().toString()))), true);

                String inquiryDescription ="请购单号：" + o.getOrderno() + "-" + o.getItemno().toString()
                        + (o.getSplititemno() == null ? "" : ("-" + o.getSplititemno().toString()));
                MdmRequest mdm = new MdmRequest(o.getModelno(),inquiryDescription,o.getDeptno(),o.getDeptno()
                        ,o.getCustomerno(),o.getCustomerno(),"","");
                requests.add(mdm);
            }
        });
        // 2.调用mdm接口每次最多处理100条
        int batchSize = 50;
        int totalRequests = requests.size();
        for (int i = 0; i < totalRequests; i += batchSize) {
            // 计算当前批次的结束索引
            int end = Math.min(i + batchSize, requests.size());
            // 获取当前批次
            List<MdmRequest> batch = requests.subList(i, end);
            long beginTime = System.currentTimeMillis();
            // 调用接口处理当前批次
            MdmResponseDto mdmResponseDto = null;
            try {
                mdmResponseDto = callBusinessApi(batch);
            } catch (Exception e) {
                mdmResponseDto = new MdmResponseDto();
                mdmResponseDto.setSuccess(false);
                mdmResponseDto.setCode("接口异常");
                mdmResponseDto.setMessage(e.getMessage());
            }
            // 执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            savaLog(batch.toString(),mdmResponseDto,time);
        }
    }

    /**
     * 保存日志
     * @param param
     * @param returnMsg
     * @param time
     */
    public void savaLog(String param,MdmResponseDto returnMsg,long time){
        // 保存系统日志
        OpsCoordinate log = new OpsCoordinate();
        log.setRemark(mdmUrl);
        log.setMessage(param);
        log.setUsetime(DateUtil.getNow());
        log.setIssuccess(returnMsg.getSuccess());
        log.setApiname("mdm询价接口");
        log.setType("sendMdmData");
        log.setReturnMessage(returnMsg.toString());
        log.setCostTime(time);
        opsCoordinateMapper.insertSelective(log);
    }

    @Override
    public MdmResponseDto callBusinessApi(List<MdmRequest> request) {
        try {
            String token = getToken();
            return mdmFeignApi.doBusiness(token,request);
        } catch (FeignException e) {
            if (e.status() == 401) {
                // Token可能过期，强制刷新后重试
                return callBusinessApiWithRefreshToken(request);
            }
            throw e;
        }
    }

    /**
     * 强制刷新Token并调用业务接口
     */
    public MdmResponseDto callBusinessApiWithRefreshToken(List<MdmRequest> request) {
        // 强制清除缓存
        currentToken = null;
        // 重新获取Token
        String token = getToken();
        return mdmFeignApi.doBusiness(token, request);
    }

}
