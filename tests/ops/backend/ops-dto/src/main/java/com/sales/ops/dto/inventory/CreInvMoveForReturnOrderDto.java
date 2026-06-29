package com.sales.ops.dto.inventory;


import java.util.Date;

/**
 * 生成库存在途库存按退货数据生成
 *
 * @author wsf
 * @version 1.0
 * @date 2022/5/17 13:34
 */

public class CreInvMoveForReturnOrderDto {

    private String applyNo;

    private String customerNo;

    private Integer itemNo;

    private String modelNo;

    private Integer qty;

    private String warehouseCode;

    private String psnNo;

    private String orderNo;

    private Integer orderItem;

    private Integer splitItemNo;

    /**
     * 是否退回用户专备
     */
    private Boolean toUserStock;

    public Boolean getToUserStock() {
        return toUserStock;
    }

    public void setToUserStock(Boolean toUserStock) {
        this.toUserStock = toUserStock;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Integer getSplitItemNo() {
        return splitItemNo;
    }

    public void setSplitItemNo(Integer splitItemNo) {
        this.splitItemNo = splitItemNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
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

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getPsnNo() {
        return psnNo;
    }

    public void setPsnNo(String psnNo) {
        this.psnNo = psnNo;
    }

}
