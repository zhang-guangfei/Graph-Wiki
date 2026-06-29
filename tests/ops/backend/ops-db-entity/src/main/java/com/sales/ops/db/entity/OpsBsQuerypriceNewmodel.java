package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsBsQuerypriceNewmodel implements Serializable {
    private Integer id;

    private String modelno;

    private String supplierid;

    private String orgcountryid;

    private Integer stddlvday;

    private Integer stddlvdatemaxnumber;

    private String statuscode;

    private Date inserttime;

    private String insertuser;

    private Date updatetime;

    private String updateuser;

    private String sourcetype;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

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

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode == null ? null : statuscode.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public String getInsertuser() {
        return insertuser;
    }

    public void setInsertuser(String insertuser) {
        this.insertuser = insertuser == null ? null : insertuser.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype == null ? null : sourcetype.trim();
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
        OpsBsQuerypriceNewmodel other = (OpsBsQuerypriceNewmodel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getOrgcountryid() == null ? other.getOrgcountryid() == null : this.getOrgcountryid().equals(other.getOrgcountryid()))
            && (this.getStddlvday() == null ? other.getStddlvday() == null : this.getStddlvday().equals(other.getStddlvday()))
            && (this.getStddlvdatemaxnumber() == null ? other.getStddlvdatemaxnumber() == null : this.getStddlvdatemaxnumber().equals(other.getStddlvdatemaxnumber()))
            && (this.getStatuscode() == null ? other.getStatuscode() == null : this.getStatuscode().equals(other.getStatuscode()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()))
            && (this.getInsertuser() == null ? other.getInsertuser() == null : this.getInsertuser().equals(other.getInsertuser()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getUpdateuser() == null ? other.getUpdateuser() == null : this.getUpdateuser().equals(other.getUpdateuser()))
            && (this.getSourcetype() == null ? other.getSourcetype() == null : this.getSourcetype().equals(other.getSourcetype()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getOrgcountryid() == null) ? 0 : getOrgcountryid().hashCode());
        result = prime * result + ((getStddlvday() == null) ? 0 : getStddlvday().hashCode());
        result = prime * result + ((getStddlvdatemaxnumber() == null) ? 0 : getStddlvdatemaxnumber().hashCode());
        result = prime * result + ((getStatuscode() == null) ? 0 : getStatuscode().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        result = prime * result + ((getInsertuser() == null) ? 0 : getInsertuser().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getUpdateuser() == null) ? 0 : getUpdateuser().hashCode());
        result = prime * result + ((getSourcetype() == null) ? 0 : getSourcetype().hashCode());
        return result;
    }
}