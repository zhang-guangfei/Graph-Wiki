package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.product.ProductDeliveryDO;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-01-21 11:35
 * Description:
 */
public interface ProductDeliveryService {

    /**
     * 根据型号查询供应商
     *
     * @param modelNo 型号
     * @return supplierNo 供应商
     */
    ResultVo<String> getSupplierNoByModelNo(String modelNo);

    /**
     * 根据型号查询产地
     *
     * @param modelNo 型号
     * @return orgCountry 产地
     */
    String getOrgCountryByModelNo(String modelNo);


    /**
     * 批量根据型号查询产地
     *
     * @param modelNo 型号
     * @return orgCountry 产地
     */
    List<ProductDeliveryDO> listOrgCountryByModelNo(List<String> modelNo);
}
