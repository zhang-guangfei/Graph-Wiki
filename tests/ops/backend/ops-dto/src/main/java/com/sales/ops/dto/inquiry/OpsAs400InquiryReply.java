package com.sales.ops.dto.inquiry;

import java.util.Date;

/**
 * @description:
 * @author: B91717
 * @time: 2023/12/25 12:02
 */
public class OpsAs400InquiryReply {

    private Long id;

    private String subsidiaryCode;

    private String subsidiaryNo;

    private String subsidiaryItem;

    private String type;

    private String partNumber;

    private Integer qty;

    private String smcjpNo;

    private Integer smcjpItem;

    private Date dueDate;

    private Integer dueDays;

    private String inquiryMessageCode;
    private String inquiryMessage;

    private String inquiryMessageContents;

    private Date inquirySendDate;

    private Date inquirySendTime;

    private String returnReferenceNo;

    private String returnADate;

    private Integer returnBDays;

    private String returnBCode;

    private String returnMessageCode;

    private String returnMessage;

    private String returnMessageContents;

    private Date returnDate;

    private Date returnReturn;

    private String hotlineLevel;

    // bug14435，对接日本新加字段
    private String subsidiaryAppLyNo;

    private String subsidiaryAppLyItem;

    public String getSubsidiaryAppLyNo() {
        return subsidiaryAppLyNo;
    }

    public void setSubsidiaryAppLyNo(String subsidiaryAppLyNo) {
        this.subsidiaryAppLyNo = subsidiaryAppLyNo;
    }

    public String getSubsidiaryAppLyItem() {
        return subsidiaryAppLyItem;
    }

    public void setSubsidiaryAppLyItem(String subsidiaryAppLyItem) {
        this.subsidiaryAppLyItem = subsidiaryAppLyItem;
    }

    public String getHotlineLevel() {
        return hotlineLevel;
    }

    public void setHotlineLevel(String hotlineLevel) {
        this.hotlineLevel = hotlineLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubsidiaryCode() {
        return subsidiaryCode;
    }

    public void setSubsidiaryCode(String subsidiaryCode) {
        this.subsidiaryCode = subsidiaryCode;
    }

    public String getSubsidiaryNo() {
        return subsidiaryNo;
    }

    public void setSubsidiaryNo(String subsidiaryNo) {
        this.subsidiaryNo = subsidiaryNo;
    }

    public String getSubsidiaryItem() {
        return subsidiaryItem;
    }

    public void setSubsidiaryItem(String subsidiaryItem) {
        this.subsidiaryItem = subsidiaryItem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getSmcjpNo() {
        return smcjpNo;
    }

    public void setSmcjpNo(String smcjpNo) {
        this.smcjpNo = smcjpNo;
    }

    public Integer getSmcjpItem() {
        return smcjpItem;
    }

    public void setSmcjpItem(Integer smcjpItem) {
        this.smcjpItem = smcjpItem;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getDueDays() {
        return dueDays;
    }

    public void setDueDays(Integer dueDays) {
        this.dueDays = dueDays;
    }

    public String getInquiryMessageCode() {
        return inquiryMessageCode;
    }

    public void setInquiryMessageCode(String inquiryMessageCode) {
        this.inquiryMessageCode = inquiryMessageCode;
    }

    public String getInquiryMessage() {
        return inquiryMessage;
    }

    public void setInquiryMessage(String inquiryMessage) {
        this.inquiryMessage = inquiryMessage;
    }

    public String getInquiryMessageContents() {
        return inquiryMessageContents;
    }

    public void setInquiryMessageContents(String inquiryMessageContents) {
        this.inquiryMessageContents = inquiryMessageContents;
    }

    public Date getInquirySendDate() {
        return inquirySendDate;
    }

    public void setInquirySendDate(Date inquirySendDate) {
        this.inquirySendDate = inquirySendDate;
    }

    public Date getInquirySendTime() {
        return inquirySendTime;
    }

    public void setInquirySendTime(Date inquirySendTime) {
        this.inquirySendTime = inquirySendTime;
    }

    public String getReturnReferenceNo() {
        return returnReferenceNo;
    }

    public void setReturnReferenceNo(String returnReferenceNo) {
        this.returnReferenceNo = returnReferenceNo;
    }

    public String getReturnADate() {
        return returnADate;
    }

    public void setReturnADate(String returnADate) {
        this.returnADate = returnADate;
    }

    public Integer getReturnBDays() {
        return returnBDays;
    }

    public void setReturnBDays(Integer returnBDays) {
        this.returnBDays = returnBDays;
    }

    public String getReturnBCode() {
        return returnBCode;
    }

    public void setReturnBCode(String returnBCode) {
        this.returnBCode = returnBCode;
    }

    public String getReturnMessageCode() {
        return returnMessageCode;
    }

    public void setReturnMessageCode(String returnMessageCode) {
        this.returnMessageCode = returnMessageCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getReturnMessageContents() {
        return returnMessageContents;
    }

    public void setReturnMessageContents(String returnMessageContents) {
        this.returnMessageContents = returnMessageContents;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getReturnReturn() {
        return returnReturn;
    }

    public void setReturnReturn(Date returnReturn) {
        this.returnReturn = returnReturn;
    }
}
