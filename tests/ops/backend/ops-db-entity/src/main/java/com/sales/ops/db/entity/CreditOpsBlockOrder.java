package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class CreditOpsBlockOrder implements Serializable {
    private Integer id;

    private String orderno;

    private Integer orderitem;

    private Date inserttime;

    private Boolean ischangestatus;

    private String creator;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Integer getOrderitem() {
        return orderitem;
    }

    public void setOrderitem(Integer orderitem) {
        this.orderitem = orderitem;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public Boolean getIschangestatus() {
        return ischangestatus;
    }

    public void setIschangestatus(Boolean ischangestatus) {
        this.ischangestatus = ischangestatus;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
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
        CreditOpsBlockOrder other = (CreditOpsBlockOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getOrderitem() == null ? other.getOrderitem() == null : this.getOrderitem().equals(other.getOrderitem()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()))
            && (this.getIschangestatus() == null ? other.getIschangestatus() == null : this.getIschangestatus().equals(other.getIschangestatus()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getOrderitem() == null) ? 0 : getOrderitem().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        result = prime * result + ((getIschangestatus() == null) ? 0 : getIschangestatus().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        return result;
    }
}