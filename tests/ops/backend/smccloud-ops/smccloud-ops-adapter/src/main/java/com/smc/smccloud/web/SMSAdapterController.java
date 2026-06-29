package com.smc.smccloud.web;

import com.sales.ops.db.entity.OpsPoTranstype;
import com.sales.ops.dto.eta.ETAPageDto;
import com.sales.ops.dto.eta.FindETADataDto;
import com.sales.ops.dto.po.core.TransTypeParam;
import com.sales.ops.dto.purchase.BaseDataDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.purchase.PurchaseTransFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.UploadFileByUIVO;
import com.smc.smccloud.model.adapter.order.UpOrderInfoVO;
import com.smc.smccloud.model.adapter.order.UpOrderMasterInfoVO;
import com.smc.smccloud.model.delivery.WarehouseSendDateVO;
import com.smc.smccloud.model.invoice.SalesInvoiceRequest;
import com.smc.smccloud.model.order.JudgeShipParams;
import com.smc.smccloud.model.order.SendProcessOrderWithRiskLevel;
import com.smc.smccloud.service.AdapterService;
import com.smc.smccloud.service.ReceiveOrderServiceFeignApi;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author lyc
 * @Date 2023/8/11 13:56
 * @Descripton TODO
 */
@RequestMapping("/adapter")
@RestController
public class SMSAdapterController {

    @Resource
    private AdapterService adapterService;

    @Resource
    private ReceiveOrderServiceFeignApi receiveOrderServiceFeignApi;

    @Resource
    private PurchaseTransFeignApi purchaseTransFeignApi;

    @RequestMapping(value = "/getTransIds", method = RequestMethod.POST)
    public ResultVo<List<OpsPoTranstype>> getTransIds(@RequestBody TransTypeParam typeParam) {
        CommonResult<List<OpsPoTranstype>> transIds = purchaseTransFeignApi.getTransIds(typeParam);
        if (!transIds.isSuccess()) {
            return ResultVo.failure(transIds.getMessage());
        }
        return ResultVo.success(transIds.getData());
    }

    /**
     * 反算物流发货日
     */
    @RequestMapping(value = "/delivery/findWarehouseSendDateByOrderNo", method = RequestMethod.POST)
    public ResultVo<List<WarehouseSendDateVO>> findWarehouseSendDateByOrderNo(@RequestBody List<WarehouseSendDateVO> info) {
        return adapterService.getWarehouseSendDateByOrderNo(info);
    }

    // 二次审批-获取风险等级
    @PostMapping("/findSendProcessRiskLevel")
    public ResultVo<List<SendProcessOrderWithRiskLevel>> findSendProcessRiskLevel(@RequestBody SalesInvoiceRequest request) {
        return adapterService.findSendProcessRiskLevel(request);
    }

    @GetMapping("/getAllSupplier")
    public ResultVo<List<BaseDataDto>> getAllSupplier() {
        return adapterService.getAllSupplier();
    }

    //查询参考货期
    @PostMapping("/findEtaAPI")
    public ResultVo<ETAPageDto> findEtaAPI(@RequestBody List<FindETADataDto> dataList) {
        return adapterService.findEtaAPI(dataList);
    }

    /**
     * 运单号查询接口
     */
    @GetMapping("/getOrderRoute")
    public ResultVo getOrderRoute(@RequestParam("expressNo") String expressNo) {
        return adapterService.getOrderRoute(expressNo);
    }

    /**
     * 通用接单查询子项返回能否变更的接口
     *   能否可更改货期
     *   最晚客户货期
     *   能否可以变更普通特发
     *   能否变更承运商
     *   能否变更收货地址
     *   能否子项特发
     *   能否组装指令(现在默认否)
     */
    @PostMapping("/canUpOrderInfo")
    public ResultVo<List<UpOrderInfoVO>> canUpOrderInfo(@RequestBody List<String> fullOrderNos) {
        return receiveOrderServiceFeignApi.canUpOrderInfo(fullOrderNos);
    }


    /**
     * 返回能否变更主单信息
     * 是否可更改出货方式
     * @param mainOrderNos
     * @return
     */
    @PostMapping("/canUpMasterOrderInfo")
    public ResultVo<List<UpOrderMasterInfoVO>> canUpMasterOrderInfo(@RequestBody List<String> mainOrderNos) {
        return receiveOrderServiceFeignApi.canUpMasterOrderInfo(mainOrderNos);
    }


    @PostMapping("/judgeShip")
    ResultVo<List<JudgeShipParams>> judgeShip(@RequestBody List<JudgeShipParams> params) {
        return adapterService.judgeShipNew(params);
    }

}
