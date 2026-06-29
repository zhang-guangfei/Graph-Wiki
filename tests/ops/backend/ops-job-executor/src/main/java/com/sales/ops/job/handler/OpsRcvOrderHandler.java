//package com.sales.ops.job.handler;
//
//import com.smc.smccloud.core.model.ResultVo.ResultVo;
//import com.smc.smccloud.core.utils.PublicUtil;
//import com.smc.smccloud.model.DataTypeVO;
//import com.smc.smccloud.service.DictDataServiceFeignApi;
//import com.smc.smccloud.service.ReceiveOrderServiceFeignApi;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//
//@Component
//@Slf4j
//public class OpsRcvOrderHandler {
//    @Resource
//    private DictDataServiceFeignApi dictDataServiceFeignApi;
//    @Resource
//    private ReceiveOrderServiceFeignApi receiveOrderServiceFeignApi;
//
//    /**
//     * 自动接入订单 30分钟执行一次
//     */
//    @XxlJob("autoRcvOrder")
//    public void autoRcvOrder() throws Exception {
//        XxlJobHelper.log("==> 进入自动接入订单执行器");
//        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "2");
//        if (PublicUtil.isEmpty(dataTypeCodesInfo.getData())) {
//            log.error("【autoRcvOrder】 自动接入订单失败");
//            return;
//        }
//        if (dataTypeCodesInfo.getData().getExtNote1().equals("1")) {
//            ResultVo<String> stringResultVo = receiveOrderServiceFeignApi.AutoRcvOrder(true);
//            if (!stringResultVo.isSuccess() || PublicUtil.isEmpty(stringResultVo.getData()))
//            {
//                log.error("【autoRcvOrder】 自动接入订单失败");
//                return;
//            }
//        }
//    }
//
//}
