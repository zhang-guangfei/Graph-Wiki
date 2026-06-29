package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.invoice.OrderInvoiceDTO;
import com.smc.smccloud.model.invoice.SalesInvoiceDTO;
import com.smc.smccloud.model.invoice.SalesInvoiceRequest;
import com.smc.smccloud.model.order.RcvDetailDO;
import com.smc.smccloud.model.order.RcvMasterDO;
import com.smc.smccloud.model.order.RcvSaleDTO;
import com.smc.smccloud.model.receiveorder.*;

import java.util.List;

public interface ReceiveOrderService {

    ResultVo<String> receiveOrderALLFromOrderSales();

    ResultVo<PageInfo<ReceiveOrderVO>> listReceiveOrder(ReceiveOrderRequest request, Page page);

//    PageInfo<RcvDetailDO> listRcvDetail(ReceiveOrderRequest request, Page page);

    ResultVo<String> receiveOrderByOrderNos(String[] orderNos);

    // 订单转为处理
    ResultVo<String> convertToProcessing(List<ReceiveOrderVO> receiveOrderVOList);

    ResultVo<RcvOrderInfoVO> getRcvOrderInfo(String orderNo, Integer itemNo);

    ResultVo<List<OrderInvoiceVO>> getOrderInvoiceInfo(OrderInvoiceDTO dto);

    // 写入 rcvDetail rcvMatser orderDlvData
    ResultVo<String> addRcvOrder(RcvOrderDTO dto);

    ResultVo<Integer> addRcvDetail(RcvDetailDO rcvDetailDO);

    ResultVo<Integer> addRcvMaster(RcvMasterDO rcvMasterDO);

    ResultVo<RcvDetailVO> findOrderDetail(String fullOrderNo);

    ResultVo<RcvMasterVO> findOrderMaster(String rorderNo);

    /**
     * 查询RcvDetail.status
     */
    Integer getRcvDetailStatus(String fullOrderNo);

    /**
     * 取消RcvDetail
     */
    ResultVo<String> cancelRevDetail(String fullOrderNo);

    /**
     * 测试事务
     */
    //String tetsTransaction(Boolean flag);

//    /**
//     * 接入订单从ordersales
//     *
//     * @return
//     */
//    ResultVo<String> receiveOrderFromOrderSales(List<OrderSalesDO> orderSalesDOList);

    /**
     * 查询订单完整信息
     */
    ResultVo<RcvOrderDTO> getfullOrderInfo(String orderNo);

    // 根据订单号获取子项信息
    ResultVo<RcvDetailDTO> getOrderDetailInfo(String rorderFno);

    /**
     * 批量查询订单的基本信息和状态
     *
     * @param dto
     * @return
     */
    ResultVo<List<RcvOrderInfoVO>> getRcvOrderInfos(RcvOrderInfosDTO dto);

    ResultVo<List<SalesInvoiceDTO>> getInvoiceForReturn(SalesInvoiceRequest request);

    /**
     * 集团内客户交易价格接口
     *
     * @param sellerGroupNo 卖方集团客户代码
     * @param buyerGroupNo  买方集团客户代码
     * @param modelNo       型号
     * @param quantity      数量
     * @param shikomiId     Shikomi代码
     * @return 参考价格
     */
    ResultVo<CalSMCGuidingPriceEntity> calSMCGuidingPrice(String sellerGroupNo, String buyerGroupNo,
                                                          String modelNo, int quantity, String shikomiId);

    /**
     * 定时获取客户集团交易价格
     */
    ResultVo<String> calcOrderPriceSMCGroupCustomer();

    ResultVo<RcvDetailVO> findRcvDetailWithIsAssOrder(String fullOrderNo);

    ResultVo<RcvSaleDTO> getSalesDateByModelNo(String modelNo, String applyDate);

    /**
     * 接入一个主订单号和包含的明细到rcvmaster和rcvdetail
     *
     * @param orderNo 订单号
     * @return 处理结果
     */
    ResultVo<String> receiveOrderByOrderNo(String orderNo);

    // 查询主单信息
    ResultVo<PageInfo<RcvMasterDO>> listRcvMaster(RcvMasterRequest request, Page page);
    // 查询订单子表信息
    ResultVo<List<RcvDetailDO>> listRcvDetail(String rorderNo);

    ResultVo<RcvOrderViewVO> getRcvOrderDataByNo(String orderNo);

    ResultVo<List<OpsOrderAssignResultVO>> getOrderAssItemsByModelNo(String orderNo,Integer itemNo);

}
