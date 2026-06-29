package com.smc.ops.delivery.model.branch;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/12/29 10:28
 */
public class BranchInvTransDo implements Serializable {
    private static final long serialVersionUID = -1694107479165501008L;

    private String companyId;

    private String modelNo;

    private String warehouseCode;

    private String inventoryType; //jyck

    private String voucherCode; // doid

    private Integer quantity;

    private String fromInventoryId;

    private String orderNo;

    private Integer orderItem;

    private Date shipTime;

    public BranchInvTransDo(){}

    public BranchInvTransDo(String companyId, String modelNo, String warehouseCode, String voucherCode, Integer quantity,
                            String fromInventoryId,String orderNo,Integer orderItem,Date shipTime,String inventoryType){
        this.companyId = companyId;
        this.modelNo = modelNo;
        this.warehouseCode = warehouseCode;
        this.voucherCode = voucherCode;
        this.inventoryType = inventoryType;
        this.quantity = quantity * -1;
        this.fromInventoryId = fromInventoryId;
        this.orderNo = orderNo;
        this.orderItem = orderItem;
        this.shipTime = shipTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getFromInventoryId() {
        return fromInventoryId;
    }

    public void setFromInventoryId(String fromInventoryId) {
        this.fromInventoryId = fromInventoryId;
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

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }
}
