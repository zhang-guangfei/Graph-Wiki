package com.sales.ops.service.order;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsRequestpurchase;

import java.util.List;

/**
 * @author C12961
 * @date 2022/4/16 16:50
 */
public interface PurchaseOrderService {

    void cancelPurchaseOrder(String poNo, Integer poItem, Integer poSplitNo, List<OpsRequestpurchase> list,String source) throws OpsException;
}
