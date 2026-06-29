package com.sales.ops.feign;

import com.sales.ops.db.entity.Customer;
import com.sales.ops.db.entity.OpsCustomer;
import com.sales.ops.db.entity.OpsCustomerWarehouse;
import com.sales.ops.dto.ba.CustomerInfo;
import com.sales.ops.dto.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ba-service", contextId = "customer")
public interface OpsCustomerFeignApi {

    /**
     * 查询客户地址
     */
    @RequestMapping(value = "/customer/info",method = RequestMethod.GET)
    CommonResult<CustomerInfo> getCustomerInfo(@RequestParam("id") String id);

    @RequestMapping(value = "/customer/refresh",method = RequestMethod.GET)
    CommonResult<List<String>> refreshCustomer(@RequestParam("mi") String mi);

    /**
     * 模糊查询
     * @param name
     * @return
     */
    @RequestMapping(value = "/customer/infoByName",method = RequestMethod.GET)
    CommonResult<List<OpsCustomer>> getCustomerInfoByName(@RequestParam("name") String name);


    @RequestMapping(value = "/customer/opsCustomerWarehouse",method = RequestMethod.GET)
    CommonResult<List<OpsCustomerWarehouse>> searchOpsCustomerWarehouse(@RequestParam("name") String name);

    @RequestMapping(value = "/opsCustomerWarehouse/refresh",method = RequestMethod.GET)
    CommonResult<List<String>> refreshOpsCustomerWarehouseData(@RequestParam("mi") String mi);

    @RequestMapping(value = "/customer/findCustomerByNo",method = RequestMethod.GET)
    List<Customer> queryCustomer(@RequestParam("customerNo") String customerNo);

}
