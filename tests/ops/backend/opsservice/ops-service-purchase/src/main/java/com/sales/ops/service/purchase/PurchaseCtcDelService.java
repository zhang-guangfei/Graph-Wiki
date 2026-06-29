package com.sales.ops.service.purchase;

import com.sales.ops.db.entity.OpsPurchaseinvoice;

import java.util.List;

/**
 * @author B91717
 * @date 2022/10/25
 * @apiNote
 * 采购删单，同步CTC表操作
 */

public interface PurchaseCtcDelService {

    /**
     * 写入
     * @param opsPurchaseinvoices
     * @return
     */
    void insertMid(List<OpsPurchaseinvoice> opsPurchaseinvoices);
}

