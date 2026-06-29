package com.sales.ops.dto.po.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/2/4 9:54
 */
public class InvoiceErrorParamDto implements Serializable {

    private List<String> supplierCodeList;

    private List<String> errorTypeList;

    private String invoiceNo;

    private String orderNo;

    private String modelNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date endTime;

    public List<String> getSupplierCodeList() {
        return supplierCodeList;
    }

    public void setSupplierCodeList(List<String> supplierCodeList) {
        this.supplierCodeList = supplierCodeList;
    }

    public List<String> getErrorTypeList() {
        return errorTypeList;
    }

    public void setErrorTypeList(List<String> errorTypeList) {
        this.errorTypeList = errorTypeList;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
