package com.smc.smccloud.service;


import com.sales.ops.dto.purchase.ModifyPurchaseDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.Purchase.OpsPurchaseOrderDO;
import com.smc.smccloud.model.order.OrderIntercepterInfoVO;
import com.smc.smccloud.model.order.OrderModifyOrderChangeVO;
import com.smc.smccloud.model.order.QueryOrderIntercepterVO;
import com.smc.smccloud.model.order.ZdWithOrderModifyVO;
import com.smc.smccloud.model.order.orderEdit.OrderModifyUpInfoRequest;
import com.smc.smccloud.model.order.orderEdit.OrderModifyUpInfoVO;
import com.smc.smccloud.model.order.orderdel.SecondProcessWithUiVO;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/7/12 15:05
 * @Descripton TODO
 */
public interface SalesNotickTaskService {

    // 写入任务表
    ResultVo<String> execInsertOpsSalesNoticeTask(String paramJson);

    // 执行待执行的任务
    ResultVo<String> execCallInterface(String groupCode);

    // 执行任务的回调
    ResultVo<String> execCallBackInterface(String groupCode);


    // 回写任务表
    ResultVo<String> upTaskInfoByBatchNo(UpTaskInfoVO param);

    // 回调门户
    ResultVo<String> callBackInterface(OpsSalesCommonParamVO info);

    // 订单还原 (订单修改页面手动处理)
    ResultVo<OpsSalesCommonParamVO> orderChangeToInitStatus(OrderModifyOrderChangeVO info);

    // 订单还原 (自动处理)
    ResultVo<DealReturnOpsParamVO> autoCommonOrderChangeToInitStatus(OpsSalesCommonParamVO info);

    // 订单转订-> 订单转订&&变更后内容为“转订出库存”&&“转订出在途”
    ResultVo<OpsSalesCommonParamVO> zdOrderModifyHand(ZdWithOrderModifyVO info);

    // 是否可修改 获取订单变更信息
    ResultVo<List<OrderModifyUpInfoVO>> findOrderModifyUpInfo(OrderModifyUpInfoRequest info);

    // 战略在库申请
    ResultVo<String> createPreStockApply(OpsSalesCommonParamVO info);

    // 申请在库回调
    ResultVo<String> callBackCreatePreStockApply(OpsSalesCommonParamVO info);

    // 变更物料号
    ResultVo<DealReturnOpsParamVO> upCproductNo(OpsSalesCommonParamVO info);

    // 变更po号
    ResultVo<DealReturnOpsParamVO> upPurchaseNo(OpsSalesCommonParamVO info);

    // SHIKOMI残数不足维护
    ResultVo<DealReturnOpsParamVO> upShikomiWarnQty(OpsSalesCommonParamVO info);

    // 获取 变更运输方式/指定工厂出荷日 信息
    ResultVo<List<ModifyPurchaseDto>> getUpPurchaseTAndDInfo(OrderModifyUpInfoRequest info);

    // 信用拦截查询接口
    ResultVo<List<QueryOrderIntercepterVO>> queryOrderIntercepterInfo(OrderIntercepterInfoVO infoVO);

    // 写入样品结转清单
    ResultVo<DealReturnOpsParamVO> insertSampleBalApply(OpsSalesCommonParamVO info);

    // 测试接口
    ResultVo<String> testCallInterface(OpsSalesCommonParamVO parm);

    // 信用拦截放行接口
    ResultVo<DealReturnOpsParamVO> releaseOrder(OpsSalesCommonParamVO info);

    // 删单二次审批
    ResultVo<DealReturnOpsParamVO> commonDelOrder(OpsSalesCommonParamVO info);

    // 删单二次审批回调门户
    ResultVo<String> callBackInterfaceWithDelOrder(OpsSalesCommonParamVO info);

    // 前端UI二次审批按钮触发事件
    ResultVo<String> secondProcessWithUi(SecondProcessWithUiVO info);

    // 采购货期提醒 写入中间表给门户
    ResultVo<String> insertOpsRemindWithDelay(OpsSalesCommonParamVO info);

    ResultVo<String> insertRemind(String batchNo,String remindType,String remindContent);

    // 批量修改货期
    ResultVo<DealReturnOpsParamVO> commonbatchUpDlvDate(OpsSalesCommonParamVO info);

    // inqA催促
    ResultVo<DealReturnOpsParamVO> inqAadd(OpsSalesCommonParamVO info);

    ResultVo<DealReturnOpsParamVO> inqAaddNew(OpsSalesCommonParamVO info);

    // inqB问询
    ResultVo<DealReturnOpsParamVO> inqBadd(OpsSalesCommonParamVO info);

    // 13191bug 订单修改删单优化 增加定时任务 若订单状态为已发货或者已开票，将该删单申请自动否决
    ResultVo<String> autoHandNotCancelData();

    // 先行在库预决算门户申请处理
    ResultVo<String> preAccountOrderHandApply(OpsSalesCommonParamVO info);

    // 获取采购信息
    OpsPurchaseOrderDO getPurchaseOrderInfo(String purchaseNo);

    // 更新门户推送shikomi的审批信息
    ResultVo<String> updateShikomiApprovalInfo(OpsSalesCommonParamVO info);

    // 通知发货计划创建
    ResultVo<DealReturnOpsParamVO> createNotifyShipmentPlan(OpsSalesCommonParamVO info);

}
