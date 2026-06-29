package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MigInventory implements Serializable {
    private String modelno;

    private Integer qtyopenning;

    private Integer qtyonhand;

    private Integer qtyimport;

    private Integer qtyexport;

    private Integer qtyprepare;

    private String itemtype;

    private Integer ordcells;

    private Integer ordquantity;

    private Date indate;

    private Date outdate;

    private String stockcode;

    private String optrec;

    private Integer ordminqty;

    private String customerno;

    private String locaplace;

    private String locationno;

    private BigDecimal discount;

    private String productdesc;

    private String locationnoAdd;

    private Integer qtyonhandOpenning;

    private Integer qtyforecast;

    private Integer qtyprein;

    private Integer qtypreout;

    private String pickupflag;

    private String projectcode;

    private String vipno;

    private Integer qtyofficeinv;

    private String unsafeFlag;

    private Integer avgqtyMonth;

    private Integer freqExport;

    private String productintype;

    private String producttype;

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

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype == null ? null : itemtype.trim();
    }

    public Integer getOrdcells() {
        return ordcells;
    }

    public void setOrdcells(Integer ordcells) {
        this.ordcells = ordcells;
    }

    public Integer getOrdquantity() {
        return ordquantity;
    }

    public void setOrdquantity(Integer ordquantity) {
        this.ordquantity = ordquantity;
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

    public String getOptrec() {
        return optrec;
    }

    public void setOptrec(String optrec) {
        this.optrec = optrec == null ? null : optrec.trim();
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

    public String getLocaplace() {
        return locaplace;
    }

    public void setLocaplace(String locaplace) {
        this.locaplace = locaplace == null ? null : locaplace.trim();
    }

    public String getLocationno() {
        return locationno;
    }

    public void setLocationno(String locationno) {
        this.locationno = locationno == null ? null : locationno.trim();
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc == null ? null : productdesc.trim();
    }

    public String getLocationnoAdd() {
        return locationnoAdd;
    }

    public void setLocationnoAdd(String locationnoAdd) {
        this.locationnoAdd = locationnoAdd == null ? null : locationnoAdd.trim();
    }

    public Integer getQtyonhandOpenning() {
        return qtyonhandOpenning;
    }

    public void setQtyonhandOpenning(Integer qtyonhandOpenning) {
        this.qtyonhandOpenning = qtyonhandOpenning;
    }

    public Integer getQtyforecast() {
        return qtyforecast;
    }

    public void setQtyforecast(Integer qtyforecast) {
        this.qtyforecast = qtyforecast;
    }

    public Integer getQtyprein() {
        return qtyprein;
    }

    public void setQtyprein(Integer qtyprein) {
        this.qtyprein = qtyprein;
    }

    public Integer getQtypreout() {
        return qtypreout;
    }

    public void setQtypreout(Integer qtypreout) {
        this.qtypreout = qtypreout;
    }

    public String getPickupflag() {
        return pickupflag;
    }

    public void setPickupflag(String pickupflag) {
        this.pickupflag = pickupflag == null ? null : pickupflag.trim();
    }

    public String getProjectcode() {
        return projectcode;
    }

    public void setProjectcode(String projectcode) {
        this.projectcode = projectcode == null ? null : projectcode.trim();
    }

    public String getVipno() {
        return vipno;
    }

    public void setVipno(String vipno) {
        this.vipno = vipno == null ? null : vipno.trim();
    }

    public Integer getQtyofficeinv() {
        return qtyofficeinv;
    }

    public void setQtyofficeinv(Integer qtyofficeinv) {
        this.qtyofficeinv = qtyofficeinv;
    }

    public String getUnsafeFlag() {
        return unsafeFlag;
    }

    public void setUnsafeFlag(String unsafeFlag) {
        this.unsafeFlag = unsafeFlag == null ? null : unsafeFlag.trim();
    }

    public Integer getAvgqtyMonth() {
        return avgqtyMonth;
    }

    public void setAvgqtyMonth(Integer avgqtyMonth) {
        this.avgqtyMonth = avgqtyMonth;
    }

    public Integer getFreqExport() {
        return freqExport;
    }

    public void setFreqExport(Integer freqExport) {
        this.freqExport = freqExport;
    }

    public String getProductintype() {
        return productintype;
    }

    public void setProductintype(String productintype) {
        this.productintype = productintype == null ? null : productintype.trim();
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype == null ? null : producttype.trim();
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
        MigInventory other = (MigInventory) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQtyopenning() == null ? other.getQtyopenning() == null : this.getQtyopenning().equals(other.getQtyopenning()))
            && (this.getQtyonhand() == null ? other.getQtyonhand() == null : this.getQtyonhand().equals(other.getQtyonhand()))
            && (this.getQtyimport() == null ? other.getQtyimport() == null : this.getQtyimport().equals(other.getQtyimport()))
            && (this.getQtyexport() == null ? other.getQtyexport() == null : this.getQtyexport().equals(other.getQtyexport()))
            && (this.getQtyprepare() == null ? other.getQtyprepare() == null : this.getQtyprepare().equals(other.getQtyprepare()))
            && (this.getItemtype() == null ? other.getItemtype() == null : this.getItemtype().equals(other.getItemtype()))
            && (this.getOrdcells() == null ? other.getOrdcells() == null : this.getOrdcells().equals(other.getOrdcells()))
            && (this.getOrdquantity() == null ? other.getOrdquantity() == null : this.getOrdquantity().equals(other.getOrdquantity()))
            && (this.getIndate() == null ? other.getIndate() == null : this.getIndate().equals(other.getIndate()))
            && (this.getOutdate() == null ? other.getOutdate() == null : this.getOutdate().equals(other.getOutdate()))
            && (this.getStockcode() == null ? other.getStockcode() == null : this.getStockcode().equals(other.getStockcode()))
            && (this.getOptrec() == null ? other.getOptrec() == null : this.getOptrec().equals(other.getOptrec()))
            && (this.getOrdminqty() == null ? other.getOrdminqty() == null : this.getOrdminqty().equals(other.getOrdminqty()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getLocaplace() == null ? other.getLocaplace() == null : this.getLocaplace().equals(other.getLocaplace()))
            && (this.getLocationno() == null ? other.getLocationno() == null : this.getLocationno().equals(other.getLocationno()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getProductdesc() == null ? other.getProductdesc() == null : this.getProductdesc().equals(other.getProductdesc()))
            && (this.getLocationnoAdd() == null ? other.getLocationnoAdd() == null : this.getLocationnoAdd().equals(other.getLocationnoAdd()))
            && (this.getQtyonhandOpenning() == null ? other.getQtyonhandOpenning() == null : this.getQtyonhandOpenning().equals(other.getQtyonhandOpenning()))
            && (this.getQtyforecast() == null ? other.getQtyforecast() == null : this.getQtyforecast().equals(other.getQtyforecast()))
            && (this.getQtyprein() == null ? other.getQtyprein() == null : this.getQtyprein().equals(other.getQtyprein()))
            && (this.getQtypreout() == null ? other.getQtypreout() == null : this.getQtypreout().equals(other.getQtypreout()))
            && (this.getPickupflag() == null ? other.getPickupflag() == null : this.getPickupflag().equals(other.getPickupflag()))
            && (this.getProjectcode() == null ? other.getProjectcode() == null : this.getProjectcode().equals(other.getProjectcode()))
            && (this.getVipno() == null ? other.getVipno() == null : this.getVipno().equals(other.getVipno()))
            && (this.getQtyofficeinv() == null ? other.getQtyofficeinv() == null : this.getQtyofficeinv().equals(other.getQtyofficeinv()))
            && (this.getUnsafeFlag() == null ? other.getUnsafeFlag() == null : this.getUnsafeFlag().equals(other.getUnsafeFlag()))
            && (this.getAvgqtyMonth() == null ? other.getAvgqtyMonth() == null : this.getAvgqtyMonth().equals(other.getAvgqtyMonth()))
            && (this.getFreqExport() == null ? other.getFreqExport() == null : this.getFreqExport().equals(other.getFreqExport()))
            && (this.getProductintype() == null ? other.getProductintype() == null : this.getProductintype().equals(other.getProductintype()))
            && (this.getProducttype() == null ? other.getProducttype() == null : this.getProducttype().equals(other.getProducttype()));
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
        result = prime * result + ((getItemtype() == null) ? 0 : getItemtype().hashCode());
        result = prime * result + ((getOrdcells() == null) ? 0 : getOrdcells().hashCode());
        result = prime * result + ((getOrdquantity() == null) ? 0 : getOrdquantity().hashCode());
        result = prime * result + ((getIndate() == null) ? 0 : getIndate().hashCode());
        result = prime * result + ((getOutdate() == null) ? 0 : getOutdate().hashCode());
        result = prime * result + ((getStockcode() == null) ? 0 : getStockcode().hashCode());
        result = prime * result + ((getOptrec() == null) ? 0 : getOptrec().hashCode());
        result = prime * result + ((getOrdminqty() == null) ? 0 : getOrdminqty().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getLocaplace() == null) ? 0 : getLocaplace().hashCode());
        result = prime * result + ((getLocationno() == null) ? 0 : getLocationno().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getProductdesc() == null) ? 0 : getProductdesc().hashCode());
        result = prime * result + ((getLocationnoAdd() == null) ? 0 : getLocationnoAdd().hashCode());
        result = prime * result + ((getQtyonhandOpenning() == null) ? 0 : getQtyonhandOpenning().hashCode());
        result = prime * result + ((getQtyforecast() == null) ? 0 : getQtyforecast().hashCode());
        result = prime * result + ((getQtyprein() == null) ? 0 : getQtyprein().hashCode());
        result = prime * result + ((getQtypreout() == null) ? 0 : getQtypreout().hashCode());
        result = prime * result + ((getPickupflag() == null) ? 0 : getPickupflag().hashCode());
        result = prime * result + ((getProjectcode() == null) ? 0 : getProjectcode().hashCode());
        result = prime * result + ((getVipno() == null) ? 0 : getVipno().hashCode());
        result = prime * result + ((getQtyofficeinv() == null) ? 0 : getQtyofficeinv().hashCode());
        result = prime * result + ((getUnsafeFlag() == null) ? 0 : getUnsafeFlag().hashCode());
        result = prime * result + ((getAvgqtyMonth() == null) ? 0 : getAvgqtyMonth().hashCode());
        result = prime * result + ((getFreqExport() == null) ? 0 : getFreqExport().hashCode());
        result = prime * result + ((getProductintype() == null) ? 0 : getProductintype().hashCode());
        result = prime * result + ((getProducttype() == null) ? 0 : getProducttype().hashCode());
        return result;
    }
}