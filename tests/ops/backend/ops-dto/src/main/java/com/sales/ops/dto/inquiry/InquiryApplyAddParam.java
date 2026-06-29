package com.sales.ops.dto.inquiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 门户接入的，催促申请dto
 * @author B91717
 */
public class InquiryApplyAddParam  implements Serializable {

    //todo 门户申请号，合为一个即  20240910004-1
    private String inquiryApplyNo; //申请单号，门户导入时，可不填 系统自动生成

    private Integer itemno; //2024-03-26.门户申请时，为门户的子项号

    private String batchNo; //批次号

    private String dataSources; // 数据来源（ops,sales）

    private String orderNo;

    private String modelNo;

    private Integer quantity;

    private String customerNo;

    private String endUser;

    private String orderStatus; //订单 or 采购单状态

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date hopeExportDate; // 当前指定出库日

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dlvModdate; //当前返信纳期

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date hopeDeliveryDate; // 催促期望出库日

    private String inquiryReasonType; // 催促原因分类码

    private String inquiryReason; //催促原因

    private String inquiryDescription; //催促描述

    private String inquiryRemark; // 催促备注

    private String inquiryType; // 催促类型（0：采购催促:1:销售单催促）

    private Date inquiryTime; // 催促申请时间

    private String inquiryDept; // 申请部门

    private String inquiryUser; // 申请人员工编号

    private String inquiryUserName; //申请人员工姓名

    private String inquiryReasonParam; // 2024-06-27 催促原因参数

    private String moreInformation; // 2024-07-5 门户端催促原因参数

    public String getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(String moreInformation) {
        this.moreInformation = moreInformation;
    }

    public String getInquiryReasonParam() {
        return inquiryReasonParam;
    }

    public void setInquiryReasonParam(String inquiryReasonParam) {
        this.inquiryReasonParam = inquiryReasonParam;
    }

    public String getInquiryApplyNo() {
        return inquiryApplyNo;
    }

    public void setInquiryApplyNo(String inquiryApplyNo) {
        this.inquiryApplyNo = inquiryApplyNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }
}
