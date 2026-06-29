package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class MigInventoryAgent implements Serializable {
    private String modelno;

    private Integer qtyopenning;

    private Integer qtyonhand;

    private Integer qtyimport;

    private Integer qtyexport;

    private Integer qtyprepare;

    private Date indate;

    private Date outdate;

    private String stockcode;

    private Integer ordminqty;

    private String customerno;

    private String locationno;

    private Date inserttime;

    private String parentcode;

    private String optrec;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getQtyopenning() {
        return qtyopenning;
    }

    public void setQtyopenning(Integer qtyopenning) {
        this.qtyopenning = qtyopenning;
    }

    public Integer getQtyonhand() {
        return qtyonhand;
    }

    public void setQtyonhand(Integer qtyonhand) {
        this.qtyonhand = qtyonhand;
    }

    public Integer getQtyimport() {
        return qtyimport;
    }

    public void setQtyimport(Integer qtyimport) {
        this.qtyimport = qtyimport;
    }

    public Integer getQtyexport() {
        return qtyexport;
    }

    public void setQtyexport(Integer qtyexport) {
        this.qtyexport = qtyexport;
    }

    public Integer getQtyprepare() {
        return qtyprepare;
    }

    public void setQtyprepare(Integer qtyprepare) {
        this.qtyprepare = qtyprepare;
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public Date getOutdate() {
        return outdate;
    }

    public void setOutdate(Date outdate) {
        this.outdate = outdate;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public Integer getOrdminqty() {
        return ordminqty;
    }

    public void setOrdminqty(Integer ordminqty) {
        this.ordminqty = ordminqty;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getLocationno() {
        return locationno;
    }

    public void setLocationno(String locationno) {
        this.locationno = locationno == null ? null : locationno.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode == null ? null : parentcode.trim();
    }

    public String getOptrec() {
        return optrec;
    }

    public void setOptrec(String optrec) {
        this.optrec = optrec == null ? null : optrec.trim();
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
        MigInventoryAgent other = (MigInventoryAgent) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQtyopenning() == null ? other.getQtyopenning() == null : this.getQtyopenning().equals(other.getQtyopenning()))
            && (this.getQtyonhand() == null ? other.getQtyonhand() == null : this.getQtyonhand().equals(other.getQtyonhand()))
            && (this.getQtyimport() == null ? other.getQtyimport() == null : this.getQtyimport().equals(other.getQtyimport()))
            && (this.getQtyexport() == null ? other.getQtyexport() == null : this.getQtyexport().equals(other.getQtyexport()))
            && (this.getQtyprepare() == null ? other.getQtyprepare() == null : this.getQtyprepare().equals(other.getQtyprepare()))
            && (this.getIndate() == null ? other.getIndate() == null : this.getIndate().equals(other.getIndate()))
            && (this.getOutdate() == null ? other.getOutdate() == null : this.getOutdate().equals(other.getOutdate()))
            && (this.getStockcode() == null ? other.getStockcode() == null : this.getStockcode().equals(other.getStockcode()))
            && (this.getOrdminqty() == null ? other.getOrdminqty() == null : this.getOrdminqty().equals(other.getOrdminqty()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getLocationno() == null ? other.getLocationno() == null : this.getLocationno().equals(other.getLocationno()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()))
            && (this.getParentcode() == null ? other.getParentcode() == null : this.getParentcode().equals(other.getParentcode()))
            && (this.getOptrec() == null ? other.getOptrec() == null : this.getOptrec().equals(other.getOptrec()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQtyopenning() == null) ? 0 : getQtyopenning().hashCode());
        result = prime * result + ((getQtyonhand() == null) ? 0 : getQtyonhand().hashCode());
        result = prime * result + ((getQtyimport() == null) ? 0 : getQtyimport().hashCode());
        result = prime * result + ((getQtyexport() == null) ? 0 : getQtyexport().hashCode());
        result = prime * result + ((getQtyprepare() == null) ? 0 : getQtyprepare().hashCode());
        result = prime * result + ((getIndate() == null) ? 0 : getIndate().hashCode());
        result = prime * result + ((getOutdate() == null) ? 0 : getOutdate().hashCode());
        result = prime * result + ((getStockcode() == null) ? 0 : getStockcode().hashCode());
        result = prime * result + ((getOrdminqty() == null) ? 0 : getOrdminqty().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getLocationno() == null) ? 0 : getLocationno().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        result = prime * result + ((getParentcode() == null) ? 0 : getParentcode().hashCode());
        result = prime * result + ((getOptrec() == null) ? 0 : getOptrec().hashCode());
        return result;
    }
}