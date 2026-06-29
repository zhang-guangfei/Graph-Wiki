package com.sales.ops.db.entity;

import java.io.Serializable;

public class EventTypeConfig implements Serializable {
    private Long id;

    private String eventCode;

    private String eventDesc;

    private Integer enableStatus;

    private Integer enableAllot;

    private Integer enablePlan;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode == null ? null : eventCode.trim();
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc == null ? null : eventDesc.trim();
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getEnableAllot() {
        return enableAllot;
    }

    public void setEnableAllot(Integer enableAllot) {
        this.enableAllot = enableAllot;
    }

    public Integer getEnablePlan() {
        return enablePlan;
    }

    public void setEnablePlan(Integer enablePlan) {
        this.enablePlan = enablePlan;
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
        EventTypeConfig other = (EventTypeConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEventCode() == null ? other.getEventCode() == null : this.getEventCode().equals(other.getEventCode()))
            && (this.getEventDesc() == null ? other.getEventDesc() == null : this.getEventDesc().equals(other.getEventDesc()))
            && (this.getEnableStatus() == null ? other.getEnableStatus() == null : this.getEnableStatus().equals(other.getEnableStatus()))
            && (this.getEnableAllot() == null ? other.getEnableAllot() == null : this.getEnableAllot().equals(other.getEnableAllot()))
            && (this.getEnablePlan() == null ? other.getEnablePlan() == null : this.getEnablePlan().equals(other.getEnablePlan()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEventCode() == null) ? 0 : getEventCode().hashCode());
        result = prime * result + ((getEventDesc() == null) ? 0 : getEventDesc().hashCode());
        result = prime * result + ((getEnableStatus() == null) ? 0 : getEnableStatus().hashCode());
        result = prime * result + ((getEnableAllot() == null) ? 0 : getEnableAllot().hashCode());
        result = prime * result + ((getEnablePlan() == null) ? 0 : getEnablePlan().hashCode());
        return result;
    }
}