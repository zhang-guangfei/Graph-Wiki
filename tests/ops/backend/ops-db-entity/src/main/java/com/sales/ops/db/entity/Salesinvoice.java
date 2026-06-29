package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Salesinvoice implements Serializable {
    private Long id;

    private String tradecompanyid;

    private String rorderno;

    private String modelno;

    private Integer quantity;

    private BigDecimal price;

    private String customerno;

    private String userno;

    private String dlventire;

    private BigDecimal amount;

    private BigDecimal taxamount;

    private BigDecimal ntaxamount;

    private String invflag;

    private Date optdate;

    private String optcode;

    private String classflag;

    private String invoiceno;

    private Date invdate;

    private String optstate;

    private String stockno;

    private String invflag1;

    private String prodflag;

    private String billno;

    private String username;

    private Date opttime;

    private String receiveEmp;

    private String receiveCust;

    private Date receiveDate;

    private String receiveRemark;

    private String receiveOptstate;

    private String purchaseno;

    private String agentpriceFlag;

    private String deptno;

    private BigDecimal discountamt;

    private String ordtype;

    private String invoicecode;

    private String invtype;

    private String invoicenoJp;

    private BigDecimal taxrate;

    private BigDecimal ntaxdiscountamt;

    private BigDecimal taxdiscountamt;

    private Date inserttime;

    private Date canceldate;

    private String extracted;

    private Date extracttime;

    private String invoicegroupkey;

    private String remark;

    private Date expdate;

    private Date ackdate;

    private String orderno;

    private Integer itemno;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradecompanyid() {
        return tradecompanyid;
    }

    public void setTradecompanyid(String tradecompanyid) {
        this.tradecompanyid = tradecompanyid == null ? null : tradecompanyid.trim();
    }

    public String getRorderno() {
        return rorderno;
    }

    public void setRorderno(String rorderno) {
        this.rorderno = rorderno == null ? null : rorderno.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getDlventire() {
        return dlventire;
    }

    public void setDlventire(String dlventire) {
        this.dlventire = dlventire == null ? null : dlventire.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    public BigDecimal getNtaxamount() {
        return ntaxamount;
    }

    public void setNtaxamount(BigDecimal ntaxamount) {
        this.ntaxamount = ntaxamount;
    }

    public String getInvflag() {
        return invflag;
    }

    public void setInvflag(String invflag) {
        this.invflag = invflag == null ? null : invflag.trim();
    }

    public Date getOptdate() {
        return optdate;
    }

    public void setOptdate(Date optdate) {
        this.optdate = optdate;
    }

    public String getOptcode() {
        return optcode;
    }

    public void setOptcode(String optcode) {
        this.optcode = optcode == null ? null : optcode.trim();
    }

    public String getClassflag() {
        return classflag;
    }

    public void setClassflag(String classflag) {
        this.classflag = classflag == null ? null : classflag.trim();
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public Date getInvdate() {
        return invdate;
    }

    public void setInvdate(Date invdate) {
        this.invdate = invdate;
    }

    public String getOptstate() {
        return optstate;
    }

    public void setOptstate(String optstate) {
        this.optstate = optstate == null ? null : optstate.trim();
    }

    public String getStockno() {
        return stockno;
    }

    public void setStockno(String stockno) {
        this.stockno = stockno == null ? null : stockno.trim();
    }

    public String getInvflag1() {
        return invflag1;
    }

    public void setInvflag1(String invflag1) {
        this.invflag1 = invflag1 == null ? null : invflag1.trim();
    }

    public String getProdflag() {
        return prodflag;
    }

    public void setProdflag(String prodflag) {
        this.prodflag = prodflag == null ? null : prodflag.trim();
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno == null ? null : billno.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getReceiveEmp() {
        return receiveEmp;
    }

    public void setReceiveEmp(String receiveEmp) {
        this.receiveEmp = receiveEmp == null ? null : receiveEmp.trim();
    }

    public String getReceiveCust() {
        return receiveCust;
    }

    public void setReceiveCust(String receiveCust) {
        this.receiveCust = receiveCust == null ? null : receiveCust.trim();
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceiveRemark() {
        return receiveRemark;
    }

    public void setReceiveRemark(String receiveRemark) {
        this.receiveRemark = receiveRemark == null ? null : receiveRemark.trim();
    }

    public String getReceiveOptstate() {
        return receiveOptstate;
    }

    public void setReceiveOptstate(String receiveOptstate) {
        this.receiveOptstate = receiveOptstate == null ? null : receiveOptstate.trim();
    }

    public String getPurchaseno() {
        return purchaseno;
    }

    public void setPurchaseno(String purchaseno) {
        this.purchaseno = purchaseno == null ? null : purchaseno.trim();
    }

    public String getAgentpriceFlag() {
        return agentpriceFlag;
    }

    public void setAgentpriceFlag(String agentpriceFlag) {
        this.agentpriceFlag = agentpriceFlag == null ? null : agentpriceFlag.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public BigDecimal getDiscountamt() {
        return discountamt;
    }

    public void setDiscountamt(BigDecimal discountamt) {
        this.discountamt = discountamt;
    }

    public String getOrdtype() {
        return ordtype;
    }

    public void setOrdtype(String ordtype) {
        this.ordtype = ordtype == null ? null : ordtype.trim();
    }

    public String getInvoicecode() {
        return invoicecode;
    }

    public void setInvoicecode(String invoicecode) {
        this.invoicecode = invoicecode == null ? null : invoicecode.trim();
    }

    public String getInvtype() {
        return invtype;
    }

    public void setInvtype(String invtype) {
        this.invtype = invtype == null ? null : invtype.trim();
    }

    public String getInvoicenoJp() {
        return invoicenoJp;
    }

    public void setInvoicenoJp(String invoicenoJp) {
        this.invoicenoJp = invoicenoJp == null ? null : invoicenoJp.trim();
    }

    public BigDecimal getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    public BigDecimal getNtaxdiscountamt() {
        return ntaxdiscountamt;
    }

    public void setNtaxdiscountamt(BigDecimal ntaxdiscountamt) {
        this.ntaxdiscountamt = ntaxdiscountamt;
    }

    public BigDecimal getTaxdiscountamt() {
        return taxdiscountamt;
    }

    public void setTaxdiscountamt(BigDecimal taxdiscountamt) {
        this.taxdiscountamt = taxdiscountamt;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public Date getCanceldate() {
        return canceldate;
    }

    public void setCanceldate(Date canceldate) {
        this.canceldate = canceldate;
    }

    public String getExtracted() {
        return extracted;
    }

    public void setExtracted(String extracted) {
        this.extracted = extracted == null ? null : extracted.trim();
    }

    public Date getExtracttime() {
        return extracttime;
    }

    public void setExtracttime(Date extracttime) {
        this.extracttime = extracttime;
    }

    public String getInvoicegroupkey() {
        return invoicegroupkey;
    }

    public void setInvoicegroupkey(String invoicegroupkey) {
        this.invoicegroupkey = invoicegroupkey == null ? null : invoicegroupkey.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getExpdate() {
        return expdate;
    }

    public void setExpdate(Date expdate) {
        this.expdate = expdate;
    }

    public Date getAckdate() {
        return ackdate;
    }

    public void setAckdate(Date ackdate) {
        this.ackdate = ackdate;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
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
        Salesinvoice other = (Salesinvoice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTradecompanyid() == null ? other.getTradecompanyid() == null : this.getTradecompanyid().equals(other.getTradecompanyid()))
            && (this.getRorderno() == null ? other.getRorderno() == null : this.getRorderno().equals(other.getRorderno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getDlventire() == null ? other.getDlventire() == null : this.getDlventire().equals(other.getDlventire()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getTaxamount() == null ? other.getTaxamount() == null : this.getTaxamount().equals(other.getTaxamount()))
            && (this.getNtaxamount() == null ? other.getNtaxamount() == null : this.getNtaxamount().equals(other.getNtaxamount()))
            && (this.getInvflag() == null ? other.getInvflag() == null : this.getInvflag().equals(other.getInvflag()))
            && (this.getOptdate() == null ? other.getOptdate() == null : this.getOptdate().equals(other.getOptdate()))
            && (this.getOptcode() == null ? other.getOptcode() == null : this.getOptcode().equals(other.getOptcode()))
            && (this.getClassflag() == null ? other.getClassflag() == null : this.getClassflag().equals(other.getClassflag()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getInvdate() == null ? other.getInvdate() == null : this.getInvdate().equals(other.getInvdate()))
            && (this.getOptstate() == null ? other.getOptstate() == null : this.getOptstate().equals(other.getOptstate()))
            && (this.getStockno() == null ? other.getStockno() == null : this.getStockno().equals(other.getStockno()))
            && (this.getInvflag1() == null ? other.getInvflag1() == null : this.getInvflag1().equals(other.getInvflag1()))
            && (this.getProdflag() == null ? other.getProdflag() == null : this.getProdflag().equals(other.getProdflag()))
            && (this.getBillno() == null ? other.getBillno() == null : this.getBillno().equals(other.getBillno()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getOpttime() == null ? other.getOpttime() == null : this.getOpttime().equals(other.getOpttime()))
            && (this.getReceiveEmp() == null ? other.getReceiveEmp() == null : this.getReceiveEmp().equals(other.getReceiveEmp()))
            && (this.getReceiveCust() == null ? other.getReceiveCust() == null : this.getReceiveCust().equals(other.getReceiveCust()))
            && (this.getReceiveDate() == null ? other.getReceiveDate() == null : this.getReceiveDate().equals(other.getReceiveDate()))
            && (this.getReceiveRemark() == null ? other.getReceiveRemark() == null : this.getReceiveRemark().equals(other.getReceiveRemark()))
            && (this.getReceiveOptstate() == null ? other.getReceiveOptstate() == null : this.getReceiveOptstate().equals(other.getReceiveOptstate()))
            && (this.getPurchaseno() == null ? other.getPurchaseno() == null : this.getPurchaseno().equals(other.getPurchaseno()))
            && (this.getAgentpriceFlag() == null ? other.getAgentpriceFlag() == null : this.getAgentpriceFlag().equals(other.getAgentpriceFlag()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getDiscountamt() == null ? other.getDiscountamt() == null : this.getDiscountamt().equals(other.getDiscountamt()))
            && (this.getOrdtype() == null ? other.getOrdtype() == null : this.getOrdtype().equals(other.getOrdtype()))
            && (this.getInvoicecode() == null ? other.getInvoicecode() == null : this.getInvoicecode().equals(other.getInvoicecode()))
            && (this.getInvtype() == null ? other.getInvtype() == null : this.getInvtype().equals(other.getInvtype()))
            && (this.getInvoicenoJp() == null ? other.getInvoicenoJp() == null : this.getInvoicenoJp().equals(other.getInvoicenoJp()))
            && (this.getTaxrate() == null ? other.getTaxrate() == null : this.getTaxrate().equals(other.getTaxrate()))
            && (this.getNtaxdiscountamt() == null ? other.getNtaxdiscountamt() == null : this.getNtaxdiscountamt().equals(other.getNtaxdiscountamt()))
            && (this.getTaxdiscountamt() == null ? other.getTaxdiscountamt() == null : this.getTaxdiscountamt().equals(other.getTaxdiscountamt()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()))
            && (this.getCanceldate() == null ? other.getCanceldate() == null : this.getCanceldate().equals(other.getCanceldate()))
            && (this.getExtracted() == null ? other.getExtracted() == null : this.getExtracted().equals(other.getExtracted()))
            && (this.getExtracttime() == null ? other.getExtracttime() == null : this.getExtracttime().equals(other.getExtracttime()))
            && (this.getInvoicegroupkey() == null ? other.getInvoicegroupkey() == null : this.getInvoicegroupkey().equals(other.getInvoicegroupkey()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getExpdate() == null ? other.getExpdate() == null : this.getExpdate().equals(other.getExpdate()))
            && (this.getAckdate() == null ? other.getAckdate() == null : this.getAckdate().equals(other.getAckdate()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTradecompanyid() == null) ? 0 : getTradecompanyid().hashCode());
        result = prime * result + ((getRorderno() == null) ? 0 : getRorderno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getDlventire() == null) ? 0 : getDlventire().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getTaxamount() == null) ? 0 : getTaxamount().hashCode());
        result = prime * result + ((getNtaxamount() == null) ? 0 : getNtaxamount().hashCode());
        result = prime * result + ((getInvflag() == null) ? 0 : getInvflag().hashCode());
        result = prime * result + ((getOptdate() == null) ? 0 : getOptdate().hashCode());
        result = prime * result + ((getOptcode() == null) ? 0 : getOptcode().hashCode());
        result = prime * result + ((getClassflag() == null) ? 0 : getClassflag().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getInvdate() == null) ? 0 : getInvdate().hashCode());
        result = prime * result + ((getOptstate() == null) ? 0 : getOptstate().hashCode());
        result = prime * result + ((getStockno() == null) ? 0 : getStockno().hashCode());
        result = prime * result + ((getInvflag1() == null) ? 0 : getInvflag1().hashCode());
        result = prime * result + ((getProdflag() == null) ? 0 : getProdflag().hashCode());
        result = prime * result + ((getBillno() == null) ? 0 : getBillno().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getOpttime() == null) ? 0 : getOpttime().hashCode());
        result = prime * result + ((getReceiveEmp() == null) ? 0 : getReceiveEmp().hashCode());
        result = prime * result + ((getReceiveCust() == null) ? 0 : getReceiveCust().hashCode());
        result = prime * result + ((getReceiveDate() == null) ? 0 : getReceiveDate().hashCode());
        result = prime * result + ((getReceiveRemark() == null) ? 0 : getReceiveRemark().hashCode());
        result = prime * result + ((getReceiveOptstate() == null) ? 0 : getReceiveOptstate().hashCode());
        result = prime * result + ((getPurchaseno() == null) ? 0 : getPurchaseno().hashCode());
        result = prime * result + ((getAgentpriceFlag() == null) ? 0 : getAgentpriceFlag().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getDiscountamt() == null) ? 0 : getDiscountamt().hashCode());
        result = prime * result + ((getOrdtype() == null) ? 0 : getOrdtype().hashCode());
        result = prime * result + ((getInvoicecode() == null) ? 0 : getInvoicecode().hashCode());
        result = prime * result + ((getInvtype() == null) ? 0 : getInvtype().hashCode());
        result = prime * result + ((getInvoicenoJp() == null) ? 0 : getInvoicenoJp().hashCode());
        result = prime * result + ((getTaxrate() == null) ? 0 : getTaxrate().hashCode());
        result = prime * result + ((getNtaxdiscountamt() == null) ? 0 : getNtaxdiscountamt().hashCode());
        result = prime * result + ((getTaxdiscountamt() == null) ? 0 : getTaxdiscountamt().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        result = prime * result + ((getCanceldate() == null) ? 0 : getCanceldate().hashCode());
        result = prime * result + ((getExtracted() == null) ? 0 : getExtracted().hashCode());
        result = prime * result + ((getExtracttime() == null) ? 0 : getExtracttime().hashCode());
        result = prime * result + ((getInvoicegroupkey() == null) ? 0 : getInvoicegroupkey().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getExpdate() == null) ? 0 : getExpdate().hashCode());
        result = prime * result + ((getAckdate() == null) ? 0 : getAckdate().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        return result;
    }
}