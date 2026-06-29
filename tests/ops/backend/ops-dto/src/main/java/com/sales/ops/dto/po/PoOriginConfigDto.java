package com.sales.ops.dto.po;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description： bugid：17244
 * @date ：Created in 2025/4/16 14:44
 */
public class PoOriginConfigDto implements Serializable {
    private static final long serialVersionUID = -6470017783126888421L;

    private Long id;
    private String modelNo;
    //采购原产地
    private String purchaseOrigin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getPurchaseOrigin() {
        return purchaseOrigin;
    }

    public void setPurchaseOrigin(String purchaseOrigin) {
        this.purchaseOrigin = purchaseOrigin;
    }
}
