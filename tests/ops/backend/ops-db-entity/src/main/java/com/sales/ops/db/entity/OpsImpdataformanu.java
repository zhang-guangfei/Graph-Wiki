package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsImpdataformanu implements Serializable {
    private Integer id;

    private String invoiceno;

    private String caseno;

    private String orderno;

    private String customerno;

    private String modelno;

    private Integer quantity;

    private String transtype;

    private Date impdate;

    private String optcode;

    private String expdesc;

    private String barcode;

    private String username;

    private Date opttime;

    private String optrecord;

    private String palletno;

    private BigDecimal price;

    private String extOrderno;

    private String extOrderItem;

    private BigDecimal taxprice;

    private BigDecimal amount;

    private BigDecimal taxamount;

    private BigDecimal taxrate;

    private Long fromid;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno == null ? null : caseno.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno == null ? null : customerno.trim();
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

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype == null ? null : transtype.trim();
    }

    public Date getImpdate() {
        return impdate;
    }

    public void setImpdate(Date impdate) {
        this.impdate = impdate;
    }

    public String getOptcode() {
        return optcode;
    }

    public void setOptcode(String optcode) {
        this.optcode = optcode == null ? null : optcode.trim();
    }

    public String getExpdesc() {
        return expdesc;
    }

    public void setExpdesc(String expdesc) {
        this.expdesc = expdesc == null ? null : expdesc.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
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

    public String getOptrecord() {
        return optrecord;
    }

    public void setOptrecord(String optrecord) {
        this.optrecord = optrecord == null ? null : optrecord.trim();
    }

    public String getPalletno() {
        return palletno;
    }

    public void setPalletno(String palletno) {
        this.palletno = palletno == null ? null : palletno.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getExtOrderno() {
        return extOrderno;
    }

    public void setExtOrderno(String extOrderno) {
        this.extOrderno = extOrderno == null ? null : extOrderno.trim();
    }

    public String getExtOrderItem() {
        return extOrderItem;
    }

    public void setExtOrderItem(String extOrderItem) {
        this.extOrderItem = extOrderItem == null ? null : extOrderItem.trim();
    }

    public BigDecimal getTaxprice() {
        return taxprice;
    }

    public void setTaxprice(BigDecimal taxprice) {
        this.taxprice = taxprice;
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

    public BigDecimal getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    public Long getFromid() {
        return fromid;
    }

    public void setFromid(Long fromid) {
        this.fromid = fromid;
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
        OpsImpdataformanu other = (OpsImpdataformanu) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getCaseno() == null ? other.getCaseno() == null : this.getCaseno().equals(other.getCaseno()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getTranstype() == null ? other.getTranstype() == null : this.getTranstype().equals(other.getTranstype()))
            && (this.getImpdate() == null ? other.getImpdate() == null : this.getImpdate().equals(other.getImpdate()))
            && (this.getOptcode() == null ? other.getOptcode() == null : this.getOptcode().equals(other.getOptcode()))
            && (this.getExpdesc() == null ? other.getExpdesc() == null : this.getExpdesc().equals(other.getExpdesc()))
            && (this.getBarcode() == null ? other.getBarcode() == null : this.getBarcode().equals(other.getBarcode()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getOpttime() == null ? other.getOpttime() == null : this.getOpttime().equals(other.getOpttime()))
            && (this.getOptrecord() == null ? other.getOptrecord() == null : this.getOptrecord().equals(other.getOptrecord()))
            && (this.getPalletno() == null ? other.getPalletno() == null : this.getPalletno().equals(other.getPalletno()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getExtOrderno() == null ? other.getExtOrderno() == null : this.getExtOrderno().equals(other.getExtOrderno()))
            && (this.getExtOrderItem() == null ? other.getExtOrderItem() == null : this.getExtOrderItem().equals(other.getExtOrderItem()))
            && (this.getTaxprice() == null ? other.getTaxprice() == null : this.getTaxprice().equals(other.getTaxprice()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getTaxamount() == null ? other.getTaxamount() == null : this.getTaxamount().equals(other.getTaxamount()))
            && (this.getTaxrate() == null ? other.getTaxrate() == null : this.getTaxrate().equals(other.getTaxrate()))
            && (this.getFromid() == null ? other.getFromid() == null : this.getFromid().equals(other.getFromid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getCaseno() == null) ? 0 : getCaseno().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getTranstype() == null) ? 0 : getTranstype().hashCode());
        result = prime * result + ((getImpdate() == null) ? 0 : getImpdate().hashCode());
        result = prime * result + ((getOptcode() == null) ? 0 : getOptcode().hashCode());
        result = prime * result + ((getExpdesc() == null) ? 0 : getExpdesc().hashCode());
        result = prime * result + ((getBarcode() == null) ? 0 : getBarcode().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getOpttime() == null) ? 0 : getOpttime().hashCode());
        result = prime * result + ((getOptrecord() == null) ? 0 : getOptrecord().hashCode());
        result = prime * result + ((getPalletno() == null) ? 0 : getPalletno().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getExtOrderno() == null) ? 0 : getExtOrderno().hashCode());
        result = prime * result + ((getExtOrderItem() == null) ? 0 : getExtOrderItem().hashCode());
        result = prime * result + ((getTaxprice() == null) ? 0 : getTaxprice().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getTaxamount() == null) ? 0 : getTaxamount().hashCode());
        result = prime * result + ((getTaxrate() == null) ? 0 : getTaxrate().hashCode());
        result = prime * result + ((getFromid() == null) ? 0 : getFromid().hashCode());
        return result;
    }
}