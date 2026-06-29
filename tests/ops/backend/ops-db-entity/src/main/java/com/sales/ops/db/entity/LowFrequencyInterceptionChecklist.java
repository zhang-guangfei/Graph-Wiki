package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class LowFrequencyInterceptionChecklist implements Serializable {
    private Long id;

    private String types;

    private String batch;

    private String modelno;

    private Date updatetime;

    private String creator;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types == null ? null : types.trim();
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
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
        LowFrequencyInterceptionChecklist other = (LowFrequencyInterceptionChecklist) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTypes() == null ? other.getTypes() == null : this.getTypes().equals(other.getTypes()))
            && (this.getBatch() == null ? other.getBatch() == null : this.getBatch().equals(other.getBatch()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTypes() == null) ? 0 : getTypes().hashCode());
        result = prime * result + ((getBatch() == null) ? 0 : getBatch().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        return result;
    }
}