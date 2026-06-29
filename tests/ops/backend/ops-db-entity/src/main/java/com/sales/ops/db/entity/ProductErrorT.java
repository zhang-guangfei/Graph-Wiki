package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductErrorT implements Serializable {
    private String id;

    private String modelno;

    private String source;

    private Boolean isDeleted;

    private Date createdDate;

    private String createdUser;

    private Date updatedDate;

    private String updatedUser;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
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
        ProductErrorT other = (ProductErrorT) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getCreatedUser() == null ? other.getCreatedUser() == null : this.getCreatedUser().equals(other.getCreatedUser()))
            && (this.getUpdatedDate() == null ? other.getUpdatedDate() == null : this.getUpdatedDate().equals(other.getUpdatedDate()))
            && (this.getUpdatedUser() == null ? other.getUpdatedUser() == null : this.getUpdatedUser().equals(other.getUpdatedUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getCreatedUser() == null) ? 0 : getCreatedUser().hashCode());
        result = prime * result + ((getUpdatedDate() == null) ? 0 : getUpdatedDate().hashCode());
        result = prime * result + ((getUpdatedUser() == null) ? 0 : getUpdatedUser().hashCode());
        return result;
    }
}