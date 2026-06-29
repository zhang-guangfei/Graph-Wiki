package com.sales.ops.feign.delivery;

import com.sales.ops.db.entity.IpsReceiveSignImpInfoFromAll;
import com.sales.ops.dto.ips.IpsReceiveDeliveryInfoFromAllVO;
import com.sales.ops.dto.ips.IpsSignDataBatchPushVO;
import com.sales.ops.dto.ips.OpsVManuorderToSales;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "delivery-server", contextId = "ipssharedb")
public interface OpsDeliveryDataToIpsFeignApi {

    @PostMapping("/do/ips/deliveryData/push")
    CommonResult<IpsReceiveDeliveryInfoFromAllVO> pushOpsDeliveryData(@RequestBody IpsReceiveDeliveryInfoFromAllVO data);

    @PostMapping("/do/ips/posignData/push")
    CommonResult<IpsReceiveSignImpInfoFromAll> pushOpsSignData(@RequestBody IpsReceiveSignImpInfoFromAll data);

    @PostMapping("/do/ips/posignData/pushBatch")
    CommonResult<Void> pushOpsSignDataBatch(@RequestBody IpsSignDataBatchPushVO data);

    @RequestMapping(method = RequestMethod.POST, value = "/do/manu/updateVManuorderToSales")
    CommonResult<OpsVManuorderToSales> updateVManuorderToSales(@RequestBody OpsVManuorderToSales data);
}
