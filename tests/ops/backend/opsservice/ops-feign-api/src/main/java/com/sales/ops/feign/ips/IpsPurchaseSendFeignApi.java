package com.sales.ops.feign.ips;

import com.sales.ops.dto.ips.IpsReceiveOrderAllOriginalInfoDto;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author B91717
 * ops发单给IPS系统的feign接口
 */
@FeignClient(name = "shareapp-service", contextId = "ipsPurchaseSend")
public interface IpsPurchaseSendFeignApi {

    /**
     *
     * OPS发送采购单给IPS系统，目前对接了manu 和 gz，后期全部切换
     *
     */
    @RequestMapping(value = "/shareapp/ipsPurchaseSend/insertIpsOrder", method = RequestMethod.POST)
    CommonResult<String> insertPurchaseOrderToIps(@RequestBody List<IpsReceiveOrderAllOriginalInfoDto> ipsReceiveOrderAllOriginalInfoDtos);

}
