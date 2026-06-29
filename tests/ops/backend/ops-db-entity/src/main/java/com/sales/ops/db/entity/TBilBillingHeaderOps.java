package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TBilBillingHeaderOps implements Serializable {
    private String organizationid;

    private String billingno;

    private String customerid;

    private String carrierid;

    private String billingtype;

    private Date billmonth;

    private Date billdatefm;

    private Date billdateto;

    private String status;

    private BigDecimal totalamount;

    private BigDecimal discountstart;

    private BigDecimal discountrate;

    private BigDecimal adjustedamount;

    private String adjustnotes;

    private String currencytype;

    private BigDecimal currencyrate;

    private BigDecimal totalbillingamount;

    private BigDecimal invoiceamount;

    private String invoicestatus;

    private String createbranchid;

    private String settlementbranchid;

    private String tariffid;

    private BigDecimal taxrate;

    private String paymentmethod;

    private String notes;

    private String udf01;

    private String udf02;

    private String udf03;

    private String udf04;

    private String udf05;

    private Integer currentversion;

    private String oprseqflag;

    private String addwho;

    private Date addtime;

    private String editwho;

    private Date edittime;

    private String edisendflag;

    private String edierrordescr;

    private Date edisendtime;

    private String notetext;

    private static final long serialVersionUID = 1L;

    public String getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(String organizationid) {
        this.organizationid = organizationid == null ? null : organizationid.trim();
    }

    public String getBillingno() {
        return billingno;
    }

    public void setBillingno(String billingno) {
        this.billingno = billingno == null ? null : billingno.trim();
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    public String getCarrierid() {
        return carrierid;
    }

    public void setCarrierid(String carrierid) {
        this.carrierid = carrierid == null ? null : carrierid.trim();
    }

    public String getBillingtype() {
        return billingtype;
    }

    public void setBillingtype(String billingtype) {
        this.billingtype = billingtype == null ? null : billingtype.trim();
    }

    public Date getBillmonth() {
        return billmonth;
    }

    public void setBillmonth(Date billmonth) {
        this.billmonth = billmonth;
    }

    public Date getBilldatefm() {
        return billdatefm;
    }

    public void setBilldatefm(Date billdatefm) {
        this.billdatefm = billdatefm;
    }

    public Date getBilldateto() {
        return billdateto;
    }

    public void setBilldateto(Date billdateto) {
        this.billdateto = billdateto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    public BigDecimal getDiscountstart() {
        return discountstart;
    }

    public void setDiscountstart(BigDecimal discountstart) {
        this.discountstart = discountstart;
    }

    public BigDecimal getDiscountrate() {
        return discountrate;
    }

    public void setDiscountrate(BigDecimal discountrate) {
        this.discountrate = discountrate;
    }

    public BigDecimal getAdjustedamount() {
        return adjustedamount;
    }

    public void setAdjustedamount(BigDecimal adjustedamount) {
        this.adjustedamount = adjustedamount;
    }

    public String getAdjustnotes() {
        return adjustnotes;
    }

    public void setAdjustnotes(String adjustnotes) {
        this.adjustnotes = adjustnotes == null ? null : adjustnotes.trim();
    }

    public String getCurrencytype() {
        return currencytype;
    }

    public void setCurrencytype(String currencytype) {
        this.currencytype = currencytype == null ? null : currencytype.trim();
    }

    public BigDecimal getCurrencyrate() {
        return currencyrate;
    }

    public void setCurrencyrate(BigDecimal currencyrate) {
        this.currencyrate = currencyrate;
    }

    public BigDecimal getTotalbillingamount() {
        return totalbillingamount;
    }

    public void setTotalbillingamount(BigDecimal totalbillingamount) {
        this.totalbillingamount = totalbillingamount;
    }

    public BigDecimal getInvoiceamount() {
        return invoiceamount;
    }

    public void setInvoiceamount(BigDecimal invoiceamount) {
        this.invoiceamount = invoiceamount;
    }

    public String getInvoicestatus() {
        return invoicestatus;
    }

    public void setInvoicestatus(String invoicestatus) {
        this.invoicestatus = invoicestatus == null ? null : invoicestatus.trim();
    }

    public String getCreatebranchid() {
        return createbranchid;
    }

    public void setCreatebranchid(String createbranchid) {
        this.createbranchid = createbranchid == null ? null : createbranchid.trim();
    }

    public String getSettlementbranchid() {
        return settlementbranchid;
    }

    public void setSettlementbranchid(String settlementbranchid) {
        this.settlementbranchid = settlementbranchid == null ? null : settlementbranchid.trim();
    }

    public String getTariffid() {
        return tariffid;
    }

    public void setTariffid(String tariffid) {
        this.tariffid = tariffid == null ? null : tariffid.trim();
    }

    public BigDecimal getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod == null ? null : paymentmethod.trim();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getUdf01() {
        return udf01;
    }

    public void setUdf01(String udf01) {
        this.udf01 = udf01 == null ? null : udf01.trim();
    }

    public String getUdf02() {
        return udf02;
    }

    public void setUdf02(String udf02) {
        this.udf02 = udf02 == null ? null : udf02.trim();
    }

    public String getUdf03() {
        return udf03;
    }

    public void setUdf03(String udf03) {
        this.udf03 = udf03 == null ? null : udf03.trim();
    }

    public String getUdf04() {
        return udf04;
    }

    public void setUdf04(String udf04) {
        this.udf04 = udf04 == null ? null : udf04.trim();
    }

    public String getUdf05() {
        return udf05;
    }

    public void setUdf05(String udf05) {
        this.udf05 = udf05 == null ? null : udf05.trim();
    }

    public Integer getCurrentversion() {
        return currentversion;
    }

    public void setCurrentversion(Integer currentversion) {
        this.currentversion = currentversion;
    }

    public String getOprseqflag() {
        return oprseqflag;
    }

    public void setOprseqflag(String oprseqflag) {
        this.oprseqflag = oprseqflag == null ? null : oprseqflag.trim();
    }

    public String getAddwho() {
        return addwho;
    }

    public void setAddwho(String addwho) {
        this.addwho = addwho == null ? null : addwho.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getEditwho() {
        return editwho;
    }

    public void setEditwho(String editwho) {
        this.editwho = editwho == null ? null : editwho.trim();
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public String getEdisendflag() {
        return edisendflag;
    }

    public void setEdisendflag(String edisendflag) {
        this.edisendflag = edisendflag == null ? null : edisendflag.trim();
    }

    public String getEdierrordescr() {
        return edierrordescr;
    }

    public void setEdierrordescr(String edierrordescr) {
        this.edierrordescr = edierrordescr == null ? null : edierrordescr.trim();
    }

    public Date getEdisendtime() {
        return edisendtime;
    }

    public void setEdisendtime(Date edisendtime) {
        this.edisendtime = edisendtime;
    }

    public String getNotetext() {
        return notetext;
    }

    public void setNotetext(String notetext) {
        this.notetext = notetext == null ? null : notetext.trim();
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
        TBilBillingHeaderOps other = (TBilBillingHeaderOps) that;
        return (this.getOrganizationid() == null ? other.getOrganizationid() == null : this.getOrganizationid().equals(other.getOrganizationid()))
            && (this.getBillingno() == null ? other.getBillingno() == null : this.getBillingno().equals(other.getBillingno()))
            && (this.getCustomerid() == null ? other.getCustomerid() == null : this.getCustomerid().equals(other.getCustomerid()))
            && (this.getCarrierid() == null ? other.getCarrierid() == null : this.getCarrierid().equals(other.getCarrierid()))
            && (this.getBillingtype() == null ? other.getBillingtype() == null : this.getBillingtype().equals(other.getBillingtype()))
            && (this.getBillmonth() == null ? other.getBillmonth() == null : this.getBillmonth().equals(other.getBillmonth()))
            && (this.getBilldatefm() == null ? other.getBilldatefm() == null : this.getBilldatefm().equals(other.getBilldatefm()))
            && (this.getBilldateto() == null ? other.getBilldateto() == null : this.getBilldateto().equals(other.getBilldateto()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getTotalamount() == null ? other.getTotalamount() == null : this.getTotalamount().equals(other.getTotalamount()))
            && (this.getDiscountstart() == null ? other.getDiscountstart() == null : this.getDiscountstart().equals(other.getDiscountstart()))
            && (this.getDiscountrate() == null ? other.getDiscountrate() == null : this.getDiscountrate().equals(other.getDiscountrate()))
            && (this.getAdjustedamount() == null ? other.getAdjustedamount() == null : this.getAdjustedamount().equals(other.getAdjustedamount()))
            && (this.getAdjustnotes() == null ? other.getAdjustnotes() == null : this.getAdjustnotes().equals(other.getAdjustnotes()))
            && (this.getCurrencytype() == null ? other.getCurrencytype() == null : this.getCurrencytype().equals(other.getCurrencytype()))
            && (this.getCurrencyrate() == null ? other.getCurrencyrate() == null : this.getCurrencyrate().equals(other.getCurrencyrate()))
            && (this.getTotalbillingamount() == null ? other.getTotalbillingamount() == null : this.getTotalbillingamount().equals(other.getTotalbillingamount()))
            && (this.getInvoiceamount() == null ? other.getInvoiceamount() == null : this.getInvoiceamount().equals(other.getInvoiceamount()))
            && (this.getInvoicestatus() == null ? other.getInvoicestatus() == null : this.getInvoicestatus().equals(other.getInvoicestatus()))
            && (this.getCreatebranchid() == null ? other.getCreatebranchid() == null : this.getCreatebranchid().equals(other.getCreatebranchid()))
            && (this.getSettlementbranchid() == null ? other.getSettlementbranchid() == null : this.getSettlementbranchid().equals(other.getSettlementbranchid()))
            && (this.getTariffid() == null ? other.getTariffid() == null : this.getTariffid().equals(other.getTariffid()))
            && (this.getTaxrate() == null ? other.getTaxrate() == null : this.getTaxrate().equals(other.getTaxrate()))
            && (this.getPaymentmethod() == null ? other.getPaymentmethod() == null : this.getPaymentmethod().equals(other.getPaymentmethod()))
            && (this.getNotes() == null ? other.getNotes() == null : this.getNotes().equals(other.getNotes()))
            && (this.getUdf01() == null ? other.getUdf01() == null : this.getUdf01().equals(other.getUdf01()))
            && (this.getUdf02() == null ? other.getUdf02() == null : this.getUdf02().equals(other.getUdf02()))
            && (this.getUdf03() == null ? other.getUdf03() == null : this.getUdf03().equals(other.getUdf03()))
            && (this.getUdf04() == null ? other.getUdf04() == null : this.getUdf04().equals(other.getUdf04()))
            && (this.getUdf05() == null ? other.getUdf05() == null : this.getUdf05().equals(other.getUdf05()))
            && (this.getCurrentversion() == null ? other.getCurrentversion() == null : this.getCurrentversion().equals(other.getCurrentversion()))
            && (this.getOprseqflag() == null ? other.getOprseqflag() == null : this.getOprseqflag().equals(other.getOprseqflag()))
            && (this.getAddwho() == null ? other.getAddwho() == null : this.getAddwho().equals(other.getAddwho()))
            && (this.getAddtime() == null ? other.getAddtime() == null : this.getAddtime().equals(other.getAddtime()))
            && (this.getEditwho() == null ? other.getEditwho() == null : this.getEditwho().equals(other.getEditwho()))
            && (this.getEdittime() == null ? other.getEdittime() == null : this.getEdittime().equals(other.getEdittime()))
            && (this.getEdisendflag() == null ? other.getEdisendflag() == null : this.getEdisendflag().equals(other.getEdisendflag()))
            && (this.getEdierrordescr() == null ? other.getEdierrordescr() == null : this.getEdierrordescr().equals(other.getEdierrordescr()))
            && (this.getEdisendtime() == null ? other.getEdisendtime() == null : this.getEdisendtime().equals(other.getEdisendtime()))
            && (this.getNotetext() == null ? other.getNotetext() == null : this.getNotetext().equals(other.getNotetext()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrganizationid() == null) ? 0 : getOrganizationid().hashCode());
        result = prime * result + ((getBillingno() == null) ? 0 : getBillingno().hashCode());
        result = prime * result + ((getCustomerid() == null) ? 0 : getCustomerid().hashCode());
        result = prime * result + ((getCarrierid() == null) ? 0 : getCarrierid().hashCode());
        result = prime * result + ((getBillingtype() == null) ? 0 : getBillingtype().hashCode());
        result = prime * result + ((getBillmonth() == null) ? 0 : getBillmonth().hashCode());
        result = prime * result + ((getBilldatefm() == null) ? 0 : getBilldatefm().hashCode());
        result = prime * result + ((getBilldateto() == null) ? 0 : getBilldateto().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getTotalamount() == null) ? 0 : getTotalamount().hashCode());
        result = prime * result + ((getDiscountstart() == null) ? 0 : getDiscountstart().hashCode());
        result = prime * result + ((getDiscountrate() == null) ? 0 : getDiscountrate().hashCode());
        result = prime * result + ((getAdjustedamount() == null) ? 0 : getAdjustedamount().hashCode());
        result = prime * result + ((getAdjustnotes() == null) ? 0 : getAdjustnotes().hashCode());
        result = prime * result + ((getCurrencytype() == null) ? 0 : getCurrencytype().hashCode());
        result = prime * result + ((getCurrencyrate() == null) ? 0 : getCurrencyrate().hashCode());
        result = prime * result + ((getTotalbillingamount() == null) ? 0 : getTotalbillingamount().hashCode());
        result = prime * result + ((getInvoiceamount() == null) ? 0 : getInvoiceamount().hashCode());
        result = prime * result + ((getInvoicestatus() == null) ? 0 : getInvoicestatus().hashCode());
        result = prime * result + ((getCreatebranchid() == null) ? 0 : getCreatebranchid().hashCode());
        result = prime * result + ((getSettlementbranchid() == null) ? 0 : getSettlementbranchid().hashCode());
        result = prime * result + ((getTariffid() == null) ? 0 : getTariffid().hashCode());
        result = prime * result + ((getTaxrate() == null) ? 0 : getTaxrate().hashCode());
        result = prime * result + ((getPaymentmethod() == null) ? 0 : getPaymentmethod().hashCode());
        result = prime * result + ((getNotes() == null) ? 0 : getNotes().hashCode());
        result = prime * result + ((getUdf01() == null) ? 0 : getUdf01().hashCode());
        result = prime * result + ((getUdf02() == null) ? 0 : getUdf02().hashCode());
        result = prime * result + ((getUdf03() == null) ? 0 : getUdf03().hashCode());
        result = prime * result + ((getUdf04() == null) ? 0 : getUdf04().hashCode());
        result = prime * result + ((getUdf05() == null) ? 0 : getUdf05().hashCode());
        result = prime * result + ((getCurrentversion() == null) ? 0 : getCurrentversion().hashCode());
        result = prime * result + ((getOprseqflag() == null) ? 0 : getOprseqflag().hashCode());
        result = prime * result + ((getAddwho() == null) ? 0 : getAddwho().hashCode());
        result = prime * result + ((getAddtime() == null) ? 0 : getAddtime().hashCode());
        result = prime * result + ((getEditwho() == null) ? 0 : getEditwho().hashCode());
        result = prime * result + ((getEdittime() == null) ? 0 : getEdittime().hashCode());
        result = prime * result + ((getEdisendflag() == null) ? 0 : getEdisendflag().hashCode());
        result = prime * result + ((getEdierrordescr() == null) ? 0 : getEdierrordescr().hashCode());
        result = prime * result + ((getEdisendtime() == null) ? 0 : getEdisendtime().hashCode());
        result = prime * result + ((getNotetext() == null) ? 0 : getNotetext().hashCode());
        return result;
    }
}