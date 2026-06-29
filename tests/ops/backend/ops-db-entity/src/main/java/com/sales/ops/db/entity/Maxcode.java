package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class Maxcode implements Serializable {
    private String opttype;

    private String pickcode;

    private String description;

    private Date optdate;

    private String operator;

    private Date opttime;

    private static final long serialVersionUID = 1L;

    public String getOpttype() {
        return opttype;
    }

    public void setOpttype(String opttype) {
        this.opttype = opttype == null ? null : opttype.trim();
    }

    public String getPickcode() {
        return pickcode;
    }

    public void setPickcode(String pickcode) {
        this.pickcode = pickcode == null ? null : pickcode.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getOptdate() {
        return optdate;
    }

    public void setOptdate(Date optdate) {
        this.optdate = optdate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
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
        Maxcode other = (Maxcode) that;
        return (this.getOpttype() == null ? other.getOpttype() == null : this.getOpttype().equals(other.getOpttype()))
            && (this.getPickcode() == null ? other.getPickcode() == null : this.getPickcode().equals(other.getPickcode()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getOptdate() == null ? other.getOptdate() == null : this.getOptdate().equals(other.getOptdate()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getOpttime() == null ? other.getOpttime() == null : this.getOpttime().equals(other.getOpttime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOpttype() == null) ? 0 : getOpttype().hashCode());
        result = prime * result + ((getPickcode() == null) ? 0 : getPickcode().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getOptdate() == null) ? 0 : getOptdate().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getOpttime() == null) ? 0 : getOpttime().hashCode());
        return result;
    }
}