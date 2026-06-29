package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductDeliveryTemp implements Serializable {
    private Long id;

    private String modelno;

    private String orgcountryid;

    private String supplyid;

    private Integer stddlvday;

    private Integer stddlvdatemaxnumber;

    private Integer maxprodqty;

    private Boolean enablemaxprodqty;

    private Boolean isprimary;

    private String supplierpartno;

    private String createuser;

    private Date createtime;

    private String updateuser;

    private Date updatetime;

    private String resultsource;

    private String resultsourcedesc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getOrgcountryid() {
        return orgcountryid;
    }

    public void setOrgcountryid(String orgcountryid) {
        this.orgcountryid = orgcountryid == null ? null : orgcountryid.trim();
    }

    public String getSupplyid() {
        return supplyid;
    }

    public void setSupplyid(String supplyid) {
        this.supplyid = supplyid == null ? null : supplyid.trim();
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

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getResultsource() {
        return resultsource;
    }

    public void setResultsource(String resultsource) {
        this.resultsource = resultsource == null ? null : resultsource.trim();
    }

    public String getResultsourcedesc() {
        return resultsourcedesc;
    }

    public void setResultsourcedesc(String resultsourcedesc) {
        this.resultsourcedesc = resultsourcedesc == null ? null : resultsourcedesc.trim();
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
        ProductDeliveryTemp other = (ProductDeliveryTemp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getOrgcountryid() == null ? other.getOrgcountryid() == null : this.getOrgcountryid().equals(other.getOrgcountryid()))
            && (this.getSupplyid() == null ? other.getSupplyid() == null : this.getSupplyid().equals(other.getSupplyid()))
            && (this.getStddlvday() == null ? other.getStddlvday() == null : this.getStddlvday().equals(other.getStddlvday()))
            && (this.getStddlvdatemaxnumber() == null ? other.getStddlvdatemaxnumber() == null : this.getStddlvdatemaxnumber().equals(other.getStddlvdatemaxnumber()))
            && (this.getMaxprodqty() == null ? other.getMaxprodqty() == null : this.getMaxprodqty().equals(other.getMaxprodqty()))
            && (this.getEnablemaxprodqty() == null ? other.getEnablemaxprodqty() == null : this.getEnablemaxprodqty().equals(other.getEnablemaxprodqty()))
            && (this.getIsprimary() == null ? other.getIsprimary() == null : this.getIsprimary().equals(other.getIsprimary()))
            && (this.getSupplierpartno() == null ? other.getSupplierpartno() == null : this.getSupplierpartno().equals(other.getSupplierpartno()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdateuser() == null ? other.getUpdateuser() == null : this.getUpdateuser().equals(other.getUpdateuser()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getResultsource() == null ? other.getResultsource() == null : this.getResultsource().equals(other.getResultsource()))
            && (this.getResultsourcedesc() == null ? other.getResultsourcedesc() == null : this.getResultsourcedesc().equals(other.getResultsourcedesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getOrgcountryid() == null) ? 0 : getOrgcountryid().hashCode());
        result = prime * result + ((getSupplyid() == null) ? 0 : getSupplyid().hashCode());
        result = prime * result + ((getStddlvday() == null) ? 0 : getStddlvday().hashCode());
        result = prime * result + ((getStddlvdatemaxnumber() == null) ? 0 : getStddlvdatemaxnumber().hashCode());
        result = prime * result + ((getMaxprodqty() == null) ? 0 : getMaxprodqty().hashCode());
        result = prime * result + ((getEnablemaxprodqty() == null) ? 0 : getEnablemaxprodqty().hashCode());
        result = prime * result + ((getIsprimary() == null) ? 0 : getIsprimary().hashCode());
        result = prime * result + ((getSupplierpartno() == null) ? 0 : getSupplierpartno().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdateuser() == null) ? 0 : getUpdateuser().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getResultsource() == null) ? 0 : getResultsource().hashCode());
        result = prime * result + ((getResultsourcedesc() == null) ? 0 : getResultsourcedesc().hashCode());
        return result;
    }
}