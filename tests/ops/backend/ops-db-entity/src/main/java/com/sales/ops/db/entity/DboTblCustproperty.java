package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DboTblCustproperty implements Serializable {
    private String pid;

    private String code;

    private String prodcountryCode;

    private String description;

    private String fullname;

    private String filename;

    private String priceterm;

    private String invno;

    private Date upddate;

    private String companyCodeDb81;

    private String countryCodeDb81;

    private String currencyType;

    private BigDecimal feeInitial;

    private BigDecimal feeAdd;

    private String emailaddr;

    private String invoicenoMax;

    private String remaek;

    private String foreigncurrencyFlag;

    private String financename;

    private static final long serialVersionUID = 1L;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getProdcountryCode() {
        return prodcountryCode;
    }

    public void setProdcountryCode(String prodcountryCode) {
        this.prodcountryCode = prodcountryCode == null ? null : prodcountryCode.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname == null ? null : fullname.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getPriceterm() {
        return priceterm;
    }

    public void setPriceterm(String priceterm) {
        this.priceterm = priceterm == null ? null : priceterm.trim();
    }

    public String getInvno() {
        return invno;
    }

    public void setInvno(String invno) {
        this.invno = invno == null ? null : invno.trim();
    }

    public Date getUpddate() {
        return upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    public String getCompanyCodeDb81() {
        return companyCodeDb81;
    }

    public void setCompanyCodeDb81(String companyCodeDb81) {
        this.companyCodeDb81 = companyCodeDb81 == null ? null : companyCodeDb81.trim();
    }

    public String getCountryCodeDb81() {
        return countryCodeDb81;
    }

    public void setCountryCodeDb81(String countryCodeDb81) {
        this.countryCodeDb81 = countryCodeDb81 == null ? null : countryCodeDb81.trim();
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType == null ? null : currencyType.trim();
    }

    public BigDecimal getFeeInitial() {
        return feeInitial;
    }

    public void setFeeInitial(BigDecimal feeInitial) {
        this.feeInitial = feeInitial;
    }

    public BigDecimal getFeeAdd() {
        return feeAdd;
    }

    public void setFeeAdd(BigDecimal feeAdd) {
        this.feeAdd = feeAdd;
    }

    public String getEmailaddr() {
        return emailaddr;
    }

    public void setEmailaddr(String emailaddr) {
        this.emailaddr = emailaddr == null ? null : emailaddr.trim();
    }

    public String getInvoicenoMax() {
        return invoicenoMax;
    }

    public void setInvoicenoMax(String invoicenoMax) {
        this.invoicenoMax = invoicenoMax == null ? null : invoicenoMax.trim();
    }

    public String getRemaek() {
        return remaek;
    }

    public void setRemaek(String remaek) {
        this.remaek = remaek == null ? null : remaek.trim();
    }

    public String getForeigncurrencyFlag() {
        return foreigncurrencyFlag;
    }

    public void setForeigncurrencyFlag(String foreigncurrencyFlag) {
        this.foreigncurrencyFlag = foreigncurrencyFlag == null ? null : foreigncurrencyFlag.trim();
    }

    public String getFinancename() {
        return financename;
    }

    public void setFinancename(String financename) {
        this.financename = financename == null ? null : financename.trim();
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
        DboTblCustproperty other = (DboTblCustproperty) that;
        return (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getProdcountryCode() == null ? other.getProdcountryCode() == null : this.getProdcountryCode().equals(other.getProdcountryCode()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getFullname() == null ? other.getFullname() == null : this.getFullname().equals(other.getFullname()))
            && (this.getFilename() == null ? other.getFilename() == null : this.getFilename().equals(other.getFilename()))
            && (this.getPriceterm() == null ? other.getPriceterm() == null : this.getPriceterm().equals(other.getPriceterm()))
            && (this.getInvno() == null ? other.getInvno() == null : this.getInvno().equals(other.getInvno()))
            && (this.getUpddate() == null ? other.getUpddate() == null : this.getUpddate().equals(other.getUpddate()))
            && (this.getCompanyCodeDb81() == null ? other.getCompanyCodeDb81() == null : this.getCompanyCodeDb81().equals(other.getCompanyCodeDb81()))
            && (this.getCountryCodeDb81() == null ? other.getCountryCodeDb81() == null : this.getCountryCodeDb81().equals(other.getCountryCodeDb81()))
            && (this.getCurrencyType() == null ? other.getCurrencyType() == null : this.getCurrencyType().equals(other.getCurrencyType()))
            && (this.getFeeInitial() == null ? other.getFeeInitial() == null : this.getFeeInitial().equals(other.getFeeInitial()))
            && (this.getFeeAdd() == null ? other.getFeeAdd() == null : this.getFeeAdd().equals(other.getFeeAdd()))
            && (this.getEmailaddr() == null ? other.getEmailaddr() == null : this.getEmailaddr().equals(other.getEmailaddr()))
            && (this.getInvoicenoMax() == null ? other.getInvoicenoMax() == null : this.getInvoicenoMax().equals(other.getInvoicenoMax()))
            && (this.getRemaek() == null ? other.getRemaek() == null : this.getRemaek().equals(other.getRemaek()))
            && (this.getForeigncurrencyFlag() == null ? other.getForeigncurrencyFlag() == null : this.getForeigncurrencyFlag().equals(other.getForeigncurrencyFlag()))
            && (this.getFinancename() == null ? other.getFinancename() == null : this.getFinancename().equals(other.getFinancename()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getProdcountryCode() == null) ? 0 : getProdcountryCode().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getFullname() == null) ? 0 : getFullname().hashCode());
        result = prime * result + ((getFilename() == null) ? 0 : getFilename().hashCode());
        result = prime * result + ((getPriceterm() == null) ? 0 : getPriceterm().hashCode());
        result = prime * result + ((getInvno() == null) ? 0 : getInvno().hashCode());
        result = prime * result + ((getUpddate() == null) ? 0 : getUpddate().hashCode());
        result = prime * result + ((getCompanyCodeDb81() == null) ? 0 : getCompanyCodeDb81().hashCode());
        result = prime * result + ((getCountryCodeDb81() == null) ? 0 : getCountryCodeDb81().hashCode());
        result = prime * result + ((getCurrencyType() == null) ? 0 : getCurrencyType().hashCode());
        result = prime * result + ((getFeeInitial() == null) ? 0 : getFeeInitial().hashCode());
        result = prime * result + ((getFeeAdd() == null) ? 0 : getFeeAdd().hashCode());
        result = prime * result + ((getEmailaddr() == null) ? 0 : getEmailaddr().hashCode());
        result = prime * result + ((getInvoicenoMax() == null) ? 0 : getInvoicenoMax().hashCode());
        result = prime * result + ((getRemaek() == null) ? 0 : getRemaek().hashCode());
        result = prime * result + ((getForeigncurrencyFlag() == null) ? 0 : getForeigncurrencyFlag().hashCode());
        result = prime * result + ((getFinancename() == null) ? 0 : getFinancename().hashCode());
        return result;
    }
}