package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductDeliveryProd extends ProductDeliveryProdKey implements Serializable {
    private String orgcountryid;

    private Integer stddlvday;

    private Integer stddlvdatemaxnumber;

    private Integer maxprodqty;

    private Boolean enablemaxprodqty;

    private Boolean isprimary;

    private String supplierpartno;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public String getOrgcountryid() {
        return orgcountryid;
    }

    public void setOrgcountryid(String orgcountryid) {
        this.orgcountryid = orgcountryid == null ? null : orgcountryid.trim();
    }

    public Integer getStddlvday() {
        return stddlvday;
    }

    public void setStddlvday(Integer stddlvday) {
        this.stddlvday = stddlvday;
    }

    public Integer getStddlvdatemaxnumber() {
        return stddlvdatemaxnumber;
    }

    public void setStddlvdatemaxnumber(Integer stddlvdatemaxnumber) {
        this.stddlvdatemaxnumber = stddlvdatemaxnumber;
    }

    public Integer getMaxprodqty() {
        return maxprodqty;
    }

    public void setMaxprodqty(Integer maxprodqty) {
        this.maxprodqty = maxprodqty;
    }

    public Boolean getEnablemaxprodqty() {
        return enablemaxprodqty;
    }

    public void setEnablemaxprodqty(Boolean enablemaxprodqty) {
        this.enablemaxprodqty = enablemaxprodqty;
    }

    public Boolean getIsprimary() {
        return isprimary;
    }

    public void setIsprimary(Boolean isprimary) {
        this.isprimary = isprimary;
    }

    public String getSupplierpartno() {
        return supplierpartno;
    }

    public void setSupplierpartno(String supplierpartno) {
        this.supplierpartno = supplierpartno == null ? null : supplierpartno.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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
        ProductDeliveryProd other = (ProductDeliveryProd) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getSupplyid() == null ? other.getSupplyid() == null : this.getSupplyid().equals(other.getSupplyid()))
            && (this.getOrgcountryid() == null ? other.getOrgcountryid() == null : this.getOrgcountryid().equals(other.getOrgcountryid()))
            && (this.getStddlvday() == null ? other.getStddlvday() == null : this.getStddlvday().equals(other.getStddlvday()))
            && (this.getStddlvdatemaxnumber() == null ? other.getStddlvdatemaxnumber() == null : this.getStddlvdatemaxnumber().equals(other.getStddlvdatemaxnumber()))
            && (this.getMaxprodqty() == null ? other.getMaxprodqty() == null : this.getMaxprodqty().equals(other.getMaxprodqty()))
            && (this.getEnablemaxprodqty() == null ? other.getEnablemaxprodqty() == null : this.getEnablemaxprodqty().equals(other.getEnablemaxprodqty()))
            && (this.getIsprimary() == null ? other.getIsprimary() == null : this.getIsprimary().equals(other.getIsprimary()))
            && (this.getSupplierpartno() == null ? other.getSupplierpartno() == null : this.getSupplierpartno().equals(other.getSupplierpartno()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getSupplyid() == null) ? 0 : getSupplyid().hashCode());
        result = prime * result + ((getOrgcountryid() == null) ? 0 : getOrgcountryid().hashCode());
        result = prime * result + ((getStddlvday() == null) ? 0 : getStddlvday().hashCode());
        result = prime * result + ((getStddlvdatemaxnumber() == null) ? 0 : getStddlvdatemaxnumber().hashCode());
        result = prime * result + ((getMaxprodqty() == null) ? 0 : getMaxprodqty().hashCode());
        result = prime * result + ((getEnablemaxprodqty() == null) ? 0 : getEnablemaxprodqty().hashCode());
        result = prime * result + ((getIsprimary() == null) ? 0 : getIsprimary().hashCode());
        result = prime * result + ((getSupplierpartno() == null) ? 0 : getSupplierpartno().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }
}