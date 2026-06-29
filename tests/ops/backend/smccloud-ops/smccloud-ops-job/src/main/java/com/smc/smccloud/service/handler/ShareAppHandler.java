package com.smc.smccloud.service.handler;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.service.*;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/1/21 14:23
 * @Descripton TODO
 */

@Component
@Slf4j
public class ShareAppHandler {

    @Resource
    private SampleOrderApplyFeignApi sampleOrderApplyFeignApi;
    @Resource
    private StockAssemblyFeignApi stockAssemblyFeignApi;
    @Resource
    private ConsignmentStockFeignApi consignmentStockFeignApi;
    @Resource
    private PreStockFeignApi preStockFeignApi;
    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private PurchaseModifyApplyFeignApi purchaseModifyApplyFeignApi;

    public void init() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    public void destroy() {
        ThreadLocalMapUtil.clear();
    }

    /**
     * 物流发货数据自动生成样品结算数据
     */
    @XxlJob(value = "autoGenerateSampleBalData", init = "init", destroy = "destroy")
    public void autoGenerateSampleBalData() {

        XxlJobHelper.log("==> 进入autoGenerateSampleBalData执行器 => 物流发货数据自动生成样品结算数据");
        ResultVo<String> stringResultVo = sampleOrderApplyFeignApi.autoGenerateSampleBalData();
        if (!stringResultVo.isSuccess()) {
            XxlJobHelper.log("【autoGenerateSampleBalData】 执行失败 ==> {}", stringResultVo.getMessage());
            XxlJobHelper.handleFail("【autoGenerateSampleBalData】 执行失败");
        }

        XxlJobHelper.handleSuccess(stringResultVo.getMessage());
    }

    /**
     * 样品自动结转 (待结转 -> 待转出)
     */
    @XxlJob(value = "autoOrderCarryTurn", init = "init", destroy = "destroy")
    public void autoOrderCarryTurn() {
        XxlJobHelper.log("开始执行:");
        ResultVo<String> resultVo = sampleOrderApplyFeignApi.autoOrderCarryTurn();
        XxlJobHelper.log(resultVo.getMessage());
        if (resultVo.isSuccess()) {
            XxlJobHelper.handleSuccess(resultVo.getMessage());
        } else {
            XxlJobHelper.handleFail(resultVo.getMessage());
        }
    }

    /**
     * 待转出写入成本表
     */
    @XxlJob(value = "insertSalesExp", init = "init", destroy = "destroy")
    public void insertSalesExp () {
        XxlJobHelper.log("开始执行: 执行结样品转自动写入成本");
        ResultVo<String> resultVo = sampleOrderApplyFeignApi.autoInsertSales();
        if (resultVo.isSuccess()) {
            if (resultVo.getData() != null && resultVo.getData().contains("未写入")) {
                XxlJobHelper.handleFail(resultVo.getData());
            } else {
                XxlJobHelper.log(resultVo.getData());
                XxlJobHelper.handleSuccess(resultVo.getMessage());
            }
        } else {
            XxlJobHelper.log(resultVo.getMessage());
            XxlJobHelper.handleFail(resultVo.getMessage());
        }

    }

    /**
     * 自动转销售开票 (待转出 -> 转销售开票)
     */
    @XxlJob(value = "autoToSalesInvoice", init = "init", destroy = "destroy")
    public void autoToSalesInvoice() {
        XxlJobHelper.log("开始执行: 自动转销售开票 (待转出 -> 转销售开票)");
        ResultVo<String> resultVo = sampleOrderApplyFeignApi.autoToSalesInvoice();
        if (resultVo.isSuccess()) {
            XxlJobHelper.log(resultVo.getData());
            XxlJobHelper.handleSuccess(resultVo.getMessage());
        } else {
            XxlJobHelper.log(resultVo.getMessage());
            XxlJobHelper.handleFail(resultVo.getMessage());
        }
    }

    /**
     * 自动录入组换申请成本数据
     */
    @XxlJob(value = "importAssemnlyCostData", init = "init", destroy = "destroy")
    public void importAssemnlyCostData() {
        XxlJobHelper.log("开始执行: 自动录入组换申请成本数据");
        ResultVo<String> importResult = stockAssemblyFeignApi.importAssemblyCostData();
        if (!importResult.isSuccess()) {
            XxlJobHelper.log("自动录入组换申请成本数据失败: {}", importResult.getMessage());
            XxlJobHelper.handleFail("自动录入组换申请成本数据失败");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 自动录入组换申请成本数据");
        }
    }

    /**
     * 自动生成样品订单
     */
    @XxlJob(value = "createOrderBySampleOrder", init = "init", destroy = "destroy")
    public void createOrderBySampleOrder() {
        RLock lock = redissonUtil.lock("ops:createOrderBySampleOrder:ing");

        XxlJobHelper.log("开始执行: 自动生成样品订单");
        ResultVo<String> resultVo = null;
        try {
            resultVo = sampleOrderApplyFeignApi.autoCreateOrerBySampleOrder();
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobHelper.log(e);
            XxlJobHelper.handleFail("自动生成样品订单失败");
            return;
        } finally {
            redissonUtil.unlock(lock);
        }
        XxlJobHelper.log(resultVo.getMessage());
    }

    /**
     * 根据出库订单反扣入库订单的剩余数量,按所有没有扣数的出库订单
     */
    @XxlJob(value = "calcImpOrderLeftQty", init = "init", destroy = "destroy")
    public void calcImpOrderLeftQty() {
        XxlJobHelper.log("开始执行: 反扣入库订单的剩余数量");
        consignmentStockFeignApi.calcImpOrderLeftQty();
        XxlJobHelper.handleSuccess("执行完毕");
    }

    /**
     * 自动执行先行在库拦截申请项的处理
     */
    @XxlJob(value = "autoProcessInterceptedApplyDetail", init = "init", destroy = "destroy")
    public void autoProcessInterceptedApplyDetail() {
        XxlJobHelper.log("开始执行: 自动执行先行在库拦截申请项的处理");
        ResultVo<String> resultVo = preStockFeignApi.autoProcessInterceptedApplyDetail();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("自动执行先行在库拦截申请项的处理失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("自动执行先行在库拦截申请项的处理失败");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 自动执行先行在库拦截申请项的处理");
        }
    }

    /**
     * 定时任务同步 importGPProductModel  调用sync_prodmast_gp存储过程
     */

    @XxlJob(value = "importGPProductModel", init = "init", destroy = "destroy")
    public void importGPProductModel() {
        XxlJobHelper.log("开始执行: 调用sync_prodmast_gp存储过程");
        productServiceFeignApi.importGPProductModel();
        XxlJobHelper.handleSuccess("执行成功.");
    }

    @XxlJob(value = "syncReturnOrderToImpData", init = "init", destroy = "destroy")
    public void syncReturnOrderToImpData() {
        XxlJobHelper.log("开始执行: 同步已收货的退货订单到cs_impdata");
        ResultVo<String> resultVo = consignmentStockFeignApi.sysReturnOrderToImpData();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("同步已收货的退货订单到cs_impdata失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("同步已收货的退货订单到cs_impdata失败");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 同步完成");
        }
    }

    @XxlJob(value = "confirmMonthBalance", init = "init", destroy = "destroy")
    public void confirmMonthBalance() {
        XxlJobHelper.log("开始执行: 每月1号自动月结上个月的数据");
        Date date = DateUtil.getLastMonthFirstDate(new Date());
        ResultVo<String> resultVo = consignmentStockFeignApi.calcBalance(date);
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("月结失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("月结失败");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 月结完成");
        }
    }

    @XxlJob(value = "syncCsImpDataToCost", init = "init", destroy = "destroy")
    public void syncCsImpDataToCost() {
        XxlJobHelper.log("开始执行: 每月1号自动同步上个月的发货数据");
        ResultVo<String> resultVo = consignmentStockFeignApi.syncCsImpDataToCost();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("同步失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("同步失败");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 同步成功");
        }
    }

    @XxlJob(value = "importInvoiceData", init = "init", destroy = "destroy")
    public void importInvoiceData() {
        XxlJobHelper.log("开始执行: 同步开票状态");
        ResultVo<String> resultVo = consignmentStockFeignApi.impInvoiceResult();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("同步失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("同步失败");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 同步开票状态成功");
        }
    }

    /**
     * 自动执行先行在库拦截申请项的处理
     */
    @XxlJob(value = "callbackPreStockInfoToPortal", init = "init", destroy = "destroy")
    public void callbackPreStockInfoToPortal() {
        XxlJobHelper.log("开始执行: 自动回调先行在库信息给门户");
        ResultVo<String> resultVo = preStockFeignApi.autoCallbackPortal();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("自动回调先行在库信息给门户处理失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("自动回调先行在库信息给门户失败");
        } else {
            XxlJobHelper.log("先行在库信息回调门户完成: " + resultVo.getData());
            XxlJobHelper.handleSuccess("执行成功: 先行在库信息回调门户完成");
        }
    }

    /**
     * 同步退货状态至门户
     */
    @XxlJob(value = "dealReturnOrderToMH", init = "init", destroy = "destroy")
    public void dealReturnOrderToMH() {
        XxlJobHelper.log("开始执行: 同步退货状态至门户");
        ResultVo<String> resultVo = consignmentStockFeignApi.dealReturnOrderToMH();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("同步失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("同步失败");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 同步退货状态成功");
        }
    }


    /**
     * 同步退货单发票信息
     */
    @XxlJob(value = "syncReturnInvoiceInfo", init = "init", destroy = "destroy")
    public void syncReturnInvoiceInfo() {
        XxlJobHelper.log("开始执行: 同步退货单发票信息");
        ResultVo<String> resultVo = consignmentStockFeignApi.syncReturnInvoiceInfo();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("同步失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("同步失败");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 同步退货单发票信息成功");
        }
    }


    /**
     * 采购批量修改，定时调用处理
     */
    @XxlJob(value = "purchaseBatchModifyDeal", init = "init", destroy = "destroy")
    public void purchaseBatchModifyDeal() {
        XxlJobHelper.log("开始执行: 采购批量修改定时操作任务");
        ResultVo<String> resultVo = purchaseModifyApplyFeignApi.handlePurchaseModify();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("执行失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("执行失败");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 采购批量修改执行成功");
        }
    }

    /**
     * 先行在库决算自动调库
     */
    @XxlJob(value = "handleTransferByAuto", init = "init", destroy = "destroy")
    public void handleTransferByAuto() {
        XxlJobHelper.log("开始执行: 先行在库决算自动调拨。");
        ResultVo<String> resultVo = preStockFeignApi.handleTransferByAuto();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("先行在库决算自动调拨失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("先行在库决算自动调拨失败");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 先行在库决算自动调拨成功");
        }
    }

    /**
     * 先行在库预决算(新)
     */
    @XxlJob(value = "crePreOrderAccountData", init = "init", destroy = "destroy")
    public void crePreOrderAccountData() {
        XxlJobHelper.log("开始执行: 先行在库预决算(新)任务");
        ResultVo<String> resultVo = preStockFeignApi.crePreOrderAccountData();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("先行在库预决算(新)失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("先行在库预决算(新)失败");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 先行在库预决算(新)成功");
        }
    }

    /**
     * 延期中变延期待决算 autoHandleYqzPre
     */
    @XxlJob(value = "autoHandleYqzPre", init = "init", destroy = "destroy")
    public void autoHandleYqzPre() {
        XxlJobHelper.log("开始执行: 延期中变延期待决算");
        ResultVo<String> resultVo = preStockFeignApi.autoHandleYqzPre();
        if (!resultVo.isSuccess()) {
            XxlJobHelper.log("延期中变延期待决算失败: {}", resultVo.getMessage());
            XxlJobHelper.handleFail("延期中变延期待决算");
        } else {
            XxlJobHelper.handleSuccess("执行成功: 延期中变延期待决算成功");
        }
    }

}
