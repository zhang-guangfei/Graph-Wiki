package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductPriceT extends ProductPriceTKey implements Serializable {
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
        ProductPriceT other = (ProductPriceT) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getMinquantity() == null ? other.getMinquantity() == null : this.getMinquantity().equals(other.getMinquantity()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getEpricejp() == null ? other.getEpricejp() == null : this.getEpricejp().equals(other.getEpricejp()))
            && (this.getEpricepra() == null ? other.getEpricepra() == null : this.getEpricepra().equals(other.getEpricepra()))
            && (this.getLowestprice() == null ? other.getLowestprice() == null : this.getLowestprice().equals(other.getLowestprice()))
            && (this.getImportfobprice() == null ? other.getImportfobprice() == null : this.getImportfobprice().equals(other.getImportfobprice()))
            && (this.getExportfobprice() == null ? other.getExportfobprice() == null : this.getExportfobprice().equals(other.getExportfobprice()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getImportfobpriceoriginal() == null ? other.getImportfobpriceoriginal() == null : this.getImportfobpriceoriginal().equals(other.getImportfobpriceoriginal()))
            && (this.getImportcurrencyid() == null ? other.getImportcurrencyid() == null : this.getImportcurrencyid().equals(other.getImportcurrencyid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getMinquantity() == null) ? 0 : getMinquantity().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getEpricejp() == null) ? 0 : getEpricejp().hashCode());
        result = prime * result + ((getEpricepra() == null) ? 0 : getEpricepra().hashCode());
        result = prime * result + ((getLowestprice() == null) ? 0 : getLowestprice().hashCode());
        result = prime * result + ((getImportfobprice() == null) ? 0 : getImportfobprice().hashCode());
        result = prime * result + ((getExportfobprice() == null) ? 0 : getExportfobprice().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getImportfobpriceoriginal() == null) ? 0 : getImportfobpriceoriginal().hashCode());
        result = prime * result + ((getImportcurrencyid() == null) ? 0 : getImportcurrencyid().hashCode());
        return result;
    }
}