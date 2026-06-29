package com.smc.ops.delivery.controller.poImps;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.invoice.OpsPoInvoiceDataDto;
import com.smc.ops.delivery.service.poImps.InvoiceGroupPurchaseService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：B91717
 * @Date ：2024/3/14
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/invoiceGroupPurchase")
@Slf4j
public class InvoiceGroupPurchaseController {

    @Resource
    private InvoiceGroupPurchaseService invoiceGroupPurchaseService;



    /**
     * 查询集团采购中间库的发票主表数据
     * @return
     * @throws OpsException
     */
    @RequestMapping(value ="/findNextInvoiceList",method = RequestMethod.GET)
    public  ResultVo<List<OpsPoInvoiceDataDto>> findDeliveryInvoiceList( @RequestParam(name = "maxId", required = true) Long maxId,
                                                                         @RequestParam(name = "pageSize", required = true) Integer pageSize) throws Exception {
        if (maxId == null || pageSize <= 0) {
            return ResultVo.failure("INVALID_PARAMETER", "Invalid parameter provided.");
        }
        try {
            return invoiceGroupPurchaseService.getDeliveryInvoiceList(maxId, pageSize);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

}
