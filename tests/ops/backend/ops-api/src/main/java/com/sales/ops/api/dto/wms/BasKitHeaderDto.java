package com.sales.ops.api.dto.wms;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：4.2 bom组件资料下发
 * @date ：Created in 2021/12/13 11:25
 */
public class BasKitHeaderDto implements Serializable {
    private static final long serialVersionUID = -2818361009097853773L;

    private String customerId;

    private String kitSku;

    private String versionNo;

    private String kitSkuDescr1;

    private String kitSkuDescr2;

    private String packId;

    private String activeFlag;

    private String packMaterialFlag;

    private List<BasKitDetailsDto> details ;

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

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getKitSkuDescr1() {
        return kitSkuDescr1;
    }

    public void setKitSkuDescr1(String kitSkuDescr1) {
        this.kitSkuDescr1 = kitSkuDescr1;
    }

    public String getKitSkuDescr2() {
        return kitSkuDescr2;
    }

    public void setKitSkuDescr2(String kitSkuDescr2) {
        this.kitSkuDescr2 = kitSkuDescr2;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getPackMaterialFlag() {
        return packMaterialFlag;
    }

    public void setPackMaterialFlag(String packMaterialFlag) {
        this.packMaterialFlag = packMaterialFlag;
    }

    public List<BasKitDetailsDto> getDetails() {
        return details;
    }

    public void setDetails(List<BasKitDetailsDto> details) {
        this.details = details;
    }
}
