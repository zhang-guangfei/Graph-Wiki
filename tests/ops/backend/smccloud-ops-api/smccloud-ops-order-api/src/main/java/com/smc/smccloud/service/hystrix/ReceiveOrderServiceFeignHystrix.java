package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.OrderSales.OrderDlvDataVO;
import com.smc.smccloud.model.OrderSales.OrderSalesDTO;
import com.smc.smccloud.model.adapter.order.AdapterOrderResult;
import com.smc.smccloud.model.adapter.order.OrderItem;
import com.smc.smccloud.model.adapter.order.UpOrderInfoVO;
import com.smc.smccloud.model.adapter.order.UpOrderMasterInfoVO;
import com.smc.smccloud.model.invoice.OrderInvoiceDTO;
import com.smc.smccloud.model.invoice.SalesInvoiceDTO;
import com.smc.smccloud.model.invoice.SalesInvoiceRequest;
import com.smc.smccloud.model.ordermodify.OrderDeliveryModifyInfo;
import com.smc.smccloud.model.receiveorder.*;
import com.smc.smccloud.service.ReceiveOrderServiceFeignApi;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Component
public class ReceiveOrderServiceFeignHystrix implements FallbackFactory<ReceiveOrderServiceFeignApi> {
    @Override
    public ReceiveOrderServiceFeignApi create(Throwable throwable) {
        return new ReceiveOrderServiceFeignApi() {
            @Override
            public ResultVo<Integer> addRcvDetail(RcvDetailVO rcvDetailVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<Integer> addRcvMaster(RcvMasterVO rcvMasterVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> addRcvOrder(@Valid RcvOrderDTO dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> addOrderSales(@Valid OrderSalesDTO dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<RcvDetailVO> findOrderDetail(String fullOrderNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<RcvMasterVO> findOrderMaster(String rorderNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<Integer> updateRcvDetail(RcvDetailVO rcvDetailVO) {
                return ResultVo.failure("服务降级");
            }


            @Override
            public ResultVo<String> autoRcvOrderFeign() {
                return ResultVo.failure("服务降级");
            }


            @Override
            public ResultVo<RcvOrderInfoVO> getRcvOrderInfo(String orderNo, Integer itemNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<OrderInvoiceVO>> getOrderInvoiceInfo(OrderInvoiceDTO dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> saveOrderDlvData(OrderDlvDataVO orderDlvDataVO) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> createCustomerOrderNo() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> updateCNMadeOrderStatus() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<RcvOrderDTO> fullOrderInfo(String rorderNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<RcvDetailDTO> getOrderDetailInfo(String rorderFno) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<RcvOrderViewVO> getRcvOrderDataByNo(String orderNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<RcvOrderInfoVO>> getRcvOrderInfos(RcvOrderInfosDTO dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public List<OrderItem> getOrderItemByOrerNo(String orderNo) {
                return null;
            }

            @Override
            public ResultVo<List<SalesInvoiceDTO>> getInvoiceForReturn(SalesInvoiceRequest request) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> calcOrderPriceSMCGroupCustomer() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<OpsOrderAssignResultVO>> getOrderAssItemsByModelNo(String orderNo, Integer orderItem) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<RcvDetailVO> findRcvDetailWithIsAssOrder(String fullOrderNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<AdapterOrderResult>> addOrderSalesFromSMS(@Valid OrderSalesDTO dto) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> updateOrderDeliveryInfo(OrderDeliveryModifyInfo info) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<Integer> updateOrderDetail(RcvDetailVO rcvDetailVO) {
                return  ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<OrderItem>> getOrderItemStatus(String orderNo) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> exportSpecOrderExpDetailToExcel() {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<UpOrderInfoVO>> canUpOrderInfo(List<String> fullOrderNos) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<List<UpOrderMasterInfoVO>> canUpMasterOrderInfo(List<String> mainOrderNos) {
                return ResultVo.failure("服务降级");
            }

            @Override
            public ResultVo<String> receiveIPSOrder(List<String> id) {
                return ResultVo.failure("服务降级");
            }

        };
    }
}
