package com.sales.ops.web.controller;


import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsPrepareOrder;
import com.sales.ops.db.entity.OpsPrepareOrderAccount;
import com.sales.ops.dto.prepareOrder.*;
import com.sales.ops.service.purchase.PrepareOrderService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/prepareOrder")
public class PrepareOrderController {

    @Resource
    private PrepareOrderService prepareOrderService;

    @PostMapping("/prepareOrderVerify")
    public ResultVo<Map<String, Boolean>> prepareOrderVerify(@RequestBody List<String> modelNos) {
        return prepareOrderService.prepareOrderVerify(modelNos);
    }

    @PostMapping("/prepareOrderCreate")
    public ResultVo<String> prepareOrderCreate(@RequestBody List<OpsPrepareOrder> opsPrepareOrders) {
        return prepareOrderService.prepareOrderCreate(opsPrepareOrders);
    }

    /**
     * 向PSI推送准备订单
     */
    @GetMapping("/pushPrepareOrderToPSI")
    public ResultVo<String> pushPrepareOrderToPSI() {
        return prepareOrderService.pushPrepareOrderToPSI();
    }

    /**
     * 准备订单预处理
     * @return
     */
    @GetMapping("/prepareOrderPreHandle")
    public ResultVo<String> prepareOrderPreHandle() {
        return prepareOrderService.prepareOrderPreHandle();
    }

    /**
     * 从PSI拉取返信信息
     */
    @GetMapping("/pullPrepareOrderDeliveryInfoFromPsi")
    public ResultVo<String> pullPrepareOrderDeliveryInfoFromPsi() {
        return prepareOrderService.pullPrepareOrderDeliveryInfoFromPsi();
    }

    /**
     * (UI)准备订单管理查询
     */
    @PostMapping("/queryPrepareOrderList")
    public ResultVo<PageInfo<OpsPrepareOrderVO>> queryPrepareOrderList(@RequestBody PrepareOrderQueryParam param) {
        return prepareOrderService.queryPrepareOrderList(param);
    }

    /**
     * 导出准备订单
     */
    @RequestMapping(value = "/exportPrepareOrder", method = RequestMethod.POST)
    public void exportPrepareOrder(@RequestBody PrepareOrderQueryParam param, HttpServletResponse response) {
        prepareOrderService.exportPrepareOrder(param, response);
    }

    @PostMapping("/editPrepareOrderAvailableUser")
    public ResultVo<String> editPrepareOrderAvailableUser(@RequestBody EditPrepareOrderAvailableUserDto editDto) {
        return prepareOrderService.editPrepareOrderAvailableUser(editDto);
    }

    /**
     * 根据订单号获取bom拆分信息
     * @param orderFno
     * @return
     */
    @GetMapping("/getPrepareOrderBomDetail")
    public ResultVo<List<OpsPrepareOrderBomDetailDto>> getPrepareOrderBomDetail(@RequestParam("orderFno") String orderFno) {
        return prepareOrderService.getPrepareOrderBomDetail(orderFno);
    }

    /**
     * 根据订单号获取核销信息
     * @param orderFno
     * @return
     */
    @GetMapping("/getPrepareOrderVerifyInfo")
    public ResultVo<List<OpsPrepareOrderReconciliationDto>> getPrepareOrderVerifyInfo(@RequestParam("orderFno") String orderFno) {
        return prepareOrderService.getPrepareOrderVerifyInfo(orderFno);
    }

    /**
     * 根据订单号获取决算信息
     * @param orderFno
     * @return
     */
    @GetMapping("/getPrepareOrderAccountInfo")
    public ResultVo<List<OpsPrepareOrderAccount>> getPrepareOrderAccountInfo(@RequestParam("orderFno") String orderFno) {
        return prepareOrderService.getPrepareOrderAccountInfo(orderFno);
    }

    /**
     * 获取可使用的准备订单清单
     */
    @PostMapping("/getAvailablePrepareOrderList")
    public ResultVo<OpsPrepareOrderVO> getAvailablePrepareOrderList(@RequestBody CanUsePrepareOrderParam  param) {
        return prepareOrderService.getAvailablePrepareOrderList(param);
    }

    /**
     * 根据订单号获取清算结果
     * @param orderFno
     * @return
     */
    @GetMapping("/getPrepareOrderLiquidationInfo")
    public ResultVo<List<OpsPrepareOrderLiquidationDto>> getPrepareOrderLiquidationInfo(@RequestParam("orderFno") String orderFno) {
        return prepareOrderService.getPrepareOrderLiquidationInfo(orderFno);
    }

    /**
     * 根据订单号获取准备订单信息
     */
    @GetMapping("/getPrepareOrderInfo")
    public ResultVo<OpsPrepareOrder> getPrepareOrderInfo(@RequestParam("orderFno") String orderFno) {
        return prepareOrderService.getPrepareOrderInfo(orderFno);
    }

    @PostMapping("/updatePreQty")
    public ResultVo<String> updatePreQty(@RequestBody PrepareOrderPreQtyUpdateDto dto) {
        return prepareOrderService.updatePreQty(dto);
    }

    @PostMapping("/prepareOrderTransfer")
    public ResultVo<String> prepareOrderTransfer(@RequestBody PrepareOrderTransferDto dto) {
        return prepareOrderService.prepareOrderTransfer(dto);
    }

    @GetMapping("/getDlvDays")
    public ResultVo<String> getDlvDays() {
        return prepareOrderService.getDlvDays();
    }
    @GetMapping("/updateDlvDays")
    public ResultVo<String> updateDlvDays(@RequestParam("dlvDays") int dlvDays) {
        return prepareOrderService.updateDlvDays(dlvDays);
    }

}
