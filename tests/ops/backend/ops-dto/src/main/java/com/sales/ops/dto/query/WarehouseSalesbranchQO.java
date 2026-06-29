package com.sales.ops.dto.query;

import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2022/2/11 9:19
 */
public class WarehouseSalesbranchQO {

    private List<String> salesBranchId;

    private String warehouseCode;

    private Integer priority;

    private String description;

    private Integer deliveryDay;

    public List<String> getSalesBranchId() {
        return salesBranchId;
    }

    public void setSalesBranchId(List<String> salesBranchId) {
        this.salesBranchId = salesBranchId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(Integer deliveryDay) {
        this.deliveryDay = deliveryDay;
    }
}
