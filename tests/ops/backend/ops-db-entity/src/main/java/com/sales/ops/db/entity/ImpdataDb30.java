package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ImpdataDb30 implements Serializable {
    private Integer id;

    private String invoiceno;

    private String caseno;

    private String orderno;

    private String rordernoAll;

    private String customerno;

    private String modelno;

    private Integer quantity;

    private String transtype;

    private Date impdate;

    private String prodcountry;

    private String optcode;

    private String stateflag;

    private String expdesc;

    private String barcode;

    private String ordflag;

    private Integer qtyimp;

    private String optflag;

    private String labelprtflag;

    private String asseflag;

    private String invflag;

    private String preno;

    private String username;

    private Date opttime;

    private String smccode;

    private String optrecord;

    private String ordtype;

    private BigDecimal unitweight;

    private String serialno;

    private String pono;

    private Integer lineitem;

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

    public String getRordernoAll() {
        return rordernoAll;
    }

    public void setRordernoAll(String rordernoAll) {
        this.rordernoAll = rordernoAll == null ? null : rordernoAll.trim();
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

    public String getProdcountry() {
        return prodcountry;
    }

    public void setProdcountry(String prodcountry) {
        this.prodcountry = prodcountry == null ? null : prodcountry.trim();
    }

    public String getOptcode() {
        return optcode;
    }

    public void setOptcode(String optcode) {
        this.optcode = optcode == null ? null : optcode.trim();
    }

    public String getStateflag() {
        return stateflag;
    }

    public void setStateflag(String stateflag) {
        this.stateflag = stateflag == null ? null : stateflag.trim();
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

    public String getOrdflag() {
        return ordflag;
    }

    public void setOrdflag(String ordflag) {
        this.ordflag = ordflag == null ? null : ordflag.trim();
    }

    public Integer getQtyimp() {
        return qtyimp;
    }

    public void setQtyimp(Integer qtyimp) {
        this.qtyimp = qtyimp;
    }

    public String getOptflag() {
        return optflag;
    }

    public void setOptflag(String optflag) {
        this.optflag = optflag == null ? null : optflag.trim();
    }

    public String getLabelprtflag() {
        return labelprtflag;
    }

    public void setLabelprtflag(String labelprtflag) {
        this.labelprtflag = labelprtflag == null ? null : labelprtflag.trim();
    }

    public String getAsseflag() {
        return asseflag;
    }

    public void setAsseflag(String asseflag) {
        this.asseflag = asseflag == null ? null : asseflag.trim();
    }

    public String getInvflag() {
        return invflag;
    }

    public void setInvflag(String invflag) {
        this.invflag = invflag == null ? null : invflag.trim();
    }

    public String getPreno() {
        return preno;
    }

    public void setPreno(String preno) {
        this.preno = preno == null ? null : preno.trim();
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

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode == null ? null : smccode.trim();
    }

    public String getOptrecord() {
        return optrecord;
    }

    public void setOptrecord(String optrecord) {
        this.optrecord = optrecord == null ? null : optrecord.trim();
    }

    public String getOrdtype() {
        return ordtype;
    }

    public void setOrdtype(String ordtype) {
        this.ordtype = ordtype == null ? null : ordtype.trim();
    }

    public BigDecimal getUnitweight() {
        return unitweight;
    }

    public void setUnitweight(BigDecimal unitweight) {
        this.unitweight = unitweight;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno == null ? null : serialno.trim();
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
        ImpdataDb30 other = (ImpdataDb30) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getCaseno() == null ? other.getCaseno() == null : this.getCaseno().equals(other.getCaseno()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getRordernoAll() == null ? other.getRordernoAll() == null : this.getRordernoAll().equals(other.getRordernoAll()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getTranstype() == null ? other.getTranstype() == null : this.getTranstype().equals(other.getTranstype()))
            && (this.getImpdate() == null ? other.getImpdate() == null : this.getImpdate().equals(other.getImpdate()))
            && (this.getProdcountry() == null ? other.getProdcountry() == null : this.getProdcountry().equals(other.getProdcountry()))
            && (this.getOptcode() == null ? other.getOptcode() == null : this.getOptcode().equals(other.getOptcode()))
            && (this.getStateflag() == null ? other.getStateflag() == null : this.getStateflag().equals(other.getStateflag()))
            && (this.getExpdesc() == null ? other.getExpdesc() == null : this.getExpdesc().equals(other.getExpdesc()))
            && (this.getBarcode() == null ? other.getBarcode() == null : this.getBarcode().equals(other.getBarcode()))
            && (this.getOrdflag() == null ? other.getOrdflag() == null : this.getOrdflag().equals(other.getOrdflag()))
            && (this.getQtyimp() == null ? other.getQtyimp() == null : this.getQtyimp().equals(other.getQtyimp()))
            && (this.getOptflag() == null ? other.getOptflag() == null : this.getOptflag().equals(other.getOptflag()))
            && (this.getLabelprtflag() == null ? other.getLabelprtflag() == null : this.getLabelprtflag().equals(other.getLabelprtflag()))
            && (this.getAsseflag() == null ? other.getAsseflag() == null : this.getAsseflag().equals(other.getAsseflag()))
            && (this.getInvflag() == null ? other.getInvflag() == null : this.getInvflag().equals(other.getInvflag()))
            && (this.getPreno() == null ? other.getPreno() == null : this.getPreno().equals(other.getPreno()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getOpttime() == null ? other.getOpttime() == null : this.getOpttime().equals(other.getOpttime()))
            && (this.getSmccode() == null ? other.getSmccode() == null : this.getSmccode().equals(other.getSmccode()))
            && (this.getOptrecord() == null ? other.getOptrecord() == null : this.getOptrecord().equals(other.getOptrecord()))
            && (this.getOrdtype() == null ? other.getOrdtype() == null : this.getOrdtype().equals(other.getOrdtype()))
            && (this.getUnitweight() == null ? other.getUnitweight() == null : this.getUnitweight().equals(other.getUnitweight()))
            && (this.getSerialno() == null ? other.getSerialno() == null : this.getSerialno().equals(other.getSerialno()))
            && (this.getPono() == null ? other.getPono() == null : this.getPono().equals(other.getPono()))
            && (this.getLineitem() == null ? other.getLineitem() == null : this.getLineitem().equals(other.getLineitem()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getCaseno() == null) ? 0 : getCaseno().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getRordernoAll() == null) ? 0 : getRordernoAll().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getTranstype() == null) ? 0 : getTranstype().hashCode());
        result = prime * result + ((getImpdate() == null) ? 0 : getImpdate().hashCode());
        result = prime * result + ((getProdcountry() == null) ? 0 : getProdcountry().hashCode());
        result = prime * result + ((getOptcode() == null) ? 0 : getOptcode().hashCode());
        result = prime * result + ((getStateflag() == null) ? 0 : getStateflag().hashCode());
        result = prime * result + ((getExpdesc() == null) ? 0 : getExpdesc().hashCode());
        result = prime * result + ((getBarcode() == null) ? 0 : getBarcode().hashCode());
        result = prime * result + ((getOrdflag() == null) ? 0 : getOrdflag().hashCode());
        result = prime * result + ((getQtyimp() == null) ? 0 : getQtyimp().hashCode());
        result = prime * result + ((getOptflag() == null) ? 0 : getOptflag().hashCode());
        result = prime * result + ((getLabelprtflag() == null) ? 0 : getLabelprtflag().hashCode());
        result = prime * result + ((getAsseflag() == null) ? 0 : getAsseflag().hashCode());
        result = prime * result + ((getInvflag() == null) ? 0 : getInvflag().hashCode());
        result = prime * result + ((getPreno() == null) ? 0 : getPreno().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getOpttime() == null) ? 0 : getOpttime().hashCode());
        result = prime * result + ((getSmccode() == null) ? 0 : getSmccode().hashCode());
        result = prime * result + ((getOptrecord() == null) ? 0 : getOptrecord().hashCode());
        result = prime * result + ((getOrdtype() == null) ? 0 : getOrdtype().hashCode());
        result = prime * result + ((getUnitweight() == null) ? 0 : getUnitweight().hashCode());
        result = prime * result + ((getSerialno() == null) ? 0 : getSerialno().hashCode());
        result = prime * result + ((getPono() == null) ? 0 : getPono().hashCode());
        result = prime * result + ((getLineitem() == null) ? 0 : getLineitem().hashCode());
        return result;
    }
}