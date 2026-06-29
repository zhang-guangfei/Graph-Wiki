package com.sales.ops.service.purchase;

/**
 * @author B91717
 * @date 2022/10/25
 * @apiNote
 * 采购单完成时，同步CTC表操作
 */
public interface PurchaseCtcFinishService {

    /**
     * 采购单完成时，同步CTC表操作
     */
    void insertMid(String orderNo, Integer itemNo, Integer splitNo);
}
