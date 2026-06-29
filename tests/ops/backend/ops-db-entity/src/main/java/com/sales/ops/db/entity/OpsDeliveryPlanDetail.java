package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsDeliveryPlanDetail implements Serializable {
    private Long id;

    private String orderId;

    private String orderItem;

    private String poNo;

    private Integer poNoItem;

    private Integer poNoSplitNo;

    private String doSource;

    private String doId;

    private String pcoId;

    private String dbId;

    private String modelNo;

    private Integer quantity;

    private Long inventoryId;

    private String inventoryStatus;

    private String delivery;

    private String receive;

    private String eventSource;

    private Integer sort;

    private String currentCycle;

    private String cycleName;

    private String beginName;

    private Date beginDate;

    private Date endDate;

    private Integer cycleDays;

    private Integer workDay;

    private Long version;

    private Integer currentValid;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private String remark;

    private Integer exRelation;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem == null ? null : orderItem.trim();
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo == null ? null : poNo.trim();
    }

    public Integer getPoNoItem() {
        return poNoItem;
    }

    public void setPoNoItem(Integer poNoItem) {
        this.poNoItem = poNoItem;
    }

    public Integer getPoNoSplitNo() {
        return poNoSplitNo;
    }

    public void setPoNoSplitNo(Integer poNoSplitNo) {
        this.poNoSplitNo = poNoSplitNo;
    }

    public String getDoSource() {
        return doSource;
    }

    public void setDoSource(String doSource) {
        this.doSource = doSource == null ? null : doSource.trim();
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId == null ? null : pcoId.trim();
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId == null ? null : dbId.trim();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus == null ? null : inventoryStatus.trim();
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery == null ? null : delivery.trim();
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive == null ? null : receive.trim();
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource == null ? null : eventSource.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(String currentCycle) {
        this.currentCycle = currentCycle == null ? null : currentCycle.trim();
    }

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName == null ? null : cycleName.trim();
    }

    public String getBeginName() {
        return beginName;
    }

    public void setBeginName(String beginName) {
        this.beginName = beginName == null ? null : beginName.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCycleDays() {
        return cycleDays;
    }

    public void setCycleDays(Integer cycleDays) {
        this.cycleDays = cycleDays;
    }

    public Integer getWorkDay() {
        return workDay;
    }

    public void setWorkDay(Integer workDay) {
        this.workDay = workDay;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getCurrentValid() {
        return currentValid;
    }

    public void setCurrentValid(Integer currentValid) {
        this.currentValid = currentValid;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getExRelation() {
        return exRelation;
    }

    public void setExRelation(Integer exRelation) {
        this.exRelation = exRelation;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OpsDeliveryPlanDetail other = (OpsDeliveryPlanDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getPoNo() == null ? other.getPoNo() == null : this.getPoNo().equals(other.getPoNo()))
            && (this.getPoNoItem() == null ? other.getPoNoItem() == null : this.getPoNoItem().equals(other.getPoNoItem()))
            && (this.getPoNoSplitNo() == null ? other.getPoNoSplitNo() == null : this.getPoNoSplitNo().equals(other.getPoNoSplitNo()))
            && (this.getDoSource() == null ? other.getDoSource() == null : this.getDoSource().equals(other.getDoSource()))
            && (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getPcoId() == null ? other.getPcoId() == null : this.getPcoId().equals(other.getPcoId()))
            && (this.getDbId() == null ? other.getDbId() == null : this.getDbId().equals(other.getDbId()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getInventoryStatus() == null ? other.getInventoryStatus() == null : this.getInventoryStatus().equals(other.getInventoryStatus()))
            && (this.getDelivery() == null ? other.getDelivery() == null : this.getDelivery().equals(other.getDelivery()))
            && (this.getReceive() == null ? other.getReceive() == null : this.getReceive().equals(other.getReceive()))
            && (this.getEventSource() == null ? other.getEventSource() == null : this.getEventSource().equals(other.getEventSource()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getCurrentCycle() == null ? other.getCurrentCycle() == null : this.getCurrentCycle().equals(other.getCurrentCycle()))
            && (this.getCycleName() == null ? other.getCycleName() == null : this.getCycleName().equals(other.getCycleName()))
            && (this.getBeginName() == null ? other.getBeginName() == null : this.getBeginName().equals(other.getBeginName()))
            && (this.getBeginDate() == null ? other.getBeginDate() == null : this.getBeginDate().equals(other.getBeginDate()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (this.getCycleDays() == null ? other.getCycleDays() == null : this.getCycleDays().equals(other.getCycleDays()))
            && (this.getWorkDay() == null ? other.getWorkDay() == null : this.getWorkDay().equals(other.getWorkDay()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getCurrentValid() == null ? other.getCurrentValid() == null : this.getCurrentValid().equals(other.getCurrentValid()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getExRelation() == null ? other.getExRelation() == null : this.getExRelation().equals(other.getExRelation()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getPoNo() == null) ? 0 : getPoNo().hashCode());
        result = prime * result + ((getPoNoItem() == null) ? 0 : getPoNoItem().hashCode());
        result = prime * result + ((getPoNoSplitNo() == null) ? 0 : getPoNoSplitNo().hashCode());
        result = prime * result + ((getDoSource() == null) ? 0 : getDoSource().hashCode());
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getPcoId() == null) ? 0 : getPcoId().hashCode());
        result = prime * result + ((getDbId() == null) ? 0 : getDbId().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getInventoryStatus() == null) ? 0 : getInventoryStatus().hashCode());
        result = prime * result + ((getDelivery() == null) ? 0 : getDelivery().hashCode());
        result = prime * result + ((getReceive() == null) ? 0 : getReceive().hashCode());
        result = prime * result + ((getEventSource() == null) ? 0 : getEventSource().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getCurrentCycle() == null) ? 0 : getCurrentCycle().hashCode());
        result = prime * result + ((getCycleName() == null) ? 0 : getCycleName().hashCode());
        result = prime * result + ((getBeginName() == null) ? 0 : getBeginName().hashCode());
        result = prime * result + ((getBeginDate() == null) ? 0 : getBeginDate().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + ((getCycleDays() == null) ? 0 : getCycleDays().hashCode());
        result = prime * result + ((getWorkDay() == null) ? 0 : getWorkDay().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getCurrentValid() == null) ? 0 : getCurrentValid().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getExRelation() == null) ? 0 : getExRelation().hashCode());
        return result;
    }
}