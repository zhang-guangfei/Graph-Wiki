package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductPtest extends ProductPtestKey implements Serializable {
    private BigDecimal fobprice;

    private BigDecimal eprice;

    private BigDecimal epricejp;

    private BigDecimal epricepra;


    private BigDecimal lowestprice;

    private BigDecimal importfobprice;

    private BigDecimal exportfobprice;

    private Boolean isDeleted;

    private Date updatetime;

    private BigDecimal importfobpriceoriginal;

    private String importcurrencyid;

    private static final long serialVersionUID = 1L;

    public BigDecimal getFobprice() {
        return fobprice;
    }

    public void setFobprice(BigDecimal fobprice) {
        this.fobprice = fobprice;
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public BigDecimal getEpricejp() {
        return epricejp;
    }

    public void setEpricejp(BigDecimal epricejp) {
        this.epricejp = epricejp;
    }

    public BigDecimal getEpricepra() {
        return epricepra;
    }

    public void setEpricepra(BigDecimal epricepra) {
        this.epricepra = epricepra;
    }

    public BigDecimal getLowestprice() {
        return lowestprice;
    }

    public void setLowestprice(BigDecimal lowestprice) {
        this.lowestprice = lowestprice;
    }

    public BigDecimal getImportfobprice() {
        return importfobprice;
    }

    public void setImportfobprice(BigDecimal importfobprice) {
        this.importfobprice = importfobprice;
    }

    public BigDecimal getExportfobprice() {
        return exportfobprice;
    }

    public void setExportfobprice(BigDecimal exportfobprice) {
        this.exportfobprice = exportfobprice;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public BigDecimal getImportfobpriceoriginal() {
        return importfobpriceoriginal;
    }

    public void setImportfobpriceoriginal(BigDecimal importfobpriceoriginal) {
        this.importfobpriceoriginal = importfobpriceoriginal;
    }

    public String getImportcurrencyid() {
        return importcurrencyid;
    }

    public void setImportcurrencyid(String importcurrencyid) {
        this.importcurrencyid = importcurrencyid == null ? null : importcurrencyid.trim();
    }
}