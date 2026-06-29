package com.sales.ops.dto.po;

import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import org.apache.commons.collections.CollectionUtils;


import java.util.Collections;
import java.util.List;

/**
 * @author C12961
 * @date 2023/3/7 17:39
 */
public class RequestAndPurchaseDto {

    private OpsPurchaseorder purchaseOrder;

    private List<OpsRequestpurchase> requestPurchaseList;

    public RequestAndPurchaseDto() {
    }

    public RequestAndPurchaseDto(OpsPurchaseorder purchaseOrder, List<OpsRequestpurchase> requestPurchaseList) {
        this.purchaseOrder = purchaseOrder;
        this.requestPurchaseList = requestPurchaseList;
    }

    public RequestAndPurchaseDto(OpsRequestpurchase requestPurchase) {
        this.purchaseOrder = null;
        this.requestPurchaseList = Collections.singletonList(requestPurchase);
    }

    public OpsPurchaseorder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(OpsPurchaseorder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public List<OpsRequestpurchase> getRequestPurchaseList() {
        return requestPurchaseList;
    }

    public void setRequestPurchaseList(List<OpsRequestpurchase> requestPurchaseList) {
        this.requestPurchaseList = requestPurchaseList;
    }

    public boolean existPurchase() {
        return this.getPurchaseOrder() != null;
    }

    public boolean existRequestPurchase() {
        return CollectionUtils.isNotEmpty(this.getRequestPurchaseList());
    }

}
