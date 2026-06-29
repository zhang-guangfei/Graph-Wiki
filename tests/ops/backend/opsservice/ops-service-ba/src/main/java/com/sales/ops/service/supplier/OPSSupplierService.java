package com.sales.ops.service.supplier;

import com.sales.ops.db.entity.Supplier;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/12/21 14:54
 */
public interface OPSSupplierService {

    Supplier findSupplierInfoById(String Id);

    List<String> refreshSupplierData(String mi);
}
