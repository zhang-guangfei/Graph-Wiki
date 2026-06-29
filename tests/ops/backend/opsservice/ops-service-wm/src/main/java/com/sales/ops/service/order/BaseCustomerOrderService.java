package com.sales.ops.service.order;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.RcvView;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.Rcvmaster;
import com.sales.ops.dto.order.ReceiveCondition;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.enums.RcvOrderStatusEnum;

import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2023/2/20 14:00
 */
public interface BaseCustomerOrderService {

    /***********************************************************【查询操作】********************************************************/
    /**
     * @description 前端接单查询页面使用
     * @author C12961
     * @date 2023/4/19 10:10
     */
    PageInfo<RcvView> searchByPage(PageModel<ReceiveCondition> pageModel) throws Exception;

    /**
     * @description 通过订单完整单号查询
     * 包括软删除订单，不抛异常，返回null
     * @author C12961
     * @date 2023/4/19 10:11
     */
    RcvView findRcvViewByFno(String orderFno);

    /**
     * @description 通过订单号项号查询
     * 包括软删除订单，不抛异常，返回null
     * @author C12961
     * @date 2023/4/19 10:11
     */
    RcvView findRcvViewByNo(String orderId, Integer orderItem);

    /**
     * @description 通过订单项号查询
     * 包括软删除订单，不抛异常，返回null
     * @author C12961
     * @date 2023/4/19 10:11
     */
    Rcvdetail findRcvDetailByNo(String orderId, String orderItem) throws OpsException;

    /**
     * @description 通过订单完整单号查询
     * 不包括软删除订单,0条多条抛异常
     * @author C12961
     * @date 2023/4/19 10:11
     */
    Rcvdetail findRcvDetailByNo(String orderId, Integer orderItem) throws OpsException;

    /**
     * @description 通过订单完整单号查询
     * 主键查询，包括软删除订单，0条不抛异常
     * @author C12961
     * @date 2023/4/19 10:12
     */
    Rcvdetail findRcvDetailByKey(String orderId, Integer orderItem);

    /**
     * @description 通过主单号查询所有订单，不抛异常
     * @author C12961
     * @date 2023/4/19 10:17
     */
    List<Rcvdetail> findRcvDetailList(String orderId);

    /**
     * @description 查询状态为信用拦截的订单
     * @author C12961
     * @date 2023/4/19 10:19
     */
    List<String> findCreditInterceptedOrderFno();

    /**
     * @description 通过完整单号查询
     * 包含软删除订单，0条抛异常
     * @author C12961
     * @date 2023/4/19 10:19
     */
    Rcvdetail findRcvDetailByFno(String orderFno) throws OpsException;

    /**
     * @description 查询订单主表
     * 主键查询，不抛异常
     * @author C12961
     * @date 2023/4/19 10:20
     */
    Rcvmaster findRcvMaster(String orderNo);

    List<Rcvdetail> findRcvDetail(String orderId) throws OpsException;

    /***********************************************************【判断操作】********************************************************/
    List<OrderNoInfo> getCustomerOrderNoFromPoNo(String orderNo, Integer itemNo, Integer splitNo);

    /**
     * @description 判断订单是否存在
     * 不包括软删除订单
     * @author C12961
     * @date 2023/4/19 10:21
     */
    boolean isRcvDetailExist(String orderId, Integer orderItem);

    /**
     * @description 判断订单是否为拆分子型号发货
     * @author C12961
     * @date 2023/4/19 10:21
     */
    boolean isAssChildModelExport(Rcvdetail rcvdetail);

    /**
     * @description 判断原状态能否更新为目标状态
     * @author C12961
     * @date 2023/4/19 10:23
     */
    Boolean enableUpdateStatus(RcvOrderStatusEnum fromStatus, RcvOrderStatusEnum toStatus) throws OpsException;

    boolean isCustomerWldate(String customerNo, Integer type);

    /***********************************************************【更新操作】********************************************************/

    int updateRcvDetail(String orderNo, Integer orderItem, Rcvdetail update) throws OpsException;

    int updateAllotInfo(String orderId, Integer integer, RcvOrderStatusEnum rcvStatus, List<String> rcvStockInfo) throws OpsException;


    int updateAllotInfo(String orderNo, Integer orderItem, RcvOrderStatusEnum status, String prodFlag, Date allotTime, List<String> stockInfo, Integer readyQuantity, Date readyTime) throws OpsException;

    int updateStockInfo(String orderNo, Integer orderItem, String stockType, String stockCode, String inventoryType) throws OpsException;

    int updateProdFlag(String orderNo, Integer orderItem, String prodFlag, Date date) throws OpsException;

    int updateInterceptInfo(String orderNo, Integer orderItem, boolean intercept, Date date) throws OpsException;

    int updateWaveTime(String orderNo, Integer orderItem, Date date) throws OpsException;

    int updateReadyTime(String orderNo, Integer orderItem, Date readyTime, Date entryTime) throws OpsException;

    int updateReadyInfo(String orderNo, Integer orderItem, Integer readyQuantity, RcvOrderStatusEnum rcvStatus) throws OpsException;

    int updateExpInfo(String orderNo, Integer orderItem, boolean allExp, int expQuantity, Date shipTime, String carrier, String expressno, Date handoverTime) throws OpsException;

    int updateReturnQty(String orderNo, Integer orderItem, int returnQty, RcvOrderStatusEnum statusEnum) throws OpsException;

    int updateInvoiceQty(String orderNo, Integer orderItem, int invoiceQty, RcvOrderStatusEnum statusEnum) throws OpsException;

    int updateStatus(String orderNo, Integer orderItem, RcvOrderStatusEnum status) throws OpsException;

    int updateStatusToInit(String orderId, String orderItem) throws OpsException;

    int updateStatusToCancel(String orderId, String orderItem) throws OpsException;

    int updateEstimatedDeliveryDay(String orderId, Integer orderItem, Date date) throws OpsException;
}
