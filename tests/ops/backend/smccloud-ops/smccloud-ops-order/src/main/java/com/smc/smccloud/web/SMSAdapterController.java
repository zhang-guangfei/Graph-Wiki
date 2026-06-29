package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInqbCodeConfig;
import com.sales.ops.db.entity.OpsInquiryCodeConfig;
import com.sales.ops.dto.inqb.InqbApplyVerifyReurn;
import com.sales.ops.dto.inqb.InqbQueryRequestParam;
import com.sales.ops.dto.inquiry.InquiryApplyVerifyReurn;
import com.sales.ops.dto.inquiry.InquiryCodeConfigBySuppily;
import com.sales.ops.dto.inquiry.InquiryOrderVerifyReurn;
import com.sales.ops.dto.inquiry.InquiryWorkdayCondition;
import com.sales.ops.dto.purchase.ModifyPurchaseDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.adapter.order.*;
import com.smc.smccloud.model.adapter.order.status.OrderItemStatusVO;
import com.smc.smccloud.model.adapter.orderEnum.StatusEnumInfo;
import com.smc.smccloud.model.deliveryType.OpsDeliveryTypeDO;
import com.smc.smccloud.model.expdetail.ExpdetailRequest;
import com.smc.smccloud.model.expdetail.ExpdetailSalesVO;
import com.smc.smccloud.model.expdetail.ExpdetailVO;
import com.smc.smccloud.model.order.*;
import com.smc.smccloud.model.order.orderEdit.OrderModifyUpInfoRequest;
import com.smc.smccloud.model.order.orderEdit.OrderModifyUpInfoVO;
import com.smc.smccloud.model.orderstate.HandDelOrderStatusVO;
import com.smc.smccloud.service.ExpdetailService;
import com.smc.smccloud.service.SMSAdapterService;
import com.smc.smccloud.service.SMSOrderService;
import com.smc.smccloud.service.SalesNotickTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author lyc
 * @Date 2022/3/9 17:03
 * @Descripton TODO
 */

@RestController
@Slf4j
@RequestMapping("/order/rcvorder")
public class SMSAdapterController {

    @Resource
    private SMSAdapterService smsAdapterService;
    @Resource
    private ExpdetailService expdetailService;
    @Resource
    private SalesNotickTaskService salesNotickTaskService;

    @Resource
    private SMSOrderService smsOrderService;


    /**
     * 订单货期状态查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/listOrderState", method = RequestMethod.POST)
    public ResultVo<PageInfo<PurchaseOrderBean>> listOrderState(@RequestBody PurchaseOrderCondition request,
                                                                @RequestParam("pageNumber") int pageNumber,
                                                                @RequestParam("pageSize") int pageSize) {
        if(pageSize> 10000){
            pageSize = 10000;
        }
        return smsAdapterService.listOrderStateWithCustomer(request, pageNumber,pageSize);
    }

    /**
     * 订单货期状态查询导出
     * @param request
     * @return
     */
    @RequestMapping(value = "/exportListOrderState", method = RequestMethod.POST)
    public ResultVo<List<PurchaseOrderBean>> exportListOrderState(@RequestBody PurchaseOrderCondition request) {
        return smsAdapterService.exportListOrderState(request);
    }

    /**
     * 获取订单货期状态列表
     * @return
     */
    @RequestMapping(value = "/listOrderStatus", method = RequestMethod.GET)
    public ResultVo<List<StatusEnumInfo>> listOrderState(){
        return smsAdapterService.getOrderStateStatus();
    }


    /**
     * 获取删单数据
     * 删单申请列表
     */
    @RequestMapping(value = "/findDelOrder", method = RequestMethod.POST)
    public ResultVo<PageInfo<OrderDeleteReq>> findDelOrder(@RequestBody OrderDeleteCondition condition, Page page) {
        return smsAdapterService.findDelOrder2(condition, page);
    }


    /**
     * 订单状态
     * 根据多个十位订单号获取多个子单可删单等状态信息
     * @param orderNoList
     * @return
     */
    @RequestMapping(value = "/findItemsListStatus", method = RequestMethod.POST)
    public ResultVo<List<OrderItemStatusVO>> findItemsListStatus(@RequestBody List<String> orderNoList) {
        return smsAdapterService.findItemsListStatus(orderNoList);
    }

    @RequestMapping(value = "/invoiceList", method = RequestMethod.POST)
    public ResultVo<List<SalesInvoice>> invoiceList(@RequestBody List<String> orderNoList) {
        return ResultVo.success(smsAdapterService.invoiceList(orderNoList));
    }

    @RequestMapping(value = "/findDeliveryInfo", method = RequestMethod.POST)
    public ResultVo<List<OrderDeliveryInfo>> findDeliveryInfo(@RequestBody List<String> orderNoList) {
        return ResultVo.success(smsAdapterService.findDeliveryInfo(orderNoList));
    }

    // 根据多个10位单号获取多个子订单可删单等状态信息
    @PostMapping("/findItemsList")
    public ResultVo<List<OrderItemData>> findItemsList(@RequestBody List<String> orderNoList) {
        List<OrderItemData> list = smsAdapterService.getItemList(orderNoList, true);
        return ResultVo.success(list);
    }

    /**
     * 发货查询导出
     */
    @RequestMapping(value = "/exportExpdetail", method = RequestMethod.POST)
    public ResultVo<List<ExpdetailVO>> exportExpdetail(@RequestBody ExpdetailRequest request) {
        return expdetailService.exportExpdetail(request);
    }


    //    /**
//     * 订单导出 (门户接口)
//     */
//    @RequestMapping(value = "/findOrderExport", method = RequestMethod.POST)
//    public ResultVo<List<OrderExport>> findOrderExport(@RequestBody SMSSearchListInfo condition){
//        return smsAdapterService.findOrderExport2(condition);
//    }

    /**
     * 门户接单查询 -> 变更交货期(批量)
     * @param orderDeliveryDate
     * @return
     */
    @RequestMapping(value = "/batchUpdateDlvDate", method = RequestMethod.POST)
    public ResultVo<Map<String, UpdateOrderInfoResultVO>> batchUpdateDlvDate(@RequestBody List<OrderDelivery> orderDeliveryDate) {
        return smsAdapterService.batchUpdateDlvDate(orderDeliveryDate);
    }

    /**
     * 门户接单查询 -> 订单修改()
     * @param orderDeliveryDate
     * @return
     */
    @RequestMapping(value = "/orderEdit", method = RequestMethod.POST)
    public ResultVo<OrderEditResult> orderEdit(@RequestBody OrderEditVO orderDeliveryDate) {
        return smsAdapterService.orderEdit(orderDeliveryDate);
    }

    /**
     * 根据子单号获取是否随发分批
     */
    @RequestMapping(value = "/getDlvEntireStatusByFullOrderNo", method = RequestMethod.POST)
    public ResultVo<List<DlvEntireVO>> getDlvEntireStatusByFullOrderNo(@RequestBody List<String> orderNoList)
    {
        return smsAdapterService.getDlvEntireStatusByFullOrderNo(orderNoList);
    }

//    /**
//     * 手动回传删单状态给门户
//     */
//    @PostMapping("/handDelOrderStatusToMenHu")
//    public ResultVo<String> handDelOrderStatusToMenHu(List<String> orderNos){
//        return smsAdapterService.handDelOrderStatusToMenHu(orderNos);
//    }

    /**
     * 手动回传删单状态给门户
     */
    @PostMapping("/handDelOrderStatusToMenHu")
    public ResultVo<String> handDelOrderStatusToMenHu(@RequestBody HandDelOrderStatusVO handDelOrderStatusVO) {
        return smsAdapterService.handDelOrderStatusToMenHu(handDelOrderStatusVO);
    }

    // 查询订单修改是否可变更的信息
    @PostMapping("/findOrderModifyUpInfo")
    public ResultVo<List<OrderModifyUpInfoVO>> findOrderModifyUpInfo(@RequestBody OrderModifyUpInfoRequest info) {
        return salesNotickTaskService.findOrderModifyUpInfo(info);
    }

    // 查询是否可变更采购单信息
    @PostMapping("/getUpPurchaseTAndDInfo")
    public ResultVo<List<ModifyPurchaseDto>> getUpPurchaseTAndDInfo(@RequestBody OrderModifyUpInfoRequest info){
        return salesNotickTaskService.getUpPurchaseTAndDInfo(info);
    }

    // leftteam接口 发货数据查询
    @PostMapping("/listOrderExpdetailWithSales")
    public ResultVo<List<ExpdetailSalesVO>> listOrderExpdetailWithSales(@RequestBody OrderDetailDTO detailDTO) {
        return smsOrderService.listOrderExpdetailWithSales(detailDTO);
    }


    /**
     * bug 12642,新增催促校验接口，调用delivery模块的校验信息
     */
    @PostMapping("/inquiryVerify")
    public ResultVo<List<InquiryApplyVerifyReurn>> inquiryVerify(@RequestBody List<String> orderNoList) {
        return smsAdapterService.getInquiryVerify(orderNoList);
    }

    // 订单催促校验接口 bugid 14912 C14717
    @PostMapping("/inqAOrderVerify")
    public ResultVo<List<InquiryOrderVerifyReurn>> inqAOrderVerify(@RequestBody List<String> orderNoList) {
        return smsAdapterService.inqAOrderVerify(orderNoList);
    }

    /**
     * bug 12642,催促模块，新增是否节假日接口，提供给门户
     */
    @RequestMapping(value = "/isWorkday",method = RequestMethod.POST)
    public ResultVo<Boolean> getWorkday(@RequestBody InquiryWorkdayCondition info){
        return smsAdapterService.getWorkday(info);
    }

    /**
     * bug14109 提供给门户不用供应商返回查询原因的清单
     *
     */
    @RequestMapping(value = "/getReasonBySuppily",method = RequestMethod.POST)
    public ResultVo<List<OpsInquiryCodeConfig>> getInquiryReasonBySuppily(@RequestBody String suppily){
        return smsAdapterService.getInquiryReasonBySuppily(suppily);
    }

    /**
     * bug14109 提供给门户不用供应商返回查询原因的清单
     *
     */
    @RequestMapping(value = "/getReasonBySuppilyBatch",method = RequestMethod.POST)
    public ResultVo<List<InquiryCodeConfigBySuppily>> getInquiryReasonBySuppilyBatch(@RequestBody List<String> suppilyList){
        return smsAdapterService.getInquiryReasonBySuppilyBatch(suppilyList);
    }

    /**
     * 提供给门户INQB，原因分类码查询信息
     */
    @RequestMapping(value = "/getInqbSendReason",method = RequestMethod.POST)
    public ResultVo<List<OpsInqbCodeConfig>> getInqbSendReason(){
        return smsAdapterService.getInqbSendReason();
    }

    /**
     * 提供给门户查询inqb可用的清单
     * @param inqbQueryRequestParam
     * @return
     */
    @RequestMapping(value = "/getInqbUsageList",method = RequestMethod.POST)
    public ResultVo<List<InqbQueryRequestParam>> getInqbUsageList(@RequestBody List<InqbQueryRequestParam> inqbQueryRequestParam){
        return smsAdapterService.findInqbUsageList(inqbQueryRequestParam);
    }

    /**
     * 门户催促新增校验接口，判断传输参数的是否可催促，初始校验
     * @param inqbApplyVerify
     * @return
     */
    @RequestMapping(value = "/inqbAddValid",method = RequestMethod.POST)
    public ResultVo<InqbApplyVerifyReurn> inqbAddValid(@RequestBody InqbApplyVerifyReurn inqbApplyVerify){
        return smsAdapterService.inqbAddValid(inqbApplyVerify);
    }

}
