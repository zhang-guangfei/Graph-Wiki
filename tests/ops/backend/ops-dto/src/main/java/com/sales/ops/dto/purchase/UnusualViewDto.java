package com.sales.ops.dto.purchase;

import java.util.Date;

public class UnusualViewDto {

    private Long id;
    // [营业所]
    private String deptNo;
    // 订单号
    private String orderFno;
    private String orderNo;
    private Integer itemNo;
    private Integer splitItemNo;
    // 型号
    private String modelno;
    // 数量
    private Integer quantity;
    // [客户代码]
    private String customerNo;
    // [客户类型]
    private String customerType;
    // [用户代码]
    private String userNo;
    // [采购日期]
    private Date purchaseDate;
    // [手配号]即供应商接单号
    private String replyOrderNo;
    // [日本返信纳期]
    private String dlvModdate;
    // 异常回复日期
    private Date replyDate;
    //异常回复内容
    private String replyContent;
    //异常反馈日期
    private Date inquiryDate;

    // 异常类别 N接单异常
    private String unusualClassify;
    // 异常订单类别 【0未接单异常，1接单后异常】
    private String unusualType;
    // 异常识别码
    private String unusualIdentificationCode;
    // [AS400异常信息]异常描述英文
    private String unusualDescEng;
    // [异常类型说明]
    private String unusualDescChn;
    // [异常处理担当部门]
    private String jobDeptName;
    // [异常反馈日期] 记录日期
    private Date recordDate;
    // 异常提醒次数
    private Integer recordCount;
    // 异常处理状态（1：待处理，2：已回复供应商，3：处理完成）
    private Integer handleStatus;
    // 回复供应商货齐
    private Date replyJapDate;
    // 异常回复供应商操作人
    private String handleUser;
    // 异常回复供应商操作人
    private String handleUserName;
    // [异常处理完成日期]
    private Date completeDate;

    private String endUser;

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getSplitItemNo() {
        return splitItemNo;
    }

    public void setSplitItemNo(Integer splitItemNo) {
        this.splitItemNo = splitItemNo;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getReplyOrderNo() {
        return replyOrderNo;
    }

    public void setReplyOrderNo(String replyOrderNo) {
        this.replyOrderNo = replyOrderNo;
    }

    public String getDlvModdate() {
        return dlvModdate;
    }

    public void setDlvModdate(String dlvModdate) {
        this.dlvModdate = dlvModdate;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(Date inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public String getUnusualClassify() {
        return unusualClassify;
    }

    public void setUnusualClassify(String unusualClassify) {
        this.unusualClassify = unusualClassify;
    }

    public String getUnusualType() {
        return unusualType;
    }

    public void setUnusualType(String unusualType) {
        this.unusualType = unusualType;
    }

    public String getUnusualIdentificationCode() {
        return unusualIdentificationCode;
    }

    public void setUnusualIdentificationCode(String unusualIdentificationCode) {
        this.unusualIdentificationCode = unusualIdentificationCode;
    }

    public String getUnusualDescEng() {
        return unusualDescEng;
    }

    public void setUnusualDescEng(String unusualDescEng) {
        this.unusualDescEng = unusualDescEng;
    }

    public String getUnusualDescChn() {
        return unusualDescChn;
    }

    public void setUnusualDescChn(String unusualDescChn) {
        this.unusualDescChn = unusualDescChn;
    }

    public String getJobDeptName() {
        return jobDeptName;
    }

    public void setJobDeptName(String jobDeptName) {
        this.jobDeptName = jobDeptName;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public Date getReplyJapDate() {
        return replyJapDate;
    }

    public void setReplyJapDate(Date replyJapDate) {
        this.replyJapDate = replyJapDate;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }
}
