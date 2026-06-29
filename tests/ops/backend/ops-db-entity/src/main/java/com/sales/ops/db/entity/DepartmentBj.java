package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DepartmentBj implements Serializable {
    private String deptno;

    private String deptdesc;

    private String address;

    private String postcode;

    private String teleno;

    private String faxno;

    private String emailaddr;

    private String relacode;

    private String filepath;

    private String stateflag;

    private String stockcode;

    private String locationno;

    private String superintendent;

    private String insidepsn;

    private Date upddate;

    private String emailaddr1;

    private String invflag;

    private String postaddress;

    private String bank;

    private String accountno;

    private String taxno;

    private String kfangid;

    private String ygid;

    private String shid;

    private String dlvflag;

    private BigDecimal invamount;

    private String empinvoice;

    private String dlvflagBj;

    private String dlvflagCn;

    private String empdelivery;

    private String deptinvno;

    private String feecode;

    private String sampleordno;

    private String dlventireFlag;

    private String deptlevel;

    private String empordprocess;

    private String statecode;

    private String emailSample;

    private String areacode;

    private String supdeptno;

    private String qpriceAnswer;

    private String departcode;

    private String custAnswer;

    private String organizationid;

    private Boolean integratesps;

    private String administrativestateflag;

    private String tradecompanyid;

    private static final long serialVersionUID = 1L;

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getDeptdesc() {
        return deptdesc;
    }

    public void setDeptdesc(String deptdesc) {
        this.deptdesc = deptdesc == null ? null : deptdesc.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getTeleno() {
        return teleno;
    }

    public void setTeleno(String teleno) {
        this.teleno = teleno == null ? null : teleno.trim();
    }

    public String getFaxno() {
        return faxno;
    }

    public void setFaxno(String faxno) {
        this.faxno = faxno == null ? null : faxno.trim();
    }

    public String getEmailaddr() {
        return emailaddr;
    }

    public void setEmailaddr(String emailaddr) {
        this.emailaddr = emailaddr == null ? null : emailaddr.trim();
    }

    public String getRelacode() {
        return relacode;
    }

    public void setRelacode(String relacode) {
        this.relacode = relacode == null ? null : relacode.trim();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public String getStateflag() {
        return stateflag;
    }

    public void setStateflag(String stateflag) {
        this.stateflag = stateflag == null ? null : stateflag.trim();
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public String getLocationno() {
        return locationno;
    }

    public void setLocationno(String locationno) {
        this.locationno = locationno == null ? null : locationno.trim();
    }

    public String getSuperintendent() {
        return superintendent;
    }

    public void setSuperintendent(String superintendent) {
        this.superintendent = superintendent == null ? null : superintendent.trim();
    }

    public String getInsidepsn() {
        return insidepsn;
    }

    public void setInsidepsn(String insidepsn) {
        this.insidepsn = insidepsn == null ? null : insidepsn.trim();
    }

    public Date getUpddate() {
        return upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    public String getEmailaddr1() {
        return emailaddr1;
    }

    public void setEmailaddr1(String emailaddr1) {
        this.emailaddr1 = emailaddr1 == null ? null : emailaddr1.trim();
    }

    public String getInvflag() {
        return invflag;
    }

    public void setInvflag(String invflag) {
        this.invflag = invflag == null ? null : invflag.trim();
    }

    public String getPostaddress() {
        return postaddress;
    }

    public void setPostaddress(String postaddress) {
        this.postaddress = postaddress == null ? null : postaddress.trim();
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

    public String getTaxno() {
        return taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno == null ? null : taxno.trim();
    }

    public String getKfangid() {
        return kfangid;
    }

    public void setKfangid(String kfangid) {
        this.kfangid = kfangid == null ? null : kfangid.trim();
    }

    public String getYgid() {
        return ygid;
    }

    public void setYgid(String ygid) {
        this.ygid = ygid == null ? null : ygid.trim();
    }

    public String getShid() {
        return shid;
    }

    public void setShid(String shid) {
        this.shid = shid == null ? null : shid.trim();
    }

    public String getDlvflag() {
        return dlvflag;
    }

    public void setDlvflag(String dlvflag) {
        this.dlvflag = dlvflag == null ? null : dlvflag.trim();
    }

    public BigDecimal getInvamount() {
        return invamount;
    }

    public void setInvamount(BigDecimal invamount) {
        this.invamount = invamount;
    }

    public String getEmpinvoice() {
        return empinvoice;
    }

    public void setEmpinvoice(String empinvoice) {
        this.empinvoice = empinvoice == null ? null : empinvoice.trim();
    }

    public String getDlvflagBj() {
        return dlvflagBj;
    }

    public void setDlvflagBj(String dlvflagBj) {
        this.dlvflagBj = dlvflagBj == null ? null : dlvflagBj.trim();
    }

    public String getDlvflagCn() {
        return dlvflagCn;
    }

    public void setDlvflagCn(String dlvflagCn) {
        this.dlvflagCn = dlvflagCn == null ? null : dlvflagCn.trim();
    }

    public String getEmpdelivery() {
        return empdelivery;
    }

    public void setEmpdelivery(String empdelivery) {
        this.empdelivery = empdelivery == null ? null : empdelivery.trim();
    }

    public String getDeptinvno() {
        return deptinvno;
    }

    public void setDeptinvno(String deptinvno) {
        this.deptinvno = deptinvno == null ? null : deptinvno.trim();
    }

    public String getFeecode() {
        return feecode;
    }

    public void setFeecode(String feecode) {
        this.feecode = feecode == null ? null : feecode.trim();
    }

    public String getSampleordno() {
        return sampleordno;
    }

    public void setSampleordno(String sampleordno) {
        this.sampleordno = sampleordno == null ? null : sampleordno.trim();
    }

    public String getDlventireFlag() {
        return dlventireFlag;
    }

    public void setDlventireFlag(String dlventireFlag) {
        this.dlventireFlag = dlventireFlag == null ? null : dlventireFlag.trim();
    }

    public String getDeptlevel() {
        return deptlevel;
    }

    public void setDeptlevel(String deptlevel) {
        this.deptlevel = deptlevel == null ? null : deptlevel.trim();
    }

    public String getEmpordprocess() {
        return empordprocess;
    }

    public void setEmpordprocess(String empordprocess) {
        this.empordprocess = empordprocess == null ? null : empordprocess.trim();
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode == null ? null : statecode.trim();
    }

    public String getEmailSample() {
        return emailSample;
    }

    public void setEmailSample(String emailSample) {
        this.emailSample = emailSample == null ? null : emailSample.trim();
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode == null ? null : areacode.trim();
    }

    public String getSupdeptno() {
        return supdeptno;
    }

    public void setSupdeptno(String supdeptno) {
        this.supdeptno = supdeptno == null ? null : supdeptno.trim();
    }

    public String getQpriceAnswer() {
        return qpriceAnswer;
    }

    public void setQpriceAnswer(String qpriceAnswer) {
        this.qpriceAnswer = qpriceAnswer == null ? null : qpriceAnswer.trim();
    }

    public String getDepartcode() {
        return departcode;
    }

    public void setDepartcode(String departcode) {
        this.departcode = departcode == null ? null : departcode.trim();
    }

    public String getCustAnswer() {
        return custAnswer;
    }

    public void setCustAnswer(String custAnswer) {
        this.custAnswer = custAnswer == null ? null : custAnswer.trim();
    }

    public String getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(String organizationid) {
        this.organizationid = organizationid == null ? null : organizationid.trim();
    }

    public Boolean getIntegratesps() {
        return integratesps;
    }

    public void setIntegratesps(Boolean integratesps) {
        this.integratesps = integratesps;
    }

    public String getAdministrativestateflag() {
        return administrativestateflag;
    }

    public void setAdministrativestateflag(String administrativestateflag) {
        this.administrativestateflag = administrativestateflag == null ? null : administrativestateflag.trim();
    }

    public String getTradecompanyid() {
        return tradecompanyid;
    }

    public void setTradecompanyid(String tradecompanyid) {
        this.tradecompanyid = tradecompanyid == null ? null : tradecompanyid.trim();
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
        DepartmentBj other = (DepartmentBj) that;
        return (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getDeptdesc() == null ? other.getDeptdesc() == null : this.getDeptdesc().equals(other.getDeptdesc()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getPostcode() == null ? other.getPostcode() == null : this.getPostcode().equals(other.getPostcode()))
            && (this.getTeleno() == null ? other.getTeleno() == null : this.getTeleno().equals(other.getTeleno()))
            && (this.getFaxno() == null ? other.getFaxno() == null : this.getFaxno().equals(other.getFaxno()))
            && (this.getEmailaddr() == null ? other.getEmailaddr() == null : this.getEmailaddr().equals(other.getEmailaddr()))
            && (this.getRelacode() == null ? other.getRelacode() == null : this.getRelacode().equals(other.getRelacode()))
            && (this.getFilepath() == null ? other.getFilepath() == null : this.getFilepath().equals(other.getFilepath()))
            && (this.getStateflag() == null ? other.getStateflag() == null : this.getStateflag().equals(other.getStateflag()))
            && (this.getStockcode() == null ? other.getStockcode() == null : this.getStockcode().equals(other.getStockcode()))
            && (this.getLocationno() == null ? other.getLocationno() == null : this.getLocationno().equals(other.getLocationno()))
            && (this.getSuperintendent() == null ? other.getSuperintendent() == null : this.getSuperintendent().equals(other.getSuperintendent()))
            && (this.getInsidepsn() == null ? other.getInsidepsn() == null : this.getInsidepsn().equals(other.getInsidepsn()))
            && (this.getUpddate() == null ? other.getUpddate() == null : this.getUpddate().equals(other.getUpddate()))
            && (this.getEmailaddr1() == null ? other.getEmailaddr1() == null : this.getEmailaddr1().equals(other.getEmailaddr1()))
            && (this.getInvflag() == null ? other.getInvflag() == null : this.getInvflag().equals(other.getInvflag()))
            && (this.getPostaddress() == null ? other.getPostaddress() == null : this.getPostaddress().equals(other.getPostaddress()))
            && (this.getBank() == null ? other.getBank() == null : this.getBank().equals(other.getBank()))
            && (this.getAccountno() == null ? other.getAccountno() == null : this.getAccountno().equals(other.getAccountno()))
            && (this.getTaxno() == null ? other.getTaxno() == null : this.getTaxno().equals(other.getTaxno()))
            && (this.getKfangid() == null ? other.getKfangid() == null : this.getKfangid().equals(other.getKfangid()))
            && (this.getYgid() == null ? other.getYgid() == null : this.getYgid().equals(other.getYgid()))
            && (this.getShid() == null ? other.getShid() == null : this.getShid().equals(other.getShid()))
            && (this.getDlvflag() == null ? other.getDlvflag() == null : this.getDlvflag().equals(other.getDlvflag()))
            && (this.getInvamount() == null ? other.getInvamount() == null : this.getInvamount().equals(other.getInvamount()))
            && (this.getEmpinvoice() == null ? other.getEmpinvoice() == null : this.getEmpinvoice().equals(other.getEmpinvoice()))
            && (this.getDlvflagBj() == null ? other.getDlvflagBj() == null : this.getDlvflagBj().equals(other.getDlvflagBj()))
            && (this.getDlvflagCn() == null ? other.getDlvflagCn() == null : this.getDlvflagCn().equals(other.getDlvflagCn()))
            && (this.getEmpdelivery() == null ? other.getEmpdelivery() == null : this.getEmpdelivery().equals(other.getEmpdelivery()))
            && (this.getDeptinvno() == null ? other.getDeptinvno() == null : this.getDeptinvno().equals(other.getDeptinvno()))
            && (this.getFeecode() == null ? other.getFeecode() == null : this.getFeecode().equals(other.getFeecode()))
            && (this.getSampleordno() == null ? other.getSampleordno() == null : this.getSampleordno().equals(other.getSampleordno()))
            && (this.getDlventireFlag() == null ? other.getDlventireFlag() == null : this.getDlventireFlag().equals(other.getDlventireFlag()))
            && (this.getDeptlevel() == null ? other.getDeptlevel() == null : this.getDeptlevel().equals(other.getDeptlevel()))
            && (this.getEmpordprocess() == null ? other.getEmpordprocess() == null : this.getEmpordprocess().equals(other.getEmpordprocess()))
            && (this.getStatecode() == null ? other.getStatecode() == null : this.getStatecode().equals(other.getStatecode()))
            && (this.getEmailSample() == null ? other.getEmailSample() == null : this.getEmailSample().equals(other.getEmailSample()))
            && (this.getAreacode() == null ? other.getAreacode() == null : this.getAreacode().equals(other.getAreacode()))
            && (this.getSupdeptno() == null ? other.getSupdeptno() == null : this.getSupdeptno().equals(other.getSupdeptno()))
            && (this.getQpriceAnswer() == null ? other.getQpriceAnswer() == null : this.getQpriceAnswer().equals(other.getQpriceAnswer()))
            && (this.getDepartcode() == null ? other.getDepartcode() == null : this.getDepartcode().equals(other.getDepartcode()))
            && (this.getCustAnswer() == null ? other.getCustAnswer() == null : this.getCustAnswer().equals(other.getCustAnswer()))
            && (this.getOrganizationid() == null ? other.getOrganizationid() == null : this.getOrganizationid().equals(other.getOrganizationid()))
            && (this.getIntegratesps() == null ? other.getIntegratesps() == null : this.getIntegratesps().equals(other.getIntegratesps()))
            && (this.getAdministrativestateflag() == null ? other.getAdministrativestateflag() == null : this.getAdministrativestateflag().equals(other.getAdministrativestateflag()))
            && (this.getTradecompanyid() == null ? other.getTradecompanyid() == null : this.getTradecompanyid().equals(other.getTradecompanyid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getDeptdesc() == null) ? 0 : getDeptdesc().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getPostcode() == null) ? 0 : getPostcode().hashCode());
        result = prime * result + ((getTeleno() == null) ? 0 : getTeleno().hashCode());
        result = prime * result + ((getFaxno() == null) ? 0 : getFaxno().hashCode());
        result = prime * result + ((getEmailaddr() == null) ? 0 : getEmailaddr().hashCode());
        result = prime * result + ((getRelacode() == null) ? 0 : getRelacode().hashCode());
        result = prime * result + ((getFilepath() == null) ? 0 : getFilepath().hashCode());
        result = prime * result + ((getStateflag() == null) ? 0 : getStateflag().hashCode());
        result = prime * result + ((getStockcode() == null) ? 0 : getStockcode().hashCode());
        result = prime * result + ((getLocationno() == null) ? 0 : getLocationno().hashCode());
        result = prime * result + ((getSuperintendent() == null) ? 0 : getSuperintendent().hashCode());
        result = prime * result + ((getInsidepsn() == null) ? 0 : getInsidepsn().hashCode());
        result = prime * result + ((getUpddate() == null) ? 0 : getUpddate().hashCode());
        result = prime * result + ((getEmailaddr1() == null) ? 0 : getEmailaddr1().hashCode());
        result = prime * result + ((getInvflag() == null) ? 0 : getInvflag().hashCode());
        result = prime * result + ((getPostaddress() == null) ? 0 : getPostaddress().hashCode());
        result = prime * result + ((getBank() == null) ? 0 : getBank().hashCode());
        result = prime * result + ((getAccountno() == null) ? 0 : getAccountno().hashCode());
        result = prime * result + ((getTaxno() == null) ? 0 : getTaxno().hashCode());
        result = prime * result + ((getKfangid() == null) ? 0 : getKfangid().hashCode());
        result = prime * result + ((getYgid() == null) ? 0 : getYgid().hashCode());
        result = prime * result + ((getShid() == null) ? 0 : getShid().hashCode());
        result = prime * result + ((getDlvflag() == null) ? 0 : getDlvflag().hashCode());
        result = prime * result + ((getInvamount() == null) ? 0 : getInvamount().hashCode());
        result = prime * result + ((getEmpinvoice() == null) ? 0 : getEmpinvoice().hashCode());
        result = prime * result + ((getDlvflagBj() == null) ? 0 : getDlvflagBj().hashCode());
        result = prime * result + ((getDlvflagCn() == null) ? 0 : getDlvflagCn().hashCode());
        result = prime * result + ((getEmpdelivery() == null) ? 0 : getEmpdelivery().hashCode());
        result = prime * result + ((getDeptinvno() == null) ? 0 : getDeptinvno().hashCode());
        result = prime * result + ((getFeecode() == null) ? 0 : getFeecode().hashCode());
        result = prime * result + ((getSampleordno() == null) ? 0 : getSampleordno().hashCode());
        result = prime * result + ((getDlventireFlag() == null) ? 0 : getDlventireFlag().hashCode());
        result = prime * result + ((getDeptlevel() == null) ? 0 : getDeptlevel().hashCode());
        result = prime * result + ((getEmpordprocess() == null) ? 0 : getEmpordprocess().hashCode());
        result = prime * result + ((getStatecode() == null) ? 0 : getStatecode().hashCode());
        result = prime * result + ((getEmailSample() == null) ? 0 : getEmailSample().hashCode());
        result = prime * result + ((getAreacode() == null) ? 0 : getAreacode().hashCode());
        result = prime * result + ((getSupdeptno() == null) ? 0 : getSupdeptno().hashCode());
        result = prime * result + ((getQpriceAnswer() == null) ? 0 : getQpriceAnswer().hashCode());
        result = prime * result + ((getDepartcode() == null) ? 0 : getDepartcode().hashCode());
        result = prime * result + ((getCustAnswer() == null) ? 0 : getCustAnswer().hashCode());
        result = prime * result + ((getOrganizationid() == null) ? 0 : getOrganizationid().hashCode());
        result = prime * result + ((getIntegratesps() == null) ? 0 : getIntegratesps().hashCode());
        result = prime * result + ((getAdministrativestateflag() == null) ? 0 : getAdministrativestateflag().hashCode());
        result = prime * result + ((getTradecompanyid() == null) ? 0 : getTradecompanyid().hashCode());
        return result;
    }
}