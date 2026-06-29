package com.sales.ops.serviceimpl.dispatch.rodispatch.domain;

import com.sales.ops.db.entity.OpsRoBarcode;
import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.dto.inventory.CreInvMoveForReturnOrderDto;
import com.sales.ops.dto.inventory.OpsRoDto;

import java.util.Optional;


public class ReturnConfirmContextItem {

    private String orderNo;
    private int orderItem;
    private int splitItemNo;
    private String invoiceNo;
    private Long invoiceId;

    private String applyNo;
    private Integer itemNo;
    private String modelNo;
    private Integer qty;
    private String psnNo;
    private String warehouseCode;
    private String customerNo;
    private Boolean toUserStock;
    private Long returnMoveId;


    public ReturnConfirmContextItem(CreInvMoveForReturnOrderDto inputDto,ReturnConfirmContext context) {
        this.orderNo = inputDto.getOrderNo();
        this.orderItem = inputDto.getOrderItem();
        this.splitItemNo = Optional.ofNullable(inputDto.getSplitItemNo()).orElse(0);
        this.invoiceNo = context.getInvoiceNo();
        this.invoiceId = context.getInvoiceId();
        this.applyNo = inputDto.getApplyNo();
        this.itemNo = inputDto.getItemNo();
        this.modelNo = inputDto.getModelNo();
        this.qty = inputDto.getQty();
        this.psnNo = inputDto.getPsnNo();
        this.warehouseCode = inputDto.getWarehouseCode();
        this.customerNo = inputDto.getCustomerNo();
        this.toUserStock = inputDto.getToUserStock();
    }


    private OpsRoDto roDto;
    private OpsRoBarcode barcode;
    private OpsWmOrderTask roTask;
    private OpsWmOrderTask barcodeTask;


    public void setTasks(OpsWmOrderTask roTask, OpsWmOrderTask barcodeTask) {
        this.roTask = roTask;
        this.barcodeTask = barcodeTask;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(int orderItem) {
        this.orderItem = orderItem;
    }

    public int getSplitItemNo() {
        return splitItemNo;
    }

    public void setSplitItemNo(int splitItemNo) {
        this.splitItemNo = splitItemNo;
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

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getPsnNo() {
        return psnNo;
    }

    public void setPsnNo(String psnNo) {
        this.psnNo = psnNo;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Boolean getToUserStock() {
        return toUserStock;
    }

    public void setToUserStock(Boolean toUserStock) {
        this.toUserStock = toUserStock;
    }

    public Long getReturnMoveId() {
        return returnMoveId;
    }

    public void setReturnMoveId(Long returnMoveId) {
        this.returnMoveId = returnMoveId;
    }

    public OpsRoDto getRoDto() {
        return roDto;
    }

    public void setRoDto(OpsRoDto roDto) {
        this.roDto = roDto;
    }

    public OpsRoBarcode getBarcode() {
        return barcode;
    }

    public void setBarcode(OpsRoBarcode barcode) {
        this.barcode = barcode;
    }

    public OpsWmOrderTask getRoTask() {
        return roTask;
    }

    public void setRoTask(OpsWmOrderTask roTask) {
        this.roTask = roTask;
    }

    public OpsWmOrderTask getBarcodeTask() {
        return barcodeTask;
    }

    public void setBarcodeTask(OpsWmOrderTask barcodeTask) {
        this.barcodeTask = barcodeTask;
    }
}
