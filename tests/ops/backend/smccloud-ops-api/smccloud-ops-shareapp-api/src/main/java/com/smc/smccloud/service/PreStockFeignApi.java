package com.smc.smccloud.service;

import com.sales.ops.dto.prepareOrder.PrepareOrderTransferDto;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.service.hystrix.PreStockFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: B90034
 * Date: 2021-11-16 09:39
 * Description:
 */
@FeignClient(name = "shareapp-service",
        contextId = "prestock",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = PreStockFeignApiHystrix.class)
public interface PreStockFeignApi {

    /**
     * 生成先行在库申请
     *
     * @param createDto 先行在库申请信息
     * @return 申请Id applyId
     */
    @RequestMapping(value = "/shareapp/preStock/createApply", method = RequestMethod.POST)
    ResultVo<String> createPreStockApply(@RequestBody PreStockApplyDetailDTO createDto);

    /**
     * 根据申请号查询申请人，审批人信息
     *
     * @param applyNo
     * @return
     */
    @RequestMapping(value = "/shareapp/preStock/findPreStockApplyByNo", method = RequestMethod.GET)
    ResultVo<PreStockApplyDetailDTO> findPreStockApplyByNo(@RequestParam("applyNo") String applyNo);

    @RequestMapping(value = "/shareapp/preStock/findPreStockDetailByNo", method = RequestMethod.GET)
    ResultVo<List<PreStockDetailDTO>> findPreStockDetailByNo(@RequestParam("applyId") Long applyId, @RequestParam("modelNo") String modelNo);

    /**
     * 处理门户先行在库申请
     *
     * @param createDto 申请信息
     * @return 处理结果
     */
    @RequestMapping(value = "/shareapp/prestock/handleSMSPreStockApply", method = RequestMethod.POST)
    ResultVo<String> handleSMSPreStockApply(@RequestBody PreStockApplyDetailDTO createDto);

    /**
     * 自动执行先行在库拦截申请项的处理
     */
    @RequestMapping(value = "/shareapp/prestock/autoProcessInterceptedApplyDetail", method = RequestMethod.GET)
    ResultVo<String> autoProcessInterceptedApplyDetail();

    @RequestMapping(value = "/shareapp/prestock/getShikomiStockData", method = RequestMethod.POST)
    ResultVo<List<ShikomiCallbackDTO>> getShikomiStockData(@RequestBody List<String> applyNos);

    /**
     * 取消采购回调接口
     *
     * @param orderNo 采购单号
     * @return 回调处理结果
     */
    @RequestMapping(value = "/shareapp/prestock/cancelPurchase", method = RequestMethod.GET)
    ResultVo<String> purchaseOrderCancelHandle(@RequestParam("orderNo") String orderNo);

    /**
     * 自动回调采购单号给门户
     */
    @RequestMapping(value = "/shareapp/prestock/autoCallbackPortal", method = RequestMethod.GET)
    ResultVo<String> autoCallbackPortal();

    /**
     * 根据申请批次号回调门户处理结果
     *
     * @param batchNo 申请批次号
     * @return 回调门户结果
     */
    @RequestMapping(value = "/shareapp/prestock/callBackResultToPortalByBatchNo", method = RequestMethod.GET)
    ResultVo<String> callBackResultToPortalByBatchNo(@RequestParam("batchNo") String batchNo);

    /**
     * 先行在库决算自动调拨
     * @return
     */
    @RequestMapping(value = "/shareapp/prestock/handleTransferByAuto", method = RequestMethod.GET)
    ResultVo<String> handleTransferByAuto();

    /**
     * 先行在库预决算计算(新)
     */
    @RequestMapping(value = "/shareapp/prestock/crePreOrderAccountData", method = RequestMethod.GET)
    ResultVo<String> crePreOrderAccountData();


    @PostMapping("/shareapp/updatePreAccountDetail")
    ResultVo<String> updatePreAccountDetail(@RequestBody PreOrderAccountDetailVO info);

    @GetMapping("/shareapp/updatePreOrderAccountByInventoryId")
    ResultVo<String> updatePreOrderAccountByInventoryId(@RequestParam("inventoryId") Long inventoryId);

    @PostMapping("/shareapp/insertPreOrderAccountApplyDetailData")
    ResultVo<String> insertPreOrderAccountApplyDetailData(@RequestBody PreOrderAccountDetailVO preOrderAccountDetailVO);

    @GetMapping("/autoHandleYqzPre")
    ResultVo<String> autoHandleYqzPre();

    @PostMapping("/rejectPrepareOrderUpPreStockStatus")
    ResultVo<String> rejectPrepareOrderUpPreStockStatus(@RequestBody RejectPrepareOrderUpStockStatusDto dto);

    @PostMapping("/prepareOrderTransferWithPresotckResult")
    ResultVo<String> prepareOrderTransferWithPresotckResult(@RequestBody PrepareOrderTransferDto dto);

}
