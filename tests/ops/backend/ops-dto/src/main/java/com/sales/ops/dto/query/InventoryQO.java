package com.sales.ops.dto.query;

import java.util.Date;

/**
 * @description
 * @date 2021/10/1 17:54
 * @auther C12961
 */

public class InventoryQO {

    private Long inventoryId;

    //库存表类别： 在库、在途
    private String inventoryStatus;
    private String modelno;
    //数量是否为空
    private Boolean available;
    //模糊查询
    private Boolean fuzzy;

    private String warehouseCode;
    private String warehouseName;
    private String warehouseType;

    //批属性ID
    private Long inventoryPropertyId;
    //库存分类：顾客在库，战略在库、通用在库
    private String inventoryTypeCode;
    private String customerNo;
    private String ppl;
    private String projectCode;
    private String groupCustomerNo;
    private String salesInfoNo;


    private String unit;
    private String qaStatus;
    private String po;
    private Long price;
    private Date inTime;

    private Integer rflag;// 风险在库标识

    public Integer getRflag() {
        return rflag;
    }

    public void setRflag(Integer rflag) {
        this.rflag = rflag;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }


    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType;
    }

    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
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

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQaStatus() {
        return qaStatus;
    }

    public void setQaStatus(String qaStatus) {
        this.qaStatus = qaStatus;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Boolean getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(Boolean fuzzy) {
        this.fuzzy = fuzzy;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo;
    }

    @Override
    public String toString() {
        return "InventoryQO{" +
                "inventoryId=" + inventoryId +
                ", inventoryStatus='" + inventoryStatus + '\'' +
                ", modelno='" + modelno + '\'' +
                ", available=" + available +
                ", fuzzy=" + fuzzy +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", warehouseType='" + warehouseType + '\'' +
                ", inventoryPropertyId=" + inventoryPropertyId +
                ", inventoryTypeCode='" + inventoryTypeCode + '\'' +
                ", customerNo='" + customerNo + '\'' +
                ", ppl='" + ppl + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", groupCustomerNo='" + groupCustomerNo + '\'' +
                ", salesInfoNo='" + salesInfoNo + '\'' +
                ", unit='" + unit + '\'' +
                ", qaStatus='" + qaStatus + '\'' +
                ", po='" + po + '\'' +
                ", price=" + price +
                ", inTime=" + inTime +
                '}';
    }
}
