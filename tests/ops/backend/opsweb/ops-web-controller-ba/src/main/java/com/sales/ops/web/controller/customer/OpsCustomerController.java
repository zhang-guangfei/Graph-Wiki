package com.sales.ops.web.controller.customer;

import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.ba.CustomerInfo;
import com.sales.ops.dto.customer.CustomerInformation;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.customer.OPSCustomerService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description
 * @date 2021/10/29 16:03
 * @auther C12961
 */
@CrossOrigin
@RestController
@RequestMapping("/customer")
public class OpsCustomerController {

    @Autowired
    private OPSCustomerService opsCustomerService;

    @RequestMapping("/info")
    public CommonResult<CustomerInfo> getCustomerInfo(@RequestParam String id) {
        try {
            CustomerInfo result = opsCustomerService.findCustomerInfoByNo(id);
            return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    @RequestMapping("/infoByName")
    public CommonResult<List<OpsCustomer>> getCustomerInfoByName(@RequestParam String name) {
        try {
            List<OpsCustomer> list = opsCustomerService.findCustomerInfoByName(name);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 刷新缓存
     * @param mi 分钟
     * @return
     */
    @RequestMapping("/refresh")
    public CommonResult<List<String>> refreshCustomer(@RequestParam String mi) {
        try {
            List list = opsCustomerService.refreshCustomerAddress(mi);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }


    /**
     * 查询委托在库和客户关系表信息 ops_customer_warehouse
     * @param customerNo
     * @return
     */
    @RequestMapping("/opsCustomerWarehouse")
    public CommonResult<List<OpsCustomerWarehouse>> searchOpsCustomerWarehouse(@RequestParam String customerNo){
        try {
            List<OpsCustomerWarehouse> list = opsCustomerService.searchOpsCustomerWarehouse(customerNo);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 刷新 委托在库和客户关系表信息 ops_customer_warehouse
     * @param mi 分钟
     * @return
     */
    @RequestMapping("/opsCustomerWarehouse/refresh")
    public CommonResult refreshOpsCustomerWarehouseData(@RequestParam String mi){
        try {
            List<String> list = opsCustomerService.refreshOpsCustomerWarehouseData(mi);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    @PostMapping("/findCustomerByNo")
    public CommonResult<List<Customer>> findCustomerInfoByNoOrName(@RequestParam(value = "customerNo") String customerNo){
        try {
            List<Customer> list = opsCustomerService.findCustomerInfoByNoOrName(customerNo);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    @PostMapping("/findCustomerInformation")
    public PageInfo<CustomerInformation> findCustomerInfoByCustomerNo(@RequestBody PageModel<String> page) {
        return opsCustomerService.findCustomerInfoByCustomerNo(page);
    }

    @GetMapping("/findCustomerByCustomerNoOrName")
    public List<Map<String, String>> findCustomerByCustomerNoOrName(@RequestParam(value = "customerNoOrName") String customerNoOrName) {
        return opsCustomerService.findCustomerByCustomerNoOrName(customerNoOrName);
    }
}
