package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Inventory implements Serializable {
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

    public String getOptrec() {
        return optrec;
    }

    public void setOptrec(String optrec) {
        this.optrec = optrec == null ? null : optrec.trim();
    }
}