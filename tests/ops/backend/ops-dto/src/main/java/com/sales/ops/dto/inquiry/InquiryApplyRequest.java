package com.sales.ops.dto.inquiry;

import java.util.Date;
import java.util.List;

/**
 * @author B91717
 */
public class InquiryApplyRequest {

    private String inquiryApplyNo; // 申请号

    private String orderNo; // 订单号

    private String inquiryStatus; //催促状态

    private String inquiryUser; // 申请人

    private String purchaseNo;

    private String customerNo;

    private String endUser;

    private String replyDept; // 回复单位
    private List<String> replyDeptList;

    private String replyNo; // 回复号

    private String inquiryType; // 催促类别

    private String inquiryDept; // 部门代码

    private List<String> inquiryDeptList;

    // 申请时间
//    @DateTimeFormat(pattern="yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
//    @DateTimeFormat(pattern="yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private String modelNo;

    private String inquiryReasonType;

    private String orderType; //订单类型

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getInquiryReasonType() {
        return inquiryReasonType;
    }

    public void setInquiryReasonType(String inquiryReasonType) {
        this.inquiryReasonType = inquiryReasonType;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getInquiryApplyNo() {
        return inquiryApplyNo;
    }

    public void setInquiryApplyNo(String inquiryApplyNo) {
        this.inquiryApplyNo = inquiryApplyNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getInquiryStatus() {
        return inquiryStatus;
    }

    public void setInquiryStatus(String inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }

    public String getInquiryUser() {
        return inquiryUser;
    }

    public void setInquiryUser(String inquiryUser) {
        this.inquiryUser = inquiryUser;
    }

    public String getPurchaseno() {
        return purchaseNo;
    }

    public void setPurchaseno(String purchaseno) {
        this.purchaseNo = purchaseno;
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

    public String getReplyDept() {
        return replyDept;
    }

    public void setReplyDept(String replyDept) {
        this.replyDept = replyDept;
    }

    public List<String> getReplyDeptList() {
        return replyDeptList;
    }

    public void setReplyDeptList(List<String> replyDeptList) {
        this.replyDeptList = replyDeptList;
    }

    public String getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(String replyNo) {
        this.replyNo = replyNo;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public String getInquiryDept() {
        return inquiryDept;
    }

    public void setInquiryDept(String inquiryDept) {
        this.inquiryDept = inquiryDept;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<String> getInquiryDeptList() {
        return inquiryDeptList;
    }

    public void setInquiryDeptList(List<String> inquiryDeptList) {
        this.inquiryDeptList = inquiryDeptList;
    }
}
