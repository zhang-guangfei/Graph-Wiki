package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductBomT implements Serializable {
    private Long bomid;

    private String modelno;

    private Boolean priorityComplete;

    private Integer priorityLevel;

    private Integer isWms;

    private Boolean isvalid;

    private String createuser;

    private Date createtime;

    private String updateuser;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Long getBomid() {
        return bomid;
    }

    public void setBomid(Long bomid) {
        this.bomid = bomid;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Boolean getPriorityComplete() {
        return priorityComplete;
    }

    public void setPriorityComplete(Boolean priorityComplete) {
        this.priorityComplete = priorityComplete;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public Integer getIsWms() {
        return isWms;
    }

    public void setIsWms(Integer isWms) {
        this.isWms = isWms;
    }

    public Boolean getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Boolean isvalid) {
        this.isvalid = isvalid;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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
        ProductBomT other = (ProductBomT) that;
        return (this.getBomid() == null ? other.getBomid() == null : this.getBomid().equals(other.getBomid()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getPriorityComplete() == null ? other.getPriorityComplete() == null : this.getPriorityComplete().equals(other.getPriorityComplete()))
            && (this.getPriorityLevel() == null ? other.getPriorityLevel() == null : this.getPriorityLevel().equals(other.getPriorityLevel()))
            && (this.getIsWms() == null ? other.getIsWms() == null : this.getIsWms().equals(other.getIsWms()))
            && (this.getIsvalid() == null ? other.getIsvalid() == null : this.getIsvalid().equals(other.getIsvalid()))
            && (this.getCreateuser() == null ? other.getCreateuser() == null : this.getCreateuser().equals(other.getCreateuser()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdateuser() == null ? other.getUpdateuser() == null : this.getUpdateuser().equals(other.getUpdateuser()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBomid() == null) ? 0 : getBomid().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getPriorityComplete() == null) ? 0 : getPriorityComplete().hashCode());
        result = prime * result + ((getPriorityLevel() == null) ? 0 : getPriorityLevel().hashCode());
        result = prime * result + ((getIsWms() == null) ? 0 : getIsWms().hashCode());
        result = prime * result + ((getIsvalid() == null) ? 0 : getIsvalid().hashCode());
        result = prime * result + ((getCreateuser() == null) ? 0 : getCreateuser().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdateuser() == null) ? 0 : getUpdateuser().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }
}