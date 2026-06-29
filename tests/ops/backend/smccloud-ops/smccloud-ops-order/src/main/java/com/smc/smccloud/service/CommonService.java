package com.smc.smccloud.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.customer.CustomerVO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-07-26 17:33
 * Description:
 */
public interface CommonService {

    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    int getOpsRoSignTimeGT(Long invoiceId);

    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    int getImpInvoiceMasterConfirmDateGT(Long invoiceId);

    CustomerVO getCustomerInfoByNo(String customerNo);

    String getCustomerNameByNo(String customerNo);

    String getEmpSaleNameByNo(String empSale);

    String getDeptNameByNo(String deptNo);

    String getSupplierNameByCode(String supplierCode);

    String getWarehouseNameByCode(String wareHouseCode);

    List<String> getIndustryMediamCodeToCstmNo(List<String> industryCode);

    List<String> getHLCodeByCustomerNo(List<String> customerNo);

    String getWarehouseType(String warehouseCode);

    List<String> getWarehouseCodeByWarehouseType(String warehouseType);

    ResultVo<String> testCallInterface(String parm);
}

