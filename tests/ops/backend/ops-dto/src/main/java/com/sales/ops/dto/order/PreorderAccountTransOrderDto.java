package com.sales.ops.dto.order;

import com.sales.ops.db.entity.TransOrder;

import java.io.Serializable;
import java.util.Date;


public class PreorderAccountTransOrderDto implements Serializable {

    // 调库单号
    private String transNo;
    // 项号
    private Integer itemNo;

    // 来源类型:来源类型 1-bin补库，2-调库申请，3先行在库申请，4委托在库，5先行在库决算
    private Integer fromType;

    // 来源单号:先行在库申请单号
    private String fromNo;
    // 空
    private Long fromId;

    // 型号
    private String modelNo;
    // 希望调库数量
    private Integer quantity;
    // 初始化，传值0
    private Integer status;

    private Long fromInventoryId;
    private String fromWarehouseCode;

    private Long fromInventoryPropertyId;
    private String fromInventoryTypeCode;
    private String fromCustomerNo;
    private String fromGroupCustomerNo;
    private String fromPpl;
    private String fromProjectCode;
    private String fromSalesInfoNo;

    private String toWarehouseCode;

    private Long toInventoryPropertyId;
    private String toInventoryTypeCode;
    private String toCustomerNo;
    private String toGroupCustomerNo;
    private String toPpl;
    private String toProjectCode;
    private String toSalesInfoNo;



    public TransOrder toTransOrder(){
        TransOrder transOrder = new TransOrder();
        transOrder.setId(null);
        transOrder.setTransType(1);
        transOrder.setTransNo(this.transNo);
        transOrder.setItemNo(this.itemNo);
        transOrder.setModelNo(this.modelNo);
        transOrder.setQuantity(this.quantity);
        transOrder.setStatus(this.status);
        transOrder.setFromNo(this.fromNo);
        transOrder.setFromId(this.fromId);
        transOrder.setFromType(this.fromType);
        transOrder.setFromInventoryPropertyId(this.fromInventoryPropertyId);
        transOrder.setFromWarehouseCode(this.fromWarehouseCode);
        transOrder.setFromInventoryTypeCode(this.fromInventoryTypeCode);
        transOrder.setFromPpl(this.fromPpl);
        transOrder.setFromProjectCode(this.fromProjectCode);
        transOrder.setFromGroupCustomerNo(this.fromGroupCustomerNo);
        transOrder.setFromSalesInfoNo(this.fromSalesInfoNo);
        transOrder.setFromCustomerNo(this.fromCustomerNo);
        transOrder.setToInventoryPropertyId(this.toInventoryPropertyId);
        transOrder.setToWarehouseCode(this.toWarehouseCode);
        transOrder.setToInventoryTypeCode(this.toInventoryTypeCode);
        transOrder.setToPpl(this.toPpl);
        transOrder.setToProjectCode(this.toProjectCode);
        transOrder.setToGroupCustomerNo(this.toGroupCustomerNo);
        transOrder.setToSalesInfoNo(this.toSalesInfoNo);
        transOrder.setToCustomerNo(this.toCustomerNo);
        transOrder.setInQuantity(null);
        transOrder.setWmsDlvDate(null);
        transOrder.setFinishTime(null);
        transOrder.setCreateTime(new Date());
        transOrder.setUpdateTime(new Date());
        transOrder.setCreateUser(null);
        transOrder.setUpdateUser(null);
        transOrder.setInvoiceNo(null);
        transOrder.setShipQty(null);
        transOrder.setShipDate(null);
        transOrder.setTransFlag(null);
        transOrder.setFromInventoryId(this.fromInventoryId);
        transOrder.setFromAssociateNo(null);
        transOrder.setFromAssociateNoItem(null);
        transOrder.setFromAssociateNoSplit(null);
        transOrder.setInvoiceId(null);
        return transOrder;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getFromInventoryId() {
        return fromInventoryId;
    }

    public void setFromInventoryId(Long fromInventoryId) {
        this.fromInventoryId = fromInventoryId;
    }

    public String getFromWarehouseCode() {
        return fromWarehouseCode;
    }

    public void setFromWarehouseCode(String fromWarehouseCode) {
        this.fromWarehouseCode = fromWarehouseCode;
    }

    public Long getFromInventoryPropertyId() {
        return fromInventoryPropertyId;
    }

    public void setFromInventoryPropertyId(Long fromInventoryPropertyId) {
        this.fromInventoryPropertyId = fromInventoryPropertyId;
    }

    public String getFromInventoryTypeCode() {
        return fromInventoryTypeCode;
    }

    public void setFromInventoryTypeCode(String fromInventoryTypeCode) {
        this.fromInventoryTypeCode = fromInventoryTypeCode;
    }

    public String getFromCustomerNo() {
        return fromCustomerNo;
    }

    public void setFromCustomerNo(String fromCustomerNo) {
        this.fromCustomerNo = fromCustomerNo;
    }

    public String getFromGroupCustomerNo() {
        return fromGroupCustomerNo;
    }

    public void setFromGroupCustomerNo(String fromGroupCustomerNo) {
        this.fromGroupCustomerNo = fromGroupCustomerNo;
    }

    public String getFromPpl() {
        return fromPpl;
    }

    public void setFromPpl(String fromPpl) {
        this.fromPpl = fromPpl;
    }

    public String getFromProjectCode() {
        return fromProjectCode;
    }

    public void setFromProjectCode(String fromProjectCode) {
        this.fromProjectCode = fromProjectCode;
    }

    public String getFromSalesInfoNo() {
        return fromSalesInfoNo;
    }

    public void setFromSalesInfoNo(String fromSalesInfoNo) {
        this.fromSalesInfoNo = fromSalesInfoNo;
    }

    public String getToWarehouseCode() {
        return toWarehouseCode;
    }

    public void setToWarehouseCode(String toWarehouseCode) {
        this.toWarehouseCode = toWarehouseCode;
    }

    public Long getToInventoryPropertyId() {
        return toInventoryPropertyId;
    }

    public void setToInventoryPropertyId(Long toInventoryPropertyId) {
        this.toInventoryPropertyId = toInventoryPropertyId;
    }

    public String getToInventoryTypeCode() {
        return toInventoryTypeCode;
    }

    public void setToInventoryTypeCode(String toInventoryTypeCode) {
        this.toInventoryTypeCode = toInventoryTypeCode;
    }

    public String getToCustomerNo() {
        return toCustomerNo;
    }

    public void setToCustomerNo(String toCustomerNo) {
        this.toCustomerNo = toCustomerNo;
    }

    public String getToGroupCustomerNo() {
        return toGroupCustomerNo;
    }

    public void setToGroupCustomerNo(String toGroupCustomerNo) {
        this.toGroupCustomerNo = toGroupCustomerNo;
    }

    public String getToPpl() {
        return toPpl;
    }

    public void setToPpl(String toPpl) {
        this.toPpl = toPpl;
    }

    public String getToProjectCode() {
        return toProjectCode;
    }

    public void setToProjectCode(String toProjectCode) {
        this.toProjectCode = toProjectCode;
    }

    public String getToSalesInfoNo() {
        return toSalesInfoNo;
    }

    public void setToSalesInfoNo(String toSalesInfoNo) {
        this.toSalesInfoNo = toSalesInfoNo;
    }
}
