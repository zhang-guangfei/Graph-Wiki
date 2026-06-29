package com.sales.ops.web.controller.wmOrder;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.sales.ops.aop.annotation.Log;
import com.sales.ops.db.entity.ImpInvoiceEventLog;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.purchase.OpsPurchaseStatusToWMDto;
import com.sales.ops.dto.purchase.PoToWmDto;
import com.sales.ops.dto.purchase.PurchaseUpdateInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.LogUtil;
import com.sales.ops.service.dispatch.PoDispatcherService;
import com.sales.ops.service.inventory.WmDispatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("po调用wm服务")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class PoDispatcherController {

    private PoDispatcherService poDispatcherService;
    private WmDispatchService wmDispatchService;

    @ApiOperation("采购转订")
    @PostMapping("/po/reset")
    public CommonResult<String> resetForPo(@RequestBody PoToWmDto dto) {
        log.info("/order/po/reset params:{}", JSONUtil.toJsonPrettyStr(dto));
        try {
            poDispatcherService.resetForPo(dto);
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("/order/po/reset params:{},error:{}", JSONUtil.toJsonPrettyStr(dto), ex);
            ex.printStackTrace();
            return CommonResult.failure(ex.getMessage());
        }
    }

    @ApiOperation("请购预处理")
    @PostMapping("/po/preprocess")
    public CommonResult<String> preprocessForRequestPo(@RequestBody List<OpsRequestpurchase> list) {
        log.info("/order/po/preprocess params:{}", JSONUtil.toJsonPrettyStr(list));
        try {
            poDispatcherService.preprocessForRequestPo(list);
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("/order/po/preprocess params:{},error:{}", JSONUtil.toJsonPrettyStr(list), ex);
            ex.printStackTrace();
            return CommonResult.failure(ex.getMessage());
        }
    }

    @ApiOperation("请购拦截")
    @PostMapping("/po/intercept")
    public CommonResult<String> interceptForRequestPo(@RequestBody List<OpsRequestpurchase> list) {
        log.info("/order/po/intercept params:{}", JSONUtil.toJsonPrettyStr(list));
        try {
            poDispatcherService.interceptForRequestPo(list);
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("/order/po/intercept params:{},error:{}", JSONUtil.toJsonPrettyStr(list), ex);
            ex.printStackTrace();
            return CommonResult.failure(ex.getMessage());
        }
    }

    @ApiOperation("请购拦截放行")
    @PostMapping("/po/release")
    public CommonResult<String> releaseForRequestPo(@RequestBody List<OpsRequestpurchase> list) {
        log.info("/order/po/release params:{}", JSONUtil.toJsonPrettyStr(list));
        try {
            poDispatcherService.releaseForRequestPo(list);
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("/order/po/release params:{},error:{}", JSONUtil.toJsonPrettyStr(list), ex);
            ex.printStackTrace();
            return CommonResult.failure(ex.getMessage());
        }
    }


    @ApiOperation("采购发单")
    @PostMapping("/po/send")
    public CommonResult<String> sendForPo(@RequestBody List<OpsPurchaseStatusToWMDto> list) {
        log.info("/order/po/send params:{}", JSONUtil.toJsonPrettyStr(list));
        ImpInvoiceEventLog invoiceLog = LogUtil.createInvoiceLog("/order/sendForPo", list);
        wmDispatchService.addImpInvoiceEventLog(invoiceLog);
        try {
            poDispatcherService.sendForPo(list);
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("/order/po/send params:{},error:{}", JSONUtil.toJsonPrettyStr(list), ex);
            ex.printStackTrace();
            return CommonResult.failure(ex.getMessage());
        }
    }


    @ApiOperation("采购接单")
    @PostMapping("/po/accept")
    public CommonResult<String> acceptForPo(@RequestBody PoToWmDto dto) {
        log.info("/order/po/accept params:{}", JSONUtil.toJsonPrettyStr(dto));
        ImpInvoiceEventLog invoiceLog = LogUtil.createInvoiceLog("/order/acceptForPo", dto);
        wmDispatchService.addImpInvoiceEventLog(invoiceLog);
        try {
            poDispatcherService.acceptForPo(dto);
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("/order/po/accept params:{},error:{}", JSONUtil.toJsonPrettyStr(dto), ex);
            return CommonResult.failure(ex.getMessage());
        }
    }


    @ApiOperation("采购预到货")
    @PostMapping("/po/invoiceImport")
    public CommonResult<String> invoiceImport(@RequestBody List<OpsPurchaseinvoice> list){
        log.info("/order/import/init/Invoice params:{}", JSONUtil.toJsonPrettyStr(list));
        ImpInvoiceEventLog invoiceLog = LogUtil.createInvoiceLog("/order/import/init/Invoice", list);
        wmDispatchService.addImpInvoiceEventLog(invoiceLog);
        try {
            poDispatcherService.invoiceImport(list);
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("/order/import/init/Invoice params:{},error:{}", JSONUtil.toJsonPrettyStr(list), ex);
            return CommonResult.failure(ex.getMessage());
        }
    }


    @Log("采购单更新")
    @PostMapping("/po/update")
    public CommonResult<String> updateForPo(@RequestBody List<PurchaseUpdateInfo> poInfoList) {
        try {
            log.info("updateForPo params = {}", JSON.toJSONString(poInfoList));
            ImpInvoiceEventLog log = new ImpInvoiceEventLog();
            log.setOpType("/order/po/update");
            log.setRequestParam(JSON.toJSONString(poInfoList));
            wmDispatchService.addImpInvoiceEventLog(log);
            for (PurchaseUpdateInfo info : poInfoList) {
                wmDispatchService.updatePurchaseOrder(info);
            }
            return CommonResult.success();
        } catch (Exception ex) {
            log.error("updateForPo params = {}", JSON.toJSONString(poInfoList));
            return CommonResult.failure(ex.getMessage());
        }
    }

}
