package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.mapper.CommonMapper;
import com.smc.smccloud.model.Employee.EmployeeVO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.service.CommonService;
import com.smc.smccloud.service.CommonServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-07-26 17:33
 * Description:
 */
@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private RedisManager redisManager;

    @Resource
    private CommonMapper commonMapper;
    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public int getOpsRoSignTimeGT(Long invoiceId) {
        return commonMapper.getOpsRoSignTimeGT(invoiceId);
    }

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public int getImpInvoiceMasterConfirmDateGT(Long invoiceId) {
        return commonMapper.getImpInvoiceMasterConfirmDateGT(invoiceId);
    }

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
    public String getWarehouseNameByCode(String wareHouseCode) {
        if (StringUtils.isBlank(wareHouseCode)) {
            return "";
        }
        Object houseName = redisManager.hGet(Constants.REDIS_WAREHOUSE_NAME_BY_CODE, wareHouseCode);
        if (houseName == null) {
            ResultVo<String> warehouseName = commonServiceFeignApi.getWarehouseName(wareHouseCode);
            if (warehouseName.isSuccess() && warehouseName.getData() != null) {
                return warehouseName.getData();
            }
            return "";
        } else {
            return houseName.toString();
        }
    }

    @Override
    public List<String> getIndustryMediamCodeToCstmNo(List<String> industryCode) {
        if (CollectionUtils.isEmpty(industryCode)) {
            return null;
        }
        String joinStr = String.join(",", industryCode);
        Object object = redisManager.get(Constants.REDIS_INDUSTRY_CODE_CUSTOMERNO + joinStr);
        if (object == null) {
            ResultVo<List<String>> industryMediamCodeToCstmNo = commonServiceFeignApi.getIndustryMediamCodeToCstmNo(industryCode);
            if (industryMediamCodeToCstmNo.isSuccess()) {
                return industryMediamCodeToCstmNo.getData();
            }
            return null;
        } else {
            return JSONObject.parseObject(object.toString(), List.class);
        }
    }

    @Override
    public List<String> getHLCodeByCustomerNo(List<String> customerNo) {
        if (CollectionUtils.isEmpty(customerNo)) {
            return null;
        }
        List<String> hlList = new ArrayList<>();
        for (String no : customerNo) {
            CustomerVO customerInfoByNo = getCustomerInfoByNo(no);
            if (customerInfoByNo != null) {
                if (StringUtils.isNotBlank(customerInfoByNo.getHlCode())) {
                    hlList.add(customerInfoByNo.getHlCode());
                }
            }
        }
        return hlList;
    }

    @Override
    public String getWarehouseType(String warehouseCode) {
        Object o = redisManager.hGet(Constants.REDIS_WAREHOUSE_TYPE_BY_CODE, warehouseCode);
        if (o != null) {
            return o.toString();
        }
        ResultVo<String> resultVo = commonServiceFeignApi.getWarehouseType(warehouseCode);
        if (!resultVo.isSuccess()) {
            return "";
        }
        return resultVo.getData();
    }

    @Override
    public List<String> getWarehouseCodeByWarehouseType(String warehouseType) {
        // update by LiYingChao from bug 9298 in 2023-1-10
        if (redisManager.hasKey(Constants.REDIS_WAREHOUSE_CODES_BY_TYPE + WarehouseTypeEnum.RDC.getHouseTypeCode())) {
            Object o = redisManager.get(Constants.REDIS_WAREHOUSE_CODES_BY_TYPE + WarehouseTypeEnum.RDC.getHouseTypeCode());
            if (o != null) {
                return JSON.parseArray(o.toString(), String.class);
            }
        }

        ResultVo<List<String>> resultVo = commonServiceFeignApi.getWarehouseCodeByWarehouseType(WarehouseTypeEnum.RDC.getHouseTypeCode());
        if (!resultVo.isSuccess()) {
            return Collections.emptyList();
        }
        return resultVo.getData();
    }

    @Override
    public ResultVo<String> testCallInterface(String parm) {
        log.info("成功进入接口testCallInterface:  参数:{}",parm);
        return ResultVo.success("testCallInterface响应成功.");
    }
}
