package com.sales.ops.db.entity;

import java.io.Serializable;

public class EventRehandleConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String eventCode;
    private String exception;
    private Integer rehandleNumber;
    private Integer delflag;

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

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception == null ? null : exception.trim();
    }

    public Integer getRehandleNumber() {
        return rehandleNumber;
    }

    public void setRehandleNumber(Integer rehandleNumber) {
        this.rehandleNumber = rehandleNumber;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
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
        EventRehandleConfig other = (EventRehandleConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getEventCode() == null ? other.getEventCode() == null : this.getEventCode().equals(other.getEventCode()))
                && (this.getException() == null ? other.getException() == null : this.getException().equals(other.getException()))
                && (this.getRehandleNumber() == null ? other.getRehandleNumber() == null : this.getRehandleNumber().equals(other.getRehandleNumber()))
                && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEventCode() == null) ? 0 : getEventCode().hashCode());
        result = prime * result + ((getException() == null) ? 0 : getException().hashCode());
        result = prime * result + ((getRehandleNumber() == null) ? 0 : getRehandleNumber().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        return result;
    }
}