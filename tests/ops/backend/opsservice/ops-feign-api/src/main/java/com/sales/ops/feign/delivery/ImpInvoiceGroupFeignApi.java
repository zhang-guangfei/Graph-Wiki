package com.sales.ops.feign.delivery;

import com.sales.ops.dto.invoice.OpsPoInvoiceDataDto;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author B91717
 * @description: 同步po适配器的发票数据，到impInvoiceMaster\detail\pack表中
 */
@FeignClient(name = "delivery-server",path = "do" ,contextId = "deliveryImpInvoiceGroupFeignApi")
public interface ImpInvoiceGroupFeignApi {


    @RequestMapping(value = "/invoiceGroupPurchase/findNextInvoiceList", method = RequestMethod.GET)
    ResultVo<List<OpsPoInvoiceDataDto>> getDeliveryInvoiceList(@RequestParam(name = "maxId", required = true) Long maxId,@RequestParam(name = "pageSize", required = true)  Integer pageSize);

}
