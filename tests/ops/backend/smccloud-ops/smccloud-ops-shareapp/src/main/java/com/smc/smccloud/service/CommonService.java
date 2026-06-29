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

    /**
     * 获取物流中心仓库代码顺序集合
     */
    List<String> getMasterWarehouseOrderList();

    boolean isMasterWarehouse(String warehouseCode);

    String getWarehouseType(String warehouseCode);

    List<String> getMasterWarehouseCodes();
}
