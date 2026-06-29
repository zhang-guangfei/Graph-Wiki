package com.sales.ops.api.dto.wms;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：4.0 Bomdetail组件下发
 * @date ：Created in 2021/12/13 13:52
 */
public class BasKitDetailsDto implements Serializable {

    private static final long serialVersionUID = -1065337079815579995L;

    private String customerId;

    private String kitSku;

    private String sku;

    private String packId;

    private String versionNo;

    private String skuDescr1;

    private String skuDescr2;

    private String qty;

    private String accessoryFlag;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getKitSku() {
        return kitSku;
    }

    public void setKitSku(String kitSku) {
        this.kitSku = kitSku;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getSkuDescr1() {
        return skuDescr1;
    }

    public void setSkuDescr1(String skuDescr1) {
        this.skuDescr1 = skuDescr1;
    }

    public String getSkuDescr2() {
        return skuDescr2;
    }

    public void setSkuDescr2(String skuDescr2) {
        this.skuDescr2 = skuDescr2;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getAccessoryFlag() {
        return accessoryFlag;
    }

    public void setAccessoryFlag(String accessoryFlag) {
        this.accessoryFlag = accessoryFlag;
    }
}
