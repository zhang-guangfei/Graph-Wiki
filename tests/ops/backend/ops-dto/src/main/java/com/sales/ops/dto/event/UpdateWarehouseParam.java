package com.sales.ops.dto.event;

/**
 * @author C12961
 * @date 2023/2/27 17:12
 */

public class UpdateWarehouseParam {
    private String doId;
    private String warehouse;
    private String doSource;
    private Integer qtyIn;

    public UpdateWarehouseParam(String doId, String doSource, String warehouse, Integer qtyIn) {
        this.doId = doId;
        this.doSource = doSource;
        this.warehouse = warehouse;
        this.qtyIn = qtyIn;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getDoSource() {
        return doSource;
    }

    public void setDoSource(String doSource) {
        this.doSource = doSource;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getQtyIn() {
        return qtyIn;
    }

    public void setQtyIn(Integer qtyIn) {
        this.qtyIn = qtyIn;
    }
}
