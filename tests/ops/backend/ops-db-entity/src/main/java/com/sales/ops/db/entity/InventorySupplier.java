package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class InventorySupplier extends InventorySupplierKey implements Serializable {
    private Integer quantity;

    private Integer quantityassembly;

    private Integer quantityproduce;

    private Date updatetime;

    private Integer quantityprepare;

    private Integer binflag;

    private String inventoryClass;

    private static final long serialVersionUID = 1L;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityassembly() {
        return quantityassembly;
    }

    public void setQuantityassembly(Integer quantityassembly) {
        this.quantityassembly = quantityassembly;
    }

    public Integer getQuantityproduce() {
        return quantityproduce;
    }

    public void setQuantityproduce(Integer quantityproduce) {
        this.quantityproduce = quantityproduce;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getQuantityprepare() {
        return quantityprepare;
    }

    public void setQuantityprepare(Integer quantityprepare) {
        this.quantityprepare = quantityprepare;
    }

    public Integer getBinflag() {
        return binflag;
    }

    public void setBinflag(Integer binflag) {
        this.binflag = binflag;
    }

    public String getInventoryClass() {
        return inventoryClass;
    }

    public void setInventoryClass(String inventoryClass) {
        this.inventoryClass = inventoryClass == null ? null : inventoryClass.trim();
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
        InventorySupplier other = (InventorySupplier) that;
        return (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getQuantityassembly() == null ? other.getQuantityassembly() == null : this.getQuantityassembly().equals(other.getQuantityassembly()))
            && (this.getQuantityproduce() == null ? other.getQuantityproduce() == null : this.getQuantityproduce().equals(other.getQuantityproduce()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getQuantityprepare() == null ? other.getQuantityprepare() == null : this.getQuantityprepare().equals(other.getQuantityprepare()))
            && (this.getBinflag() == null ? other.getBinflag() == null : this.getBinflag().equals(other.getBinflag()))
            && (this.getInventoryClass() == null ? other.getInventoryClass() == null : this.getInventoryClass().equals(other.getInventoryClass()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getQuantityassembly() == null) ? 0 : getQuantityassembly().hashCode());
        result = prime * result + ((getQuantityproduce() == null) ? 0 : getQuantityproduce().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getQuantityprepare() == null) ? 0 : getQuantityprepare().hashCode());
        result = prime * result + ((getBinflag() == null) ? 0 : getBinflag().hashCode());
        result = prime * result + ((getInventoryClass() == null) ? 0 : getInventoryClass().hashCode());
        return result;
    }
}