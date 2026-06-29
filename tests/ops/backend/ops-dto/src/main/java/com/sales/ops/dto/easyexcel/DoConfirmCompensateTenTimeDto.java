package com.sales.ops.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：doconfirm异常补偿定时任务大于10次实体
 * @date ：Created in 2023/1/9 16:08
 */
public class DoConfirmCompensateTenTimeDto implements Serializable {
    private static final long serialVersionUID = -88363316524604499L;

    @ExcelProperty("订单号")
    private String orderNo;

    @ExcelProperty("订单项号")
    private String orderItem;

    @ExcelProperty("doId")
    private String doid;

    @ExcelProperty("数量")
    private String qty;

    @ExcelProperty("仓库号")
    private String warehouseCode;

    @ExcelProperty("inventoryId")
    private String inventoryId;

    @ExcelProperty("inventoryTableType")
    private String inventoryTableType;

    @ExcelProperty("pcoId")
    private String pcoId;

    @ExcelProperty("pcoItem")
    private String pcoItem;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public String getDoid() {
        return doid;
    }

    public void setDoid(String doid) {
        this.doid = doid;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryTableType() {
        return inventoryTableType;
    }

    public void setInventoryTableType(String inventoryTableType) {
        this.inventoryTableType = inventoryTableType;
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId;
    }

    public String getPcoItem() {
        return pcoItem;
    }

    public void setPcoItem(String pcoItem) {
        this.pcoItem = pcoItem;
    }
}
