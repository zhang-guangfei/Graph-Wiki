package com.sales.ops.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class OpsInquiryDetail implements Serializable {
    private Long id;

    private String inquiryApplyNo;

    private Integer itemNo;

    private String taskNo;

    private String fptype;

    private String orderNo;

    private Integer version;

    private String replyDept;

    private String supplierOrderNo;

    private String replyNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date replyDeliveryDate;

    private String replyUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date replyTime;

    private String replyDelayReason;

    private String replyRemark;

    private String inquiryLevel;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String replySupplierDept;

    private String pono;

    private Integer lineitem;

    private String rorderNo;

    private Integer rorderItem;

    private Integer splititemno;

    private String smccode;

    private String purchasetype;

    private String inquiryReasonParam;

    private String rorderSplitno;

    private String modelNo;

    private Integer quantity;

    private String orderStatus;

    private String supplierId;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date hopeExportDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date dlvModdate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date hopeDeliveryDate;

    private String replyDeliveryDateSrc;

    private String replyResultDesc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInquiryApplyNo() {
        return inquiryApplyNo;
    }

    public void setInquiryApplyNo(String inquiryApplyNo) {
        this.inquiryApplyNo = inquiryApplyNo == null ? null : inquiryApplyNo.trim();
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo == null ? null : taskNo.trim();
    }

    public String getFptype() {
        return fptype;
    }

    public void setFptype(String fptype) {
        this.fptype = fptype == null ? null : fptype.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getReplyDept() {
        return replyDept;
    }

    public void setReplyDept(String replyDept) {
        this.replyDept = replyDept == null ? null : replyDept.trim();
    }

    public String getSupplierOrderNo() {
        return supplierOrderNo;
    }

    public void setSupplierOrderNo(String supplierOrderNo) {
        this.supplierOrderNo = supplierOrderNo == null ? null : supplierOrderNo.trim();
    }

    public String getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(String replyNo) {
        this.replyNo = replyNo == null ? null : replyNo.trim();
    }

    public Date getReplyDeliveryDate() {
        return replyDeliveryDate;
    }

    public void setReplyDeliveryDate(Date replyDeliveryDate) {
        this.replyDeliveryDate = replyDeliveryDate;
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser == null ? null : replyUser.trim();
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyDelayReason() {
        return replyDelayReason;
    }

    public void setReplyDelayReason(String replyDelayReason) {
        this.replyDelayReason = replyDelayReason == null ? null : replyDelayReason.trim();
    }

    public String getReplyRemark() {
        return replyRemark;
    }

    public void setReplyRemark(String replyRemark) {
        this.replyRemark = replyRemark == null ? null : replyRemark.trim();
    }

    public String getInquiryLevel() {
        return inquiryLevel;
    }

    public void setInquiryLevel(String inquiryLevel) {
        this.inquiryLevel = inquiryLevel == null ? null : inquiryLevel.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getReplySupplierDept() {
        return replySupplierDept;
    }

    public void setReplySupplierDept(String replySupplierDept) {
        this.replySupplierDept = replySupplierDept == null ? null : replySupplierDept.trim();
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

    public String getRorderNo() {
        return rorderNo;
    }

    public void setRorderNo(String rorderNo) {
        this.rorderNo = rorderNo == null ? null : rorderNo.trim();
    }

    public Integer getRorderItem() {
        return rorderItem;
    }

    public void setRorderItem(Integer rorderItem) {
        this.rorderItem = rorderItem;
    }

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode == null ? null : smccode.trim();
    }

    public String getPurchasetype() {
        return purchasetype;
    }

    public void setPurchasetype(String purchasetype) {
        this.purchasetype = purchasetype == null ? null : purchasetype.trim();
    }

    public String getInquiryReasonParam() {
        return inquiryReasonParam;
    }

    public void setInquiryReasonParam(String inquiryReasonParam) {
        this.inquiryReasonParam = inquiryReasonParam == null ? null : inquiryReasonParam.trim();
    }

    public String getRorderSplitno() {
        return rorderSplitno;
    }

    public void setRorderSplitno(String rorderSplitno) {
        this.rorderSplitno = rorderSplitno == null ? null : rorderSplitno.trim();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public Date getHopeExportDate() {
        return hopeExportDate;
    }

    public void setHopeExportDate(Date hopeExportDate) {
        this.hopeExportDate = hopeExportDate;
    }

    public Date getDlvModdate() {
        return dlvModdate;
    }

    public void setDlvModdate(Date dlvModdate) {
        this.dlvModdate = dlvModdate;
    }

    public Date getHopeDeliveryDate() {
        return hopeDeliveryDate;
    }

    public void setHopeDeliveryDate(Date hopeDeliveryDate) {
        this.hopeDeliveryDate = hopeDeliveryDate;
    }

    public String getReplyDeliveryDateSrc() {
        return replyDeliveryDateSrc;
    }

    public void setReplyDeliveryDateSrc(String replyDeliveryDateSrc) {
        this.replyDeliveryDateSrc = replyDeliveryDateSrc == null ? null : replyDeliveryDateSrc.trim();
    }

    public String getReplyResultDesc() {
        return replyResultDesc;
    }

    public void setReplyResultDesc(String replyResultDesc) {
        this.replyResultDesc = replyResultDesc == null ? null : replyResultDesc.trim();
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
        OpsInquiryDetail other = (OpsInquiryDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInquiryApplyNo() == null ? other.getInquiryApplyNo() == null : this.getInquiryApplyNo().equals(other.getInquiryApplyNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getTaskNo() == null ? other.getTaskNo() == null : this.getTaskNo().equals(other.getTaskNo()))
            && (this.getFptype() == null ? other.getFptype() == null : this.getFptype().equals(other.getFptype()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getReplyDept() == null ? other.getReplyDept() == null : this.getReplyDept().equals(other.getReplyDept()))
            && (this.getSupplierOrderNo() == null ? other.getSupplierOrderNo() == null : this.getSupplierOrderNo().equals(other.getSupplierOrderNo()))
            && (this.getReplyNo() == null ? other.getReplyNo() == null : this.getReplyNo().equals(other.getReplyNo()))
            && (this.getReplyDeliveryDate() == null ? other.getReplyDeliveryDate() == null : this.getReplyDeliveryDate().equals(other.getReplyDeliveryDate()))
            && (this.getReplyUser() == null ? other.getReplyUser() == null : this.getReplyUser().equals(other.getReplyUser()))
            && (this.getReplyTime() == null ? other.getReplyTime() == null : this.getReplyTime().equals(other.getReplyTime()))
            && (this.getReplyDelayReason() == null ? other.getReplyDelayReason() == null : this.getReplyDelayReason().equals(other.getReplyDelayReason()))
            && (this.getReplyRemark() == null ? other.getReplyRemark() == null : this.getReplyRemark().equals(other.getReplyRemark()))
            && (this.getInquiryLevel() == null ? other.getInquiryLevel() == null : this.getInquiryLevel().equals(other.getInquiryLevel()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getReplySupplierDept() == null ? other.getReplySupplierDept() == null : this.getReplySupplierDept().equals(other.getReplySupplierDept()))
            && (this.getPono() == null ? other.getPono() == null : this.getPono().equals(other.getPono()))
            && (this.getLineitem() == null ? other.getLineitem() == null : this.getLineitem().equals(other.getLineitem()))
            && (this.getRorderNo() == null ? other.getRorderNo() == null : this.getRorderNo().equals(other.getRorderNo()))
            && (this.getRorderItem() == null ? other.getRorderItem() == null : this.getRorderItem().equals(other.getRorderItem()))
            && (this.getSplititemno() == null ? other.getSplititemno() == null : this.getSplititemno().equals(other.getSplititemno()))
            && (this.getSmccode() == null ? other.getSmccode() == null : this.getSmccode().equals(other.getSmccode()))
            && (this.getPurchasetype() == null ? other.getPurchasetype() == null : this.getPurchasetype().equals(other.getPurchasetype()))
            && (this.getInquiryReasonParam() == null ? other.getInquiryReasonParam() == null : this.getInquiryReasonParam().equals(other.getInquiryReasonParam()))
            && (this.getRorderSplitno() == null ? other.getRorderSplitno() == null : this.getRorderSplitno().equals(other.getRorderSplitno()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getOrderStatus() == null ? other.getOrderStatus() == null : this.getOrderStatus().equals(other.getOrderStatus()))
            && (this.getSupplierId() == null ? other.getSupplierId() == null : this.getSupplierId().equals(other.getSupplierId()))
            && (this.getHopeExportDate() == null ? other.getHopeExportDate() == null : this.getHopeExportDate().equals(other.getHopeExportDate()))
            && (this.getDlvModdate() == null ? other.getDlvModdate() == null : this.getDlvModdate().equals(other.getDlvModdate()))
            && (this.getHopeDeliveryDate() == null ? other.getHopeDeliveryDate() == null : this.getHopeDeliveryDate().equals(other.getHopeDeliveryDate()))
            && (this.getReplyDeliveryDateSrc() == null ? other.getReplyDeliveryDateSrc() == null : this.getReplyDeliveryDateSrc().equals(other.getReplyDeliveryDateSrc()))
            && (this.getReplyResultDesc() == null ? other.getReplyResultDesc() == null : this.getReplyResultDesc().equals(other.getReplyResultDesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInquiryApplyNo() == null) ? 0 : getInquiryApplyNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getTaskNo() == null) ? 0 : getTaskNo().hashCode());
        result = prime * result + ((getFptype() == null) ? 0 : getFptype().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getReplyDept() == null) ? 0 : getReplyDept().hashCode());
        result = prime * result + ((getSupplierOrderNo() == null) ? 0 : getSupplierOrderNo().hashCode());
        result = prime * result + ((getReplyNo() == null) ? 0 : getReplyNo().hashCode());
        result = prime * result + ((getReplyDeliveryDate() == null) ? 0 : getReplyDeliveryDate().hashCode());
        result = prime * result + ((getReplyUser() == null) ? 0 : getReplyUser().hashCode());
        result = prime * result + ((getReplyTime() == null) ? 0 : getReplyTime().hashCode());
        result = prime * result + ((getReplyDelayReason() == null) ? 0 : getReplyDelayReason().hashCode());
        result = prime * result + ((getReplyRemark() == null) ? 0 : getReplyRemark().hashCode());
        result = prime * result + ((getInquiryLevel() == null) ? 0 : getInquiryLevel().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getReplySupplierDept() == null) ? 0 : getReplySupplierDept().hashCode());
        result = prime * result + ((getPono() == null) ? 0 : getPono().hashCode());
        result = prime * result + ((getLineitem() == null) ? 0 : getLineitem().hashCode());
        result = prime * result + ((getRorderNo() == null) ? 0 : getRorderNo().hashCode());
        result = prime * result + ((getRorderItem() == null) ? 0 : getRorderItem().hashCode());
        result = prime * result + ((getSplititemno() == null) ? 0 : getSplititemno().hashCode());
        result = prime * result + ((getSmccode() == null) ? 0 : getSmccode().hashCode());
        result = prime * result + ((getPurchasetype() == null) ? 0 : getPurchasetype().hashCode());
        result = prime * result + ((getInquiryReasonParam() == null) ? 0 : getInquiryReasonParam().hashCode());
        result = prime * result + ((getRorderSplitno() == null) ? 0 : getRorderSplitno().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getOrderStatus() == null) ? 0 : getOrderStatus().hashCode());
        result = prime * result + ((getSupplierId() == null) ? 0 : getSupplierId().hashCode());
        result = prime * result + ((getHopeExportDate() == null) ? 0 : getHopeExportDate().hashCode());
        result = prime * result + ((getDlvModdate() == null) ? 0 : getDlvModdate().hashCode());
        result = prime * result + ((getHopeDeliveryDate() == null) ? 0 : getHopeDeliveryDate().hashCode());
        result = prime * result + ((getReplyDeliveryDateSrc() == null) ? 0 : getReplyDeliveryDateSrc().hashCode());
        result = prime * result + ((getReplyResultDesc() == null) ? 0 : getReplyResultDesc().hashCode());
        return result;
    }
}