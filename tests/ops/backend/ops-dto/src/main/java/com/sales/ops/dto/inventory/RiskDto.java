package com.sales.ops.dto.inventory;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/4/23 13:19
 */
public class RiskDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long inventoryId;

    private String modelNo;

    private Integer quantity;

    private Integer prepareQuantity;

    private Long inventoryPropertyId;

    private String inventoryTypeCode;

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrepareQuantity() {
        return prepareQuantity;
    }

    public void setPrepareQuantity(Integer prepareQuantity) {
        this.prepareQuantity = prepareQuantity;
    }

    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }
}
