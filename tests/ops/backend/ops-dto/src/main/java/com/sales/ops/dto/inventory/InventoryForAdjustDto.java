package com.sales.ops.dto.inventory;


import java.io.Serializable;
import java.util.Date;

/**
 * @author C02483
 * @version 1.0
 * @description: 调账单
 * @date 2021/10/15 13:40
 */
public class InventoryForAdjustDto implements Serializable {


    private String orderId;
    private Integer orderItem;
    private String modelno;
    private String deptno;
    private Integer qty;
    private Long   price;
    private String qaStatus;

    private Long inventoryId;
    private String warehouseCode;
    private String customerNo;
    private String ppl;
    private String projectCode;
    private String groupCustomerNo;
    private String salesInfoNo;
    private Date expDate;

    private String remark;

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getQaStatus() {
        return qaStatus;
    }

    public void setQaStatus(String qaStatus) {
        this.qaStatus = qaStatus;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "InventoryForAdjustDto{" +
                "warehouseCode='" + warehouseCode + '\'' +
                ", qaStatus='" + qaStatus + '\'' +
                ", modelno='" + modelno + '\'' +
                ", price=" + price +
                ", customerNo='" + customerNo + '\'' +
                ", ppl='" + ppl + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", groupCustomerNo='" + groupCustomerNo + '\'' +
                ", salesInfoNo='" + salesInfoNo + '\'' +
                ", qty=" + qty +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }
}
