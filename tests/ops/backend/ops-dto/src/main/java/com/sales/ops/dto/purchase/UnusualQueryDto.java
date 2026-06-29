package com.sales.ops.dto.purchase;

import java.util.Date;
import java.util.List;

public class UnusualQueryDto {
    private List<String> deptNo;
    private String orderFno;
    private List<String> orderFnoList;
    private String modelno;
    private String customerNo;
    private String userNo;
    private Date[] inquiryDate;
    private Date startInquiryDate;
    private Date endInquiryDate;
    private String unusualDescChn;
    private String unusualType;
    private String jobDeptName;
    private Integer recordCount;
    private Integer handleStatus;
    private String endUser;

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public List<String> getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(List<String> deptNo) {
        this.deptNo = deptNo;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public List<String> getOrderFnoList() {
        return orderFnoList;
    }

    public void setOrderFnoList(List<String> orderFnoList) {
        this.orderFnoList = orderFnoList;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Date[] getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(Date[] inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public Date getStartInquiryDate() {
        return startInquiryDate;
    }

    public void setStartInquiryDate(Date startInquiryDate) {
        this.startInquiryDate = startInquiryDate;
    }

    public Date getEndInquiryDate() {
        return endInquiryDate;
    }

    public void setEndInquiryDate(Date endInquiryDate) {
        this.endInquiryDate = endInquiryDate;
    }

    public String getUnusualDescChn() {
        return unusualDescChn;
    }

    public void setUnusualDescChn(String unusualDescChn) {
        this.unusualDescChn = unusualDescChn;
    }

    public String getUnusualType() {
        return unusualType;
    }

    public void setUnusualType(String unusualType) {
        this.unusualType = unusualType;
    }

    public String getJobDeptName() {
        return jobDeptName;
    }

    public void setJobDeptName(String jobDeptName) {
        this.jobDeptName = jobDeptName;
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
}
