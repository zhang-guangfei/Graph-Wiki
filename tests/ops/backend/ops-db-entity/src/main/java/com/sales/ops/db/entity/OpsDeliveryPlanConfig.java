package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsDeliveryPlanConfig implements Serializable {
    private Long id;

    private String currentCycle;

    private String cycleDescribe;

    private String calculationFormula;

    private Integer days;

    private Integer workday;

    private Date creTime;

    private String creator;

    private String reliability;

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

    public String getCycleDescribe() {
        return cycleDescribe;
    }

    public void setCycleDescribe(String cycleDescribe) {
        this.cycleDescribe = cycleDescribe == null ? null : cycleDescribe.trim();
    }

    public String getCalculationFormula() {
        return calculationFormula;
    }

    public void setCalculationFormula(String calculationFormula) {
        this.calculationFormula = calculationFormula == null ? null : calculationFormula.trim();
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getWorkday() {
        return workday;
    }

    public void setWorkday(Integer workday) {
        this.workday = workday;
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

    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability == null ? null : reliability.trim();
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
        OpsDeliveryPlanConfig other = (OpsDeliveryPlanConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCurrentCycle() == null ? other.getCurrentCycle() == null : this.getCurrentCycle().equals(other.getCurrentCycle()))
            && (this.getCycleDescribe() == null ? other.getCycleDescribe() == null : this.getCycleDescribe().equals(other.getCycleDescribe()))
            && (this.getCalculationFormula() == null ? other.getCalculationFormula() == null : this.getCalculationFormula().equals(other.getCalculationFormula()))
            && (this.getDays() == null ? other.getDays() == null : this.getDays().equals(other.getDays()))
            && (this.getWorkday() == null ? other.getWorkday() == null : this.getWorkday().equals(other.getWorkday()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getReliability() == null ? other.getReliability() == null : this.getReliability().equals(other.getReliability()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCurrentCycle() == null) ? 0 : getCurrentCycle().hashCode());
        result = prime * result + ((getCycleDescribe() == null) ? 0 : getCycleDescribe().hashCode());
        result = prime * result + ((getCalculationFormula() == null) ? 0 : getCalculationFormula().hashCode());
        result = prime * result + ((getDays() == null) ? 0 : getDays().hashCode());
        result = prime * result + ((getWorkday() == null) ? 0 : getWorkday().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getReliability() == null) ? 0 : getReliability().hashCode());
        return result;
    }
}