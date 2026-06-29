package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsDeliveryPlanReliabilityConfig implements Serializable {
    private Long id;

    private String currentCycle;

    private String currentCyclePoint;

    private Integer reliability;

    private Date creTime;

    private String creator;

    private Integer delflag;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        OpsDeliveryPlanReliabilityConfig other = (OpsDeliveryPlanReliabilityConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCurrentCycle() == null ? other.getCurrentCycle() == null : this.getCurrentCycle().equals(other.getCurrentCycle()))
            && (this.getCurrentCyclePoint() == null ? other.getCurrentCyclePoint() == null : this.getCurrentCyclePoint().equals(other.getCurrentCyclePoint()))
            && (this.getReliability() == null ? other.getReliability() == null : this.getReliability().equals(other.getReliability()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCurrentCycle() == null) ? 0 : getCurrentCycle().hashCode());
        result = prime * result + ((getCurrentCyclePoint() == null) ? 0 : getCurrentCyclePoint().hashCode());
        result = prime * result + ((getReliability() == null) ? 0 : getReliability().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}