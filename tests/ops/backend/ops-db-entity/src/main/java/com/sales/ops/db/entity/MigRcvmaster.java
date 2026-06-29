package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class MigRcvmaster implements Serializable {
    private String rorderno;

    private String customerno;

    private String userno;

    private Date rorddate;

    private String dlventire;

    private String dlvsite;

    private String transfee;

    private String transchannel;

    private String prjcode;

    private String ororderno;

    private String dlvtype;

    private String employee;

    private String employeeno;

    private Date opttime;

    private String operator;

    private String deptno;

    private String contractno;

    private String quotationno;

    private String purchaseno;

    private String cttflag;

    private String ordtype;

    private String typecode;

    private Boolean pricechecked;

    private String hlcode;

    private String tradecompanyid;

    private Boolean isconfirm;

    private Date inserttime;

    private static final long serialVersionUID = 1L;

    public String getRorderno() {
        return rorderno;
    }

    public void setRorderno(String rorderno) {
        this.rorderno = rorderno == null ? null : rorderno.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno == null ? null : userno.trim();
    }

    public Date getRorddate() {
        return rorddate;
    }

    public void setRorddate(Date rorddate) {
        this.rorddate = rorddate;
    }

    public String getDlventire() {
        return dlventire;
    }

    public void setDlventire(String dlventire) {
        this.dlventire = dlventire == null ? null : dlventire.trim();
    }

    public String getDlvsite() {
        return dlvsite;
    }

    public void setDlvsite(String dlvsite) {
        this.dlvsite = dlvsite == null ? null : dlvsite.trim();
    }

    public String getTransfee() {
        return transfee;
    }

    public void setTransfee(String transfee) {
        this.transfee = transfee == null ? null : transfee.trim();
    }

    public String getTranschannel() {
        return transchannel;
    }

    public void setTranschannel(String transchannel) {
        this.transchannel = transchannel == null ? null : transchannel.trim();
    }

    public String getPrjcode() {
        return prjcode;
    }

    public void setPrjcode(String prjcode) {
        this.prjcode = prjcode == null ? null : prjcode.trim();
    }

    public String getOrorderno() {
        return ororderno;
    }

    public void setOrorderno(String ororderno) {
        this.ororderno = ororderno == null ? null : ororderno.trim();
    }

    public String getDlvtype() {
        return dlvtype;
    }

    public void setDlvtype(String dlvtype) {
        this.dlvtype = dlvtype == null ? null : dlvtype.trim();
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee == null ? null : employee.trim();
    }

    public String getEmployeeno() {
        return employeeno;
    }

    public void setEmployeeno(String employeeno) {
        this.employeeno = employeeno == null ? null : employeeno.trim();
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno == null ? null : contractno.trim();
    }

    public String getQuotationno() {
        return quotationno;
    }

    public void setQuotationno(String quotationno) {
        this.quotationno = quotationno == null ? null : quotationno.trim();
    }

    public String getPurchaseno() {
        return purchaseno;
    }

    public void setPurchaseno(String purchaseno) {
        this.purchaseno = purchaseno == null ? null : purchaseno.trim();
    }

    public String getCttflag() {
        return cttflag;
    }

    public void setCttflag(String cttflag) {
        this.cttflag = cttflag == null ? null : cttflag.trim();
    }

    public String getOrdtype() {
        return ordtype;
    }

    public void setOrdtype(String ordtype) {
        this.ordtype = ordtype == null ? null : ordtype.trim();
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode == null ? null : typecode.trim();
    }

    public Boolean getPricechecked() {
        return pricechecked;
    }

    public void setPricechecked(Boolean pricechecked) {
        this.pricechecked = pricechecked;
    }

    public String getHlcode() {
        return hlcode;
    }

    public void setHlcode(String hlcode) {
        this.hlcode = hlcode == null ? null : hlcode.trim();
    }

    public String getTradecompanyid() {
        return tradecompanyid;
    }

    public void setTradecompanyid(String tradecompanyid) {
        this.tradecompanyid = tradecompanyid == null ? null : tradecompanyid.trim();
    }

    public Boolean getIsconfirm() {
        return isconfirm;
    }

    public void setIsconfirm(Boolean isconfirm) {
        this.isconfirm = isconfirm;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
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
        MigRcvmaster other = (MigRcvmaster) that;
        return (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getRorddate() == null ? other.getRorddate() == null : this.getRorddate().equals(other.getRorddate()))
            && (this.getDlventire() == null ? other.getDlventire() == null : this.getDlventire().equals(other.getDlventire()))
            && (this.getDlvsite() == null ? other.getDlvsite() == null : this.getDlvsite().equals(other.getDlvsite()))
            && (this.getTransfee() == null ? other.getTransfee() == null : this.getTransfee().equals(other.getTransfee()))
            && (this.getTranschannel() == null ? other.getTranschannel() == null : this.getTranschannel().equals(other.getTranschannel()))
            && (this.getPrjcode() == null ? other.getPrjcode() == null : this.getPrjcode().equals(other.getPrjcode()))
            && (this.getOrorderno() == null ? other.getOrorderno() == null : this.getOrorderno().equals(other.getOrorderno()))
            && (this.getDlvtype() == null ? other.getDlvtype() == null : this.getDlvtype().equals(other.getDlvtype()))
            && (this.getEmployee() == null ? other.getEmployee() == null : this.getEmployee().equals(other.getEmployee()))
            && (this.getEmployeeno() == null ? other.getEmployeeno() == null : this.getEmployeeno().equals(other.getEmployeeno()))
            && (this.getOpttime() == null ? other.getOpttime() == null : this.getOpttime().equals(other.getOpttime()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getContractno() == null ? other.getContractno() == null : this.getContractno().equals(other.getContractno()))
            && (this.getQuotationno() == null ? other.getQuotationno() == null : this.getQuotationno().equals(other.getQuotationno()))
            && (this.getPurchaseno() == null ? other.getPurchaseno() == null : this.getPurchaseno().equals(other.getPurchaseno()))
            && (this.getCttflag() == null ? other.getCttflag() == null : this.getCttflag().equals(other.getCttflag()))
            && (this.getOrdtype() == null ? other.getOrdtype() == null : this.getOrdtype().equals(other.getOrdtype()))
            && (this.getTypecode() == null ? other.getTypecode() == null : this.getTypecode().equals(other.getTypecode()))
            && (this.getPricechecked() == null ? other.getPricechecked() == null : this.getPricechecked().equals(other.getPricechecked()))
            && (this.getHlcode() == null ? other.getHlcode() == null : this.getHlcode().equals(other.getHlcode()))
            && (this.getTradecompanyid() == null ? other.getTradecompanyid() == null : this.getTradecompanyid().equals(other.getTradecompanyid()))
            && (this.getIsconfirm() == null ? other.getIsconfirm() == null : this.getIsconfirm().equals(other.getIsconfirm()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRorderno() == null) ? 0 : getRorderno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getRorddate() == null) ? 0 : getRorddate().hashCode());
        result = prime * result + ((getDlventire() == null) ? 0 : getDlventire().hashCode());
        result = prime * result + ((getDlvsite() == null) ? 0 : getDlvsite().hashCode());
        result = prime * result + ((getTransfee() == null) ? 0 : getTransfee().hashCode());
        result = prime * result + ((getTranschannel() == null) ? 0 : getTranschannel().hashCode());
        result = prime * result + ((getPrjcode() == null) ? 0 : getPrjcode().hashCode());
        result = prime * result + ((getOrorderno() == null) ? 0 : getOrorderno().hashCode());
        result = prime * result + ((getDlvtype() == null) ? 0 : getDlvtype().hashCode());
        result = prime * result + ((getEmployee() == null) ? 0 : getEmployee().hashCode());
        result = prime * result + ((getEmployeeno() == null) ? 0 : getEmployeeno().hashCode());
        result = prime * result + ((getOpttime() == null) ? 0 : getOpttime().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getContractno() == null) ? 0 : getContractno().hashCode());
        result = prime * result + ((getQuotationno() == null) ? 0 : getQuotationno().hashCode());
        result = prime * result + ((getPurchaseno() == null) ? 0 : getPurchaseno().hashCode());
        result = prime * result + ((getCttflag() == null) ? 0 : getCttflag().hashCode());
        result = prime * result + ((getOrdtype() == null) ? 0 : getOrdtype().hashCode());
        result = prime * result + ((getTypecode() == null) ? 0 : getTypecode().hashCode());
        result = prime * result + ((getPricechecked() == null) ? 0 : getPricechecked().hashCode());
        result = prime * result + ((getHlcode() == null) ? 0 : getHlcode().hashCode());
        result = prime * result + ((getTradecompanyid() == null) ? 0 : getTradecompanyid().hashCode());
        result = prime * result + ((getIsconfirm() == null) ? 0 : getIsconfirm().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        return result;
    }
}