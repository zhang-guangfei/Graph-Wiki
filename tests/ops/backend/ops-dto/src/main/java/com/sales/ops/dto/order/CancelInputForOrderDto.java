package com.sales.ops.dto.order;

import com.sales.ops.dto.purchase.PurchaseInfoForCancel;

import java.util.List;

/**
 * @author C12961
 * @date 2022/3/21 8:12
 */
public class CancelInputForOrderDto {

    private CancelForOrderDto cancelForOrderDto;
    private List<PurchaseInfoForCancel> purchaseList;

    public CancelForOrderDto getCancelForOrderDto() {
        return cancelForOrderDto;
    }

    public void setCancelForOrderDto(CancelForOrderDto cancelForOrderDto) {
        this.cancelForOrderDto = cancelForOrderDto;
    }

    public List<PurchaseInfoForCancel> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<PurchaseInfoForCancel> purchaseList) {
        this.purchaseList = purchaseList;
    }

}
