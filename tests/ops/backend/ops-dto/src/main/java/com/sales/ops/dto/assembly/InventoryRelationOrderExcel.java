package com.sales.ops.dto.assembly;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.util.Date;

public class InventoryRelationOrderExcel implements Serializable {
    @ExcelIgnore
    private Long inventoryId;
    @ExcelProperty("仓库代码")
    private String warehouseCode;
    @ExcelProperty("库存类别")
    private String inventoryTypeCode;
    @ExcelProperty("型号")
    private String modelno;
    @ExcelProperty("客户代码")
    private String customerNo;
    @ExcelProperty("客户群代码")
    private String groupCustomerNo;
    @ExcelProperty("PPL")
    private String ppl;
    @ExcelProperty("PJ")
    private String projectCode;
    @ExcelProperty("情报号")
    private String salesInfoNo;
    @ExcelProperty("营业所")
    private String deptCode;
    @ExcelProperty("库存状态")
    private String inventoryStatus;
    @ExcelProperty("库存数量")
    private Integer invQty;
    @ExcelProperty("库存预占数量")
    private Integer invPreQty;
    @ExcelIgnore
    private String doId;
    @ExcelIgnore
    private String orderId;
    @ExcelIgnore
    private String orderItem;
    @ExcelIgnore
    private Integer num;
    @ExcelIgnore
    private Integer assNum;
    @ExcelIgnore
    private String doSource;
    @ExcelProperty("拆分订单号")
    private String orderFno;
    @ExcelIgnore
    private String doType;
    @ExcelProperty("拆分型号")
    private String splitModelno;
    @ExcelProperty("订单拆分数量")
    private Integer doQty;
    @ExcelProperty("订单预占数量")
    private Integer doUseQty;
    @ExcelProperty("订货日期")
    private Date orderDate;
    @ExcelProperty("交货日期")
    private Date hopeDate;
    @ExcelProperty("客户代码")
    private String customer;
    @ExcelProperty("用户代码")
    private String userNo;
    @ExcelProperty("营业所")
    private String deptNo;
    @ExcelProperty("营业部")
    private String salesDeptNo;
    @ExcelProperty("出库区分")
    private String stockInfo;
    @ExcelProperty("发货仓")
    private String gatherWarehouseCode;
    @ExcelProperty("拆分订单详细状态")
    private String status;
    @ExcelProperty("指定物流出库日期")
    private Date wlDate;
    @ExcelProperty("出货方式")
    private String dlvEntire;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
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

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public Integer getInvQty() {
        return invQty;
    }

    public void setInvQty(Integer invQty) {
        this.invQty = invQty;
    }

    public Integer getInvPreQty() {
        return invPreQty;
    }

    public void setInvPreQty(Integer invPreQty) {
        this.invPreQty = invPreQty;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAssNum() {
        return assNum;
    }

    public void setAssNum(Integer assNum) {
        this.assNum = assNum;
    }

    public String getDoSource() {
        return doSource;
    }

    public void setDoSource(String doSource) {
        this.doSource = doSource;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public String getDoType() {
        return doType;
    }

    public void setDoType(String doType) {
        this.doType = doType;
    }

    public String getSplitModelno() {
        return splitModelno;
    }

    public void setSplitModelno(String splitModelno) {
        this.splitModelno = splitModelno;
    }

    public Integer getDoQty() {
        return doQty;
    }

    public void setDoQty(Integer doQty) {
        this.doQty = doQty;
    }

    public Integer getDoUseQty() {
        return doUseQty;
    }

    public void setDoUseQty(Integer doUseQty) {
        this.doUseQty = doUseQty;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getHopeDate() {
        return hopeDate;
    }

    public void setHopeDate(Date hopeDate) {
        this.hopeDate = hopeDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getSalesDeptNo() {
        return salesDeptNo;
    }

    public void setSalesDeptNo(String salesDeptNo) {
        this.salesDeptNo = salesDeptNo;
    }

    public String getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(String stockInfo) {
        this.stockInfo = stockInfo;
    }

    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getWlDate() {
        return wlDate;
    }

    public void setWlDate(Date wlDate) {
        this.wlDate = wlDate;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire;
    }
}