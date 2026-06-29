package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class Organizationrelation implements Serializable {
    private String id;

    private String parentid;

    private String formalid;

    private Integer organizationtype;

    private String hrunitid;

    private String cnunitid;

    private Date updatetime;

    private String remark;

    private String empinvoice;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getFormalid() {
        return formalid;
    }

    public void setFormalid(String formalid) {
        this.formalid = formalid == null ? null : formalid.trim();
    }

    public Integer getOrganizationtype() {
        return organizationtype;
    }

    public void setOrganizationtype(Integer organizationtype) {
        this.organizationtype = organizationtype;
    }

    public String getHrunitid() {
        return hrunitid;
    }

    public void setHrunitid(String hrunitid) {
        this.hrunitid = hrunitid == null ? null : hrunitid.trim();
    }

    public String getCnunitid() {
        return cnunitid;
    }

    public void setCnunitid(String cnunitid) {
        this.cnunitid = cnunitid == null ? null : cnunitid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getEmpinvoice() {
        return empinvoice;
    }

    public void setEmpinvoice(String empinvoice) {
        this.empinvoice = empinvoice == null ? null : empinvoice.trim();
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
        Organizationrelation other = (Organizationrelation) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParentid() == null ? other.getParentid() == null : this.getParentid().equals(other.getParentid()))
            && (this.getFormalid() == null ? other.getFormalid() == null : this.getFormalid().equals(other.getFormalid()))
            && (this.getOrganizationtype() == null ? other.getOrganizationtype() == null : this.getOrganizationtype().equals(other.getOrganizationtype()))
            && (this.getHrunitid() == null ? other.getHrunitid() == null : this.getHrunitid().equals(other.getHrunitid()))
            && (this.getCnunitid() == null ? other.getCnunitid() == null : this.getCnunitid().equals(other.getCnunitid()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getEmpinvoice() == null ? other.getEmpinvoice() == null : this.getEmpinvoice().equals(other.getEmpinvoice()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParentid() == null) ? 0 : getParentid().hashCode());
        result = prime * result + ((getFormalid() == null) ? 0 : getFormalid().hashCode());
        result = prime * result + ((getOrganizationtype() == null) ? 0 : getOrganizationtype().hashCode());
        result = prime * result + ((getHrunitid() == null) ? 0 : getHrunitid().hashCode());
        result = prime * result + ((getCnunitid() == null) ? 0 : getCnunitid().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getEmpinvoice() == null) ? 0 : getEmpinvoice().hashCode());
        return result;
    }
}