package com.smc.smccloud.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.VSalesManuorder.OPSVRequisitionStausOtherToSalesVO;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.*;
import com.smc.smccloud.model.receiveorder.RcvDetailVO;
import com.smc.smccloud.model.receiveorder.RcvOrderInfoVO;
import com.smc.smccloud.service.OrderStateServiceFeignApi;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class OrderStateServiceFeignHystrix implements FallbackFactory<OrderStateServiceFeignApi> {
    @Override
    public OrderStateServiceFeignApi create(Throwable throwable) {
        return new OrderStateServiceFeignApi() {

            @Override
            public ResultVo<String> addOrderState(OrderStateVO orderStateVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<PageInfo<OrderStateDTO>> listOrderState(OrderStateRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<OrderStateDetailVO>> getStateDetail(String orderNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> importJPDeliveryData(List<JPDeliveryDataVO> jpDeliveryDataVOList) {
                return ResultVo.failure("导入jp deliverydata调用失败");
            }

            @Override
            public ResultVo<String> importJPReceiveOrderFile(MultipartFile file) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public Date calcEsArrivalDate(String provider, String transType, Date dlvDate) {
                return null;
            }

            @Override
            public ResultVo<String> importCNMOrderReply() {
                return null;
            }

            @Override
            public ResultVo<String> importOverseaOrderReply(List<OrderStateVO> list) {
                return null;
            }

            @Override
            public ResultVo<String> importCNFactoryNotSendOrderState() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> importCNMOPSVRequisitionStatusToSales() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> supplierReplyOrderState(OrderSupplierReplyState replyState) {
                return null;
            }

            @Override
            public ResultVo<String> handleDeleleOrder(HandleDeleteOrderDTO dto) {
                return null;
            }

            @Override
            public ResultVo<String> checkOrderState() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public void syncOrderStateInfo() {

            }

            @Override
            public ResultVo<String> importSupplierReplyDate(MultipartFile file) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> downloadJPDeliveryFile() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<String>> canDelOrderStatus() {
                return ResultVo.failure("服务降级");
            }

//            @Override
//            public ResultVo<String> supplierReplyOrderState(OrderSupplierReplyState replyState) {
//                return null;
//            }
        };
    }
}
