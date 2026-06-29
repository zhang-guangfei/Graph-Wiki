package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductRestrictCustomerWhitelistT extends ProductRestrictCustomerWhitelistTKey implements Serializable {
    private Boolean maincustomer;

    private String createid;

    private Date createtime;

    private Date updatetime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Boolean getMaincustomer() {
        return maincustomer;
    }

    public void setMaincustomer(Boolean maincustomer) {
        this.maincustomer = maincustomer;
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid == null ? null : createid.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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
        ProductRestrictCustomerWhitelistT other = (ProductRestrictCustomerWhitelistT) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getMaincustomer() == null ? other.getMaincustomer() == null : this.getMaincustomer().equals(other.getMaincustomer()))
            && (this.getCreateid() == null ? other.getCreateid() == null : this.getCreateid().equals(other.getCreateid()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getMaincustomer() == null) ? 0 : getMaincustomer().hashCode());
        result = prime * result + ((getCreateid() == null) ? 0 : getCreateid().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}