package com.sales.ops.db.entity;

import java.io.Serializable;

public class BindataKey implements Serializable {
    private Integer stocktype;

    private String warehouseCode;

    private String modelno;

    private String customerno;

    private Long propertyId;

    private static final long serialVersionUID = 1L;

    public Integer getStocktype() {
        return stocktype;
    }

    public void setStocktype(Integer stocktype) {
        this.stocktype = stocktype;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BindataKey other = (BindataKey) that;
        return (this.getStocktype() == null ? other.getStocktype() == null : this.getStocktype().equals(other.getStocktype()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getPropertyId() == null ? other.getPropertyId() == null : this.getPropertyId().equals(other.getPropertyId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStocktype() == null) ? 0 : getStocktype().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getPropertyId() == null) ? 0 : getPropertyId().hashCode());
        return result;
    }
}