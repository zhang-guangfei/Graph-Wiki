package com.sales.ops.dto.inquiry;

import java.util.Date;

/**
 * @description:
 * @author: B91717
 * @time: 2023/12/25 12:02
 */
public class OpsAs400Inquiry {

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

    private Date dueDays;

    private String inquiryMessageCode;
    private String inquiryMessage;

    private String inquiryMessageContents;

    // bug14435，对接日本新加字段
    private String subsidiaryAppLyNo;

    private String subsidiaryAppLyItem;

    private Date createTime;

    private String hotlineLevel;

    public String getHotlineLevel() {
        return hotlineLevel;
    }

    public void setHotlineLevel(String hotlineLevel) {
        this.hotlineLevel = hotlineLevel;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getDueDays() {
        return dueDays;
    }

    public void setDueDays(Date dueDays) {
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
}
