package com.sales.ops.feign.purchase;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author B91717
 * @description: 同步po适配器的发票数据，到impInvoiceMaster\detail\pack表中
 */
@FeignClient(name = "po-service", contextId = "OpsImpInvoiceSysnFeignApi")
public interface OpsImpInvoiceSysnFeignApi {

    /**
     * 写入impInvoiceMaster\detail\pack表中
     * @return
     */
    @RequestMapping(value = "/impInvoiceSysn/insertImpInvoice", method = RequestMethod.GET)
    ResultVo<String> InsertImpInvoice();

}
