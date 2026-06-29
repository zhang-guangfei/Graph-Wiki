package com.smc.smccloud.web.rpc;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.RcvdetailMapper;
import com.smc.smccloud.model.OrderSales.OrderDlvDataDO;
import com.smc.smccloud.model.OrderSales.OrderDlvDataVO;
import com.smc.smccloud.model.OrderSales.OrderSalesDTO;
import com.smc.smccloud.model.adapter.order.AdapterOrderResult;
import com.smc.smccloud.model.adapter.order.OrderItem;
import com.smc.smccloud.model.adapter.order.UpOrderInfoVO;
import com.smc.smccloud.model.adapter.order.UpOrderMasterInfoVO;
import com.smc.smccloud.model.invoice.OrderInvoiceDTO;
import com.smc.smccloud.model.invoice.SalesInvoiceDTO;
import com.smc.smccloud.model.invoice.SalesInvoiceRequest;
import com.smc.smccloud.model.order.RcvDetailDO;
import com.smc.smccloud.model.order.RcvMasterDO;
import com.smc.smccloud.model.ordermodify.OrderDeliveryModifyInfo;
import com.smc.smccloud.model.receiveorder.*;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class ReceiveOrderServiceFeignClient implements ReceiveOrderServiceFeignApi {

    @Resource
    private RcvdetailMapper rcvdetailMapper;
    @Autowired
    @Lazy
    private ReceiveOrderService receiveOrderService;
    @Resource
    private OrderSalesService orderSalesService;
    @Resource
    private OrderDlvDataService orderDlvDataService;
    @Resource
    private RedisManager redisManager;
    @Resource
    private SMSAdapterService smsAdapterService;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private SpecOrderService specOrderService;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @DS("opsdb")
    public ResultVo<Integer> addRcvDetail(RcvDetailVO rcvDetailVO) {
        RcvDetailDO rcvdetail = BeanCopyUtil.copy(rcvDetailVO, RcvDetailDO.class);
        return receiveOrderService.addRcvDetail(rcvdetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @DS("opsdb")
    public ResultVo<Integer> addRcvMaster(RcvMasterVO rcvMasterVO) {
        RcvMasterDO rcvmaster = BeanCopyUtil.copy(rcvMasterVO, RcvMasterDO.class);
        return receiveOrderService.addRcvMaster(rcvmaster);
    }


    @Override
    public ResultVo<String> addRcvOrder(@Valid RcvOrderDTO dto) {
        return receiveOrderService.addRcvOrder(dto);
    }

    @Override
    public ResultVo<RcvDetailVO> findOrderDetail(String fullOrderNo) {
        return receiveOrderService.findOrderDetail(fullOrderNo);
    }

    @Override
    public ResultVo<RcvMasterVO> findOrderMaster(String rorderNo) {
        return receiveOrderService.findOrderMaster(rorderNo);
    }

    @Override
    @Transactional
    public ResultVo<Integer> updateRcvDetail(RcvDetailVO rcvDetailVO) {
        RcvDetailDO rcvdetailDO = BeanCopyUtil.copy(rcvDetailVO, RcvDetailDO.class);
        int i;
        try {
            i = rcvdetailMapper.updateById(rcvdetailDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        if (i > 0) {
            return ResultVo.success(i);
        } else {
            return ResultVo.failure("操作失败");
        }
    }

//    @Override
//    public ResultVo<String> accessOrder(@Valid RcvOrderVO rcvOrderVO) {
//        return receiveOrderService.accessOrder(rcvOrderVO);
//    }

    @Override
    public ResultVo<String> autoRcvOrderFeign() {
        ResultVo<String> stringResultVo = receiveOrderService.receiveOrderALLFromOrderSales();
        if (!stringResultVo.isSuccess()) {
            return ResultVo.failure("订单接入失败, " + stringResultVo.getMessage());
        }
        return ResultVo.success("订单接入成功, " + stringResultVo.getData());
    }


    @Override
    // @Transactional
    public ResultVo<String> addOrderSales(OrderSalesDTO dto) {
        return orderSalesService.addOrderSales(dto);
    }

    @Override
    public ResultVo<RcvOrderInfoVO> getRcvOrderInfo(String orderNo, Integer itemNo) {
        return receiveOrderService.getRcvOrderInfo(orderNo, itemNo);
    }

    @Override
    public ResultVo<List<OrderInvoiceVO>> getOrderInvoiceInfo(OrderInvoiceDTO dto) {
        return receiveOrderService.getOrderInvoiceInfo(dto);
    }

    @Override
    public ResultVo<String> saveOrderDlvData(OrderDlvDataVO orderDlvDataVO) {
        if (PublicUtil.isEmpty(orderDlvDataVO)) {
            return ResultVo.failure("保存订单收货信息失败");
        }

        Assert.hasText(orderDlvDataVO.getCustomerNo(), "客户代码必填");
        Assert.hasText(orderDlvDataVO.getCstmName(), "客户名称必填");
        Assert.hasText(orderDlvDataVO.getDlvAddress(), "收货地址必填");
        Assert.hasText(orderDlvDataVO.getContactPsn(), "收货人必填");
        Assert.hasText(orderDlvDataVO.getTelNo(), "联系电话必填");

        OrderDlvDataDO orderDlvDataDO = BeanCopyUtil.copy(orderDlvDataVO, OrderDlvDataDO.class);
        boolean b = orderDlvDataService.saveOrderDlvData(orderDlvDataDO);
        if (!b) {
            return ResultVo.failure("保存订单收货信息失败");
        }
        return ResultVo.success("成功保存订单收货信息");
    }

    @Override
    public ResultVo<String> createCustomerOrderNo() {
        Object o = redisManager.lLeftPop(Constants.SALES_ORDER_NO); // bug-8874
        if (PublicUtil.isNotEmpty(o.toString())) {
            return ResultVo.success(o.toString());
        }
        return null;
    }



    @Override
    public ResultVo<String> updateCNMadeOrderStatus() {
        return orderSalesService.updateCNMadeOrderStatus();
    }

    @Override
    public ResultVo<RcvOrderDTO> fullOrderInfo(String orderNo) {
        return receiveOrderService.getfullOrderInfo(orderNo);
    }

    @Override
    public ResultVo<RcvDetailDTO> getOrderDetailInfo(String rorderFno) {
        return receiveOrderService.getOrderDetailInfo(rorderFno);
    }

    @Override
    public ResultVo<RcvOrderViewVO> getRcvOrderDataByNo(String orderNo) {
        return receiveOrderService.getRcvOrderDataByNo(orderNo);
    }

    @Override
    public ResultVo<List<RcvOrderInfoVO>> getRcvOrderInfos(RcvOrderInfosDTO dto) {
        return receiveOrderService.getRcvOrderInfos(dto);
    }

    @Override
    public List<OrderItem> getOrderItemByOrerNo(String orderNo) {
        return smsAdapterService.getOrderItemByOrerNo(orderNo);
    }

    @Override
    public ResultVo<List<SalesInvoiceDTO>> getInvoiceForReturn(SalesInvoiceRequest request) {
        return receiveOrderService.getInvoiceForReturn(request);
    }

    @Override
    public ResultVo<String> calcOrderPriceSMCGroupCustomer() {
        return receiveOrderService.calcOrderPriceSMCGroupCustomer();
    }

    @Override
    public ResultVo<List<OpsOrderAssignResultVO>> getOrderAssItemsByModelNo(String orderNo, Integer orderItem) {
        return receiveOrderService.getOrderAssItemsByModelNo(orderNo, orderItem);
    }

    @Override
    public ResultVo<RcvDetailVO> findRcvDetailWithIsAssOrder(String fullOrderNo) {
        return receiveOrderService.findRcvDetailWithIsAssOrder(fullOrderNo);
    }

    @Override
    public ResultVo<List<AdapterOrderResult>> addOrderSalesFromSMS(OrderSalesDTO dto) {
        ResultVo<List<AdapterOrderResult>> result;
        String key = "ops:order:orderSales:" + dto.getItems().get(0).getOrOrderNo();
        try {
            redissonUtil.lock(key, 50, TimeUnit.SECONDS);
            result = orderSalesService.addOrderSalesFromSMS(dto);
        } finally {
            redissonUtil.unlock(key);
        }
        return result;
    }

    @Override
    public ResultVo<String> updateOrderDeliveryInfo(OrderDeliveryModifyInfo info) {
        return smsAdapterService.updateOrderDeliveryInfo(info);
    }


    @Override
    public ResultVo<Integer> updateOrderDetail(RcvDetailVO rcvDetailVO) {
        return smsAdapterService.updateOrderDetail(rcvDetailVO);
    }

    @Override
    public ResultVo<List<OrderItem>> getOrderItemStatus(String orderNo) {
        return smsAdapterService.getOrderItemStatus(orderNo);
    }

    @Override
    public ResultVo<String> exportSpecOrderExpDetailToExcel() {
        return specOrderService.exportSpecOrderExpDetailToExcel();
    }

    @Override
    public ResultVo<List<UpOrderInfoVO>> canUpOrderInfo(List<String> fullOrderNos) {
        return smsAdapterService.canUpOrderInfo(fullOrderNos);
    }

    @Override
    public ResultVo<List<UpOrderMasterInfoVO>> canUpMasterOrderInfo(List<String> mainOrderNos) {
        return smsAdapterService.canUpMasterOrderInfo(mainOrderNos);
    }

    @Override
    public ResultVo<String> receiveIPSOrder(List<String> id) {
        return orderSalesService.receiveIPSOrder(id);
    }


}
