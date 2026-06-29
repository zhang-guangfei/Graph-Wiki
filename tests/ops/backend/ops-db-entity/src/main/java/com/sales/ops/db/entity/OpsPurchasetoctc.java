package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsPurchasetoctc implements Serializable {
    private Long id;

    private String pono;

    private Integer lineitem;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private String modelno;

    private Integer quantity;

    private BigDecimal stdprice;

    private Date purchasedate;

    private Date hopedeliverydate;

    private String supplierid;

    private String receivewarehouseid;

    private String remark;

    private Date hopeexportdate;

    private String inqno;

    private String shikomianswerno;

    private String deptno;

    private String deliveryflag;

    private String smccode;

    private String hscode;

    private String customerno;

    private String userno;

    private String locationno;

    private String vipcode;

    private String salesinfono;

    private String purchasetype;

    private String supplierpartno;

    private BigDecimal importfobpriceoriginal;

    private String importcurrencyid;

    private String operatorid;

    private String cnno;

    private Long invoiceid;

    private String invoiceno;

    private Date sendtime;

    private Date updatetime;

    private String status;

    private String dealtype;

    private String errorMsg;

    private Date finishdate;

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

    public BigDecimal getStdprice() {
        return stdprice;
    }

    public void setStdprice(BigDecimal stdprice) {
        this.stdprice = stdprice;
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

    public String getReceivewarehouseid() {
        return receivewarehouseid;
    }

    public void setReceivewarehouseid(String receivewarehouseid) {
        this.receivewarehouseid = receivewarehouseid == null ? null : receivewarehouseid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getHopeexportdate() {
        return hopeexportdate;
    }

    public void setHopeexportdate(Date hopeexportdate) {
        this.hopeexportdate = hopeexportdate;
    }

    public String getInqno() {
        return inqno;
    }

    public void setInqno(String inqno) {
        this.inqno = inqno == null ? null : inqno.trim();
    }

    public String getShikomianswerno() {
        return shikomianswerno;
    }

    public void setShikomianswerno(String shikomianswerno) {
        this.shikomianswerno = shikomianswerno == null ? null : shikomianswerno.trim();
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getDeliveryflag() {
        return deliveryflag;
    }

    public void setDeliveryflag(String deliveryflag) {
        this.deliveryflag = deliveryflag == null ? null : deliveryflag.trim();
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode == null ? null : smccode.trim();
    }

    public String getHscode() {
        return hscode;
    }

    public void setHscode(String hscode) {
        this.hscode = hscode == null ? null : hscode.trim();
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

    public String getLocationno() {
        return locationno;
    }

    public void setLocationno(String locationno) {
        this.locationno = locationno == null ? null : locationno.trim();
    }

    public String getVipcode() {
        return vipcode;
    }

    public void setVipcode(String vipcode) {
        this.vipcode = vipcode == null ? null : vipcode.trim();
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

    public String getSupplierpartno() {
        return supplierpartno;
    }

    public void setSupplierpartno(String supplierpartno) {
        this.supplierpartno = supplierpartno == null ? null : supplierpartno.trim();
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

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid == null ? null : operatorid.trim();
    }

    public String getCnno() {
        return cnno;
    }

    public void setCnno(String cnno) {
        this.cnno = cnno == null ? null : cnno.trim();
    }

    public Long getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Long invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDealtype() {
        return dealtype;
    }

    public void setDealtype(String dealtype) {
        this.dealtype = dealtype == null ? null : dealtype.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    public Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(Date finishdate) {
        this.finishdate = finishdate;
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
        OpsPurchasetoctc other = (OpsPurchasetoctc) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPono() == null ? other.getPono() == null : this.getPono().equals(other.getPono()))
            && (this.getLineitem() == null ? other.getLineitem() == null : this.getLineitem().equals(other.getLineitem()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getSplititemno() == null ? other.getSplititemno() == null : this.getSplititemno().equals(other.getSplititemno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getStdprice() == null ? other.getStdprice() == null : this.getStdprice().equals(other.getStdprice()))
            && (this.getPurchasedate() == null ? other.getPurchasedate() == null : this.getPurchasedate().equals(other.getPurchasedate()))
            && (this.getHopedeliverydate() == null ? other.getHopedeliverydate() == null : this.getHopedeliverydate().equals(other.getHopedeliverydate()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getReceivewarehouseid() == null ? other.getReceivewarehouseid() == null : this.getReceivewarehouseid().equals(other.getReceivewarehouseid()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getHopeexportdate() == null ? other.getHopeexportdate() == null : this.getHopeexportdate().equals(other.getHopeexportdate()))
            && (this.getInqno() == null ? other.getInqno() == null : this.getInqno().equals(other.getInqno()))
            && (this.getShikomianswerno() == null ? other.getShikomianswerno() == null : this.getShikomianswerno().equals(other.getShikomianswerno()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getDeliveryflag() == null ? other.getDeliveryflag() == null : this.getDeliveryflag().equals(other.getDeliveryflag()))
            && (this.getSmccode() == null ? other.getSmccode() == null : this.getSmccode().equals(other.getSmccode()))
            && (this.getHscode() == null ? other.getHscode() == null : this.getHscode().equals(other.getHscode()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getLocationno() == null ? other.getLocationno() == null : this.getLocationno().equals(other.getLocationno()))
            && (this.getVipcode() == null ? other.getVipcode() == null : this.getVipcode().equals(other.getVipcode()))
            && (this.getSalesinfono() == null ? other.getSalesinfono() == null : this.getSalesinfono().equals(other.getSalesinfono()))
            && (this.getPurchasetype() == null ? other.getPurchasetype() == null : this.getPurchasetype().equals(other.getPurchasetype()))
            && (this.getSupplierpartno() == null ? other.getSupplierpartno() == null : this.getSupplierpartno().equals(other.getSupplierpartno()))
            && (this.getImportfobpriceoriginal() == null ? other.getImportfobpriceoriginal() == null : this.getImportfobpriceoriginal().equals(other.getImportfobpriceoriginal()))
            && (this.getImportcurrencyid() == null ? other.getImportcurrencyid() == null : this.getImportcurrencyid().equals(other.getImportcurrencyid()))
            && (this.getOperatorid() == null ? other.getOperatorid() == null : this.getOperatorid().equals(other.getOperatorid()))
            && (this.getCnno() == null ? other.getCnno() == null : this.getCnno().equals(other.getCnno()))
            && (this.getInvoiceid() == null ? other.getInvoiceid() == null : this.getInvoiceid().equals(other.getInvoiceid()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getSendtime() == null ? other.getSendtime() == null : this.getSendtime().equals(other.getSendtime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDealtype() == null ? other.getDealtype() == null : this.getDealtype().equals(other.getDealtype()))
            && (this.getErrorMsg() == null ? other.getErrorMsg() == null : this.getErrorMsg().equals(other.getErrorMsg()))
            && (this.getFinishdate() == null ? other.getFinishdate() == null : this.getFinishdate().equals(other.getFinishdate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPono() == null) ? 0 : getPono().hashCode());
        result = prime * result + ((getLineitem() == null) ? 0 : getLineitem().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getSplititemno() == null) ? 0 : getSplititemno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getStdprice() == null) ? 0 : getStdprice().hashCode());
        result = prime * result + ((getPurchasedate() == null) ? 0 : getPurchasedate().hashCode());
        result = prime * result + ((getHopedeliverydate() == null) ? 0 : getHopedeliverydate().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getReceivewarehouseid() == null) ? 0 : getReceivewarehouseid().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getHopeexportdate() == null) ? 0 : getHopeexportdate().hashCode());
        result = prime * result + ((getInqno() == null) ? 0 : getInqno().hashCode());
        result = prime * result + ((getShikomianswerno() == null) ? 0 : getShikomianswerno().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getDeliveryflag() == null) ? 0 : getDeliveryflag().hashCode());
        result = prime * result + ((getSmccode() == null) ? 0 : getSmccode().hashCode());
        result = prime * result + ((getHscode() == null) ? 0 : getHscode().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getLocationno() == null) ? 0 : getLocationno().hashCode());
        result = prime * result + ((getVipcode() == null) ? 0 : getVipcode().hashCode());
        result = prime * result + ((getSalesinfono() == null) ? 0 : getSalesinfono().hashCode());
        result = prime * result + ((getPurchasetype() == null) ? 0 : getPurchasetype().hashCode());
        result = prime * result + ((getSupplierpartno() == null) ? 0 : getSupplierpartno().hashCode());
        result = prime * result + ((getImportfobpriceoriginal() == null) ? 0 : getImportfobpriceoriginal().hashCode());
        result = prime * result + ((getImportcurrencyid() == null) ? 0 : getImportcurrencyid().hashCode());
        result = prime * result + ((getOperatorid() == null) ? 0 : getOperatorid().hashCode());
        result = prime * result + ((getCnno() == null) ? 0 : getCnno().hashCode());
        result = prime * result + ((getInvoiceid() == null) ? 0 : getInvoiceid().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getSendtime() == null) ? 0 : getSendtime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDealtype() == null) ? 0 : getDealtype().hashCode());
        result = prime * result + ((getErrorMsg() == null) ? 0 : getErrorMsg().hashCode());
        result = prime * result + ((getFinishdate() == null) ? 0 : getFinishdate().hashCode());
        return result;
    }
}