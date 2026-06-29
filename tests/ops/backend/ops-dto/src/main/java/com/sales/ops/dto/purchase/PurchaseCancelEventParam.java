package com.sales.ops.dto.purchase;

import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;

import java.util.List;

public class PurchaseCancelEventParam {

    private String cancelSource;
    private List<OpsRequestpurchase> requestpurchaseList;
    private OpsPurchaseorder purchaseOrder;

    public PurchaseCancelEventParam(String cancelSource, OpsPurchaseorder opsPurchaseorder, List<OpsRequestpurchase> requestpurchaseList) {
        this.cancelSource = cancelSource;
        this.purchaseOrder = opsPurchaseorder;
        this.requestpurchaseList = requestpurchaseList;
    }

    public String getCancelSource() {
        return cancelSource;
    }

    public void setCancelSource(String cancelSource) {
        this.cancelSource = cancelSource;
    }

    public List<OpsRequestpurchase> getRequestpurchaseList() {
        return requestpurchaseList;
    }

    public void setRequestpurchaseList(List<OpsRequestpurchase> requestpurchaseList) {
        this.requestpurchaseList = requestpurchaseList;
    }

    public OpsPurchaseorder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(OpsPurchaseorder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
