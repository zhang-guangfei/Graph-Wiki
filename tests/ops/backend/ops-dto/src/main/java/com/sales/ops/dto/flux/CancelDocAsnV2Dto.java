package com.sales.ops.dto.flux;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：4.5.	删除ro
 * @date ：Created in 2024/05/09 14:49
 */
public class CancelDocAsnV2Dto implements Serializable {
    private static final long serialVersionUID = 8143407728155259695L;

    private String docType;//固定值ROID或INVOICE
    private String warehouseId;
    private String customerId;  // 固定值smc
    private String docNo;
    private String invoiceNo;
    private String invoiceId;
    private String erpCancelReason; //取消原因
    private String erpCancelUser;
    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getErpCancelReason() {
        return erpCancelReason;
    }

    public void setErpCancelReason(String erpCancelReason) {
        this.erpCancelReason = erpCancelReason;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getErpCancelUser() {
        return erpCancelUser;
    }

    public void setErpCancelUser(String erpCancelUser) {
        this.erpCancelUser = erpCancelUser;
    }
}
