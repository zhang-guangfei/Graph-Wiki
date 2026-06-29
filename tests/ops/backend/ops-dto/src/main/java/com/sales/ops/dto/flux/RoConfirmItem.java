package com.sales.ops.dto.flux;

/**
 * @author C02483
 * @version 1.0
 * @description: 到货确认反馈数据
 * @date 2022/1/23 21:57
 */
public class RoConfirmItem {
    private String receiveType;
    private String roId;
    private String doid;
    private String pcoid;
    private String invoiceNo;

    private Long invoiceId;

    public RoConfirmItem() {

    }
    public RoConfirmItem(String receiveType, String roId, String doid, String pcoid, String invoiceNo,Long invoiceId) {
        this.receiveType = receiveType;
        this.roId = roId;
        this.doid = doid;
        this.pcoid = pcoid;
        this.invoiceNo = invoiceNo;
        this.invoiceId = invoiceId;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public String getRoId() {
        return roId;
    }

    public void setRoId(String roId) {
        this.roId = roId;
    }

    public String getDoid() {
        return doid;
    }

    public void setDoid(String doid) {
        this.doid = doid;
    }

    public String getPcoid() {
        return pcoid;
    }

    public void setPcoid(String pcoid) {
        this.pcoid = pcoid;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
}
