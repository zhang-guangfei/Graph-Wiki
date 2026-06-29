package com.smc.smccloud.service;

import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
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
import com.smc.smccloud.service.hystrix.ReceiveOrderServiceFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;


/**
 * 发布 去掉 url= "http://10.116.194.236:8100"
 */
@FeignClient(name = "order-service",
        contextId = "rcvorder",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = ReceiveOrderServiceFeignHystrix.class)
public interface ReceiveOrderServiceFeignApi {

    @RequestMapping(value = "/order/rcvorder/addRcvDetail", method = RequestMethod.POST)
    ResultVo<Integer> addRcvDetail(@Valid @RequestBody RcvDetailVO rcvDetailVO);

    @RequestMapping(value = "/order/rcvorder/addRcvMaster", method = RequestMethod.POST)
    ResultVo<Integer> addRcvMaster(@Valid @RequestBody RcvMasterVO rcvMasterVO);

    /**
     * rcvMaster、rcvDetail、orderDlvData 数据写入
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/order/rcvOrder/add", method = RequestMethod.POST)
    ResultVo<String> addRcvOrder(@Valid @RequestBody RcvOrderDTO dto);

    /**
     * 增加销售订单到ordersales,orderdlvdata
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/order/orderSales/add", method = RequestMethod.POST)
    ResultVo<String> addOrderSales(@Valid @RequestBody OrderSalesDTO dto);

    @RequestMapping(value = "/order/rcvorder/findOrderDetail", method = RequestMethod.GET)
    ResultVo<RcvDetailVO> findOrderDetail(@RequestParam("fullOrderNo") String fullOrderNo);

    @RequestMapping(value = "/order/rcvorder/findOrderMaster", method = RequestMethod.GET)
    ResultVo<RcvMasterVO> findOrderMaster(@RequestParam("rorderNo") String rorderNo);

    @RequestMapping(value = "/order/rcvorder/updateRcvDetail", method = RequestMethod.POST)
    ResultVo<Integer> updateRcvDetail(@RequestBody RcvDetailVO rcvDetailVO);

    /**
     * 自动接入订单接口
     *
     * @return
     */
    @RequestMapping(value = "/order/rcvorder/autoRcvOrderFeign", method = RequestMethod.GET)
    ResultVo<String> autoRcvOrderFeign();


    /**
     * 查询订单的基本信息和状态
     *
     * @param orderNo
     * @param itemNo
     * @return
     */
    @RequestMapping(value = "/order/rcvorder/info", method = RequestMethod.GET)
    ResultVo<RcvOrderInfoVO> getRcvOrderInfo(@RequestParam("orderNo") String orderNo, @RequestParam("itemNo") Integer itemNo);

    /**
     * 查询订单的开票信息
     *
     * @return
     */
    @RequestMapping(value = "/order/rcvorder/invoice", method = RequestMethod.POST)
    ResultVo<List<OrderInvoiceVO>> getOrderInvoiceInfo(@RequestBody OrderInvoiceDTO dto);

    /**
     * 保存订单收货信息
     *
     * @param orderDlvDataVO 订单收货信息
     * @return
     */
    @RequestMapping(value = "/order/rcvorder/saveOrderDlvData", method = RequestMethod.POST)
    ResultVo<String> saveOrderDlvData(@RequestBody OrderDlvDataVO orderDlvDataVO);


    /**
     * 生成一个客户单号
     *
     * @return 订单号
     */
    @RequestMapping(value = "/order/rcvorder/createCustomerOrderNo", method = RequestMethod.GET)
    ResultVo<String> createCustomerOrderNo();

    /**
     * 根据接入订单状态 修改接入中国制造订单状态
     */
    @RequestMapping(value = "/order/rcvorder/updateCNMadeOrderStatus", method = RequestMethod.GET)
    ResultVo<String> updateCNMadeOrderStatus();

    @RequestMapping(value = "/order/rcvorder/fullOrderInfo", method = RequestMethod.GET)
    ResultVo<RcvOrderDTO> fullOrderInfo(@RequestParam("orderNo") String orderNo);

    /**
     * 根据订单号获取子项信息
     *
     * @param rorderFno 完整订单号
     * @return 订单号 状态 是否可以取消 是否可以修改
     */
    @RequestMapping(value = "/order/rcvorder/getOrderDetailInfo", method = RequestMethod.GET)
    ResultVo<RcvDetailDTO> getOrderDetailInfo(@RequestParam("rorderFno") String rorderFno);

    /**
     * 根据完整订单号查rcvorder_view视图
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "/order/rcvorder/getRcvOrderDataByNo", method = RequestMethod.GET)
    ResultVo<RcvOrderViewVO> getRcvOrderDataByNo(@RequestParam("orderNo") String orderNo);

    /**
     * 批量查询订单的基本信息和状态
     *
     * @return
     */
    @RequestMapping(value = "/order/rcvorder/infos", method = RequestMethod.POST)
    ResultVo<List<RcvOrderInfoVO>> getRcvOrderInfos(@RequestBody RcvOrderInfosDTO dto);

    @RequestMapping(value = "/order/rcvorder/getOrderItemByOrerNo", method = RequestMethod.GET)
    List<OrderItem> getOrderItemByOrerNo(String orderNo);

    @RequestMapping(value = "/order/rcvorder/getInvoiceForReturn", method = RequestMethod.POST)
    ResultVo<List<SalesInvoiceDTO>> getInvoiceForReturn(@RequestBody SalesInvoiceRequest request);

    /**
     * 定时获取客户集团交易价格
     */
    @RequestMapping(value = "/order/rcvorder/calcOrderPriceSMCGroupCustomer", method = RequestMethod.POST)
    ResultVo<String> calcOrderPriceSMCGroupCustomer();

    @RequestMapping(value = "/order/rcvorder/getOrderAssItemsByModelNo", method = RequestMethod.GET)
    ResultVo<List<OpsOrderAssignResultVO>> getOrderAssItemsByModelNo(@RequestParam("orderNo") String orderNo, @RequestParam("orderItem") Integer orderItem);

    // 获取组装订单信息
    @RequestMapping(value = "/order/rcvorder/findRcvDetailWithIsAssOrder", method = RequestMethod.GET)
    ResultVo<RcvDetailVO> findRcvDetailWithIsAssOrder(@RequestParam("fullOrderNo") String fullOrderNo);

    //  ===================================适配器队列调用======= start==========================================

    /**
     * 增加来自门户的订单
     */
    @RequestMapping(value = "/order/orderSales/addFromSMS", method = RequestMethod.POST)
    ResultVo<List<AdapterOrderResult>> addOrderSalesFromSMS(@Valid @RequestBody OrderSalesDTO dto);



    /**
     * 修改信息（集约方式、出货方式、收货地址信息、客户货期、开票类型和开票方式）
     */
    @RequestMapping(value = "/order/smsAdapter/updateOrderDeliveryInfo", method = RequestMethod.POST)
    ResultVo<String> updateOrderDeliveryInfo(@RequestBody OrderDeliveryModifyInfo info);


    @RequestMapping(value = "/order/smsAdapter/updateOrderDetail", method = RequestMethod.POST)
    ResultVo<Integer> updateOrderDetail(@RequestBody RcvDetailVO rcvDetailVO);

    /**
     * 查询订单处理状态
     * @param orderNo 订单号
     * @return List
     */
    @RequestMapping(value = "/order/smsAdapter/getOrderItemStatus", method = RequestMethod.POST)
    ResultVo<List<OrderItem>> getOrderItemStatus(@RequestParam("orderNo") String orderNo);

    //===================================适配器队列调用======= end==========================================

    @RequestMapping(value = "/order/specOrder/exportSpecOrderExpDetailToExcel", method = RequestMethod.GET)
    ResultVo<String> exportSpecOrderExpDetailToExcel();


    @RequestMapping(value = "/order/smsAdapter/canUpOrderInfo", method = RequestMethod.POST)
    ResultVo<List<UpOrderInfoVO>> canUpOrderInfo(@RequestBody List<String> fullOrderNos);

    @RequestMapping(value = "/order/smsAdapter/canUpMasterOrderInfo", method = RequestMethod.POST)
    ResultVo<List<UpOrderMasterInfoVO>> canUpMasterOrderInfo(@RequestBody List<String> mainOrderNos);

    @RequestMapping(value = "/order/smsAdapter/receiveIPSOrder", method = RequestMethod.POST)
    ResultVo<String> receiveIPSOrder(@RequestBody List<String> id);

}
