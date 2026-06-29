package com.sales.ops.db.entity;

import java.io.Serializable;

public class Testinv0505a implements Serializable {
    private String warehouseCode;

    private String modelno;

    private Integer qty;

    private Integer specqty;

    private Integer qtybin;

    private Integer bincell;

    private Integer overqty;

    private static final long serialVersionUID = 1L;

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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getSpecqty() {
        return specqty;
    }

    public void setSpecqty(Integer specqty) {
        this.specqty = specqty;
    }

    public Integer getQtybin() {
        return qtybin;
    }

    public void setQtybin(Integer qtybin) {
        this.qtybin = qtybin;
    }

    public Integer getBincell() {
        return bincell;
    }

    public void setBincell(Integer bincell) {
        this.bincell = bincell;
    }

    public Integer getOverqty() {
        return overqty;
    }

    public void setOverqty(Integer overqty) {
        this.overqty = overqty;
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
        Testinv0505a other = (Testinv0505a) that;
        return (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getSpecqty() == null ? other.getSpecqty() == null : this.getSpecqty().equals(other.getSpecqty()))
            && (this.getQtybin() == null ? other.getQtybin() == null : this.getQtybin().equals(other.getQtybin()))
            && (this.getBincell() == null ? other.getBincell() == null : this.getBincell().equals(other.getBincell()))
            && (this.getOverqty() == null ? other.getOverqty() == null : this.getOverqty().equals(other.getOverqty()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getSpecqty() == null) ? 0 : getSpecqty().hashCode());
        result = prime * result + ((getQtybin() == null) ? 0 : getQtybin().hashCode());
        result = prime * result + ((getBincell() == null) ? 0 : getBincell().hashCode());
        result = prime * result + ((getOverqty() == null) ? 0 : getOverqty().hashCode());
        return result;
    }
}