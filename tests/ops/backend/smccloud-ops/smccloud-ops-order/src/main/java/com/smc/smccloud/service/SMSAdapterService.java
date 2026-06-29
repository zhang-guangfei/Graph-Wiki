package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInqbCodeConfig;
import com.sales.ops.db.entity.OpsInquiryCodeConfig;
import com.sales.ops.dto.inqb.InqbApplyVerifyReurn;
import com.sales.ops.dto.inqb.InqbQueryRequestParam;
import com.sales.ops.dto.inquiry.InquiryApplyVerifyReurn;
import com.sales.ops.dto.inquiry.InquiryCodeConfigBySuppily;
import com.sales.ops.dto.inquiry.InquiryOrderVerifyReurn;
import com.sales.ops.dto.inquiry.InquiryWorkdayCondition;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.adapter.order.*;
import com.smc.smccloud.model.adapter.order.status.OrderItemStatusVO;
import com.smc.smccloud.model.adapter.orderEnum.StatusEnumInfo;
import com.smc.smccloud.model.deliveryType.OpsDeliveryTypeDO;
import com.smc.smccloud.model.order.*;
import com.smc.smccloud.model.ordermodify.OrderDeliveryModifyInfo;
import com.smc.smccloud.model.orderstate.HandDelOrderStatusVO;
import com.smc.smccloud.model.receiveorder.RcvDetailVO;

import java.util.List;
import java.util.Map;


/**
 * @Author lyc
 * @Date 2022/3/9 17:01
 * @Descripton TODO
 */
/**
 *   与门户对接接口 (适配器)
 */
public interface SMSAdapterService {


    /**
     * 代理店接单查询
     */
    ResultVo<PageInfo<SMSOrder>> queryOrderForAgent(SMSSearchListInfo condition, int pageNumber, int pageSize);

    /**
     * 代理店接单查询导出
     */
    ResultVo<List<SMSOrder>> exportRcvOrderForAgent(SMSSearchListInfo condition);



    /**
     * 代理店 订单数据[行业]接口查询
     */
    ResultVo<PageInfo<IndCodeEntity>> queryRcvOrderWithCustomerByIndCodeForAgent (SMSSearchListInfo condition, int pageNumber, int pageSize);


    /**
     * 代理店 订单数据[行业]接口查询导出 rcvdetail,ops_customer
     */
    ResultVo<List<IndCodeEntity>> exportIndCodeDataForAgent(SMSSearchListInfo condition);

    /**
     * 接单查询详情
     * @param orderNo 主订单号
     * @return
     */
    ResultVo<Order> detail(String orderNo);

    /**
     * 获取接单状态
     * @return
     */
    ResultVo<List<StatusEnumInfo>> orderStatus();

    /**
     * 查询订单处理状态
     * @param orderNo 订单号
     * @return List
     */
    ResultVo<List<OrderItem>> getOrderItemStatus(String orderNo);

    // 接单查询追踪
    ResultVo<OrderSchedule> orderSchedule(String orderNo);


    /**
     * 删单申请列表2
     * @param condition
     * @param page
     * @return
     */
    ResultVo<PageInfo<OrderDeleteReq>> findDelOrder2(OrderDeleteCondition condition, Page page);

    ResultVo<PageInfo<OrderDeleteReq>> findDelOrderForAgent(OrderDeleteCondition condition, Page page);

    /**
     * 交货期查询 (联查客户表)
     * @param request
     * @param pageNumber
     * @param pageSize
     * @return
     */
    ResultVo<PageInfo<PurchaseOrderBean>> listOrderStateWithCustomer(PurchaseOrderCondition request, int pageNumber, int pageSize);

    ResultVo<PageInfo<PurchaseOrderBean>> listOrderStateWithCustomerForAgent(PurchaseOrderCondition request, int pageNumber, int pageSize);

    //  交货期导出
    ResultVo<List<PurchaseOrderBean>> exportListOrderState(PurchaseOrderCondition request);

    //  代理店交货期导出
    ResultVo<List<PurchaseOrderBean>> exportListOrderStateForAgent(PurchaseOrderCondition request);

    // 获取订单货期状态
    ResultVo<List<StatusEnumInfo>> getOrderStateStatus();

    // 主单号
    List<OrderItem> getOrderItemByOrerNo(String orderNo);

    /**
     * 变更订单信息(交货地点.出货方式.承运商.费用承担方.集约方式.操作担当.操作时间)
     */
    ResultVo<String> updateOrderDeliveryInfo(OrderDeliveryModifyInfo info);

    // 订单变更
    ResultVo<Integer> updateOrderDetail(RcvDetailVO rcvDetailVO);

    ResultVo<List<OrderItemStatusVO>> findItemsListStatus(List<String> orderNoList);

    /**
     * 获取发票信息
     * @param orderNoList
     * @return
     */
    List<SalesInvoice> invoiceList(List<String> orderNoList);

    /**
     * 获取发货信息
     * @param orderNoList
     * @return
     */
    List<OrderDeliveryInfo> findDeliveryInfo(List<String> orderNoList);

    /**
     * 根据批量10位单号获取子项信息
     * @param orderNoList
     * @param calCanPress
     * @return
     */
    List<OrderItemData> getItemList(List<String> orderNoList, boolean calCanPress);


    // 接单查询 -> 批量变更交货期
    ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpdateDlvDate(List<OrderDelivery> orderDeliveryDate);


    // 接单查询 -> 订单修改(子单行变更)
    ResultVo<OrderEditResult> orderEdit(OrderEditVO orderDeliveryDate);

    ResultVo<List<DlvEntireVO>> getDlvEntireStatusByFullOrderNo(List<String> orderNoList);


    ResultVo<String> handDelOrderStatusToMenHu(HandDelOrderStatusVO handDelOrderStatusVO);


    // 根据发货方式获取可变更的发货方式
    ResultVo<List<OpsDeliveryTypeDO>> findDeliveryType(String dlvType);


    ResultVo<AmountAndEdiscountVO> quertTotalAmountAndEdiscount(SMSSearchListInfo condition);



    /**
     * 门户调用，催促模块校验接口
     * @param orderNoList
     * @return
     */
    ResultVo<List<InquiryApplyVerifyReurn>> getInquiryVerify(List<String> orderNoList);

    ResultVo<List<InquiryOrderVerifyReurn>> inqAOrderVerify(List<String> orderNoList);

    /**
     * 新增是否节假日接口，提供给门户
     * @param info
     * @return
     */
    ResultVo<Boolean> getWorkday(InquiryWorkdayCondition info);

    /**
     * bug14109 提供给门户不用供应商返回查询原因的清单
     */
    ResultVo<List<OpsInquiryCodeConfig>> getInquiryReasonBySuppily(String suppily);

    public ResultVo<List<InquiryCodeConfigBySuppily>> getInquiryReasonBySuppilyBatch(List<String> suppilyList);


    ResultVo<List<OpsInqbCodeConfig>> getInqbSendReason();
    /**
     * 提供给门户查询inqb可用的清单
     * @param inqbQueryRequestParam
     * @return
     */
    public ResultVo<List<InqbQueryRequestParam>>findInqbUsageList(List<InqbQueryRequestParam> inqbQueryRequestParam);


    /**
     * 通用接单查询子项返回能否变更的接口
     *   能否可更改货期
     *   最晚客户货期
     *   能否可以变更普通特发
     *   能否变更承运商
     *   能否变更收货地址
     *   能否子项特发
     */
    ResultVo<List<UpOrderInfoVO>> canUpOrderInfo(List<String> fullOrderNos);


    /**
     * 返回能否变更主单信息
     * 是否可更改出货方式
     * @param mainOrderNos
     * @return
     */
    ResultVo<List<UpOrderMasterInfoVO>> canUpMasterOrderInfo(List<String> mainOrderNos);

    /**
            * 门户催促新增校验接口，判断传输参数的是否可催促，初始校验
     * @param inqbApplyVerify
     * @return
             */
    ResultVo<InqbApplyVerifyReurn> inqbAddValid(InqbApplyVerifyReurn inqbApplyVerify);
}
