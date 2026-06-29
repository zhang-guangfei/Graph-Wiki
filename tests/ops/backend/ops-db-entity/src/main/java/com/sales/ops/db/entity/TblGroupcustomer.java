package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class TblGroupcustomer implements Serializable {
    private String customerno;

    private Integer id;

    private String customername;

    private String companyid;

    private Date updatetime;

    private String countrycode;

    private String dlvdeptno;

    private String deptno;

    private String province;

    private String city;

    private String district;

    private static final long serialVersionUID = 1L;

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid == null ? null : companyid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode == null ? null : countrycode.trim();
    }

    public String getDlvdeptno() {
        return dlvdeptno;
    }

    public void setDlvdeptno(String dlvdeptno) {
        this.dlvdeptno = dlvdeptno == null ? null : dlvdeptno.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
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
        TblGroupcustomer other = (TblGroupcustomer) that;
        return (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustomername() == null ? other.getCustomername() == null : this.getCustomername().equals(other.getCustomername()))
            && (this.getCompanyid() == null ? other.getCompanyid() == null : this.getCompanyid().equals(other.getCompanyid()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getCountrycode() == null ? other.getCountrycode() == null : this.getCountrycode().equals(other.getCountrycode()))
            && (this.getDlvdeptno() == null ? other.getDlvdeptno() == null : this.getDlvdeptno().equals(other.getDlvdeptno()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getDistrict() == null ? other.getDistrict() == null : this.getDistrict().equals(other.getDistrict()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustomername() == null) ? 0 : getCustomername().hashCode());
        result = prime * result + ((getCompanyid() == null) ? 0 : getCompanyid().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getCountrycode() == null) ? 0 : getCountrycode().hashCode());
        result = prime * result + ((getDlvdeptno() == null) ? 0 : getDlvdeptno().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getDistrict() == null) ? 0 : getDistrict().hashCode());
        return result;
    }
}