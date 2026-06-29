package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsPayTerm implements Serializable {
    private String customerno;

    private Integer creditterm;

    private String creditgrade;

    private String payterm;

    private BigDecimal creditlimit;

    private BigDecimal gracelimit;

    private BigDecimal adjustlimit;

    private Date createtime;

    private String createuser;

    private Date updatetime;

    private String updateuser;

    private static final long serialVersionUID = 1L;

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public Integer getCreditterm() {
        return creditterm;
    }

    public void setCreditterm(Integer creditterm) {
        this.creditterm = creditterm;
    }

    public String getCreditgrade() {
        return creditgrade;
    }

    public void setCreditgrade(String creditgrade) {
        this.creditgrade = creditgrade == null ? null : creditgrade.trim();
    }

    public String getPayterm() {
        return payterm;
    }

    public void setPayterm(String payterm) {
        this.payterm = payterm == null ? null : payterm.trim();
    }

    public BigDecimal getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(BigDecimal creditlimit) {
        this.creditlimit = creditlimit;
    }

    public BigDecimal getGracelimit() {
        return gracelimit;
    }

    public void setGracelimit(BigDecimal gracelimit) {
        this.gracelimit = gracelimit;
    }

    public BigDecimal getAdjustlimit() {
        return adjustlimit;
    }

    public void setAdjustlimit(BigDecimal adjustlimit) {
        this.adjustlimit = adjustlimit;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
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
        OpsPayTerm other = (OpsPayTerm) that;
        return (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getCreditterm() == null ? other.getCreditterm() == null : this.getCreditterm().equals(other.getCreditterm()))
            && (this.getCreditgrade() == null ? other.getCreditgrade() == null : this.getCreditgrade().equals(other.getCreditgrade()))
            && (this.getPayterm() == null ? other.getPayterm() == null : this.getPayterm().equals(other.getPayterm()))
            && (this.getCreditlimit() == null ? other.getCreditlimit() == null : this.getCreditlimit().equals(other.getCreditlimit()))
            && (this.getGracelimit() == null ? other.getGracelimit() == null : this.getGracelimit().equals(other.getGracelimit()))
            && (this.getAdjustlimit() == null ? other.getAdjustlimit() == null : this.getAdjustlimit().equals(other.getAdjustlimit()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getUpdateuser() == null ? other.getUpdateuser() == null : this.getUpdateuser().equals(other.getUpdateuser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getCreditterm() == null) ? 0 : getCreditterm().hashCode());
        result = prime * result + ((getCreditgrade() == null) ? 0 : getCreditgrade().hashCode());
        result = prime * result + ((getPayterm() == null) ? 0 : getPayterm().hashCode());
        result = prime * result + ((getCreditlimit() == null) ? 0 : getCreditlimit().hashCode());
        result = prime * result + ((getGracelimit() == null) ? 0 : getGracelimit().hashCode());
        result = prime * result + ((getAdjustlimit() == null) ? 0 : getAdjustlimit().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getUpdateuser() == null) ? 0 : getUpdateuser().hashCode());
        return result;
    }
}