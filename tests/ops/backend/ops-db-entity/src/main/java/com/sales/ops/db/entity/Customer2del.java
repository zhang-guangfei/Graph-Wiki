package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Customer2del implements Serializable {
    private String customerno;

    private String userno;

    private String cstmname;

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

    private String eaddress;

    private String invflag;

    private String remark;

    private BigDecimal requirement;

    private String agentno;

    private String stockflag;

    private String invagent;

    private String vipcode;

    private String laji;

    private String vipcodeCn;

    private String enduserType;

    private Boolean beijian;

    private Boolean peitao;

    private String phoneFirst;

    private String phoneLast;

    private String restrictCust;

    private String deliveryFlag;

    private String agenttype;

    private String deliverytype;

    private String licenceno;

    private Boolean nolicence;

    private String belongedgroup;

    private String statementemail;

    private String invoiceaddress;

    private String indcodeGroupid;

    private String indcodePointrate;

    private String unuseFlag;

    private String customernoNc;

    private String risktip;

    private String survivalstatusdescription;

    private String registeredcapital;

    private String contributedcapital;

    private String companytypedescription;

    private String marketcategorycode;

    private String hlcode;

    private String tradecompanyid;

    private String smcgroupid;

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

    public String getVipcode() {
        return vipcode;
    }

    public void setVipcode(String vipcode) {
        this.vipcode = vipcode == null ? null : vipcode.trim();
    }

    public String getLaji() {
        return laji;
    }

    public void setLaji(String laji) {
        this.laji = laji == null ? null : laji.trim();
    }

    public String getVipcodeCn() {
        return vipcodeCn;
    }

    public void setVipcodeCn(String vipcodeCn) {
        this.vipcodeCn = vipcodeCn == null ? null : vipcodeCn.trim();
    }

    public String getEnduserType() {
        return enduserType;
    }

    public void setEnduserType(String enduserType) {
        this.enduserType = enduserType == null ? null : enduserType.trim();
    }

    public Boolean getBeijian() {
        return beijian;
    }

    public void setBeijian(Boolean beijian) {
        this.beijian = beijian;
    }

    public Boolean getPeitao() {
        return peitao;
    }

    public void setPeitao(Boolean peitao) {
        this.peitao = peitao;
    }

    public String getPhoneFirst() {
        return phoneFirst;
    }

    public void setPhoneFirst(String phoneFirst) {
        this.phoneFirst = phoneFirst == null ? null : phoneFirst.trim();
    }

    public String getPhoneLast() {
        return phoneLast;
    }

    public void setPhoneLast(String phoneLast) {
        this.phoneLast = phoneLast == null ? null : phoneLast.trim();
    }

    public String getRestrictCust() {
        return restrictCust;
    }

    public void setRestrictCust(String restrictCust) {
        this.restrictCust = restrictCust == null ? null : restrictCust.trim();
    }

    public String getDeliveryFlag() {
        return deliveryFlag;
    }

    public void setDeliveryFlag(String deliveryFlag) {
        this.deliveryFlag = deliveryFlag == null ? null : deliveryFlag.trim();
    }

    public String getAgenttype() {
        return agenttype;
    }

    public void setAgenttype(String agenttype) {
        this.agenttype = agenttype == null ? null : agenttype.trim();
    }

    public String getDeliverytype() {
        return deliverytype;
    }

    public void setDeliverytype(String deliverytype) {
        this.deliverytype = deliverytype == null ? null : deliverytype.trim();
    }

    public String getLicenceno() {
        return licenceno;
    }

    public void setLicenceno(String licenceno) {
        this.licenceno = licenceno == null ? null : licenceno.trim();
    }

    public Boolean getNolicence() {
        return nolicence;
    }

    public void setNolicence(Boolean nolicence) {
        this.nolicence = nolicence;
    }

    public String getBelongedgroup() {
        return belongedgroup;
    }

    public void setBelongedgroup(String belongedgroup) {
        this.belongedgroup = belongedgroup == null ? null : belongedgroup.trim();
    }

    public String getStatementemail() {
        return statementemail;
    }

    public void setStatementemail(String statementemail) {
        this.statementemail = statementemail == null ? null : statementemail.trim();
    }

    public String getInvoiceaddress() {
        return invoiceaddress;
    }

    public void setInvoiceaddress(String invoiceaddress) {
        this.invoiceaddress = invoiceaddress == null ? null : invoiceaddress.trim();
    }

    public String getIndcodeGroupid() {
        return indcodeGroupid;
    }

    public void setIndcodeGroupid(String indcodeGroupid) {
        this.indcodeGroupid = indcodeGroupid == null ? null : indcodeGroupid.trim();
    }

    public String getIndcodePointrate() {
        return indcodePointrate;
    }

    public void setIndcodePointrate(String indcodePointrate) {
        this.indcodePointrate = indcodePointrate == null ? null : indcodePointrate.trim();
    }

    public String getUnuseFlag() {
        return unuseFlag;
    }

    public void setUnuseFlag(String unuseFlag) {
        this.unuseFlag = unuseFlag == null ? null : unuseFlag.trim();
    }

    public String getCustomernoNc() {
        return customernoNc;
    }

    public void setCustomernoNc(String customernoNc) {
        this.customernoNc = customernoNc == null ? null : customernoNc.trim();
    }

    public String getRisktip() {
        return risktip;
    }

    public void setRisktip(String risktip) {
        this.risktip = risktip == null ? null : risktip.trim();
    }

    public String getSurvivalstatusdescription() {
        return survivalstatusdescription;
    }

    public void setSurvivalstatusdescription(String survivalstatusdescription) {
        this.survivalstatusdescription = survivalstatusdescription == null ? null : survivalstatusdescription.trim();
    }

    public String getRegisteredcapital() {
        return registeredcapital;
    }

    public void setRegisteredcapital(String registeredcapital) {
        this.registeredcapital = registeredcapital == null ? null : registeredcapital.trim();
    }

    public String getContributedcapital() {
        return contributedcapital;
    }

    public void setContributedcapital(String contributedcapital) {
        this.contributedcapital = contributedcapital == null ? null : contributedcapital.trim();
    }

    public String getCompanytypedescription() {
        return companytypedescription;
    }

    public void setCompanytypedescription(String companytypedescription) {
        this.companytypedescription = companytypedescription == null ? null : companytypedescription.trim();
    }

    public String getMarketcategorycode() {
        return marketcategorycode;
    }

    public void setMarketcategorycode(String marketcategorycode) {
        this.marketcategorycode = marketcategorycode == null ? null : marketcategorycode.trim();
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

    public String getSmcgroupid() {
        return smcgroupid;
    }

    public void setSmcgroupid(String smcgroupid) {
        this.smcgroupid = smcgroupid == null ? null : smcgroupid.trim();
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
        Customer2del other = (Customer2del) that;
        return (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getCstmname() == null ? other.getCstmname() == null : this.getCstmname().equals(other.getCstmname()))
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
            && (this.getEaddress() == null ? other.getEaddress() == null : this.getEaddress().equals(other.getEaddress()))
            && (this.getInvflag() == null ? other.getInvflag() == null : this.getInvflag().equals(other.getInvflag()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getRequirement() == null ? other.getRequirement() == null : this.getRequirement().equals(other.getRequirement()))
            && (this.getAgentno() == null ? other.getAgentno() == null : this.getAgentno().equals(other.getAgentno()))
            && (this.getStockflag() == null ? other.getStockflag() == null : this.getStockflag().equals(other.getStockflag()))
            && (this.getInvagent() == null ? other.getInvagent() == null : this.getInvagent().equals(other.getInvagent()))
            && (this.getVipcode() == null ? other.getVipcode() == null : this.getVipcode().equals(other.getVipcode()))
            && (this.getLaji() == null ? other.getLaji() == null : this.getLaji().equals(other.getLaji()))
            && (this.getVipcodeCn() == null ? other.getVipcodeCn() == null : this.getVipcodeCn().equals(other.getVipcodeCn()))
            && (this.getEnduserType() == null ? other.getEnduserType() == null : this.getEnduserType().equals(other.getEnduserType()))
            && (this.getBeijian() == null ? other.getBeijian() == null : this.getBeijian().equals(other.getBeijian()))
            && (this.getPeitao() == null ? other.getPeitao() == null : this.getPeitao().equals(other.getPeitao()))
            && (this.getPhoneFirst() == null ? other.getPhoneFirst() == null : this.getPhoneFirst().equals(other.getPhoneFirst()))
            && (this.getPhoneLast() == null ? other.getPhoneLast() == null : this.getPhoneLast().equals(other.getPhoneLast()))
            && (this.getRestrictCust() == null ? other.getRestrictCust() == null : this.getRestrictCust().equals(other.getRestrictCust()))
            && (this.getDeliveryFlag() == null ? other.getDeliveryFlag() == null : this.getDeliveryFlag().equals(other.getDeliveryFlag()))
            && (this.getAgenttype() == null ? other.getAgenttype() == null : this.getAgenttype().equals(other.getAgenttype()))
            && (this.getDeliverytype() == null ? other.getDeliverytype() == null : this.getDeliverytype().equals(other.getDeliverytype()))
            && (this.getLicenceno() == null ? other.getLicenceno() == null : this.getLicenceno().equals(other.getLicenceno()))
            && (this.getNolicence() == null ? other.getNolicence() == null : this.getNolicence().equals(other.getNolicence()))
            && (this.getBelongedgroup() == null ? other.getBelongedgroup() == null : this.getBelongedgroup().equals(other.getBelongedgroup()))
            && (this.getStatementemail() == null ? other.getStatementemail() == null : this.getStatementemail().equals(other.getStatementemail()))
            && (this.getInvoiceaddress() == null ? other.getInvoiceaddress() == null : this.getInvoiceaddress().equals(other.getInvoiceaddress()))
            && (this.getIndcodeGroupid() == null ? other.getIndcodeGroupid() == null : this.getIndcodeGroupid().equals(other.getIndcodeGroupid()))
            && (this.getIndcodePointrate() == null ? other.getIndcodePointrate() == null : this.getIndcodePointrate().equals(other.getIndcodePointrate()))
            && (this.getUnuseFlag() == null ? other.getUnuseFlag() == null : this.getUnuseFlag().equals(other.getUnuseFlag()))
            && (this.getCustomernoNc() == null ? other.getCustomernoNc() == null : this.getCustomernoNc().equals(other.getCustomernoNc()))
            && (this.getRisktip() == null ? other.getRisktip() == null : this.getRisktip().equals(other.getRisktip()))
            && (this.getSurvivalstatusdescription() == null ? other.getSurvivalstatusdescription() == null : this.getSurvivalstatusdescription().equals(other.getSurvivalstatusdescription()))
            && (this.getRegisteredcapital() == null ? other.getRegisteredcapital() == null : this.getRegisteredcapital().equals(other.getRegisteredcapital()))
            && (this.getContributedcapital() == null ? other.getContributedcapital() == null : this.getContributedcapital().equals(other.getContributedcapital()))
            && (this.getCompanytypedescription() == null ? other.getCompanytypedescription() == null : this.getCompanytypedescription().equals(other.getCompanytypedescription()))
            && (this.getMarketcategorycode() == null ? other.getMarketcategorycode() == null : this.getMarketcategorycode().equals(other.getMarketcategorycode()))
            && (this.getHlcode() == null ? other.getHlcode() == null : this.getHlcode().equals(other.getHlcode()))
            && (this.getTradecompanyid() == null ? other.getTradecompanyid() == null : this.getTradecompanyid().equals(other.getTradecompanyid()))
            && (this.getSmcgroupid() == null ? other.getSmcgroupid() == null : this.getSmcgroupid().equals(other.getSmcgroupid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getCstmname() == null) ? 0 : getCstmname().hashCode());
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
        result = prime * result + ((getEaddress() == null) ? 0 : getEaddress().hashCode());
        result = prime * result + ((getInvflag() == null) ? 0 : getInvflag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getRequirement() == null) ? 0 : getRequirement().hashCode());
        result = prime * result + ((getAgentno() == null) ? 0 : getAgentno().hashCode());
        result = prime * result + ((getStockflag() == null) ? 0 : getStockflag().hashCode());
        result = prime * result + ((getInvagent() == null) ? 0 : getInvagent().hashCode());
        result = prime * result + ((getVipcode() == null) ? 0 : getVipcode().hashCode());
        result = prime * result + ((getLaji() == null) ? 0 : getLaji().hashCode());
        result = prime * result + ((getVipcodeCn() == null) ? 0 : getVipcodeCn().hashCode());
        result = prime * result + ((getEnduserType() == null) ? 0 : getEnduserType().hashCode());
        result = prime * result + ((getBeijian() == null) ? 0 : getBeijian().hashCode());
        result = prime * result + ((getPeitao() == null) ? 0 : getPeitao().hashCode());
        result = prime * result + ((getPhoneFirst() == null) ? 0 : getPhoneFirst().hashCode());
        result = prime * result + ((getPhoneLast() == null) ? 0 : getPhoneLast().hashCode());
        result = prime * result + ((getRestrictCust() == null) ? 0 : getRestrictCust().hashCode());
        result = prime * result + ((getDeliveryFlag() == null) ? 0 : getDeliveryFlag().hashCode());
        result = prime * result + ((getAgenttype() == null) ? 0 : getAgenttype().hashCode());
        result = prime * result + ((getDeliverytype() == null) ? 0 : getDeliverytype().hashCode());
        result = prime * result + ((getLicenceno() == null) ? 0 : getLicenceno().hashCode());
        result = prime * result + ((getNolicence() == null) ? 0 : getNolicence().hashCode());
        result = prime * result + ((getBelongedgroup() == null) ? 0 : getBelongedgroup().hashCode());
        result = prime * result + ((getStatementemail() == null) ? 0 : getStatementemail().hashCode());
        result = prime * result + ((getInvoiceaddress() == null) ? 0 : getInvoiceaddress().hashCode());
        result = prime * result + ((getIndcodeGroupid() == null) ? 0 : getIndcodeGroupid().hashCode());
        result = prime * result + ((getIndcodePointrate() == null) ? 0 : getIndcodePointrate().hashCode());
        result = prime * result + ((getUnuseFlag() == null) ? 0 : getUnuseFlag().hashCode());
        result = prime * result + ((getCustomernoNc() == null) ? 0 : getCustomernoNc().hashCode());
        result = prime * result + ((getRisktip() == null) ? 0 : getRisktip().hashCode());
        result = prime * result + ((getSurvivalstatusdescription() == null) ? 0 : getSurvivalstatusdescription().hashCode());
        result = prime * result + ((getRegisteredcapital() == null) ? 0 : getRegisteredcapital().hashCode());
        result = prime * result + ((getContributedcapital() == null) ? 0 : getContributedcapital().hashCode());
        result = prime * result + ((getCompanytypedescription() == null) ? 0 : getCompanytypedescription().hashCode());
        result = prime * result + ((getMarketcategorycode() == null) ? 0 : getMarketcategorycode().hashCode());
        result = prime * result + ((getHlcode() == null) ? 0 : getHlcode().hashCode());
        result = prime * result + ((getTradecompanyid() == null) ? 0 : getTradecompanyid().hashCode());
        result = prime * result + ((getSmcgroupid() == null) ? 0 : getSmcgroupid().hashCode());
        return result;
    }
}