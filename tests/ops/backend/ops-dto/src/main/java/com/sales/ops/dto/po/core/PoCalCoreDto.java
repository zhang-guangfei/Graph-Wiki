package com.sales.ops.dto.po.core;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/12/15 15:47
 */
public class PoCalCoreDto {

    private Long id;

    private String transId;

    private Integer priority;

    private Integer transDay;

    private Integer wmsOperateDay;

    private Boolean enableInventory;

    private Boolean compareGreater;

    private Boolean enableStdDeliveryDay;

    private String supplier;

    private String warehouse;

    private String poOrigin;

    private Integer conditionJudgmentDays;

    private String inventoryClass;

    private Integer compensationDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getTransDay() {
        return transDay;
    }

    public void setTransDay(Integer transDay) {
        this.transDay = transDay;
    }

    public Integer getWmsOperateDay() {
        return wmsOperateDay;
    }

    public void setWmsOperateDay(Integer wmsOperateDay) {
        this.wmsOperateDay = wmsOperateDay;
    }

    public Boolean getEnableInventory() {
        return enableInventory;
    }

    public void setEnableInventory(Boolean enableInventory) {
        this.enableInventory = enableInventory;
    }

    public Boolean getCompareGreater() {
        return compareGreater;
    }

    public void setCompareGreater(Boolean compareGreater) {
        this.compareGreater = compareGreater;
    }

    public Boolean getEnableStdDeliveryDay() {
        return enableStdDeliveryDay;
    }

    public void setEnableStdDeliveryDay(Boolean enableStdDeliveryDay) {
        this.enableStdDeliveryDay = enableStdDeliveryDay;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getPoOrigin() {
        return poOrigin;
    }

    public void setPoOrigin(String poOrigin) {
        this.poOrigin = poOrigin;
    }

    public Integer getConditionJudgmentDays() {
        return conditionJudgmentDays;
    }

    public void setConditionJudgmentDays(Integer conditionJudgmentDays) {
        this.conditionJudgmentDays = conditionJudgmentDays;
    }

    public String getInventoryClass() {
        return inventoryClass;
    }

    public void setInventoryClass(String inventoryClass) {
        this.inventoryClass = inventoryClass;
    }

    public Integer getCompensationDays() {
        return compensationDays;
    }

    public void setCompensationDays(Integer compensationDays) {
        this.compensationDays = compensationDays;
    }
}
