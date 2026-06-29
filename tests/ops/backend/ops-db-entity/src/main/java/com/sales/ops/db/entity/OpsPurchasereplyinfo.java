package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPurchasereplyinfo implements Serializable {
    private Long id;

    private String pono;

    private Integer lineitem;

    private String invoiceno;

    private Long invoiceid;

    private String cnno;

    private String transtype;

    private String supplierid;

    private String replyorderno;

    private Date replyorderdate;

    private Date replyexportdate;

    private Integer qtytrans;

    private Integer qtyreceive;

    private String reasonremark;

    private String dlvanswerno;

    private String producefactory;

    private String produceholon;

    private String errdescription;

    private String barcode;

    private String caseno;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono == null ? null : pono.trim();
    }

    public Integer getLineitem() {
        return lineitem;
    }

    public void setLineitem(Integer lineitem) {
        this.lineitem = lineitem;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public Long getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Long invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getCnno() {
        return cnno;
    }

    public void setCnno(String cnno) {
        this.cnno = cnno == null ? null : cnno.trim();
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype == null ? null : transtype.trim();
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getReplyorderno() {
        return replyorderno;
    }

    public void setReplyorderno(String replyorderno) {
        this.replyorderno = replyorderno == null ? null : replyorderno.trim();
    }

    public Date getReplyorderdate() {
        return replyorderdate;
    }

    public void setReplyorderdate(Date replyorderdate) {
        this.replyorderdate = replyorderdate;
    }

    public Date getReplyexportdate() {
        return replyexportdate;
    }

    public void setReplyexportdate(Date replyexportdate) {
        this.replyexportdate = replyexportdate;
    }

    public Integer getQtytrans() {
        return qtytrans;
    }

    public void setQtytrans(Integer qtytrans) {
        this.qtytrans = qtytrans;
    }

    public Integer getQtyreceive() {
        return qtyreceive;
    }

    public void setQtyreceive(Integer qtyreceive) {
        this.qtyreceive = qtyreceive;
    }

    public String getReasonremark() {
        return reasonremark;
    }

    public void setReasonremark(String reasonremark) {
        this.reasonremark = reasonremark == null ? null : reasonremark.trim();
    }

    public String getDlvanswerno() {
        return dlvanswerno;
    }

    public void setDlvanswerno(String dlvanswerno) {
        this.dlvanswerno = dlvanswerno == null ? null : dlvanswerno.trim();
    }

    public String getProducefactory() {
        return producefactory;
    }

    public void setProducefactory(String producefactory) {
        this.producefactory = producefactory == null ? null : producefactory.trim();
    }

    public String getProduceholon() {
        return produceholon;
    }

    public void setProduceholon(String produceholon) {
        this.produceholon = produceholon == null ? null : produceholon.trim();
    }

    public String getErrdescription() {
        return errdescription;
    }

    public void setErrdescription(String errdescription) {
        this.errdescription = errdescription == null ? null : errdescription.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno == null ? null : caseno.trim();
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
        OpsPurchasereplyinfo other = (OpsPurchasereplyinfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPono() == null ? other.getPono() == null : this.getPono().equals(other.getPono()))
            && (this.getLineitem() == null ? other.getLineitem() == null : this.getLineitem().equals(other.getLineitem()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getInvoiceid() == null ? other.getInvoiceid() == null : this.getInvoiceid().equals(other.getInvoiceid()))
            && (this.getCnno() == null ? other.getCnno() == null : this.getCnno().equals(other.getCnno()))
            && (this.getTranstype() == null ? other.getTranstype() == null : this.getTranstype().equals(other.getTranstype()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getReplyorderno() == null ? other.getReplyorderno() == null : this.getReplyorderno().equals(other.getReplyorderno()))
            && (this.getReplyorderdate() == null ? other.getReplyorderdate() == null : this.getReplyorderdate().equals(other.getReplyorderdate()))
            && (this.getReplyexportdate() == null ? other.getReplyexportdate() == null : this.getReplyexportdate().equals(other.getReplyexportdate()))
            && (this.getQtytrans() == null ? other.getQtytrans() == null : this.getQtytrans().equals(other.getQtytrans()))
            && (this.getQtyreceive() == null ? other.getQtyreceive() == null : this.getQtyreceive().equals(other.getQtyreceive()))
            && (this.getReasonremark() == null ? other.getReasonremark() == null : this.getReasonremark().equals(other.getReasonremark()))
            && (this.getDlvanswerno() == null ? other.getDlvanswerno() == null : this.getDlvanswerno().equals(other.getDlvanswerno()))
            && (this.getProducefactory() == null ? other.getProducefactory() == null : this.getProducefactory().equals(other.getProducefactory()))
            && (this.getProduceholon() == null ? other.getProduceholon() == null : this.getProduceholon().equals(other.getProduceholon()))
            && (this.getErrdescription() == null ? other.getErrdescription() == null : this.getErrdescription().equals(other.getErrdescription()))
            && (this.getBarcode() == null ? other.getBarcode() == null : this.getBarcode().equals(other.getBarcode()))
            && (this.getCaseno() == null ? other.getCaseno() == null : this.getCaseno().equals(other.getCaseno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPono() == null) ? 0 : getPono().hashCode());
        result = prime * result + ((getLineitem() == null) ? 0 : getLineitem().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getInvoiceid() == null) ? 0 : getInvoiceid().hashCode());
        result = prime * result + ((getCnno() == null) ? 0 : getCnno().hashCode());
        result = prime * result + ((getTranstype() == null) ? 0 : getTranstype().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getReplyorderno() == null) ? 0 : getReplyorderno().hashCode());
        result = prime * result + ((getReplyorderdate() == null) ? 0 : getReplyorderdate().hashCode());
        result = prime * result + ((getReplyexportdate() == null) ? 0 : getReplyexportdate().hashCode());
        result = prime * result + ((getQtytrans() == null) ? 0 : getQtytrans().hashCode());
        result = prime * result + ((getQtyreceive() == null) ? 0 : getQtyreceive().hashCode());
        result = prime * result + ((getReasonremark() == null) ? 0 : getReasonremark().hashCode());
        result = prime * result + ((getDlvanswerno() == null) ? 0 : getDlvanswerno().hashCode());
        result = prime * result + ((getProducefactory() == null) ? 0 : getProducefactory().hashCode());
        result = prime * result + ((getProduceholon() == null) ? 0 : getProduceholon().hashCode());
        result = prime * result + ((getErrdescription() == null) ? 0 : getErrdescription().hashCode());
        result = prime * result + ((getBarcode() == null) ? 0 : getBarcode().hashCode());
        result = prime * result + ((getCaseno() == null) ? 0 : getCaseno().hashCode());
        return result;
    }
}