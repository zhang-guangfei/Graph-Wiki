package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PurchaseorderView implements Serializable {
    private String orderItem;

    private String orderItemSplit;

    private Long id;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private Boolean mergeflag;

    private String modelno;

    private Integer quantity;

    private BigDecimal stdprice;

    private String transtype;

    private Date purchasedate;

    private Date hopedeliverydate;

    private String supplierid;

    private String statecode;

    private String ordtype;

    private String specmark;

    private String receivewarehouseid;

    private String remark;

    private Date hopeexportdate;

    private String inqno;

    private String shikomianswerno;

    private String operatorid;

    private String deptno;

    private String applyDeptNo;

    private String smccode;

    private String invoiceno;

    private String hscode;

    private String greencode;

    private Integer producttype;

    private String customerno;

    private String userno;

    private String salesinfono;

    private String purchasetype;

    private String supplierpartno;

    private BigDecimal importfobpriceoriginal;

    private String importcurrencyid;

    private String serverremark;

    private Date updatetime;

    private String corderno;

    private Long inventorypropertyid;

    private String replyorderno;

    private Date dlvmoddate;

    private Integer qtyreceive;

    private Date finishdate;

    private String operator;

    private String information;

    private String filepath;

    private String sendversion;

    private String deletereason;

    private String vipcode;

    private String srcDeliveryTime;

    private String dlvmoddateStr;

    private String supplierAssignType;

    private String endUser;

    private String prepareorderno;

    private static final long serialVersionUID = 1L;

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem == null ? null : orderItem.trim();
    }

    public String getOrderItemSplit() {
        return orderItemSplit;
    }

    public void setOrderItemSplit(String orderItemSplit) {
        this.orderItemSplit = orderItemSplit == null ? null : orderItemSplit.trim();
    }

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

    public Boolean getMergeflag() {
        return mergeflag;
    }

    public void setMergeflag(Boolean mergeflag) {
        this.mergeflag = mergeflag;
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

    public String getSpecmark() {
        return specmark;
    }

    public void setSpecmark(String specmark) {
        this.specmark = specmark == null ? null : specmark.trim();
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

    public String getApplyDeptNo() {
        return applyDeptNo;
    }

    public void setApplyDeptNo(String applyDeptNo) {
        this.applyDeptNo = applyDeptNo == null ? null : applyDeptNo.trim();
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

    public String getServerremark() {
        return serverremark;
    }

    public void setServerremark(String serverremark) {
        this.serverremark = serverremark == null ? null : serverremark.trim();
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

    public Long getInventorypropertyid() {
        return inventorypropertyid;
    }

    public void setInventorypropertyid(Long inventorypropertyid) {
        this.inventorypropertyid = inventorypropertyid;
    }

    public String getReplyorderno() {
        return replyorderno;
    }

    public void setReplyorderno(String replyorderno) {
        this.replyorderno = replyorderno == null ? null : replyorderno.trim();
    }

    public Date getDlvmoddate() {
        return dlvmoddate;
    }

    public void setDlvmoddate(Date dlvmoddate) {
        this.dlvmoddate = dlvmoddate;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information == null ? null : information.trim();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public String getSendversion() {
        return sendversion;
    }

    public void setSendversion(String sendversion) {
        this.sendversion = sendversion == null ? null : sendversion.trim();
    }

    public String getDeletereason() {
        return deletereason;
    }

    public void setDeletereason(String deletereason) {
        this.deletereason = deletereason == null ? null : deletereason.trim();
    }

    public String getVipcode() {
        return vipcode;
    }

    public void setVipcode(String vipcode) {
        this.vipcode = vipcode == null ? null : vipcode.trim();
    }

    public String getSrcDeliveryTime() {
        return srcDeliveryTime;
    }

    public void setSrcDeliveryTime(String srcDeliveryTime) {
        this.srcDeliveryTime = srcDeliveryTime == null ? null : srcDeliveryTime.trim();
    }

    public String getDlvmoddateStr() {
        return dlvmoddateStr;
    }

    public void setDlvmoddateStr(String dlvmoddateStr) {
        this.dlvmoddateStr = dlvmoddateStr == null ? null : dlvmoddateStr.trim();
    }

    public String getSupplierAssignType() {
        return supplierAssignType;
    }

    public void setSupplierAssignType(String supplierAssignType) {
        this.supplierAssignType = supplierAssignType == null ? null : supplierAssignType.trim();
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser == null ? null : endUser.trim();
    }

    public String getPrepareorderno() {
        return prepareorderno;
    }

    public void setPrepareorderno(String prepareorderno) {
        this.prepareorderno = prepareorderno == null ? null : prepareorderno.trim();
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
        PurchaseorderView other = (PurchaseorderView) that;
        return (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getOrderItemSplit() == null ? other.getOrderItemSplit() == null : this.getOrderItemSplit().equals(other.getOrderItemSplit()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getSplititemno() == null ? other.getSplititemno() == null : this.getSplititemno().equals(other.getSplititemno()))
            && (this.getMergeflag() == null ? other.getMergeflag() == null : this.getMergeflag().equals(other.getMergeflag()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getStdprice() == null ? other.getStdprice() == null : this.getStdprice().equals(other.getStdprice()))
            && (this.getTranstype() == null ? other.getTranstype() == null : this.getTranstype().equals(other.getTranstype()))
            && (this.getPurchasedate() == null ? other.getPurchasedate() == null : this.getPurchasedate().equals(other.getPurchasedate()))
            && (this.getHopedeliverydate() == null ? other.getHopedeliverydate() == null : this.getHopedeliverydate().equals(other.getHopedeliverydate()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getStatecode() == null ? other.getStatecode() == null : this.getStatecode().equals(other.getStatecode()))
            && (this.getOrdtype() == null ? other.getOrdtype() == null : this.getOrdtype().equals(other.getOrdtype()))
            && (this.getSpecmark() == null ? other.getSpecmark() == null : this.getSpecmark().equals(other.getSpecmark()))
            && (this.getReceivewarehouseid() == null ? other.getReceivewarehouseid() == null : this.getReceivewarehouseid().equals(other.getReceivewarehouseid()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getHopeexportdate() == null ? other.getHopeexportdate() == null : this.getHopeexportdate().equals(other.getHopeexportdate()))
            && (this.getInqno() == null ? other.getInqno() == null : this.getInqno().equals(other.getInqno()))
            && (this.getShikomianswerno() == null ? other.getShikomianswerno() == null : this.getShikomianswerno().equals(other.getShikomianswerno()))
            && (this.getOperatorid() == null ? other.getOperatorid() == null : this.getOperatorid().equals(other.getOperatorid()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getApplyDeptNo() == null ? other.getApplyDeptNo() == null : this.getApplyDeptNo().equals(other.getApplyDeptNo()))
            && (this.getSmccode() == null ? other.getSmccode() == null : this.getSmccode().equals(other.getSmccode()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getHscode() == null ? other.getHscode() == null : this.getHscode().equals(other.getHscode()))
            && (this.getGreencode() == null ? other.getGreencode() == null : this.getGreencode().equals(other.getGreencode()))
            && (this.getProducttype() == null ? other.getProducttype() == null : this.getProducttype().equals(other.getProducttype()))
            && (this.getCustomerno() == null ? other.getCustomerno() == null : this.getCustomerno().equals(other.getCustomerno()))
            && (this.getUserno() == null ? other.getUserno() == null : this.getUserno().equals(other.getUserno()))
            && (this.getSalesinfono() == null ? other.getSalesinfono() == null : this.getSalesinfono().equals(other.getSalesinfono()))
            && (this.getPurchasetype() == null ? other.getPurchasetype() == null : this.getPurchasetype().equals(other.getPurchasetype()))
            && (this.getSupplierpartno() == null ? other.getSupplierpartno() == null : this.getSupplierpartno().equals(other.getSupplierpartno()))
            && (this.getImportfobpriceoriginal() == null ? other.getImportfobpriceoriginal() == null : this.getImportfobpriceoriginal().equals(other.getImportfobpriceoriginal()))
            && (this.getImportcurrencyid() == null ? other.getImportcurrencyid() == null : this.getImportcurrencyid().equals(other.getImportcurrencyid()))
            && (this.getServerremark() == null ? other.getServerremark() == null : this.getServerremark().equals(other.getServerremark()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getCorderno() == null ? other.getCorderno() == null : this.getCorderno().equals(other.getCorderno()))
            && (this.getInventorypropertyid() == null ? other.getInventorypropertyid() == null : this.getInventorypropertyid().equals(other.getInventorypropertyid()))
            && (this.getReplyorderno() == null ? other.getReplyorderno() == null : this.getReplyorderno().equals(other.getReplyorderno()))
            && (this.getDlvmoddate() == null ? other.getDlvmoddate() == null : this.getDlvmoddate().equals(other.getDlvmoddate()))
            && (this.getQtyreceive() == null ? other.getQtyreceive() == null : this.getQtyreceive().equals(other.getQtyreceive()))
            && (this.getFinishdate() == null ? other.getFinishdate() == null : this.getFinishdate().equals(other.getFinishdate()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getInformation() == null ? other.getInformation() == null : this.getInformation().equals(other.getInformation()))
            && (this.getFilepath() == null ? other.getFilepath() == null : this.getFilepath().equals(other.getFilepath()))
            && (this.getSendversion() == null ? other.getSendversion() == null : this.getSendversion().equals(other.getSendversion()))
            && (this.getDeletereason() == null ? other.getDeletereason() == null : this.getDeletereason().equals(other.getDeletereason()))
            && (this.getVipcode() == null ? other.getVipcode() == null : this.getVipcode().equals(other.getVipcode()))
            && (this.getSrcDeliveryTime() == null ? other.getSrcDeliveryTime() == null : this.getSrcDeliveryTime().equals(other.getSrcDeliveryTime()))
            && (this.getDlvmoddateStr() == null ? other.getDlvmoddateStr() == null : this.getDlvmoddateStr().equals(other.getDlvmoddateStr()))
            && (this.getSupplierAssignType() == null ? other.getSupplierAssignType() == null : this.getSupplierAssignType().equals(other.getSupplierAssignType()))
            && (this.getEndUser() == null ? other.getEndUser() == null : this.getEndUser().equals(other.getEndUser()))
            && (this.getPrepareorderno() == null ? other.getPrepareorderno() == null : this.getPrepareorderno().equals(other.getPrepareorderno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getOrderItemSplit() == null) ? 0 : getOrderItemSplit().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getSplititemno() == null) ? 0 : getSplititemno().hashCode());
        result = prime * result + ((getMergeflag() == null) ? 0 : getMergeflag().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getStdprice() == null) ? 0 : getStdprice().hashCode());
        result = prime * result + ((getTranstype() == null) ? 0 : getTranstype().hashCode());
        result = prime * result + ((getPurchasedate() == null) ? 0 : getPurchasedate().hashCode());
        result = prime * result + ((getHopedeliverydate() == null) ? 0 : getHopedeliverydate().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getStatecode() == null) ? 0 : getStatecode().hashCode());
        result = prime * result + ((getOrdtype() == null) ? 0 : getOrdtype().hashCode());
        result = prime * result + ((getSpecmark() == null) ? 0 : getSpecmark().hashCode());
        result = prime * result + ((getReceivewarehouseid() == null) ? 0 : getReceivewarehouseid().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getHopeexportdate() == null) ? 0 : getHopeexportdate().hashCode());
        result = prime * result + ((getInqno() == null) ? 0 : getInqno().hashCode());
        result = prime * result + ((getShikomianswerno() == null) ? 0 : getShikomianswerno().hashCode());
        result = prime * result + ((getOperatorid() == null) ? 0 : getOperatorid().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getApplyDeptNo() == null) ? 0 : getApplyDeptNo().hashCode());
        result = prime * result + ((getSmccode() == null) ? 0 : getSmccode().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getHscode() == null) ? 0 : getHscode().hashCode());
        result = prime * result + ((getGreencode() == null) ? 0 : getGreencode().hashCode());
        result = prime * result + ((getProducttype() == null) ? 0 : getProducttype().hashCode());
        result = prime * result + ((getCustomerno() == null) ? 0 : getCustomerno().hashCode());
        result = prime * result + ((getUserno() == null) ? 0 : getUserno().hashCode());
        result = prime * result + ((getSalesinfono() == null) ? 0 : getSalesinfono().hashCode());
        result = prime * result + ((getPurchasetype() == null) ? 0 : getPurchasetype().hashCode());
        result = prime * result + ((getSupplierpartno() == null) ? 0 : getSupplierpartno().hashCode());
        result = prime * result + ((getImportfobpriceoriginal() == null) ? 0 : getImportfobpriceoriginal().hashCode());
        result = prime * result + ((getImportcurrencyid() == null) ? 0 : getImportcurrencyid().hashCode());
        result = prime * result + ((getServerremark() == null) ? 0 : getServerremark().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getCorderno() == null) ? 0 : getCorderno().hashCode());
        result = prime * result + ((getInventorypropertyid() == null) ? 0 : getInventorypropertyid().hashCode());
        result = prime * result + ((getReplyorderno() == null) ? 0 : getReplyorderno().hashCode());
        result = prime * result + ((getDlvmoddate() == null) ? 0 : getDlvmoddate().hashCode());
        result = prime * result + ((getQtyreceive() == null) ? 0 : getQtyreceive().hashCode());
        result = prime * result + ((getFinishdate() == null) ? 0 : getFinishdate().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getInformation() == null) ? 0 : getInformation().hashCode());
        result = prime * result + ((getFilepath() == null) ? 0 : getFilepath().hashCode());
        result = prime * result + ((getSendversion() == null) ? 0 : getSendversion().hashCode());
        result = prime * result + ((getDeletereason() == null) ? 0 : getDeletereason().hashCode());
        result = prime * result + ((getVipcode() == null) ? 0 : getVipcode().hashCode());
        result = prime * result + ((getSrcDeliveryTime() == null) ? 0 : getSrcDeliveryTime().hashCode());
        result = prime * result + ((getDlvmoddateStr() == null) ? 0 : getDlvmoddateStr().hashCode());
        result = prime * result + ((getSupplierAssignType() == null) ? 0 : getSupplierAssignType().hashCode());
        result = prime * result + ((getEndUser() == null) ? 0 : getEndUser().hashCode());
        result = prime * result + ((getPrepareorderno() == null) ? 0 : getPrepareorderno().hashCode());
        return result;
    }
}