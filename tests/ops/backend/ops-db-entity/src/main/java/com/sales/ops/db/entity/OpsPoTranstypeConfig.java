package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoTranstypeConfig implements Serializable {
    private String modelno;

    private Boolean matchtype;

    private String type;

    private String remark;

    private Date updatetime;

    private String transtype;

    private Boolean comparegreater;

    private Integer comparequantity;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Boolean getMatchtype() {
        return matchtype;
    }

    public void setMatchtype(Boolean matchtype) {
        this.matchtype = matchtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype == null ? null : transtype.trim();
    }

    public Boolean getComparegreater() {
        return comparegreater;
    }

    public void setComparegreater(Boolean comparegreater) {
        this.comparegreater = comparegreater;
    }

    public Integer getComparequantity() {
        return comparequantity;
    }

    public void setComparequantity(Integer comparequantity) {
        this.comparequantity = comparequantity;
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
        OpsPoTranstypeConfig other = (OpsPoTranstypeConfig) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getMatchtype() == null ? other.getMatchtype() == null : this.getMatchtype().equals(other.getMatchtype()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getTranstype() == null ? other.getTranstype() == null : this.getTranstype().equals(other.getTranstype()))
            && (this.getComparegreater() == null ? other.getComparegreater() == null : this.getComparegreater().equals(other.getComparegreater()))
            && (this.getComparequantity() == null ? other.getComparequantity() == null : this.getComparequantity().equals(other.getComparequantity()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getMatchtype() == null) ? 0 : getMatchtype().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getTranstype() == null) ? 0 : getTranstype().hashCode());
        result = prime * result + ((getComparegreater() == null) ? 0 : getComparegreater().hashCode());
        result = prime * result + ((getComparequantity() == null) ? 0 : getComparequantity().hashCode());
        return result;
    }
}