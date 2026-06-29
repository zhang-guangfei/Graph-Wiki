package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VOrdering implements Serializable {
    private Long id;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private String modelno;

    private Integer quantity;

    private String transtype;

    private Date purchasedate;

    private Date hopedeliverydate;

    private String supplierid;

    private String statecode;

    private String ordtype;

    private String receivewarehouseid;

    private Date hopeexportdate;

    private String shikomianswerno;

    private String operatorid;

    private String deptno;

    private Integer qtyreceive;

    private Date finishdate;

    private String smccode;

    private String invoiceno;

    private String hscode;

    private String greencode;

    private Integer producttype;

    private String customerno;

    private String userno;

    private String salesinfono;

    private String purchasetype;

    private BigDecimal importfobpriceoriginal;

    private String importcurrencyid;

    private Date updatetime;

    private String corderno;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
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

    public Date getPurchasedate() {
        return purchasedate;
    }

    public void setPurchasedate(Date purchasedate) {
        this.purchasedate = purchasedate;
    }

    public Date getHopedeliverydate() {
        return hopedeliverydate;
    }

    public void setHopedeliverydate(Date hopedeliverydate) {
        this.hopedeliverydate = hopedeliverydate;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode == null ? null : statecode.trim();
    }

    public String getOrdtype() {
        return ordtype;
    }

    public void setOrdtype(String ordtype) {
        this.ordtype = ordtype == null ? null : ordtype.trim();
    }

    public String getReceivewarehouseid() {
        return receivewarehouseid;
    }

    public void setReceivewarehouseid(String receivewarehouseid) {
        this.receivewarehouseid = receivewarehouseid == null ? null : receivewarehouseid.trim();
    }

    public Date getHopeexportdate() {
        return hopeexportdate;
    }

    public void setHopeexportdate(Date hopeexportdate) {
        this.hopeexportdate = hopeexportdate;
    }

    public String getShikomianswerno() {
        return shikomianswerno;
    }

    public void setShikomianswerno(String shikomianswerno) {
        this.shikomianswerno = shikomianswerno == null ? null : shikomianswerno.trim();
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid == null ? null : operatorid.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public Integer getQtyreceive() {
        return qtyreceive;
    }

    public void setQtyreceive(Integer qtyreceive) {
        this.qtyreceive = qtyreceive;
    }

    public Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(Date finishdate) {
        this.finishdate = finishdate;
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode == null ? null : smccode.trim();
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode == null ? null : hscode.trim();
    }

    public String getGreencode() {
        return greencode;
    }

    public void setGreencode(String greencode) {
        this.greencode = greencode == null ? null : greencode.trim();
    }

    public Integer getProducttype() {
        return producttype;
    }

    public void setProducttype(Integer producttype) {
        this.producttype = producttype;
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

    public String getSalesinfono() {
        return salesinfono;
    }

    public void setSalesinfono(String salesinfono) {
        this.salesinfono = salesinfono == null ? null : salesinfono.trim();
    }

    public String getPurchasetype() {
        return purchasetype;
    }

    public void setPurchasetype(String purchasetype) {
        this.purchasetype = purchasetype == null ? null : purchasetype.trim();
    }

    public BigDecimal getImportfobpriceoriginal() {
        return importfobpriceoriginal;
    }

    public void setImportfobpriceoriginal(BigDecimal importfobpriceoriginal) {
        this.importfobpriceoriginal = importfobpriceoriginal;
    }

    public String getImportcurrencyid() {
        return importcurrencyid;
    }

    public void setImportcurrencyid(String importcurrencyid) {
        this.importcurrencyid = importcurrencyid == null ? null : importcurrencyid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCorderno() {
        return corderno;
    }

    public void setCorderno(String corderno) {
        this.corderno = corderno == null ? null : corderno.trim();
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
        VOrdering other = (VOrdering) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getSplititemno() == null ? other.getSplititemno() == null : this.getSplititemno().equals(other.getSplititemno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getTranstype() == null ? other.getTranstype() == null : this.getTranstype().equals(other.getTranstype()))
            && (this.getPurchasedate() == null ? other.getPurchasedate() == null : this.getPurchasedate().equals(other.getPurchasedate()))
            && (this.getHopedeliverydate() == null ? other.getHopedeliverydate() == null : this.getHopedeliverydate().equals(other.getHopedeliverydate()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getStatecode() == null ? other.getStatecode() == null : this.getStatecode().equals(other.getStatecode()))
            && (this.getOrdtype() == null ? other.getOrdtype() == null : this.getOrdtype().equals(other.getOrdtype()))
            && (this.getReceivewarehouseid() == null ? other.getReceivewarehouseid() == null : this.getReceivewarehouseid().equals(other.getReceivewarehouseid()))
            && (this.getHopeexportdate() == null ? other.getHopeexportdate() == null : this.getHopeexportdate().equals(other.getHopeexportdate()))
            && (this.getShikomianswerno() == null ? other.getShikomianswerno() == null : this.getShikomianswerno().equals(other.getShikomianswerno()))
            && (this.getOperatorid() == null ? other.getOperatorid() == null : this.getOperatorid().equals(other.getOperatorid()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getQtyreceive() == null ? other.getQtyreceive() == null : this.getQtyreceive().equals(other.getQtyreceive()))
            && (this.getFinishdate() == null ? other.getFinishdate() == null : this.getFinishdate().equals(other.getFinishdate()))
            && (this.getSmccode() == null ? other.getSmccode() == null : this.getSmccode().equals(other.getSmccode()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getHscode() == null ? other.getHscode() == null : this.getHscode().equals(other.getHscode()))
            && (this.getGreencode() == null ? other.getGreencode() == null : this.getGreencode().equals(other.getGreencode()))
            && (this.getProducttype() == null ? other.getProducttype() == null : this.getProducttype().equals(other.getProducttype()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getSalesinfono() == null ? other.getSalesinfono() == null : this.getSalesinfono().equals(other.getSalesinfono()))
            && (this.getPurchasetype() == null ? other.getPurchasetype() == null : this.getPurchasetype().equals(other.getPurchasetype()))
            && (this.getImportfobpriceoriginal() == null ? other.getImportfobpriceoriginal() == null : this.getImportfobpriceoriginal().equals(other.getImportfobpriceoriginal()))
            && (this.getImportcurrencyid() == null ? other.getImportcurrencyid() == null : this.getImportcurrencyid().equals(other.getImportcurrencyid()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getCorderno() == null ? other.getCorderno() == null : this.getCorderno().equals(other.getCorderno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getSplititemno() == null) ? 0 : getSplititemno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getTranstype() == null) ? 0 : getTranstype().hashCode());
        result = prime * result + ((getPurchasedate() == null) ? 0 : getPurchasedate().hashCode());
        result = prime * result + ((getHopedeliverydate() == null) ? 0 : getHopedeliverydate().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getStatecode() == null) ? 0 : getStatecode().hashCode());
        result = prime * result + ((getOrdtype() == null) ? 0 : getOrdtype().hashCode());
        result = prime * result + ((getReceivewarehouseid() == null) ? 0 : getReceivewarehouseid().hashCode());
        result = prime * result + ((getHopeexportdate() == null) ? 0 : getHopeexportdate().hashCode());
        result = prime * result + ((getShikomianswerno() == null) ? 0 : getShikomianswerno().hashCode());
        result = prime * result + ((getOperatorid() == null) ? 0 : getOperatorid().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getQtyreceive() == null) ? 0 : getQtyreceive().hashCode());
        result = prime * result + ((getFinishdate() == null) ? 0 : getFinishdate().hashCode());
        result = prime * result + ((getSmccode() == null) ? 0 : getSmccode().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getHscode() == null) ? 0 : getHscode().hashCode());
        result = prime * result + ((getGreencode() == null) ? 0 : getGreencode().hashCode());
        result = prime * result + ((getProducttype() == null) ? 0 : getProducttype().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getSalesinfono() == null) ? 0 : getSalesinfono().hashCode());
        result = prime * result + ((getPurchasetype() == null) ? 0 : getPurchasetype().hashCode());
        result = prime * result + ((getImportfobpriceoriginal() == null) ? 0 : getImportfobpriceoriginal().hashCode());
        result = prime * result + ((getImportcurrencyid() == null) ? 0 : getImportcurrencyid().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getCorderno() == null) ? 0 : getCorderno().hashCode());
        return result;
    }
}