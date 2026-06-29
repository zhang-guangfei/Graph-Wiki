package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductDeli extends ProductDeliKey implements Serializable {
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
}