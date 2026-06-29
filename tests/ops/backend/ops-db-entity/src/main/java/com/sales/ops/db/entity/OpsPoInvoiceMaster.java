package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class OpsPoInvoiceMaster extends OpsPoInvoiceMasterKey implements Serializable {
    private Integer id;

    private BigDecimal amountjp;

    private BigDecimal amountrmb;

    private BigDecimal customfee;

    private BigDecimal transfee;

    private BigDecimal otherfee;

    private Double rate;

    private String prodcountry;

    private String moneytype;

    private String impflag;

    private String supplierinvoiceno;

    private String suppliercode;

    private Date shipdate;

    private Date costdate;

    private String optstate;

    private BigDecimal adjamount;

    private BigDecimal amountrmbM;

    private BigDecimal amountrmbP;

    private Integer gzid;

    private Date supplierinvdate;

    private Date pocostdate;

    private String ownercode;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private byte[] rv;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmountjp() {
        return amountjp;
    }

    public void setAmountjp(BigDecimal amountjp) {
        this.amountjp = amountjp;
    }

    public BigDecimal getAmountrmb() {
        return amountrmb;
    }

    public void setAmountrmb(BigDecimal amountrmb) {
        this.amountrmb = amountrmb;
    }

    public BigDecimal getCustomfee() {
        return customfee;
    }

    public void setCustomfee(BigDecimal customfee) {
        this.customfee = customfee;
    }

    public BigDecimal getTransfee() {
        return transfee;
    }

    public void setTransfee(BigDecimal transfee) {
        this.transfee = transfee;
    }

    public BigDecimal getOtherfee() {
        return otherfee;
    }

    public void setOtherfee(BigDecimal otherfee) {
        this.otherfee = otherfee;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getProdcountry() {
        return prodcountry;
    }

    public void setProdcountry(String prodcountry) {
        this.prodcountry = prodcountry == null ? null : prodcountry.trim();
    }

    public String getMoneytype() {
        return moneytype;
    }

    public void setMoneytype(String moneytype) {
        this.moneytype = moneytype == null ? null : moneytype.trim();
    }

    public String getImpflag() {
        return impflag;
    }

    public void setImpflag(String impflag) {
        this.impflag = impflag == null ? null : impflag.trim();
    }

    public String getSupplierinvoiceno() {
        return supplierinvoiceno;
    }

    public void setSupplierinvoiceno(String supplierinvoiceno) {
        this.supplierinvoiceno = supplierinvoiceno == null ? null : supplierinvoiceno.trim();
    }

    public String getSuppliercode() {
        return suppliercode;
    }

    public void setSuppliercode(String suppliercode) {
        this.suppliercode = suppliercode == null ? null : suppliercode.trim();
    }

    public Date getShipdate() {
        return shipdate;
    }

    public void setShipdate(Date shipdate) {
        this.shipdate = shipdate;
    }

    public Date getCostdate() {
        return costdate;
    }

    public void setCostdate(Date costdate) {
        this.costdate = costdate;
    }

    public String getOptstate() {
        return optstate;
    }

    public void setOptstate(String optstate) {
        this.optstate = optstate == null ? null : optstate.trim();
    }

    public BigDecimal getAdjamount() {
        return adjamount;
    }

    public void setAdjamount(BigDecimal adjamount) {
        this.adjamount = adjamount;
    }

    public BigDecimal getAmountrmbM() {
        return amountrmbM;
    }

    public void setAmountrmbM(BigDecimal amountrmbM) {
        this.amountrmbM = amountrmbM;
    }

    public BigDecimal getAmountrmbP() {
        return amountrmbP;
    }

    public void setAmountrmbP(BigDecimal amountrmbP) {
        this.amountrmbP = amountrmbP;
    }

    public Integer getGzid() {
        return gzid;
    }

    public void setGzid(Integer gzid) {
        this.gzid = gzid;
    }

    public Date getSupplierinvdate() {
        return supplierinvdate;
    }

    public void setSupplierinvdate(Date supplierinvdate) {
        this.supplierinvdate = supplierinvdate;
    }

    public Date getPocostdate() {
        return pocostdate;
    }

    public void setPocostdate(Date pocostdate) {
        this.pocostdate = pocostdate;
    }

    public String getOwnercode() {
        return ownercode;
    }

    public void setOwnercode(String ownercode) {
        this.ownercode = ownercode == null ? null : ownercode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public byte[] getRv() {
        return rv;
    }

    public void setRv(byte[] rv) {
        this.rv = rv;
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
        OpsPoInvoiceMaster other = (OpsPoInvoiceMaster) that;
        return (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getImpdate() == null ? other.getImpdate() == null : this.getImpdate().equals(other.getImpdate()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAmountjp() == null ? other.getAmountjp() == null : this.getAmountjp().equals(other.getAmountjp()))
            && (this.getAmountrmb() == null ? other.getAmountrmb() == null : this.getAmountrmb().equals(other.getAmountrmb()))
            && (this.getCustomfee() == null ? other.getCustomfee() == null : this.getCustomfee().equals(other.getCustomfee()))
            && (this.getTransfee() == null ? other.getTransfee() == null : this.getTransfee().equals(other.getTransfee()))
            && (this.getOtherfee() == null ? other.getOtherfee() == null : this.getOtherfee().equals(other.getOtherfee()))
            && (this.getRate() == null ? other.getRate() == null : this.getRate().equals(other.getRate()))
            && (this.getProdcountry() == null ? other.getProdcountry() == null : this.getProdcountry().equals(other.getProdcountry()))
            && (this.getMoneytype() == null ? other.getMoneytype() == null : this.getMoneytype().equals(other.getMoneytype()))
            && (this.getImpflag() == null ? other.getImpflag() == null : this.getImpflag().equals(other.getImpflag()))
            && (this.getSupplierinvoiceno() == null ? other.getSupplierinvoiceno() == null : this.getSupplierinvoiceno().equals(other.getSupplierinvoiceno()))
            && (this.getSuppliercode() == null ? other.getSuppliercode() == null : this.getSuppliercode().equals(other.getSuppliercode()))
            && (this.getShipdate() == null ? other.getShipdate() == null : this.getShipdate().equals(other.getShipdate()))
            && (this.getCostdate() == null ? other.getCostdate() == null : this.getCostdate().equals(other.getCostdate()))
            && (this.getOptstate() == null ? other.getOptstate() == null : this.getOptstate().equals(other.getOptstate()))
            && (this.getAdjamount() == null ? other.getAdjamount() == null : this.getAdjamount().equals(other.getAdjamount()))
            && (this.getAmountrmbM() == null ? other.getAmountrmbM() == null : this.getAmountrmbM().equals(other.getAmountrmbM()))
            && (this.getAmountrmbP() == null ? other.getAmountrmbP() == null : this.getAmountrmbP().equals(other.getAmountrmbP()))
            && (this.getGzid() == null ? other.getGzid() == null : this.getGzid().equals(other.getGzid()))
            && (this.getSupplierinvdate() == null ? other.getSupplierinvdate() == null : this.getSupplierinvdate().equals(other.getSupplierinvdate()))
            && (this.getPocostdate() == null ? other.getPocostdate() == null : this.getPocostdate().equals(other.getPocostdate()))
            && (this.getOwnercode() == null ? other.getOwnercode() == null : this.getOwnercode().equals(other.getOwnercode()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (Arrays.equals(this.getRv(), other.getRv()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getImpdate() == null) ? 0 : getImpdate().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAmountjp() == null) ? 0 : getAmountjp().hashCode());
        result = prime * result + ((getAmountrmb() == null) ? 0 : getAmountrmb().hashCode());
        result = prime * result + ((getCustomfee() == null) ? 0 : getCustomfee().hashCode());
        result = prime * result + ((getTransfee() == null) ? 0 : getTransfee().hashCode());
        result = prime * result + ((getOtherfee() == null) ? 0 : getOtherfee().hashCode());
        result = prime * result + ((getRate() == null) ? 0 : getRate().hashCode());
        result = prime * result + ((getProdcountry() == null) ? 0 : getProdcountry().hashCode());
        result = prime * result + ((getMoneytype() == null) ? 0 : getMoneytype().hashCode());
        result = prime * result + ((getImpflag() == null) ? 0 : getImpflag().hashCode());
        result = prime * result + ((getSupplierinvoiceno() == null) ? 0 : getSupplierinvoiceno().hashCode());
        result = prime * result + ((getSuppliercode() == null) ? 0 : getSuppliercode().hashCode());
        result = prime * result + ((getShipdate() == null) ? 0 : getShipdate().hashCode());
        result = prime * result + ((getCostdate() == null) ? 0 : getCostdate().hashCode());
        result = prime * result + ((getOptstate() == null) ? 0 : getOptstate().hashCode());
        result = prime * result + ((getAdjamount() == null) ? 0 : getAdjamount().hashCode());
        result = prime * result + ((getAmountrmbM() == null) ? 0 : getAmountrmbM().hashCode());
        result = prime * result + ((getAmountrmbP() == null) ? 0 : getAmountrmbP().hashCode());
        result = prime * result + ((getGzid() == null) ? 0 : getGzid().hashCode());
        result = prime * result + ((getSupplierinvdate() == null) ? 0 : getSupplierinvdate().hashCode());
        result = prime * result + ((getPocostdate() == null) ? 0 : getPocostdate().hashCode());
        result = prime * result + ((getOwnercode() == null) ? 0 : getOwnercode().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + (Arrays.hashCode(getRv()));
        return result;
    }
}