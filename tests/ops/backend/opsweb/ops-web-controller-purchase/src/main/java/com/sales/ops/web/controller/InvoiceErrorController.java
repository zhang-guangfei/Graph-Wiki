package com.sales.ops.web.controller;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.po.invoice.ErrorTypeDto;
import com.sales.ops.dto.po.invoice.InvoiceErrorDto;
import com.sales.ops.dto.po.invoice.InvoiceErrorParamDto;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.invoice.InvoiceErrorService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/2/4 14:05
 */
@CrossOrigin
@RestController
@RequestMapping("/invoiceError")
@Slf4j
public class InvoiceErrorController {

    @Autowired
    private InvoiceErrorService invoiceErrorService;

    @RequestMapping(value = "/getInvoiceErrorListForPage")
    public ResultVo<PageInfo<InvoiceErrorDto>> getInvoiceErrorListForPage(@RequestBody PageModel<InvoiceErrorParamDto> pageModel) {
        try {
            PageInfo<InvoiceErrorDto> invoiceErrorDtoPageInfo = invoiceErrorService.getInvoiceErrorListForPage(pageModel);
            return ResultVo.success(invoiceErrorDtoPageInfo);
        } catch (Exception e) {
            log.error("getInvoiceErrorListForPage--", e);
            return ResultVo.failure(e.getMessage());
        }
    }

    @RequestMapping(value = "/getInvoiceErrorList")
    public ResultVo<List<InvoiceErrorDto>> getInvoiceErrorList(@RequestBody InvoiceErrorParamDto param){
        try {
            return ResultVo.success(invoiceErrorService.getInvoiceErrorList(param));
        } catch (Exception e) {
            log.error("getInvoiceErrorList--", e);
            return ResultVo.failure(e.getMessage());
        }
    }

    @RequestMapping(value = "/getInvoiceCheckRule")
    public ResultVo<List<ErrorTypeDto>> getInvoiceCheckRule() {
        try {
            List<ErrorTypeDto> invoiceCheckRule = invoiceErrorService.getInvoiceCheckRule();
            if (invoiceCheckRule != null && !invoiceCheckRule.isEmpty()) {
                return ResultVo.success(invoiceCheckRule);
            } else {
                return ResultVo.failure("无数据");
            }
        } catch (Exception e) {
            log.error("getInvoiceCheckRule--", e);
            return ResultVo.failure(e.getMessage());
        }
    }
}
