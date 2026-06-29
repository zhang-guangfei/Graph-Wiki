package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsInventoryProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author C02483
 * @version 1.0
 * @description: 库存查询返回结果对象
 * @date 2021/10/25 18:36
 */
public class InventoryForOrderOutDto implements Serializable {

    private String inventoryStatus;
    private String warehouseCode;
    private String modelno;
    private String inventoryTypeCode;
    private String customerNo;
    private String ppl;
    private String projectCode;
    private String groupCustomerNo;
    private String orderno;
    private Integer itemno;
    private Integer splititemno;
    private Integer qty;//在库数量
    private Integer preqty;//占用数量

    public InventoryForOrderOutDto() {}

    public InventoryForOrderOutDto(OpsInventoryMove dto, OpsInventoryProperty property) {
        this.inventoryStatus = dto.getInventoryStatus();
        this.warehouseCode = dto.getWarehouseCode();
        this.modelno = dto.getModelno();
        this.inventoryTypeCode = property.getInventoryTypeCode();
        this.customerNo = property.getCustomerNo();
        this.ppl = property.getPpl();
        this.projectCode = property.getProjectCode();
        this.groupCustomerNo = property.getGroupCustomerNo();
        this.orderno = dto.getOrderno();
        this.itemno = dto.getItemno();
        //this.splititemno = dto.getSplititemno();
        this.qty=dto.getQuantity();
        this.preqty=dto.getPrepareQuantity();
    }

    public InventoryForOrderOutDto(OpsInventory dto, OpsInventoryProperty property) {
        this.inventoryStatus = dto.getInventoryStatus();
        this.warehouseCode = dto.getWarehouseCode();
        this.modelno = dto.getModelno();
        this.inventoryTypeCode = property.getInventoryTypeCode();
        this.customerNo = property.getCustomerNo();
        this.ppl = property.getPpl();
        this.projectCode = property.getProjectCode();
        this.groupCustomerNo = property.getGroupCustomerNo();
        this.qty=dto.getQuantity();
        this.preqty=dto.getPrepareQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryForOrderOutDto that = (InventoryForOrderOutDto) o;
        return Objects.equals(inventoryStatus, that.inventoryStatus) &&
                Objects.equals(warehouseCode, that.warehouseCode) &&
                Objects.equals(modelno, that.modelno) &&
                Objects.equals(inventoryTypeCode, that.inventoryTypeCode) &&
                Objects.equals(customerNo, that.customerNo) &&
                Objects.equals(ppl, that.ppl) &&
                Objects.equals(projectCode, that.projectCode) &&
                Objects.equals(groupCustomerNo, that.groupCustomerNo) &&
                Objects.equals(itemno, that.itemno) &&
                Objects.equals(splititemno, that.splititemno) &&
                Objects.equals(orderno, that.orderno);
    }

    @Override
    public int hashCode() {

        return Objects.hash(inventoryStatus, warehouseCode, modelno, inventoryTypeCode, customerNo, ppl, projectCode, groupCustomerNo, orderno,itemno,splititemno);
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
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

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public void addQty(int qty) {
        this.qty = this.qty + preqty;
    }

    public Integer getPreqty() {
        return preqty;
    }

    public void addPreqty(int preqty) {
        this.preqty = this.preqty + preqty;
    }

    public void setPreqty(Integer preqty) {
        this.preqty = preqty;
    }
}
