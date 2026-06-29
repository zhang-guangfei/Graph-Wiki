package com.sales.ops.dto.order;

/**
 * @author C12961
 * @date 2022/4/20 15:21
 */
public class StatusParamDto {

    private String doId;
    private Integer doItem;
    private String doType;
    private String invoiceNo;
    private Long invoiceId;
    private Integer quantity;
    private Integer invoiceQty;
    private String date;
    private Integer money;
    private String poNo;
    private Integer poItem;
    private Integer poSplitNo;
    private String warehouseCode;
    private String supplierId;


    public String getDoType() {
        return doType;
    }

    public void setDoType(String doType) {
        this.doType = doType;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public Integer getDoItem() {
        return doItem;
    }

    public void setDoItem(Integer doItem) {
        this.doItem = doItem;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public Integer getPoItem() {
        return poItem;
    }

    public void setPoItem(Integer poItem) {
        this.poItem = poItem;
    }

    public Integer getPoSplitNo() {
        return poSplitNo;
    }

    public void setPoSplitNo(Integer poSplitNo) {
        this.poSplitNo = poSplitNo;
    }

    public Integer getInvoiceQty() {
        return invoiceQty;
    }

    public void setInvoiceQty(Integer invoiceQty) {
        this.invoiceQty = invoiceQty;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
