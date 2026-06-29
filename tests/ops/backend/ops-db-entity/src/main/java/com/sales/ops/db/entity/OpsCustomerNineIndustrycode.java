package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsCustomerNineIndustrycode implements Serializable {
    private String customerno;

    private Integer ordernumber;

    private String industrymediamcode;

    private String industry4;

    private String industry56;

    private Integer industrypercent;

    private String createduser;

    private Date createdtime;

    private String updateduser;

    private Date updatedtime;

    private static final long serialVersionUID = 1L;

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public Integer getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(Integer ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getIndustrymediamcode() {
        return industrymediamcode;
    }

    public void setIndustrymediamcode(String industrymediamcode) {
        this.industrymediamcode = industrymediamcode == null ? null : industrymediamcode.trim();
    }

    public String getIndustry4() {
        return industry4;
    }

    public void setIndustry4(String industry4) {
        this.industry4 = industry4 == null ? null : industry4.trim();
    }

    public String getIndustry56() {
        return industry56;
    }

    public void setIndustry56(String industry56) {
        this.industry56 = industry56 == null ? null : industry56.trim();
    }

    public Integer getIndustrypercent() {
        return industrypercent;
    }

    public void setIndustrypercent(Integer industrypercent) {
        this.industrypercent = industrypercent;
    }

    public String getCreateduser() {
        return createduser;
    }

    public void setCreateduser(String createduser) {
        this.createduser = createduser == null ? null : createduser.trim();
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public String getUpdateduser() {
        return updateduser;
    }

    public void setUpdateduser(String updateduser) {
        this.updateduser = updateduser == null ? null : updateduser.trim();
    }

    public Date getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(Date updatedtime) {
        this.updatedtime = updatedtime;
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
        OpsCustomerNineIndustrycode other = (OpsCustomerNineIndustrycode) that;
        return (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getOrdernumber() == null ? other.getOrdernumber() == null : this.getOrdernumber().equals(other.getOrdernumber()))
            && (this.getIndustrymediamcode() == null ? other.getIndustrymediamcode() == null : this.getIndustrymediamcode().equals(other.getIndustrymediamcode()))
            && (this.getIndustry4() == null ? other.getIndustry4() == null : this.getIndustry4().equals(other.getIndustry4()))
            && (this.getIndustry56() == null ? other.getIndustry56() == null : this.getIndustry56().equals(other.getIndustry56()))
            && (this.getIndustrypercent() == null ? other.getIndustrypercent() == null : this.getIndustrypercent().equals(other.getIndustrypercent()))
            && (this.getCreateduser() == null ? other.getCreateduser() == null : this.getCreateduser().equals(other.getCreateduser()))
            && (this.getCreatedtime() == null ? other.getCreatedtime() == null : this.getCreatedtime().equals(other.getCreatedtime()))
            && (this.getUpdateduser() == null ? other.getUpdateduser() == null : this.getUpdateduser().equals(other.getUpdateduser()))
            && (this.getUpdatedtime() == null ? other.getUpdatedtime() == null : this.getUpdatedtime().equals(other.getUpdatedtime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getOrdernumber() == null) ? 0 : getOrdernumber().hashCode());
        result = prime * result + ((getIndustrymediamcode() == null) ? 0 : getIndustrymediamcode().hashCode());
        result = prime * result + ((getIndustry4() == null) ? 0 : getIndustry4().hashCode());
        result = prime * result + ((getIndustry56() == null) ? 0 : getIndustry56().hashCode());
        result = prime * result + ((getIndustrypercent() == null) ? 0 : getIndustrypercent().hashCode());
        result = prime * result + ((getCreateduser() == null) ? 0 : getCreateduser().hashCode());
        result = prime * result + ((getCreatedtime() == null) ? 0 : getCreatedtime().hashCode());
        result = prime * result + ((getUpdateduser() == null) ? 0 : getUpdateduser().hashCode());
        result = prime * result + ((getUpdatedtime() == null) ? 0 : getUpdatedtime().hashCode());
        return result;
    }
}