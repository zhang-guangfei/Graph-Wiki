package com.sales.ops.dto.po.invoice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description： 发票校验错误信息实体类 对应数据库表：Imp_invoice_error（主表）与 Imp_invoice_master（关联供应商）
 * @date ：Created in 2026/2/3 18:03
 */
public class InvoiceErrorDto implements Serializable {
    /** 发票供应商（来自 Imp_invoice_master.supplier_code） */
    private String supplierCode;

    /** 发票ID（Imp_invoice_error.invoice_id） */
    private String invoiceId;

    /** 发票号（Imp_invoice_error.invoice_no） */
    private String invoiceNo;

    /** 发票订单号（Imp_invoice_error.order_no） */
    private String orderNo;

    /** 入库型号（Imp_invoice_error.model_no） */
    private String modelNo;

    /** 采购型号（Imp_invoice_error.po_model_no） */
    private String poModelNo;

    /** 发票数量（Imp_invoice_error.qty） */
    private BigDecimal qty;

    /** 采购剩余数量（Imp_invoice_error.po_qty） */
    private BigDecimal poQty;

    /** 分包数量（Imp_invoice_error.pack_qty） */
    private BigDecimal packQty;

    /** 错误原因（Imp_invoice_error.error_text） */
    private String errorText;

    /** 原采购仓（Imp_invoice_error.po_warehouse_code） */
    private String poWarehouseCode;

    /** 是否忽略差异（Imp_invoice_error.ignore_error）：0-否，1-是 */
    private Integer ignoreError;

    /** 忽略人（Imp_invoice_error.ignore_psn） */
    private String ignorePsn;

    /** 忽略时间（Imp_invoice_error.ignore_time） */
    private Date ignoreTime;

    /** 忽略原因（Imp_invoice_error.ignore_reason） */
    private String ignoreReason;

    /** 备注（Imp_invoice_error.remark） */
    private String remark;

    /** 创建时间（Imp_invoice_error.create_time） */
    private Date createTime;

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
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

    public String getPoModelNo() {
        return poModelNo;
    }

    public void setPoModelNo(String poModelNo) {
        this.poModelNo = poModelNo;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPoQty() {
        return poQty;
    }

    public void setPoQty(BigDecimal poQty) {
        this.poQty = poQty;
    }

    public BigDecimal getPackQty() {
        return packQty;
    }

    public void setPackQty(BigDecimal packQty) {
        this.packQty = packQty;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public String getPoWarehouseCode() {
        return poWarehouseCode;
    }

    public void setPoWarehouseCode(String poWarehouseCode) {
        this.poWarehouseCode = poWarehouseCode;
    }

    public Integer getIgnoreError() {
        return ignoreError;
    }

    public void setIgnoreError(Integer ignoreError) {
        this.ignoreError = ignoreError;
    }

    public String getIgnorePsn() {
        return ignorePsn;
    }

    public void setIgnorePsn(String ignorePsn) {
        this.ignorePsn = ignorePsn;
    }

    public Date getIgnoreTime() {
        return ignoreTime;
    }

    public void setIgnoreTime(Date ignoreTime) {
        this.ignoreTime = ignoreTime;
    }

    public String getIgnoreReason() {
        return ignoreReason;
    }

    public void setIgnoreReason(String ignoreReason) {
        this.ignoreReason = ignoreReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
