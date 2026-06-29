package com.sales.ops.db.entity;

import java.io.Serializable;

public class SalesinvoiceArrivalConfig implements Serializable {
    private String deptno;

    private String deptnoOld;

    private String name;

    private String province;

    private String branch;

    private String parentname;

    private Integer arrival;

    private static final long serialVersionUID = 1L;

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getDeptnoOld() {
        return deptnoOld;
    }

    public void setDeptnoOld(String deptnoOld) {
        this.deptnoOld = deptnoOld == null ? null : deptnoOld.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch == null ? null : branch.trim();
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname == null ? null : parentname.trim();
    }

    public Integer getArrival() {
        return arrival;
    }

    public void setArrival(Integer arrival) {
        this.arrival = arrival;
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
        SalesinvoiceArrivalConfig other = (SalesinvoiceArrivalConfig) that;
        return (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getDeptnoOld() == null ? other.getDeptnoOld() == null : this.getDeptnoOld().equals(other.getDeptnoOld()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getBranch() == null ? other.getBranch() == null : this.getBranch().equals(other.getBranch()))
            && (this.getParentname() == null ? other.getParentname() == null : this.getParentname().equals(other.getParentname()))
            && (this.getArrival() == null ? other.getArrival() == null : this.getArrival().equals(other.getArrival()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getDeptnoOld() == null) ? 0 : getDeptnoOld().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getBranch() == null) ? 0 : getBranch().hashCode());
        result = prime * result + ((getParentname() == null) ? 0 : getParentname().hashCode());
        result = prime * result + ((getArrival() == null) ? 0 : getArrival().hashCode());
        return result;
    }
}