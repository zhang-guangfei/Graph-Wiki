package com.sales.ops.dto.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/30 13:39
 */
public class MdmRequest implements Serializable {

    /**
     * 型号编号
     */
    private String modelNo;

    /**
     * 询价人ID
     */
    private String inquirerId;

    /**
     * 询价人姓名
     */
    private String inquirerName;

    /**
     * 询价日期
     */
    private Date inquiryDate;

    /**
     * 询价描述
     */
    private String inquiryDescription;

    /**
     * 部门编号
     */
    private String deptNo;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 客户编号
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户姓名
     */
    private String userName;

    public MdmRequest(){};

    public MdmRequest(String modelNo,String inquiryDescription,String deptNo,String deptName,
                      String customerNo,String customerName, String userNo,String userName){
        this.modelNo = modelNo;
        this.inquiryDescription = inquiryDescription;
        this.deptNo= deptNo;
        this.deptName = deptName;
        this.customerNo = customerNo;
        this.customerName = customerName;
        this.userNo = userNo;
        this.userName = userName;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getInquirerId() {
        return inquirerId;
    }

    public void setInquirerId(String inquirerId) {
        this.inquirerId = inquirerId;
    }

    public String getInquirerName() {
        return inquirerName;
    }

    public void setInquirerName(String inquirerName) {
        this.inquirerName = inquirerName;
    }

    public Date getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(Date inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public String getInquiryDescription() {
        return inquiryDescription;
    }

    public void setInquiryDescription(String inquiryDescription) {
        this.inquiryDescription = inquiryDescription;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "{" +
                "modelNo='" + modelNo + '\'' +
                ", inquiryDescription='" + inquiryDescription + '\'' +
                '}';
    }
}
