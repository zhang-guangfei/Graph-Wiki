package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class InventoryGz implements Serializable {
    private String modelno;

    private String stockcode;

    private BigDecimal qtyonhand;

    private BigDecimal qtyprepare;

    private String customerno;

    private Long propertyId;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public BigDecimal getQtyonhand() {
        return qtyonhand;
    }

    public void setQtyonhand(BigDecimal qtyonhand) {
        this.qtyonhand = qtyonhand;
    }

    public BigDecimal getQtyprepare() {
        return qtyprepare;
    }

    public void setQtyprepare(BigDecimal qtyprepare) {
        this.qtyprepare = qtyprepare;
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
        InventoryGz other = (InventoryGz) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getStockcode() == null ? other.getStockcode() == null : this.getStockcode().equals(other.getStockcode()))
            && (this.getQtyonhand() == null ? other.getQtyonhand() == null : this.getQtyonhand().equals(other.getQtyonhand()))
            && (this.getQtyprepare() == null ? other.getQtyprepare() == null : this.getQtyprepare().equals(other.getQtyprepare()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getPropertyId() == null ? other.getPropertyId() == null : this.getPropertyId().equals(other.getPropertyId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getStockcode() == null) ? 0 : getStockcode().hashCode());
        result = prime * result + ((getQtyonhand() == null) ? 0 : getQtyonhand().hashCode());
        result = prime * result + ((getQtyprepare() == null) ? 0 : getQtyprepare().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getPropertyId() == null) ? 0 : getPropertyId().hashCode());
        return result;
    }
}