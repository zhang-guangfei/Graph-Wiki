package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.mapper.sampleorder.SampleorderDetailMapper;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.enums.BalTypeEnum;
import com.smc.smccloud.model.enums.SampleBalAppTypeEnum;
import com.smc.smccloud.model.sampleorder.SamplebalDO;
import com.smc.smccloud.model.sampleorder.SampleorderDetailDO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.SampleOrderApplyService;
import com.smc.smccloud.service.SampleOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/7/23 12:14
 * @Descripton TODO
 */
@Service
@Slf4j
public class SampleOrderDetailServiceImpl implements SampleOrderDetailService {

    @Resource
    private SampleorderDetailMapper sampleorderDetailMapper;
    @Resource
    private SampleOrderApplyService sampleOrderApplyService;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @DS("sharedb")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public String getInvoiceNoByCostType(SamplebalDO samplebalDO) {
        log.info("根据结转类型获取发票前缀,生成发票号 实体参数: " + JSONObject.toJSONString(samplebalDO));
        // 根据结转类型获取发票前缀,生成发票号
        String invoiceNo = "";
        if (StringUtils.isBlank(samplebalDO.getBalType())) {
            if (StringUtils.isNotBlank(samplebalDO.getAppType()) && samplebalDO.getAppType().equals(SampleBalAppTypeEnum.SYPMFSY.getCode())) {
                samplebalDO.setBalType(BalTypeEnum.zp.getCode());
            } else {
                return invoiceNo;
            }
        }
        // 获取发票类型
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("1007", samplebalDO.getBalType().trim());
        log.info("根据结转类型获取发票前缀,生成发票号: "+ JSONObject.toJSONString(dataTypeCodesInfo));
        if (!dataTypeCodesInfo.isSuccess()) {
            return invoiceNo;
        }
        if (StringUtils.isBlank(dataTypeCodesInfo.getData().getExtNote1()) || dataTypeCodesInfo.getData().getExtNote1().contains("-")) {
            return invoiceNo;
        }
        invoiceNo = dataTypeCodesInfo.getData().getExtNote1() + DateUtil.getYearMonthCode(new Date());
        // 回改sampleOrderDetail表
//        try {
//            LambdaUpdateWrapper<SampleorderDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
//            updateWrapper
//                    .eq(SampleorderDetailDO::getOrderNo,samplebalDO.getRorderNo())
//                    .set(SampleorderDetailDO::getInvoiceType,dataTypeCodesInfo.getData().getExtNote1());
//            sampleorderDetailMapper.update(null,updateWrapper);
//        } catch (Exception e) {
//            return invoiceNo;
//        }
        return invoiceNo;
    }

}
