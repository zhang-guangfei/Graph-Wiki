package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.sales.ops.db.entity.OpsPoTranstypeConfig;
import com.sales.ops.dto.eta.ETAPageDto;
import com.sales.ops.dto.eta.FindETADataDto;
import com.sales.ops.dto.po.core.PoTransTypeCoreDto;
import com.sales.ops.dto.purchase.BaseDataDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsOrderFeignApi;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.feign.eta.EtaFeign;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.mapper.OpsPoTranstypeConfigMapper;
import com.smc.smccloud.model.binorder.ModelExpFreqRequest;
import com.smc.smccloud.model.binorder.ModelExpFreqVO;
import com.smc.smccloud.model.delivery.WarehouseSendDateVO;
import com.smc.smccloud.model.invoice.SalesInvoiceRequest;
import com.smc.smccloud.model.order.JudgeShipParams;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.order.SendProcessOrderWithRiskLevel;
import com.smc.smccloud.model.receiveorder.RcvDetailVO;
import com.smc.smccloud.service.AdapterService;
import com.smc.smccloud.service.BinServiceFeignApi;
import com.smc.smccloud.service.OpsCommonService;
import com.smc.smccloud.service.ReceiveOrderServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author lyc
 * @Date 2023/8/11 13:55
 * @Descripton TODO
 */
@Service
@Slf4j
public class AdapterServiceImpl implements AdapterService {

    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

    @Resource
    private OpsOrderFeignApi opsOrderFeignApi;

    @Resource
    private ReceiveOrderServiceFeignApi receiveOrderServiceFeignApi;

    @Resource
    private EtaFeign etaFeign;

    @Resource
    private OpsCommonService opsCommonService;

    @Resource
    private BinServiceFeignApi binServiceFeignApi;

    @Resource
    private OpsPoTranstypeConfigMapper opsPoTranstypeConfigMapper;

    @Override
    public ResultVo<List<WarehouseSendDateVO>> getWarehouseSendDateByOrderNo(List<WarehouseSendDateVO> info) {
        if(CollectionUtils.isEmpty(info)) {
            return ResultVo.failure("入参不可为空");
        }
        log.info("反算物流货期参数 {}", JSONArray.toJSONString(info));
        for (WarehouseSendDateVO item : info ) {
            if (StringUtils.isBlank(item.getOrderNo())) {
                continue;
            }
            if(Objects.isNull(item.getDlvDate())) {
                continue;
            }
            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(item.getOrderNo());
            CommonResult<String> orderMaxWlday = null;
            try {
                orderMaxWlday = opsWmDispatchForOrderFeignApi.getOrderMaxWlday(orderNoInfo.getOrderNo(),
                        String.valueOf(orderNoInfo.getItemNo()), item.getDlvDate());
            } catch (Exception e) {
                throw new BusinessException("wm服务接口异常");
            }
            log.info("反算物流货期接口返回结果 {}", JSONUtil.toJsonStr(orderMaxWlday));
            if (!Objects.isNull(orderMaxWlday)) {
                if (orderMaxWlday.isSuccess()) {
                    String data = orderMaxWlday.getData();
                    item.setWarehouseSendDate(DateUtil.stringToDate(data));
                } else {
                    item.setWarehouseSendDate(DateUtil.addDay(item.getDlvDate(),100));
                }
            } else {
                throw new BusinessException("wm服务接口异常,接口返回null");
            }
        }
        return ResultVo.success(info);
    }

    @Override
    public ResultVo<List<SendProcessOrderWithRiskLevel>> findSendProcessRiskLevel(SalesInvoiceRequest request) {
        if (request == null || CollectionUtils.isEmpty(request.getOrderNos())
                || StringUtils.isBlank(request.getCustomerNo())) {
            return ResultVo.failure("订单号/客户不可为空");
        }
        SendProcessOrderWithRiskLevel riskLevel;
        List<SendProcessOrderWithRiskLevel> list = new ArrayList<>();
        ResultVo<List<ModelExpFreqVO>> all;
        ResultVo<RcvDetailVO> orderDetail;
        for (String orderNo: request.getOrderNos()) {
            riskLevel = new SendProcessOrderWithRiskLevel();
            riskLevel.setOrderNo(orderNo);
            orderDetail = receiveOrderServiceFeignApi.findOrderDetail(orderNo);
            if (orderDetail == null || !orderDetail.isSuccess()) {
                riskLevel.setProductRiskLevel("高风险");
                list.add(riskLevel);
                continue;
            }

            // 通过型号获取频率
            ModelExpFreqRequest freqRequest = new ModelExpFreqRequest();
            freqRequest.setModelTYpe("2");
            freqRequest.setModelNo(orderDetail.getData().getModelNo());
            freqRequest.setStockcode( "ALL");
            freqRequest.setStockType(0);

            all = binServiceFeignApi.getModelExpFreqWithRiskLevel(freqRequest);
            if (all.isSuccess() && all.getData() != null) {
                int freq = all.getData().get(0).getMonthsOf12() != null ? all.getData().get(0).getMonthsOf12() : 0;
                int customerOf12 = all.getData().get(0).getCustomersOf12() != null ? all.getData().get(0).getCustomersOf12() : 0;
                if (customerOf12 <= 1 || (freq >= 0 && freq <= 3)) {
                    riskLevel.setProductRiskLevel("高风险");
                }
                if (freq >= 4 && freq <= 8) {
                    riskLevel.setProductRiskLevel("中风险");
                }
                if (freq >= 9 && freq <= 13) {
                    riskLevel.setProductRiskLevel("低风险");
                }
            } else {
                riskLevel.setProductRiskLevel("高风险");
            }
            list.add(riskLevel);
        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<List<BaseDataDto>> getAllSupplier() {
      return  opsCommonService.getAllSupplier();
    }

    @Override
    public ResultVo<ETAPageDto> findEtaAPI(List<FindETADataDto> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return ResultVo.failure("入参不可为空");
        }
        return etaFeign.findEtaAPI(dataList);
    }

    @Override
    public ResultVo getOrderRoute(String expressNo) {
        try {
            CommonResult orderRoute = opsOrderFeignApi.getOrderRoute(expressNo);
            if(orderRoute.isSuccess()) {
                return ResultVo.success(orderRoute.getData());
            } else {
                return ResultVo.failure(orderRoute.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("运单号查询接口异常: "+e.getMessage());
        }
    }


    @Override
    public ResultVo<List<JudgeShipParams>> judgeShipNew(List<JudgeShipParams> params){
        if (CollectionUtils.isEmpty(params)) {
            return ResultVo.failure("入参不可为空");
        }

        if(params.size() > 1500) {
            return ResultVo.failure("最大批量支持1500");
        }
        List<JudgeShipParams> list  = new ArrayList<>();
        // 5.型号选择器 模糊匹配
        List<PoTransTypeCoreDto> transTypeMatchCore = opsPoTranstypeConfigMapper.getTransTypeMatchCore();
        for (JudgeShipParams item : params) {
            boolean b = judgeShipNew(item,transTypeMatchCore);
            item.setIsShip(b);
            list.add(item);
        }
        return ResultVo.success(list);
    }

    public Boolean judgeShipNew(JudgeShipParams item,List<PoTransTypeCoreDto> transTypeMatchCore){
        //3.型号选择器
        List<String> transIdsList = new ArrayList<>();
        String porductNonStandard = opsPoTranstypeConfigMapper.getPorductNonStandard(item.getModelNo());// 海运优先 海运和快船
        if(StringUtils.isNotBlank(porductNonStandard)){
            transIdsList.add("0");
        }
        String inspectionsGroup = opsPoTranstypeConfigMapper.getInspectionsGroup(item.getModelNo()); // 温控器必须海运
        if(StringUtils.isNotBlank(inspectionsGroup)){
            transIdsList.add("0");
        }
        // 4.型号选择器 精确匹配
        List<PoTransTypeCoreDto> transTypeCores = opsPoTranstypeConfigMapper.getTransTypeCore(item.getModelNo());

        for(PoTransTypeCoreDto obj : transTypeCores){
            if(Objects.nonNull(obj.getCompareGreater())){
                if(obj.getCompareGreater()){
                    if(item.getQty() > obj.getCompareQuantity()){
                        transIdsList.add(obj.getTransId());
                    }
                }else {
                    if(item.getQty() < obj.getCompareQuantity()){
                        transIdsList.add(obj.getTransId());
                    }
                }
            }else {
                transIdsList.add(obj.getTransId());

            }
        }

        for(PoTransTypeCoreDto obj : transTypeMatchCore){
            if (Pattern.matches(obj.getModelNo(), item.getModelNo())){
                if(Objects.nonNull(obj.getCompareGreater())){
                    if(obj.getCompareGreater()){
                        if(item.getQty() > obj.getCompareQuantity()){
                            transIdsList.add(obj.getTransId());
                        }
                    }else {
                        if(item.getQty() < obj.getCompareQuantity()){
                            transIdsList.add(obj.getTransId());
                        }
                    }
                }else {
                    transIdsList.add(obj.getTransId());
                }
            }
        }

        if(transIdsList.isEmpty()){
            return false;
        }

        // 创建一个新的去重后的列表
        List<String> distinctList = transIdsList.stream()
                .distinct()           // 核心去重方法
                .collect(Collectors.toList());
        if(distinctList.isEmpty()){
            return false;
        }
        if(distinctList.size() == 1 && distinctList.get(0).equals("0")){
            return true;
        }
        return false;
    }



    @Override
    public ResultVo<List<JudgeShipParams>> judgeShip(List<JudgeShipParams> params) {

        if (CollectionUtils.isEmpty(params)) {
            return ResultVo.failure("入参不可为空");
        }

        if(params.size() > 1500) {
            return ResultVo.failure("最大批量支持1500");
        }

        List<OpsPoTranstypeConfig> transConfigList = opsPoTranstypeConfigMapper.getPoTransTypeConfigList();
        if(CollectionUtils.isEmpty(transConfigList)) {
            return ResultVo.failure("未查询到配置信息");
        }
        // 海运优先配置表--模糊匹配
        List<OpsPoTranstypeConfig> shipConfigListFuzzy = transConfigList.stream()
                .filter(s -> StringUtils.equals(s.getTranstype(), "0") && s.getMatchtype())
                .collect(Collectors.toList());

        // 海运配置表--精确匹配
        List<OpsPoTranstypeConfig> shipConfigComplete = transConfigList.stream()
                .filter(s -> StringUtils.equals(s.getTranstype(), "0") && !s.getMatchtype())
                .collect(Collectors.toList());

        Map<String, OpsPoTranstypeConfig> shipCompleteMap = new HashMap<String, OpsPoTranstypeConfig>();
        shipConfigComplete.forEach(c -> {
            shipCompleteMap.put(c.getModelno(), c);
        });
        List<JudgeShipParams> list  = new ArrayList<>();
        for (JudgeShipParams item : params) {
            boolean b = judgeShip(item.getModelNo(), shipConfigListFuzzy, shipCompleteMap, "0", item.getQty());
            item.setIsShip(b);
            list.add(item);
        }
        return ResultVo.success(list);
    }


    public boolean judgeShip(String modelNo, List<OpsPoTranstypeConfig> shipConfigListFuzzy,
                             Map<String, OpsPoTranstypeConfig> shipCompleteMap, String bigModel, Integer quantity) {
        // 超长超宽
        if (StringUtils.equals("1", bigModel) || StringUtils.equals("2", bigModel)) {
            return true;
        }
        if (!shipCompleteMap.isEmpty() && shipCompleteMap.containsKey(modelNo)) {
            if (StringUtils.equals("0", shipCompleteMap.get(modelNo).getType())) {
                // bug 14938 判断数量
                if (shipCompleteMap.get(modelNo).getComparegreater() != null) {
                    if (shipCompleteMap.get(modelNo).getComparegreater()
                            && quantity > shipCompleteMap.get(modelNo).getComparequantity()) {
                        return true;
                    } else if (!shipCompleteMap.get(modelNo).getComparegreater()
                            && quantity < shipCompleteMap.get(modelNo).getComparequantity()) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        // 海运优先清单
        for (OpsPoTranstypeConfig item : shipConfigListFuzzy) {
            if (StringUtils.equals("0", item.getType())) {
                // 正则匹配
                if (modelNo.matches(item.getModelno())) {
                    // bug 14938 判断数量
                    if (item.getComparegreater() != null) {
                        if (item.getComparegreater() && quantity > item.getComparequantity()) {
                            return true;
                        } else if (!item.getComparegreater() && quantity < item.getComparequantity()) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }

            }
        }
        return false;
    }
}
