package com.sales.ops.dto.inventory;

import com.sales.ops.enums.InventoryTypeEnum;

/**
 * @author C12961
 * @date 2023/4/7 14:48
 */

public class AdjustItemDTO {

    private AdjustType adjustType;
    private boolean adjustAvailableInventory = true;

    private String id;
    private String orderId;
    private Integer orderItem;
    private String invoiceNo;

    private String modelNo;
    private String warehouseCode;
    private int qty;


    private InventoryTypeEnum propertyType;
    private String customerNo;
    private String groupCustomerNo;
    private String ppl;
    private String projectCode;

    private Integer result;
    private String message;

    public AdjustType getAdjustType() {
        return adjustType;
    }

    public void setAdjustType(AdjustType adjustType) {
        this.adjustType = adjustType;
    }

    public boolean isAdjustAvailableInventory() {
        return adjustAvailableInventory;
    }

    public void setAdjustAvailableInventory(boolean adjustAvailableInventory) {
        this.adjustAvailableInventory = adjustAvailableInventory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public InventoryTypeEnum getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(InventoryTypeEnum propertyType) {
        this.propertyType = propertyType;
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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
