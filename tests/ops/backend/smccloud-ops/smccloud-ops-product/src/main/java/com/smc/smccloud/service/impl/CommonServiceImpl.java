package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.model.Employee.EmployeeVO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.service.CommonService;
import com.smc.smccloud.service.CommonServiceFeignApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-07-28 14:11
 * Description:
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private RedisManager redisManager;

    @Override
    public CustomerVO getCustomerInfoByNo(String customerNo) {
        if (StringUtils.isBlank(customerNo)) {
            return null;
        }
        Object customer = redisManager.hGet(Constants.REDIS_ALL_CUSTOMER_INFO, customerNo);
        if (customer == null) {
            ResultVo<CustomerVO> resultVo = commonServiceFeignApi.findCustomerByCustomerNo(customerNo);
            if (resultVo.isSuccess() && resultVo.getData() != null) {
                return resultVo.getData();
            }
            return null;
        }
        return JSONObject.parseObject(customer.toString(), CustomerVO.class);
    }

    @Override
    public String getCustomerNameByNo(String customerNo) {
        if (StringUtils.isBlank(customerNo)) {
            return null;
        }
        Object customerName = redisManager.hGet(Constants.REDIS_CUSTOMER_NAME, customerNo);
        if (customerName == null) {
            ResultVo<String> resultVo = commonServiceFeignApi.getCustomerNameByNo(customerNo);
            if (resultVo.isSuccess() && resultVo.getData() != null) {
                return resultVo.getData();
            }
            return null;
        }
        return customerName.toString();
    }

    @Override
    public String getEmpSaleNameByNo(String empSale) {
        if (StringUtils.isBlank(empSale)) {
            return null;
        }
        Object empSalesObj = redisManager.hGet(Constants.REDIS_EMPLOYEE_INFO_ALL, empSale);
        if (empSalesObj == null) {
            ResultVo<String> employeeNameByNo = commonServiceFeignApi.getEmployeeNameByNo(empSale);
            if (employeeNameByNo.isSuccess() && employeeNameByNo.getData() != null) {
                return employeeNameByNo.getData();
            }
            return "";
        }
        EmployeeVO employeeVO = JSONObject.parseObject(empSalesObj.toString(), EmployeeVO.class);
        return employeeVO.getName();
    }

    @Override
    public String getDeptNameByNo(String deptNo) {
        if (StringUtils.isBlank(deptNo)) {
            return null;
        }
        Object obj = redisManager.hGet(Constants.REDIS_DEPARTMENT_NAME_ALL, deptNo);
        if (obj == null) {
            ResultVo<String> deptNameByNo = commonServiceFeignApi.getDeptNameByNo(deptNo);
            if (deptNameByNo.isSuccess() && deptNameByNo.getData() != null) {
                return deptNameByNo.getData();
            }
            return null;
        }
        return obj.toString();
    }

    @Override
    public String getSupplierNameByCode(String supplierCode) {
        if (StringUtils.isBlank(supplierCode)) {
            return null;
        }
        Object obj = redisManager.hGet(Constants.REDIS_SUPPLIER_NAME_ALL, supplierCode);
        if (obj == null) {
            ResultVo<String> supplierResult = commonServiceFeignApi.getSupplierName(supplierCode);
            if (supplierResult.isSuccess() && supplierResult.getData() != null) {
                return supplierResult.getData();
            }
            return null;
        } else {
            return obj.toString();
        }
    }

    @Override
    public String getWarehouseNameByCode(String warehouseCode) {
        if (StringUtils.isBlank(warehouseCode)) {
            return null;
        }
        Object houseName = redisManager.hGet(Constants.REDIS_WAREHOUSE_NAME_BY_CODE, warehouseCode);
        if (houseName == null) {
            ResultVo<String> warehouseName = commonServiceFeignApi.getWarehouseName(warehouseCode);
            if (warehouseName.isSuccess() && warehouseName.getData() != null) {
                return warehouseName.getData();
            }
            return null;
        } else {
            return houseName.toString();
        }
    }

    @Override
    public List<String> getMasterWarehouseCodes() {
        Object o = redisManager.get(Constants.REDIS_WAREHOUSE_CODES_BY_TYPE + WarehouseTypeEnum.RDC.getHouseTypeCode());
        if (o != null) {
            return JSON.parseArray(o.toString(), String.class);
        }
        ResultVo<List<String>> resultVo = commonServiceFeignApi.getWarehouseCodeByWarehouseType(WarehouseTypeEnum.RDC.getHouseTypeCode());
        if (!resultVo.isSuccess()) {
            return Collections.emptyList();
        }
        return resultVo.getData();
    }
}
