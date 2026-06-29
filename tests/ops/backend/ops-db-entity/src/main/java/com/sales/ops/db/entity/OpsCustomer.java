package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsCustomer implements Serializable {
    private String customerNo;

    private String name;

    private String ename;

    private String hrunitid;

    private String psnsmcid;

    private String agentno;

    private String customertype;

    private String smcgroupid;

    private String tradesubjectid;

    private String taxno;

    private String bank;

    private String accountno;

    private String invoiceaddress;

    private String invoicephoneno;

    private String invoicetype;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private String cstmgrade;

    private String vipcodeCn;

    private Integer customerlevel;

    private String hlcode;

    private String aliasEname;

    private static final long serialVersionUID = 1L;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    public String getHrunitid() {
        return hrunitid;
    }

    public void setHrunitid(String hrunitid) {
        this.hrunitid = hrunitid == null ? null : hrunitid.trim();
    }

    public String getPsnsmcid() {
        return psnsmcid;
    }

    public void setPsnsmcid(String psnsmcid) {
        this.psnsmcid = psnsmcid == null ? null : psnsmcid.trim();
    }

    public String getAgentno() {
        return agentno;
    }

    public void setAgentno(String agentno) {
        this.agentno = agentno == null ? null : agentno.trim();
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype == null ? null : customertype.trim();
    }

    public String getSmcgroupid() {
        return smcgroupid;
    }

    public void setSmcgroupid(String smcgroupid) {
        this.smcgroupid = smcgroupid == null ? null : smcgroupid.trim();
    }

    public String getTradesubjectid() {
        return tradesubjectid;
    }

    public void setTradesubjectid(String tradesubjectid) {
        this.tradesubjectid = tradesubjectid == null ? null : tradesubjectid.trim();
    }

    public String getTaxno() {
        return taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno == null ? null : taxno.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno == null ? null : accountno.trim();
    }

    public String getInvoiceaddress() {
        return invoiceaddress;
    }

    public void setInvoiceaddress(String invoiceaddress) {
        this.invoiceaddress = invoiceaddress == null ? null : invoiceaddress.trim();
    }

    public String getInvoicephoneno() {
        return invoicephoneno;
    }

    public void setInvoicephoneno(String invoicephoneno) {
        this.invoicephoneno = invoicephoneno == null ? null : invoicephoneno.trim();
    }

    public String getInvoicetype() {
        return invoicetype;
    }

    public void setInvoicetype(String invoicetype) {
        this.invoicetype = invoicetype == null ? null : invoicetype.trim();
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public String getCstmgrade() {
        return cstmgrade;
    }

    public void setCstmgrade(String cstmgrade) {
        this.cstmgrade = cstmgrade == null ? null : cstmgrade.trim();
    }

    public String getVipcodeCn() {
        return vipcodeCn;
    }

    public void setVipcodeCn(String vipcodeCn) {
        this.vipcodeCn = vipcodeCn == null ? null : vipcodeCn.trim();
    }

    public Integer getCustomerlevel() {
        return customerlevel;
    }

    public void setCustomerlevel(Integer customerlevel) {
        this.customerlevel = customerlevel;
    }

    public String getHlcode() {
        return hlcode;
    }

    public void setHlcode(String hlcode) {
        this.hlcode = hlcode == null ? null : hlcode.trim();
    }

    public String getAliasEname() {
        return aliasEname;
    }

    public void setAliasEname(String aliasEname) {
        this.aliasEname = aliasEname == null ? null : aliasEname.trim();
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
        OpsCustomer other = (OpsCustomer) that;
        return (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getEname() == null ? other.getEname() == null : this.getEname().equals(other.getEname()))
            && (this.getHrunitid() == null ? other.getHrunitid() == null : this.getHrunitid().equals(other.getHrunitid()))
            && (this.getPsnsmcid() == null ? other.getPsnsmcid() == null : this.getPsnsmcid().equals(other.getPsnsmcid()))
            && (this.getAgentno() == null ? other.getAgentno() == null : this.getAgentno().equals(other.getAgentno()))
            && (this.getCustomertype() == null ? other.getCustomertype() == null : this.getCustomertype().equals(other.getCustomertype()))
            && (this.getSmcgroupid() == null ? other.getSmcgroupid() == null : this.getSmcgroupid().equals(other.getSmcgroupid()))
            && (this.getTradesubjectid() == null ? other.getTradesubjectid() == null : this.getTradesubjectid().equals(other.getTradesubjectid()))
            && (this.getTaxno() == null ? other.getTaxno() == null : this.getTaxno().equals(other.getTaxno()))
            && (this.getBank() == null ? other.getBank() == null : this.getBank().equals(other.getBank()))
            && (this.getAccountno() == null ? other.getAccountno() == null : this.getAccountno().equals(other.getAccountno()))
            && (this.getInvoiceaddress() == null ? other.getInvoiceaddress() == null : this.getInvoiceaddress().equals(other.getInvoiceaddress()))
            && (this.getInvoicephoneno() == null ? other.getInvoicephoneno() == null : this.getInvoicephoneno().equals(other.getInvoicephoneno()))
            && (this.getInvoicetype() == null ? other.getInvoicetype() == null : this.getInvoicetype().equals(other.getInvoicetype()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getCstmgrade() == null ? other.getCstmgrade() == null : this.getCstmgrade().equals(other.getCstmgrade()))
            && (this.getVipcodeCn() == null ? other.getVipcodeCn() == null : this.getVipcodeCn().equals(other.getVipcodeCn()))
            && (this.getCustomerlevel() == null ? other.getCustomerlevel() == null : this.getCustomerlevel().equals(other.getCustomerlevel()))
            && (this.getHlcode() == null ? other.getHlcode() == null : this.getHlcode().equals(other.getHlcode()))
            && (this.getAliasEname() == null ? other.getAliasEname() == null : this.getAliasEname().equals(other.getAliasEname()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getEname() == null) ? 0 : getEname().hashCode());
        result = prime * result + ((getHrunitid() == null) ? 0 : getHrunitid().hashCode());
        result = prime * result + ((getPsnsmcid() == null) ? 0 : getPsnsmcid().hashCode());
        result = prime * result + ((getAgentno() == null) ? 0 : getAgentno().hashCode());
        result = prime * result + ((getCustomertype() == null) ? 0 : getCustomertype().hashCode());
        result = prime * result + ((getSmcgroupid() == null) ? 0 : getSmcgroupid().hashCode());
        result = prime * result + ((getTradesubjectid() == null) ? 0 : getTradesubjectid().hashCode());
        result = prime * result + ((getTaxno() == null) ? 0 : getTaxno().hashCode());
        result = prime * result + ((getBank() == null) ? 0 : getBank().hashCode());
        result = prime * result + ((getAccountno() == null) ? 0 : getAccountno().hashCode());
        result = prime * result + ((getInvoiceaddress() == null) ? 0 : getInvoiceaddress().hashCode());
        result = prime * result + ((getInvoicephoneno() == null) ? 0 : getInvoicephoneno().hashCode());
        result = prime * result + ((getInvoicetype() == null) ? 0 : getInvoicetype().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getCstmgrade() == null) ? 0 : getCstmgrade().hashCode());
        result = prime * result + ((getVipcodeCn() == null) ? 0 : getVipcodeCn().hashCode());
        result = prime * result + ((getCustomerlevel() == null) ? 0 : getCustomerlevel().hashCode());
        result = prime * result + ((getHlcode() == null) ? 0 : getHlcode().hashCode());
        result = prime * result + ((getAliasEname() == null) ? 0 : getAliasEname().hashCode());
        return result;
    }
}