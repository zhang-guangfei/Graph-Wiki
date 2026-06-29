package com.sales.ops.web.controller;

import com.sales.ops.service.impinvoice.ImpInvoiceSysnCommonService;
import com.sales.ops.service.impinvoice.ImpInvoiceSysnService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: B91717
 * @time: 2024/8/12 11:38
 */
@CrossOrigin
@RestController
@RequestMapping("/impInvoiceSysn")
@Slf4j
public class ImpInvoiceSysnController {

    @Resource
    private ImpInvoiceSysnService impInvoiceSysnService;

    @Resource
    private ImpInvoiceSysnCommonService impInvoiceCommonService;

    /**
     * 取适配器的中间表数据，写到imp_invoice 三张表中
     * @return
     * @throws Exception
     */
    @RequestMapping("/insertImpInvoice")
    public ResultVo<String> InsertImpInvoice() throws Exception{
        try {
            return impInvoiceSysnService.impInvoiceSysn();
        } catch (Exception e) {
            log.error("ImpInvoiceSysn导入错误-",e);
//            impInvoiceCommonService.insertPoFailLog("ImpInvoiceSysn导入错误-",null,"",e.getMessage(),null); //打印异常日志
            return ResultVo.failure(e.getMessage());
        }
    }
}
