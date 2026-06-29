package com.sales.ops.dto.inqb;

import java.io.Serializable;
import java.util.Date;

/**
 * 门户接入的，inqB催促申请dto
 * @author B91717
 */
public class InqbSalesApplyAddParam implements Serializable {
    private String sourcesApplyNo; // 门户端申请单号
    private String batchNo; //批次号
    private String dataSources; // 数据来源（ops,sales）
    private String modelNo; // 申请型号
    private Integer quantity; // 申请数量
    private String endUser; // 最终用户
    private Integer expectedDeliveryDate; // INQB申请的期望货期（天数）
    private Date inqbDate; // INQB申请时间
    private Integer inqbType; // 是否强制问询、一般问询、可预占问询
    private String inqbClassify; // INQB催促分类码
    private String inqbDept; // 申请部门
    private String inqbUser; // 申请人员工编号
    private String inqbUserName; //申请人员工姓名
    private String description; // 备注说明

    public String getSourcesApplyNo() {
        return sourcesApplyNo;
    }

    public void setSourcesApplyNo(String sourcesApplyNo) {
        this.sourcesApplyNo = sourcesApplyNo;
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

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public Integer getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Integer expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Date getInqbDate() {
        return inqbDate;
    }

    public void setInqbDate(Date inqbDate) {
        this.inqbDate = inqbDate;
    }

    public Integer getInqbType() {
        return inqbType;
    }

    public void setInqbType(Integer inqbType) {
        this.inqbType = inqbType;
    }

    public String getInqbClassify() {
        return inqbClassify;
    }

    public void setInqbClassify(String inqbClassify) {
        this.inqbClassify = inqbClassify;
    }

    public String getInqbDept() {
        return inqbDept;
    }

    public void setInqbDept(String inqbDept) {
        this.inqbDept = inqbDept;
    }

    public String getInqbUser() {
        return inqbUser;
    }

    public void setInqbUser(String inqbUser) {
        this.inqbUser = inqbUser;
    }

    public String getInqbUserName() {
        return inqbUserName;
    }

    public void setInqbUserName(String inqbUserName) {
        this.inqbUserName = inqbUserName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
