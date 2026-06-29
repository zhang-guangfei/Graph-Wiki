package com.sales.ops.dto.inquiry;

import java.util.Date;
import java.util.Objects;

/**
 * @description:
 * @author: B91717
 * @time: 2023/12/25 12:02
 */
public class OpsGzInquiry {

    private Long id;

    private String taskNo;

    private String orderNo;

    private String modelNo;

    private Integer quantity;

    private String customerNo;

    private String endUser;


    private String purchaseNo;

    private Date hopeExportDate;
    private Date dlvModdate;

    private Date hopeDeliveryDate;

    private String inquiryReasonType;

    private String inquiryReason;

    private String inquiryDescription;

    private String inquiryRemark;

    private String inquiryType;

    private Date inquiryTime;

    private String inquiryDept;

    private String inquiryUser;

    private String inquiryUserName;

    private String replyDept;

    private String supplierOrderNo;

    private String replyNo;

    private Date replyDeliveryDate;

    private String replyUser;

    private Date replyTime;

    private String replyDelayReason;

    private String replyRemark;

    private String inquiryLevel;

    private String createUser;

    private Date createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpsGzInquiry that = (OpsGzInquiry) o;
        return Objects.equals(id, that.id) && Objects.equals(taskNo, that.taskNo) && Objects.equals(orderNo, that.orderNo) && Objects.equals(modelNo, that.modelNo) && Objects.equals(quantity, that.quantity) && Objects.equals(customerNo, that.customerNo) && Objects.equals(endUser, that.endUser) && Objects.equals(purchaseNo, that.purchaseNo) && Objects.equals(hopeExportDate, that.hopeExportDate) && Objects.equals(dlvModdate, that.dlvModdate) && Objects.equals(hopeDeliveryDate, that.hopeDeliveryDate) && Objects.equals(inquiryReasonType, that.inquiryReasonType) && Objects.equals(inquiryReason, that.inquiryReason) && Objects.equals(inquiryDescription, that.inquiryDescription) && Objects.equals(inquiryRemark, that.inquiryRemark) && Objects.equals(inquiryType, that.inquiryType) && Objects.equals(inquiryTime, that.inquiryTime) && Objects.equals(inquiryDept, that.inquiryDept) && Objects.equals(inquiryUser, that.inquiryUser) && Objects.equals(inquiryUserName, that.inquiryUserName) && Objects.equals(replyDept, that.replyDept) && Objects.equals(supplierOrderNo, that.supplierOrderNo) && Objects.equals(replyNo, that.replyNo) && Objects.equals(replyDeliveryDate, that.replyDeliveryDate) && Objects.equals(replyUser, that.replyUser) && Objects.equals(replyTime, that.replyTime) && Objects.equals(replyDelayReason, that.replyDelayReason) && Objects.equals(replyRemark, that.replyRemark) && Objects.equals(inquiryLevel, that.inquiryLevel) && Objects.equals(createUser, that.createUser) && Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskNo, orderNo, modelNo, quantity, customerNo, endUser, purchaseNo, hopeExportDate, dlvModdate, hopeDeliveryDate, inquiryReasonType, inquiryReason, inquiryDescription, inquiryRemark, inquiryType, inquiryTime, inquiryDept, inquiryUser, inquiryUserName, replyDept, supplierOrderNo, replyNo, replyDeliveryDate, replyUser, replyTime, replyDelayReason, replyRemark, inquiryLevel, createUser, createTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
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

    public String getInquiryReasonType() {
        return inquiryReasonType;
    }

    public void setInquiryReasonType(String inquiryReasonType) {
        this.inquiryReasonType = inquiryReasonType;
    }

    public String getInquiryReason() {
        return inquiryReason;
    }

    public void setInquiryReason(String inquiryReason) {
        this.inquiryReason = inquiryReason;
    }

    public String getInquiryDescription() {
        return inquiryDescription;
    }

    public void setInquiryDescription(String inquiryDescription) {
        this.inquiryDescription = inquiryDescription;
    }

    public String getInquiryRemark() {
        return inquiryRemark;
    }

    public void setInquiryRemark(String inquiryRemark) {
        this.inquiryRemark = inquiryRemark;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public Date getInquiryTime() {
        return inquiryTime;
    }

    public void setInquiryTime(Date inquiryTime) {
        this.inquiryTime = inquiryTime;
    }

    public String getInquiryDept() {
        return inquiryDept;
    }

    public void setInquiryDept(String inquiryDept) {
        this.inquiryDept = inquiryDept;
    }

    public String getInquiryUser() {
        return inquiryUser;
    }

    public void setInquiryUser(String inquiryUser) {
        this.inquiryUser = inquiryUser;
    }

    public String getInquiryUserName() {
        return inquiryUserName;
    }

    public void setInquiryUserName(String inquiryUserName) {
        this.inquiryUserName = inquiryUserName;
    }

    public String getReplyDept() {
        return replyDept;
    }

    public void setReplyDept(String replyDept) {
        this.replyDept = replyDept;
    }

    public String getSupplierOrderNo() {
        return supplierOrderNo;
    }

    public void setSupplierOrderNo(String supplierOrderNo) {
        this.supplierOrderNo = supplierOrderNo;
    }

    public String getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(String replyNo) {
        this.replyNo = replyNo;
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
        this.replyUser = replyUser;
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
        this.replyDelayReason = replyDelayReason;
    }

    public String getReplyRemark() {
        return replyRemark;
    }

    public void setReplyRemark(String replyRemark) {
        this.replyRemark = replyRemark;
    }

    public String getInquiryLevel() {
        return inquiryLevel;
    }

    public void setInquiryLevel(String inquiryLevel) {
        this.inquiryLevel = inquiryLevel;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
