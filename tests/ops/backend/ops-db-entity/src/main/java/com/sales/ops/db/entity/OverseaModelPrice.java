package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OverseaModelPrice implements Serializable {
    private Integer id;

    private String modelno;

    private String ecode;

    private String countryId;

    private Integer quantity;

    private BigDecimal priceOversea;

    private BigDecimal reto;

    private BigDecimal custTax;

    private String qttType;

    private BigDecimal weight;

    private BigDecimal transFee;

    private BigDecimal otherFee;

    private BigDecimal priceStdrmb;

    private Date upddate;

    private String appartnumber;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode == null ? null : ecode.trim();
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId == null ? null : countryId.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceOversea() {
        return priceOversea;
    }

    public void setPriceOversea(BigDecimal priceOversea) {
        this.priceOversea = priceOversea;
    }

    public BigDecimal getReto() {
        return reto;
    }

    public void setReto(BigDecimal reto) {
        this.reto = reto;
    }

    public BigDecimal getCustTax() {
        return custTax;
    }

    public void setCustTax(BigDecimal custTax) {
        this.custTax = custTax;
    }

    public String getQttType() {
        return qttType;
    }

    public void setQttType(String qttType) {
        this.qttType = qttType == null ? null : qttType.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public BigDecimal getPriceStdrmb() {
        return priceStdrmb;
    }

    public void setPriceStdrmb(BigDecimal priceStdrmb) {
        this.priceStdrmb = priceStdrmb;
    }

    public Date getUpddate() {
        return upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    public String getAppartnumber() {
        return appartnumber;
    }

    public void setAppartnumber(String appartnumber) {
        this.appartnumber = appartnumber == null ? null : appartnumber.trim();
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
        OverseaModelPrice other = (OverseaModelPrice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getEcode() == null ? other.getEcode() == null : this.getEcode().equals(other.getEcode()))
            && (this.getCountryId() == null ? other.getCountryId() == null : this.getCountryId().equals(other.getCountryId()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPriceOversea() == null ? other.getPriceOversea() == null : this.getPriceOversea().equals(other.getPriceOversea()))
            && (this.getReto() == null ? other.getReto() == null : this.getReto().equals(other.getReto()))
            && (this.getCustTax() == null ? other.getCustTax() == null : this.getCustTax().equals(other.getCustTax()))
            && (this.getQttType() == null ? other.getQttType() == null : this.getQttType().equals(other.getQttType()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getTransFee() == null ? other.getTransFee() == null : this.getTransFee().equals(other.getTransFee()))
            && (this.getOtherFee() == null ? other.getOtherFee() == null : this.getOtherFee().equals(other.getOtherFee()))
            && (this.getPriceStdrmb() == null ? other.getPriceStdrmb() == null : this.getPriceStdrmb().equals(other.getPriceStdrmb()))
            && (this.getUpddate() == null ? other.getUpddate() == null : this.getUpddate().equals(other.getUpddate()))
            && (this.getAppartnumber() == null ? other.getAppartnumber() == null : this.getAppartnumber().equals(other.getAppartnumber()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getEcode() == null) ? 0 : getEcode().hashCode());
        result = prime * result + ((getCountryId() == null) ? 0 : getCountryId().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPriceOversea() == null) ? 0 : getPriceOversea().hashCode());
        result = prime * result + ((getReto() == null) ? 0 : getReto().hashCode());
        result = prime * result + ((getCustTax() == null) ? 0 : getCustTax().hashCode());
        result = prime * result + ((getQttType() == null) ? 0 : getQttType().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getTransFee() == null) ? 0 : getTransFee().hashCode());
        result = prime * result + ((getOtherFee() == null) ? 0 : getOtherFee().hashCode());
        result = prime * result + ((getPriceStdrmb() == null) ? 0 : getPriceStdrmb().hashCode());
        result = prime * result + ((getUpddate() == null) ? 0 : getUpddate().hashCode());
        result = prime * result + ((getAppartnumber() == null) ? 0 : getAppartnumber().hashCode());
        return result;
    }
}