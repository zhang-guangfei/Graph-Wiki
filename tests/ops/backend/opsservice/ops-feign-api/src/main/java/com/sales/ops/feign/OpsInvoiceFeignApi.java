package com.sales.ops.feign;

import com.sales.ops.dto.order.OpsInvoiceResDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.MenhuResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(name = "wm-service", contextId = "invoice", url = "${ips.url: }")
public interface OpsInvoiceFeignApi {

    @RequestMapping(value = "/authToken/token", method = RequestMethod.POST)
    ResponseEntity<CommonResult> getToken(@RequestBody Map<String, String> user);



    @RequestMapping(value = "/isalesInvoice/query/getInvoiceSum", method = RequestMethod.POST)
    MenhuResult<List<OpsInvoiceResDto>> getInvoiceInfo(@RequestBody List<String> rorderno);

}
