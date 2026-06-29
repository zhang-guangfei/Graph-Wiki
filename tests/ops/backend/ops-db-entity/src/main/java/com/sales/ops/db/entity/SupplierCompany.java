package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class SupplierCompany implements Serializable {
    private String id;

    private String name;

    private String countryid;

    private String currencyid;

    private Date updatetime;

    private String updator;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCountryid() {
        return countryid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid == null ? null : countryid.trim();
    }

    public String getCurrencyid() {
        return currencyid;
    }

    public void setCurrencyid(String currencyid) {
        this.currencyid = currencyid == null ? null : currencyid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
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
        SupplierCompany other = (SupplierCompany) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCountryid() == null ? other.getCountryid() == null : this.getCountryid().equals(other.getCountryid()))
            && (this.getCurrencyid() == null ? other.getCurrencyid() == null : this.getCurrencyid().equals(other.getCurrencyid()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCountryid() == null) ? 0 : getCountryid().hashCode());
        result = prime * result + ((getCurrencyid() == null) ? 0 : getCurrencyid().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        return result;
    }
}