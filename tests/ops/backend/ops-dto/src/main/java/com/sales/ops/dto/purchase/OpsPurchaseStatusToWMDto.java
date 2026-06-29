package com.sales.ops.dto.purchase;

import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2022/2/9 9:48
 */
public class OpsPurchaseStatusToWMDto implements Serializable {
    private static final long serialVersionUID = -1128084067095825707L;

    //采购单
    private OpsPurchaseorder opsPurchaseorder;
    //请购单
    private OpsRequestpurchase opsRequestpurchase;

    public OpsPurchaseorder getOpsPurchaseorder() {
        return opsPurchaseorder;
    }

    public void setOpsPurchaseorder(OpsPurchaseorder opsPurchaseorder) {
        this.opsPurchaseorder = opsPurchaseorder;
    }

    public OpsRequestpurchase getOpsRequestpurchase() {
        return opsRequestpurchase;
    }

    public void setOpsRequestpurchase(OpsRequestpurchase opsRequestpurchase) {
        this.opsRequestpurchase = opsRequestpurchase;
    }
}
