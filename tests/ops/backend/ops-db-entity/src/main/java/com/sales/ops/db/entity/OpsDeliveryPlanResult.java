package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsDeliveryPlanResult implements Serializable {
    private Long id;

    private String orderId;

    private String orderItem;

    private String doId;

    private Date expectDeliveryDay;

    private Date planDeliveryDay;

    private String eventSource;

    private Long version;

    private Integer currentValid;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private String remark;

    private Integer exRelation;

    private String currentCycle;

    private String currentCyclePoint;

    private Integer reliability;

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

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public Date getExpectDeliveryDay() {
        return expectDeliveryDay;
    }

    public void setExpectDeliveryDay(Date expectDeliveryDay) {
        this.expectDeliveryDay = expectDeliveryDay;
    }

    public Date getPlanDeliveryDay() {
        return planDeliveryDay;
    }

    public void setPlanDeliveryDay(Date planDeliveryDay) {
        this.planDeliveryDay = planDeliveryDay;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource == null ? null : eventSource.trim();
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

    public String getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(String currentCycle) {
        this.currentCycle = currentCycle == null ? null : currentCycle.trim();
    }

    public String getCurrentCyclePoint() {
        return currentCyclePoint;
    }

    public void setCurrentCyclePoint(String currentCyclePoint) {
        this.currentCyclePoint = currentCyclePoint == null ? null : currentCyclePoint.trim();
    }

    public Integer getReliability() {
        return reliability;
    }

    public void setReliability(Integer reliability) {
        this.reliability = reliability;
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
        OpsDeliveryPlanResult other = (OpsDeliveryPlanResult) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getExpectDeliveryDay() == null ? other.getExpectDeliveryDay() == null : this.getExpectDeliveryDay().equals(other.getExpectDeliveryDay()))
            && (this.getPlanDeliveryDay() == null ? other.getPlanDeliveryDay() == null : this.getPlanDeliveryDay().equals(other.getPlanDeliveryDay()))
            && (this.getEventSource() == null ? other.getEventSource() == null : this.getEventSource().equals(other.getEventSource()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getCurrentValid() == null ? other.getCurrentValid() == null : this.getCurrentValid().equals(other.getCurrentValid()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getExRelation() == null ? other.getExRelation() == null : this.getExRelation().equals(other.getExRelation()))
            && (this.getCurrentCycle() == null ? other.getCurrentCycle() == null : this.getCurrentCycle().equals(other.getCurrentCycle()))
            && (this.getCurrentCyclePoint() == null ? other.getCurrentCyclePoint() == null : this.getCurrentCyclePoint().equals(other.getCurrentCyclePoint()))
            && (this.getReliability() == null ? other.getReliability() == null : this.getReliability().equals(other.getReliability()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getExpectDeliveryDay() == null) ? 0 : getExpectDeliveryDay().hashCode());
        result = prime * result + ((getPlanDeliveryDay() == null) ? 0 : getPlanDeliveryDay().hashCode());
        result = prime * result + ((getEventSource() == null) ? 0 : getEventSource().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getCurrentValid() == null) ? 0 : getCurrentValid().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getExRelation() == null) ? 0 : getExRelation().hashCode());
        result = prime * result + ((getCurrentCycle() == null) ? 0 : getCurrentCycle().hashCode());
        result = prime * result + ((getCurrentCyclePoint() == null) ? 0 : getCurrentCyclePoint().hashCode());
        result = prime * result + ((getReliability() == null) ? 0 : getReliability().hashCode());
        return result;
    }
}