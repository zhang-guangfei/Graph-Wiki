package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductTypeCost implements Serializable {
    private String modelno;

    private Integer minquantity;

    private BigDecimal eprice;

    private BigDecimal costpricer;

    private BigDecimal rerate;

    private Integer sourcetypeid;

    private BigDecimal confirmcost;

    private BigDecimal calculatecost;

    private Date updatetime;

    private String detail;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getMinquantity() {
        return minquantity;
    }

    public void setMinquantity(Integer minquantity) {
        this.minquantity = minquantity;
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public BigDecimal getCostpricer() {
        return costpricer;
    }

    public void setCostpricer(BigDecimal costpricer) {
        this.costpricer = costpricer;
    }

    public BigDecimal getRerate() {
        return rerate;
    }

    public void setRerate(BigDecimal rerate) {
        this.rerate = rerate;
    }

    public Integer getSourcetypeid() {
        return sourcetypeid;
    }

    public void setSourcetypeid(Integer sourcetypeid) {
        this.sourcetypeid = sourcetypeid;
    }

    public BigDecimal getConfirmcost() {
        return confirmcost;
    }

    public void setConfirmcost(BigDecimal confirmcost) {
        this.confirmcost = confirmcost;
    }

    public BigDecimal getCalculatecost() {
        return calculatecost;
    }

    public void setCalculatecost(BigDecimal calculatecost) {
        this.calculatecost = calculatecost;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
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
        ProductTypeCost other = (ProductTypeCost) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getMinquantity() == null ? other.getMinquantity() == null : this.getMinquantity().equals(other.getMinquantity()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getCostpricer() == null ? other.getCostpricer() == null : this.getCostpricer().equals(other.getCostpricer()))
            && (this.getRerate() == null ? other.getRerate() == null : this.getRerate().equals(other.getRerate()))
            && (this.getSourcetypeid() == null ? other.getSourcetypeid() == null : this.getSourcetypeid().equals(other.getSourcetypeid()))
            && (this.getConfirmcost() == null ? other.getConfirmcost() == null : this.getConfirmcost().equals(other.getConfirmcost()))
            && (this.getCalculatecost() == null ? other.getCalculatecost() == null : this.getCalculatecost().equals(other.getCalculatecost()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getDetail() == null ? other.getDetail() == null : this.getDetail().equals(other.getDetail()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getMinquantity() == null) ? 0 : getMinquantity().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getCostpricer() == null) ? 0 : getCostpricer().hashCode());
        result = prime * result + ((getRerate() == null) ? 0 : getRerate().hashCode());
        result = prime * result + ((getSourcetypeid() == null) ? 0 : getSourcetypeid().hashCode());
        result = prime * result + ((getConfirmcost() == null) ? 0 : getConfirmcost().hashCode());
        result = prime * result + ((getCalculatecost() == null) ? 0 : getCalculatecost().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getDetail() == null) ? 0 : getDetail().hashCode());
        return result;
    }
}