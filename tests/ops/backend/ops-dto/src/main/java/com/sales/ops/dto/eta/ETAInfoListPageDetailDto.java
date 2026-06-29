package com.sales.ops.dto.eta;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/12/6 15:49
 */
public class ETAInfoListPageDetailDto implements Serializable {

    private static final long serialVersionUID = -558519702331506534L;

    private Integer rowspan = 0;

    private Integer colspan = 0;

    private Integer colorFlag = 1;

    private boolean cgFlag = false;

    private Integer index;

    private String modelNO;

    private Integer quantity;//计算数量

    //标准参考货期
    private Integer deliveryDays;

    //出库区分 采购【JP】 在库【KBJ】
    private String isStockInfo;

    // 供应商库存数
    private Integer supplierInventory;
    // 生产天数
    private Integer produceDay;
    // 运输方式名称
    private String transportWayName;

    // 运输天数
    private Integer transportDay;

    //采购收货天数
    private Integer roDays = 1;

    //调拨天数
    private Integer dbDays = 0;

    //送到天数
    private Integer warehouseToDeptDays;

    //采购 计算详情 数据来源
    private String calDesc;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getModelNO() {
        return modelNO;
    }

    public void setModelNO(String modelNO) {
        this.modelNO = modelNO;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(Integer deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public String getIsStockInfo() {
        return isStockInfo;
    }

    public void setIsStockInfo(String isStockInfo) {
        this.isStockInfo = isStockInfo;
    }

    public Integer getSupplierInventory() {
        return supplierInventory;
    }

    public void setSupplierInventory(Integer supplierInventory) {
        this.supplierInventory = supplierInventory;
    }

    public Integer getProduceDay() {
        return produceDay;
    }

    public void setProduceDay(Integer produceDay) {
        this.produceDay = produceDay;
    }

    public String getTransportWayName() {
        return transportWayName;
    }

    public void setTransportWayName(String transportWayName) {
        this.transportWayName = transportWayName;
    }

    public Integer getTransportDay() {
        return transportDay;
    }

    public void setTransportDay(Integer transportDay) {
        this.transportDay = transportDay;
    }

    public Integer getRoDays() {
        return roDays;
    }

    public void setRoDays(Integer roDays) {
        this.roDays = roDays;
    }

    public Integer getDbDays() {
        return dbDays;
    }

    public void setDbDays(Integer dbDays) {
        this.dbDays = dbDays;
    }

    public Integer getWarehouseToDeptDays() {
        return warehouseToDeptDays;
    }

    public void setWarehouseToDeptDays(Integer warehouseToDeptDays) {
        this.warehouseToDeptDays = warehouseToDeptDays;
    }

    public Integer getRowspan() {
        return rowspan;
    }

    public void setRowspan(Integer rowspan) {
        this.rowspan = rowspan;
    }

    public Integer getColspan() {
        return colspan;
    }

    public void setColspan(Integer colspan) {
        this.colspan = colspan;
    }

    public Integer getColorFlag() {
        return colorFlag;
    }

    public void setColorFlag(Integer colorFlag) {
        this.colorFlag = colorFlag;
    }

    public boolean isCgFlag() {
        return cgFlag;
    }

    public void setCgFlag(boolean cgFlag) {
        this.cgFlag = cgFlag;
    }

    public String getCalDesc() {
        return calDesc;
    }

    public void setCalDesc(String calDesc) {
        this.calDesc = calDesc;
    }
}
