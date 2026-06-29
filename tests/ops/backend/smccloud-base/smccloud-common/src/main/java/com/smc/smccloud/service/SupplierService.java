package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.customer.TblGroupcustomerVO;
import com.smc.smccloud.model.supplier.SupplierRequest;
import com.smc.smccloud.model.supplier.SupplierVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SupplierService {

    /**
     * 查询供应商信息
     * @return
     */
    ResultVo<List<SupplierVo>> findSupplierInfo();

    ResultVo<List<SupplierVo>> findSupplierByIdOrName(String companyId, String name);

    ResultVo<String> updateSupplierData(SupplierVo supplierVo);

    ResultVo<String> getSupplierName(String supplierCode);

    ResultVo<List<SupplierVo>> findChinaSuppliers();

    ResultVo<TblGroupcustomerVO> getTblGroupCustInfo(String customerNo);

    ResultVo<PageInfo<SupplierVo>> findSupplierList(SupplierRequest request);

    ResultVo<SupplierVo> findSupplierById(String id);

    // 新增供应商
    ResultVo<String> addSupplier(SupplierVo supplierVo);

    // 删除供应商
    ResultVo<String> deleteSupplierById(List<String> id);

    // 编辑供应商
    ResultVo<String> editSupplier(SupplierVo supplierVo);

}
