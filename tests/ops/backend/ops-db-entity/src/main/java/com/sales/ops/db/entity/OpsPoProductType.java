package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoProductType implements Serializable {
    private String code;

    private String description;

    private Date updatetime;

    private String oprator;

    private static final long serialVersionUID = 1L;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getOprator() {
        return oprator;
    }

    public void setOprator(String oprator) {
        this.oprator = oprator == null ? null : oprator.trim();
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
        OpsPoProductType other = (OpsPoProductType) that;
        return (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getOprator() == null ? other.getOprator() == null : this.getOprator().equals(other.getOprator()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getOprator() == null) ? 0 : getOprator().hashCode());
        return result;
    }
}