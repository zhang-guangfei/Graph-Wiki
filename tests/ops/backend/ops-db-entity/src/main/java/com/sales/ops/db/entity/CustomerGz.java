package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class CustomerGz implements Serializable {
    private String customerno;

    private String userno;

    private String cstmname;

    private String englishname;

    private String cstmtype;

    private String cstmgrade;

    private String statecode;

    private String indcode;

    private String prjcode;

    private String etpofjp;

    private String lgealrep;

    private String caddress1;

    private String caddress2;

    private String postcode;

    private String telnofirst;

    private String telnolast;

    private String telother;

    private String faxnofirst;

    private String faxnolast;

    private String contactpsn;

    private String contpsnduty;

    private String contdept;

    private String bank;

    private String taxno;

    private String accountno;

    private String deptcode;

    private String psnsmcid;

    private String pricelvl;

    private Date filedate;

    private Date upddate;

    private String obinvoice;

    private String notaxinvoice;

    private String eaddress;

    private String invflag;

    private String remark;

    private BigDecimal requirement;

    private String agentno;

    private String stockflag;

    private String invagent;

    private String cstmgcode;

    private String areagcode;

    private String customerHk;

    private BigDecimal deposit;

    private Integer priceflag;

    private Integer specpack;

    private String laji;

    private String cstmdivision;

    private String salesin;

    private String indclass1;

    private String indclass2;

    private String indclass3;

    private String business;

    private String gcode;

    private String groupname;

    private String comefrom;

    private String web;

    private String pricegrade;

    private String samecustomerno;

    private Short status;

    private String jpvipcode;

    private String villagecode;

    private String indclass4;

    private String indclass5;

    private String indclass6;

    private Date lastorderdate;

    private String nccustomerno;

    private String rcvinvoiceemail;

    private String sleepcustomer;

    private byte[] rv;

    private static final long serialVersionUID = 1L;

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

    public String getCstmname() {
        return cstmname;
    }

    public void setCstmname(String cstmname) {
        this.cstmname = cstmname == null ? null : cstmname.trim();
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname == null ? null : englishname.trim();
    }

    public String getCstmtype() {
        return cstmtype;
    }

    public void setCstmtype(String cstmtype) {
        this.cstmtype = cstmtype == null ? null : cstmtype.trim();
    }

    public String getCstmgrade() {
        return cstmgrade;
    }

    public void setCstmgrade(String cstmgrade) {
        this.cstmgrade = cstmgrade == null ? null : cstmgrade.trim();
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode == null ? null : statecode.trim();
    }

    public String getIndcode() {
        return indcode;
    }

    public void setIndcode(String indcode) {
        this.indcode = indcode == null ? null : indcode.trim();
    }

    public String getPrjcode() {
        return prjcode;
    }

    public void setPrjcode(String prjcode) {
        this.prjcode = prjcode == null ? null : prjcode.trim();
    }

    public String getEtpofjp() {
        return etpofjp;
    }

    public void setEtpofjp(String etpofjp) {
        this.etpofjp = etpofjp == null ? null : etpofjp.trim();
    }

    public String getLgealrep() {
        return lgealrep;
    }

    public void setLgealrep(String lgealrep) {
        this.lgealrep = lgealrep == null ? null : lgealrep.trim();
    }

    public String getCaddress1() {
        return caddress1;
    }

    public void setCaddress1(String caddress1) {
        this.caddress1 = caddress1 == null ? null : caddress1.trim();
    }

    public String getCaddress2() {
        return caddress2;
    }

    public void setCaddress2(String caddress2) {
        this.caddress2 = caddress2 == null ? null : caddress2.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getTelnofirst() {
        return telnofirst;
    }

    public void setTelnofirst(String telnofirst) {
        this.telnofirst = telnofirst == null ? null : telnofirst.trim();
    }

    public String getTelnolast() {
        return telnolast;
    }

    public void setTelnolast(String telnolast) {
        this.telnolast = telnolast == null ? null : telnolast.trim();
    }

    public String getTelother() {
        return telother;
    }

    public void setTelother(String telother) {
        this.telother = telother == null ? null : telother.trim();
    }

    public String getFaxnofirst() {
        return faxnofirst;
    }

    public void setFaxnofirst(String faxnofirst) {
        this.faxnofirst = faxnofirst == null ? null : faxnofirst.trim();
    }

    public String getFaxnolast() {
        return faxnolast;
    }

    public void setFaxnolast(String faxnolast) {
        this.faxnolast = faxnolast == null ? null : faxnolast.trim();
    }

    public String getContactpsn() {
        return contactpsn;
    }

    public void setContactpsn(String contactpsn) {
        this.contactpsn = contactpsn == null ? null : contactpsn.trim();
    }

    public String getContpsnduty() {
        return contpsnduty;
    }

    public void setContpsnduty(String contpsnduty) {
        this.contpsnduty = contpsnduty == null ? null : contpsnduty.trim();
    }

    public String getContdept() {
        return contdept;
    }

    public void setContdept(String contdept) {
        this.contdept = contdept == null ? null : contdept.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getTaxno() {
        return taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno == null ? null : taxno.trim();
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno == null ? null : accountno.trim();
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode == null ? null : deptcode.trim();
    }

    public String getPsnsmcid() {
        return psnsmcid;
    }

    public void setPsnsmcid(String psnsmcid) {
        this.psnsmcid = psnsmcid == null ? null : psnsmcid.trim();
    }

    public String getPricelvl() {
        return pricelvl;
    }

    public void setPricelvl(String pricelvl) {
        this.pricelvl = pricelvl == null ? null : pricelvl.trim();
    }

    public Date getFiledate() {
        return filedate;
    }

    public void setFiledate(Date filedate) {
        this.filedate = filedate;
    }

    public Date getUpddate() {
        return upddate;
    }

    public void setUpddate(Date upddate) {
        this.upddate = upddate;
    }

    public String getObinvoice() {
        return obinvoice;
    }

    public void setObinvoice(String obinvoice) {
        this.obinvoice = obinvoice == null ? null : obinvoice.trim();
    }

    public String getNotaxinvoice() {
        return notaxinvoice;
    }

    public void setNotaxinvoice(String notaxinvoice) {
        this.notaxinvoice = notaxinvoice == null ? null : notaxinvoice.trim();
    }

    public String getEaddress() {
        return eaddress;
    }

    public void setEaddress(String eaddress) {
        this.eaddress = eaddress == null ? null : eaddress.trim();
    }

    public String getInvflag() {
        return invflag;
    }

    public void setInvflag(String invflag) {
        this.invflag = invflag == null ? null : invflag.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getRequirement() {
        return requirement;
    }

    public void setRequirement(BigDecimal requirement) {
        this.requirement = requirement;
    }

    public String getAgentno() {
        return agentno;
    }

    public void setAgentno(String agentno) {
        this.agentno = agentno == null ? null : agentno.trim();
    }

    public String getStockflag() {
        return stockflag;
    }

    public void setStockflag(String stockflag) {
        this.stockflag = stockflag == null ? null : stockflag.trim();
    }

    public String getInvagent() {
        return invagent;
    }

    public void setInvagent(String invagent) {
        this.invagent = invagent == null ? null : invagent.trim();
    }

    public String getCstmgcode() {
        return cstmgcode;
    }

    public void setCstmgcode(String cstmgcode) {
        this.cstmgcode = cstmgcode == null ? null : cstmgcode.trim();
    }

    public String getAreagcode() {
        return areagcode;
    }

    public void setAreagcode(String areagcode) {
        this.areagcode = areagcode == null ? null : areagcode.trim();
    }

    public String getCustomerHk() {
        return customerHk;
    }

    public void setCustomerHk(String customerHk) {
        this.customerHk = customerHk == null ? null : customerHk.trim();
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Integer getPriceflag() {
        return priceflag;
    }

    public void setPriceflag(Integer priceflag) {
        this.priceflag = priceflag;
    }

    public Integer getSpecpack() {
        return specpack;
    }

    public void setSpecpack(Integer specpack) {
        this.specpack = specpack;
    }

    public String getLaji() {
        return laji;
    }

    public void setLaji(String laji) {
        this.laji = laji == null ? null : laji.trim();
    }

    public String getCstmdivision() {
        return cstmdivision;
    }

    public void setCstmdivision(String cstmdivision) {
        this.cstmdivision = cstmdivision == null ? null : cstmdivision.trim();
    }

    public String getSalesin() {
        return salesin;
    }

    public void setSalesin(String salesin) {
        this.salesin = salesin == null ? null : salesin.trim();
    }

    public String getIndclass1() {
        return indclass1;
    }

    public void setIndclass1(String indclass1) {
        this.indclass1 = indclass1 == null ? null : indclass1.trim();
    }

    public String getIndclass2() {
        return indclass2;
    }

    public void setIndclass2(String indclass2) {
        this.indclass2 = indclass2 == null ? null : indclass2.trim();
    }

    public String getIndclass3() {
        return indclass3;
    }

    public void setIndclass3(String indclass3) {
        this.indclass3 = indclass3 == null ? null : indclass3.trim();
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business == null ? null : business.trim();
    }

    public String getGcode() {
        return gcode;
    }

    public void setGcode(String gcode) {
        this.gcode = gcode == null ? null : gcode.trim();
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom == null ? null : comefrom.trim();
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web == null ? null : web.trim();
    }

    public String getPricegrade() {
        return pricegrade;
    }

    public void setPricegrade(String pricegrade) {
        this.pricegrade = pricegrade == null ? null : pricegrade.trim();
    }

    public String getSamecustomerno() {
        return samecustomerno;
    }

    public void setSamecustomerno(String samecustomerno) {
        this.samecustomerno = samecustomerno == null ? null : samecustomerno.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getJpvipcode() {
        return jpvipcode;
    }

    public void setJpvipcode(String jpvipcode) {
        this.jpvipcode = jpvipcode == null ? null : jpvipcode.trim();
    }

    public String getVillagecode() {
        return villagecode;
    }

    public void setVillagecode(String villagecode) {
        this.villagecode = villagecode == null ? null : villagecode.trim();
    }

    public String getIndclass4() {
        return indclass4;
    }

    public void setIndclass4(String indclass4) {
        this.indclass4 = indclass4 == null ? null : indclass4.trim();
    }

    public String getIndclass5() {
        return indclass5;
    }

    public void setIndclass5(String indclass5) {
        this.indclass5 = indclass5 == null ? null : indclass5.trim();
    }

    public String getIndclass6() {
        return indclass6;
    }

    public void setIndclass6(String indclass6) {
        this.indclass6 = indclass6 == null ? null : indclass6.trim();
    }

    public Date getLastorderdate() {
        return lastorderdate;
    }

    public void setLastorderdate(Date lastorderdate) {
        this.lastorderdate = lastorderdate;
    }

    public String getNccustomerno() {
        return nccustomerno;
    }

    public void setNccustomerno(String nccustomerno) {
        this.nccustomerno = nccustomerno == null ? null : nccustomerno.trim();
    }

    public String getRcvinvoiceemail() {
        return rcvinvoiceemail;
    }

    public void setRcvinvoiceemail(String rcvinvoiceemail) {
        this.rcvinvoiceemail = rcvinvoiceemail == null ? null : rcvinvoiceemail.trim();
    }

    public String getSleepcustomer() {
        return sleepcustomer;
    }

    public void setSleepcustomer(String sleepcustomer) {
        this.sleepcustomer = sleepcustomer == null ? null : sleepcustomer.trim();
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
        CustomerGz other = (CustomerGz) that;
        return (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getCstmname() == null ? other.getCstmname() == null : this.getCstmname().equals(other.getCstmname()))
            && (this.getEnglishname() == null ? other.getEnglishname() == null : this.getEnglishname().equals(other.getEnglishname()))
            && (this.getCstmtype() == null ? other.getCstmtype() == null : this.getCstmtype().equals(other.getCstmtype()))
            && (this.getCstmgrade() == null ? other.getCstmgrade() == null : this.getCstmgrade().equals(other.getCstmgrade()))
            && (this.getStatecode() == null ? other.getStatecode() == null : this.getStatecode().equals(other.getStatecode()))
            && (this.getIndcode() == null ? other.getIndcode() == null : this.getIndcode().equals(other.getIndcode()))
            && (this.getPrjcode() == null ? other.getPrjcode() == null : this.getPrjcode().equals(other.getPrjcode()))
            && (this.getEtpofjp() == null ? other.getEtpofjp() == null : this.getEtpofjp().equals(other.getEtpofjp()))
            && (this.getLgealrep() == null ? other.getLgealrep() == null : this.getLgealrep().equals(other.getLgealrep()))
            && (this.getCaddress1() == null ? other.getCaddress1() == null : this.getCaddress1().equals(other.getCaddress1()))
            && (this.getCaddress2() == null ? other.getCaddress2() == null : this.getCaddress2().equals(other.getCaddress2()))
            && (this.getPostcode() == null ? other.getPostcode() == null : this.getPostcode().equals(other.getPostcode()))
            && (this.getTelnofirst() == null ? other.getTelnofirst() == null : this.getTelnofirst().equals(other.getTelnofirst()))
            && (this.getTelnolast() == null ? other.getTelnolast() == null : this.getTelnolast().equals(other.getTelnolast()))
            && (this.getTelother() == null ? other.getTelother() == null : this.getTelother().equals(other.getTelother()))
            && (this.getFaxnofirst() == null ? other.getFaxnofirst() == null : this.getFaxnofirst().equals(other.getFaxnofirst()))
            && (this.getFaxnolast() == null ? other.getFaxnolast() == null : this.getFaxnolast().equals(other.getFaxnolast()))
            && (this.getContactpsn() == null ? other.getContactpsn() == null : this.getContactpsn().equals(other.getContactpsn()))
            && (this.getContpsnduty() == null ? other.getContpsnduty() == null : this.getContpsnduty().equals(other.getContpsnduty()))
            && (this.getContdept() == null ? other.getContdept() == null : this.getContdept().equals(other.getContdept()))
            && (this.getBank() == null ? other.getBank() == null : this.getBank().equals(other.getBank()))
            && (this.getTaxno() == null ? other.getTaxno() == null : this.getTaxno().equals(other.getTaxno()))
            && (this.getAccountno() == null ? other.getAccountno() == null : this.getAccountno().equals(other.getAccountno()))
            && (this.getDeptcode() == null ? other.getDeptcode() == null : this.getDeptcode().equals(other.getDeptcode()))
            && (this.getPsnsmcid() == null ? other.getPsnsmcid() == null : this.getPsnsmcid().equals(other.getPsnsmcid()))
            && (this.getPricelvl() == null ? other.getPricelvl() == null : this.getPricelvl().equals(other.getPricelvl()))
            && (this.getFiledate() == null ? other.getFiledate() == null : this.getFiledate().equals(other.getFiledate()))
            && (this.getUpddate() == null ? other.getUpddate() == null : this.getUpddate().equals(other.getUpddate()))
            && (this.getObinvoice() == null ? other.getObinvoice() == null : this.getObinvoice().equals(other.getObinvoice()))
            && (this.getNotaxinvoice() == null ? other.getNotaxinvoice() == null : this.getNotaxinvoice().equals(other.getNotaxinvoice()))
            && (this.getEaddress() == null ? other.getEaddress() == null : this.getEaddress().equals(other.getEaddress()))
            && (this.getInvflag() == null ? other.getInvflag() == null : this.getInvflag().equals(other.getInvflag()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getRequirement() == null ? other.getRequirement() == null : this.getRequirement().equals(other.getRequirement()))
            && (this.getAgentno() == null ? other.getAgentno() == null : this.getAgentno().equals(other.getAgentno()))
            && (this.getStockflag() == null ? other.getStockflag() == null : this.getStockflag().equals(other.getStockflag()))
            && (this.getInvagent() == null ? other.getInvagent() == null : this.getInvagent().equals(other.getInvagent()))
            && (this.getCstmgcode() == null ? other.getCstmgcode() == null : this.getCstmgcode().equals(other.getCstmgcode()))
            && (this.getAreagcode() == null ? other.getAreagcode() == null : this.getAreagcode().equals(other.getAreagcode()))
            && (this.getCustomerHk() == null ? other.getCustomerHk() == null : this.getCustomerHk().equals(other.getCustomerHk()))
            && (this.getDeposit() == null ? other.getDeposit() == null : this.getDeposit().equals(other.getDeposit()))
            && (this.getPriceflag() == null ? other.getPriceflag() == null : this.getPriceflag().equals(other.getPriceflag()))
            && (this.getSpecpack() == null ? other.getSpecpack() == null : this.getSpecpack().equals(other.getSpecpack()))
            && (this.getLaji() == null ? other.getLaji() == null : this.getLaji().equals(other.getLaji()))
            && (this.getCstmdivision() == null ? other.getCstmdivision() == null : this.getCstmdivision().equals(other.getCstmdivision()))
            && (this.getSalesin() == null ? other.getSalesin() == null : this.getSalesin().equals(other.getSalesin()))
            && (this.getIndclass1() == null ? other.getIndclass1() == null : this.getIndclass1().equals(other.getIndclass1()))
            && (this.getIndclass2() == null ? other.getIndclass2() == null : this.getIndclass2().equals(other.getIndclass2()))
            && (this.getIndclass3() == null ? other.getIndclass3() == null : this.getIndclass3().equals(other.getIndclass3()))
            && (this.getBusiness() == null ? other.getBusiness() == null : this.getBusiness().equals(other.getBusiness()))
            && (this.getGcode() == null ? other.getGcode() == null : this.getGcode().equals(other.getGcode()))
            && (this.getGroupname() == null ? other.getGroupname() == null : this.getGroupname().equals(other.getGroupname()))
            && (this.getComefrom() == null ? other.getComefrom() == null : this.getComefrom().equals(other.getComefrom()))
            && (this.getWeb() == null ? other.getWeb() == null : this.getWeb().equals(other.getWeb()))
            && (this.getPricegrade() == null ? other.getPricegrade() == null : this.getPricegrade().equals(other.getPricegrade()))
            && (this.getSamecustomerno() == null ? other.getSamecustomerno() == null : this.getSamecustomerno().equals(other.getSamecustomerno()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getJpvipcode() == null ? other.getJpvipcode() == null : this.getJpvipcode().equals(other.getJpvipcode()))
            && (this.getVillagecode() == null ? other.getVillagecode() == null : this.getVillagecode().equals(other.getVillagecode()))
            && (this.getIndclass4() == null ? other.getIndclass4() == null : this.getIndclass4().equals(other.getIndclass4()))
            && (this.getIndclass5() == null ? other.getIndclass5() == null : this.getIndclass5().equals(other.getIndclass5()))
            && (this.getIndclass6() == null ? other.getIndclass6() == null : this.getIndclass6().equals(other.getIndclass6()))
            && (this.getLastorderdate() == null ? other.getLastorderdate() == null : this.getLastorderdate().equals(other.getLastorderdate()))
            && (this.getNccustomerno() == null ? other.getNccustomerno() == null : this.getNccustomerno().equals(other.getNccustomerno()))
            && (this.getRcvinvoiceemail() == null ? other.getRcvinvoiceemail() == null : this.getRcvinvoiceemail().equals(other.getRcvinvoiceemail()))
            && (this.getSleepcustomer() == null ? other.getSleepcustomer() == null : this.getSleepcustomer().equals(other.getSleepcustomer()))
            && (Arrays.equals(this.getRv(), other.getRv()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getCstmname() == null) ? 0 : getCstmname().hashCode());
        result = prime * result + ((getEnglishname() == null) ? 0 : getEnglishname().hashCode());
        result = prime * result + ((getCstmtype() == null) ? 0 : getCstmtype().hashCode());
        result = prime * result + ((getCstmgrade() == null) ? 0 : getCstmgrade().hashCode());
        result = prime * result + ((getStatecode() == null) ? 0 : getStatecode().hashCode());
        result = prime * result + ((getIndcode() == null) ? 0 : getIndcode().hashCode());
        result = prime * result + ((getPrjcode() == null) ? 0 : getPrjcode().hashCode());
        result = prime * result + ((getEtpofjp() == null) ? 0 : getEtpofjp().hashCode());
        result = prime * result + ((getLgealrep() == null) ? 0 : getLgealrep().hashCode());
        result = prime * result + ((getCaddress1() == null) ? 0 : getCaddress1().hashCode());
        result = prime * result + ((getCaddress2() == null) ? 0 : getCaddress2().hashCode());
        result = prime * result + ((getPostcode() == null) ? 0 : getPostcode().hashCode());
        result = prime * result + ((getTelnofirst() == null) ? 0 : getTelnofirst().hashCode());
        result = prime * result + ((getTelnolast() == null) ? 0 : getTelnolast().hashCode());
        result = prime * result + ((getTelother() == null) ? 0 : getTelother().hashCode());
        result = prime * result + ((getFaxnofirst() == null) ? 0 : getFaxnofirst().hashCode());
        result = prime * result + ((getFaxnolast() == null) ? 0 : getFaxnolast().hashCode());
        result = prime * result + ((getContactpsn() == null) ? 0 : getContactpsn().hashCode());
        result = prime * result + ((getContpsnduty() == null) ? 0 : getContpsnduty().hashCode());
        result = prime * result + ((getContdept() == null) ? 0 : getContdept().hashCode());
        result = prime * result + ((getBank() == null) ? 0 : getBank().hashCode());
        result = prime * result + ((getTaxno() == null) ? 0 : getTaxno().hashCode());
        result = prime * result + ((getAccountno() == null) ? 0 : getAccountno().hashCode());
        result = prime * result + ((getDeptcode() == null) ? 0 : getDeptcode().hashCode());
        result = prime * result + ((getPsnsmcid() == null) ? 0 : getPsnsmcid().hashCode());
        result = prime * result + ((getPricelvl() == null) ? 0 : getPricelvl().hashCode());
        result = prime * result + ((getFiledate() == null) ? 0 : getFiledate().hashCode());
        result = prime * result + ((getUpddate() == null) ? 0 : getUpddate().hashCode());
        result = prime * result + ((getObinvoice() == null) ? 0 : getObinvoice().hashCode());
        result = prime * result + ((getNotaxinvoice() == null) ? 0 : getNotaxinvoice().hashCode());
        result = prime * result + ((getEaddress() == null) ? 0 : getEaddress().hashCode());
        result = prime * result + ((getInvflag() == null) ? 0 : getInvflag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getRequirement() == null) ? 0 : getRequirement().hashCode());
        result = prime * result + ((getAgentno() == null) ? 0 : getAgentno().hashCode());
        result = prime * result + ((getStockflag() == null) ? 0 : getStockflag().hashCode());
        result = prime * result + ((getInvagent() == null) ? 0 : getInvagent().hashCode());
        result = prime * result + ((getCstmgcode() == null) ? 0 : getCstmgcode().hashCode());
        result = prime * result + ((getAreagcode() == null) ? 0 : getAreagcode().hashCode());
        result = prime * result + ((getCustomerHk() == null) ? 0 : getCustomerHk().hashCode());
        result = prime * result + ((getDeposit() == null) ? 0 : getDeposit().hashCode());
        result = prime * result + ((getPriceflag() == null) ? 0 : getPriceflag().hashCode());
        result = prime * result + ((getSpecpack() == null) ? 0 : getSpecpack().hashCode());
        result = prime * result + ((getLaji() == null) ? 0 : getLaji().hashCode());
        result = prime * result + ((getCstmdivision() == null) ? 0 : getCstmdivision().hashCode());
        result = prime * result + ((getSalesin() == null) ? 0 : getSalesin().hashCode());
        result = prime * result + ((getIndclass1() == null) ? 0 : getIndclass1().hashCode());
        result = prime * result + ((getIndclass2() == null) ? 0 : getIndclass2().hashCode());
        result = prime * result + ((getIndclass3() == null) ? 0 : getIndclass3().hashCode());
        result = prime * result + ((getBusiness() == null) ? 0 : getBusiness().hashCode());
        result = prime * result + ((getGcode() == null) ? 0 : getGcode().hashCode());
        result = prime * result + ((getGroupname() == null) ? 0 : getGroupname().hashCode());
        result = prime * result + ((getComefrom() == null) ? 0 : getComefrom().hashCode());
        result = prime * result + ((getWeb() == null) ? 0 : getWeb().hashCode());
        result = prime * result + ((getPricegrade() == null) ? 0 : getPricegrade().hashCode());
        result = prime * result + ((getSamecustomerno() == null) ? 0 : getSamecustomerno().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getJpvipcode() == null) ? 0 : getJpvipcode().hashCode());
        result = prime * result + ((getVillagecode() == null) ? 0 : getVillagecode().hashCode());
        result = prime * result + ((getIndclass4() == null) ? 0 : getIndclass4().hashCode());
        result = prime * result + ((getIndclass5() == null) ? 0 : getIndclass5().hashCode());
        result = prime * result + ((getIndclass6() == null) ? 0 : getIndclass6().hashCode());
        result = prime * result + ((getLastorderdate() == null) ? 0 : getLastorderdate().hashCode());
        result = prime * result + ((getNccustomerno() == null) ? 0 : getNccustomerno().hashCode());
        result = prime * result + ((getRcvinvoiceemail() == null) ? 0 : getRcvinvoiceemail().hashCode());
        result = prime * result + ((getSleepcustomer() == null) ? 0 : getSleepcustomer().hashCode());
        result = prime * result + (Arrays.hashCode(getRv()));
        return result;
    }
}