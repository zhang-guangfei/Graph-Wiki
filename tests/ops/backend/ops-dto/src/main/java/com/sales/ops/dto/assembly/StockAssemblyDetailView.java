package com.sales.ops.dto.assembly;


import lombok.Data;

import java.util.Date;

@Data
public class StockAssemblyDetailView {

    private String inventoryKeys;

    private String detailWarehouseCode;

    private Long detailId;

    private Long applyId;

    private String modelNo;

    private Boolean isTransOut;

    private Double quantity;

    private Boolean isPrepareInv;

     // 处理状态 0-编辑中; 1-审核中; 2-待处理; 4-退回; 5-组换中; 6-已完成; 7-不能组换; 8-已计入成本; 9-已取消;
    private String optCode;

    private String lenDM;

    private String unit;

    private String detailRemark;

    private String allowOutQty;

    private String inventoryId;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private String InvoiceNo;

    private Date detailTransTime;

    private String status;

    private String deptNo;

    private String assembleType;

     // 申请类型: 1-组换; 2-调库;
    private String applyType;

    private String applyRemark;

    private String needForOrderNo;

    private String needModelNo;

    private Integer needQuantity;

    private String billNo;

    private Date answerDate;

    private String answerText;

    private Date applyDate;

    private String applyPsn;

    private String approvePsn;

    private Date dlvDate;

    private String transPsn;

    private Date applyTransTime;

    private Date receiveTime;

    private String receivePsn;

    private String applyWarehouseCode;

    private String applyNo;

    private Date approveDate;

    private String inventoryTypeCode;

    private String customerNo;

    private String groupCustomerNo;

    private String ppl;

    private String projectCode;

    private String customerName;

    public String getInventoryKeys() {
        return inventoryKeys;
    }

    public void setInventoryKeys(String inventoryKeys) {
        this.inventoryKeys = inventoryKeys;
    }

    public String getDetailWarehouseCode() {
        return detailWarehouseCode;
    }

    public void setDetailWarehouseCode(String detailWarehouseCode) {
        this.detailWarehouseCode = detailWarehouseCode;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Boolean getTransOut() {
        return isTransOut;
    }

    public void setTransOut(Boolean transOut) {
        isTransOut = transOut;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Boolean getPrepareInv() {
        return isPrepareInv;
    }

    public void setPrepareInv(Boolean prepareInv) {
        isPrepareInv = prepareInv;
    }

    public String getOptCode() {
        return optCode;
    }

    public void setOptCode(String optCode) {
        this.optCode = optCode;
    }

    public String getLenDM() {
        return lenDM;
    }

    public void setLenDM(String lenDM) {
        this.lenDM = lenDM;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    public String getAllowOutQty() {
        return allowOutQty;
    }

    public void setAllowOutQty(String allowOutQty) {
        this.allowOutQty = allowOutQty;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    public Date getDetailTransTime() {
        return detailTransTime;
    }

    public void setDetailTransTime(Date detailTransTime) {
        this.detailTransTime = detailTransTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getAssembleType() {
        return assembleType;
    }

    public void setAssembleType(String assembleType) {
        this.assembleType = assembleType;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }

    public String getNeedForOrderNo() {
        return needForOrderNo;
    }

    public void setNeedForOrderNo(String needForOrderNo) {
        this.needForOrderNo = needForOrderNo;
    }

    public String getNeedModelNo() {
        return needModelNo;
    }

    public void setNeedModelNo(String needModelNo) {
        this.needModelNo = needModelNo;
    }

    public Integer getNeedQuantity() {
        return needQuantity;
    }

    public void setNeedQuantity(Integer needQuantity) {
        this.needQuantity = needQuantity;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyPsn() {
        return applyPsn;
    }

    public void setApplyPsn(String applyPsn) {
        this.applyPsn = applyPsn;
    }

    public String getApprovePsn() {
        return approvePsn;
    }

    public void setApprovePsn(String approvePsn) {
        this.approvePsn = approvePsn;
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public String getTransPsn() {
        return transPsn;
    }

    public void setTransPsn(String transPsn) {
        this.transPsn = transPsn;
    }

    public Date getApplyTransTime() {
        return applyTransTime;
    }

    public void setApplyTransTime(Date applyTransTime) {
        this.applyTransTime = applyTransTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReceivePsn() {
        return receivePsn;
    }

    public void setReceivePsn(String receivePsn) {
        this.receivePsn = receivePsn;
    }

    public String getApplyWarehouseCode() {
        return applyWarehouseCode;
    }

    public void setApplyWarehouseCode(String applyWarehouseCode) {
        this.applyWarehouseCode = applyWarehouseCode;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
