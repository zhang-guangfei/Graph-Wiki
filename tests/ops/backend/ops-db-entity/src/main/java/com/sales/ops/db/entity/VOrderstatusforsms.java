package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VOrderstatusforsms implements Serializable {
    private String rorderNo;

    private String ororderno;

    private Date updateTime;

    private String rorderFno;

    private Integer rorderItem;

    private Integer quantity;

    private String modelNo;

    private Short status;

    private Date dlvDate;

    private Integer expQty;

    private Date rorddate;

    private BigDecimal eprice;

    private BigDecimal price;

    private BigDecimal discount;

    private static final long serialVersionUID = 1L;

    public String getRorderNo() {
        return rorderNo;
    }

    public void setRorderNo(String rorderNo) {
        this.rorderNo = rorderNo == null ? null : rorderNo.trim();
    }

    public String getOrorderno() {
        return ororderno;
    }

    public void setOrorderno(String ororderno) {
        this.ororderno = ororderno == null ? null : ororderno.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRorderFno() {
        return rorderFno;
    }

    public void setRorderFno(String rorderFno) {
        this.rorderFno = rorderFno == null ? null : rorderFno.trim();
    }

    public Integer getRorderItem() {
        return rorderItem;
    }

    public void setRorderItem(Integer rorderItem) {
        this.rorderItem = rorderItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public Integer getExpQty() {
        return expQty;
    }

    public void setExpQty(Integer expQty) {
        this.expQty = expQty;
    }

    public Date getRorddate() {
        return rorddate;
    }

    public void setRorddate(Date rorddate) {
        this.rorddate = rorddate;
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
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
        VOrderstatusforsms other = (VOrderstatusforsms) that;
        return (this.getRorderNo() == null ? other.getRorderNo() == null : this.getRorderNo().equals(other.getRorderNo()))
            && (this.getOrorderno() == null ? other.getOrorderno() == null : this.getOrorderno().equals(other.getOrorderno()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getRorderFno() == null ? other.getRorderFno() == null : this.getRorderFno().equals(other.getRorderFno()))
            && (this.getRorderItem() == null ? other.getRorderItem() == null : this.getRorderItem().equals(other.getRorderItem()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDlvDate() == null ? other.getDlvDate() == null : this.getDlvDate().equals(other.getDlvDate()))
            && (this.getExpQty() == null ? other.getExpQty() == null : this.getExpQty().equals(other.getExpQty()))
            && (this.getRorddate() == null ? other.getRorddate() == null : this.getRorddate().equals(other.getRorddate()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRorderNo() == null) ? 0 : getRorderNo().hashCode());
        result = prime * result + ((getOrorderno() == null) ? 0 : getOrorderno().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getRorderFno() == null) ? 0 : getRorderFno().hashCode());
        result = prime * result + ((getRorderItem() == null) ? 0 : getRorderItem().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDlvDate() == null) ? 0 : getDlvDate().hashCode());
        result = prime * result + ((getExpQty() == null) ? 0 : getExpQty().hashCode());
        result = prime * result + ((getRorddate() == null) ? 0 : getRorddate().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        return result;
    }
}