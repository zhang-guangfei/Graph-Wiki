package com.smc.smccloud.service;

import com.smc.smccloud.model.customer.CustomerVO;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-07-28 14:11
 * Description:
 */
public interface CommonService {

    CustomerVO getCustomerInfoByNo(String customerNo);

    String getCustomerNameByNo(String customerNo);

    String getEmpSaleNameByNo(String empSale);

    String getDeptNameByNo(String deptNo);

    String getSupplierNameByCode(String supplierCode);

    String getWarehouseNameByCode(String warehouseCode);

    List<String> getMasterWarehouseCodes();
}
