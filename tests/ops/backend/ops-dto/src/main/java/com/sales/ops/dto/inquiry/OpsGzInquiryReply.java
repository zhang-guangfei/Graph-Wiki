package com.sales.ops.dto.inquiry;

import java.util.Date;

/**
 * @description:
 * @author: B91717
 * @time: 2023/12/25 12:02
 */
public class OpsGzInquiryReply {

    private Long id;

    private String taskNo;

    private String orderNo;

    private String modelNo;

    private Integer quantity;

    private String status;

    private Date acceptTime;

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

    private String updateUser;

    private Date updateTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
