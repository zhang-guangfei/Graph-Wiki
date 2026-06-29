package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class IpsReceiveSignImpInfoFromAll implements Serializable {
    private Long id;

    private String sourceSystem;

    private String supplierSystem;

    private String srcOrderNo;

    private String srcOrderItem;

    private String orderNo;

    private String orderItem;

    private String modelNo;

    private String dataType;

    private BigDecimal qty;

    private BigDecimal deviationQty;

    private String deviationReason;

    private String processStatus;

    private String processPlace;

    private Date processDate;

    private String processorCode;

    private Date createTime;

    private String createUser;

    private String invoiceNo;

    private String invoiceBatchNo;

    private String additionInfo;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem == null ? null : sourceSystem.trim();
    }

    public String getSupplierSystem() {
        return supplierSystem;
    }

    public void setSupplierSystem(String supplierSystem) {
        this.supplierSystem = supplierSystem == null ? null : supplierSystem.trim();
    }

    public String getSrcOrderNo() {
        return srcOrderNo;
    }

    public void setSrcOrderNo(String srcOrderNo) {
        this.srcOrderNo = srcOrderNo == null ? null : srcOrderNo.trim();
    }

    public String getSrcOrderItem() {
        return srcOrderItem;
    }

    public void setSrcOrderItem(String srcOrderItem) {
        this.srcOrderItem = srcOrderItem == null ? null : srcOrderItem.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem == null ? null : orderItem.trim();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getDeviationQty() {
        return deviationQty;
    }

    public void setDeviationQty(BigDecimal deviationQty) {
        this.deviationQty = deviationQty;
    }

    public String getDeviationReason() {
        return deviationReason;
    }

    public void setDeviationReason(String deviationReason) {
        this.deviationReason = deviationReason == null ? null : deviationReason.trim();
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus == null ? null : processStatus.trim();
    }

    public String getProcessPlace() {
        return processPlace;
    }

    public void setProcessPlace(String processPlace) {
        this.processPlace = processPlace == null ? null : processPlace.trim();
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getProcessorCode() {
        return processorCode;
    }

    public void setProcessorCode(String processorCode) {
        this.processorCode = processorCode == null ? null : processorCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getInvoiceBatchNo() {
        return invoiceBatchNo;
    }

    public void setInvoiceBatchNo(String invoiceBatchNo) {
        this.invoiceBatchNo = invoiceBatchNo == null ? null : invoiceBatchNo.trim();
    }

    public String getAdditionInfo() {
        return additionInfo;
    }

    public void setAdditionInfo(String additionInfo) {
        this.additionInfo = additionInfo == null ? null : additionInfo.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IpsReceiveSignImpInfoFromAll info = (IpsReceiveSignImpInfoFromAll) o;
        return Objects.equals(id, info.id) && Objects.equals(sourceSystem, info.sourceSystem) && Objects.equals(supplierSystem, info.supplierSystem) && Objects.equals(srcOrderNo, info.srcOrderNo) && Objects.equals(srcOrderItem, info.srcOrderItem) && Objects.equals(orderNo, info.orderNo) && Objects.equals(orderItem, info.orderItem) && Objects.equals(modelNo, info.modelNo) && Objects.equals(dataType, info.dataType) && Objects.equals(qty, info.qty) && Objects.equals(deviationQty, info.deviationQty) && Objects.equals(deviationReason, info.deviationReason) && Objects.equals(processStatus, info.processStatus) && Objects.equals(processPlace, info.processPlace) && Objects.equals(processDate, info.processDate) && Objects.equals(processorCode, info.processorCode) && Objects.equals(createTime, info.createTime) && Objects.equals(createUser, info.createUser) && Objects.equals(invoiceNo, info.invoiceNo) && Objects.equals(invoiceBatchNo, info.invoiceBatchNo) && Objects.equals(additionInfo, info.additionInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceSystem, supplierSystem, srcOrderNo, srcOrderItem, orderNo, orderItem, modelNo, dataType, qty, deviationQty, deviationReason, processStatus, processPlace, processDate, processorCode, createTime, createUser, invoiceNo, invoiceBatchNo, additionInfo);
    }
}