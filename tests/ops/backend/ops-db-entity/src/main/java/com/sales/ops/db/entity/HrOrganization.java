package com.sales.ops.db.entity;

import java.io.Serializable;

public class HrOrganization implements Serializable {
    private String id;

    private String parentid;

    private String name;

    private String fullname;

    private String level;

    private String status;

    private Boolean salescustomized;

    private String companycode;

    private String companyname;

    private String unitcode;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname == null ? null : fullname.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Boolean getSalescustomized() {
        return salescustomized;
    }

    public void setSalescustomized(Boolean salescustomized) {
        this.salescustomized = salescustomized;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode == null ? null : companycode.trim();
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname == null ? null : companyname.trim();
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode == null ? null : unitcode.trim();
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
        HrOrganization other = (HrOrganization) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParentid() == null ? other.getParentid() == null : this.getParentid().equals(other.getParentid()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getFullname() == null ? other.getFullname() == null : this.getFullname().equals(other.getFullname()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSalescustomized() == null ? other.getSalescustomized() == null : this.getSalescustomized().equals(other.getSalescustomized()))
            && (this.getCompanycode() == null ? other.getCompanycode() == null : this.getCompanycode().equals(other.getCompanycode()))
            && (this.getCompanyname() == null ? other.getCompanyname() == null : this.getCompanyname().equals(other.getCompanyname()))
            && (this.getUnitcode() == null ? other.getUnitcode() == null : this.getUnitcode().equals(other.getUnitcode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParentid() == null) ? 0 : getParentid().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getFullname() == null) ? 0 : getFullname().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSalescustomized() == null) ? 0 : getSalescustomized().hashCode());
        result = prime * result + ((getCompanycode() == null) ? 0 : getCompanycode().hashCode());
        result = prime * result + ((getCompanyname() == null) ? 0 : getCompanyname().hashCode());
        result = prime * result + ((getUnitcode() == null) ? 0 : getUnitcode().hashCode());
        return result;
    }
}