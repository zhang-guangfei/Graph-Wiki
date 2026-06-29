package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductPhysics implements Serializable {
    private String modelno;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private BigDecimal weight;

    private BigDecimal netweight;

    private BigDecimal afterpackagingLength;

    private BigDecimal afterpackagingWidth;

    private BigDecimal afterpackagingHeight;

    private BigDecimal cyldiameter;

    private Boolean isDeleted;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getNetweight() {
        return netweight;
    }

    public void setNetweight(BigDecimal netweight) {
        this.netweight = netweight;
    }

    public BigDecimal getAfterpackagingLength() {
        return afterpackagingLength;
    }

    public void setAfterpackagingLength(BigDecimal afterpackagingLength) {
        this.afterpackagingLength = afterpackagingLength;
    }

    public BigDecimal getAfterpackagingWidth() {
        return afterpackagingWidth;
    }

    public void setAfterpackagingWidth(BigDecimal afterpackagingWidth) {
        this.afterpackagingWidth = afterpackagingWidth;
    }

    public BigDecimal getAfterpackagingHeight() {
        return afterpackagingHeight;
    }

    public void setAfterpackagingHeight(BigDecimal afterpackagingHeight) {
        this.afterpackagingHeight = afterpackagingHeight;
    }

    public BigDecimal getCyldiameter() {
        return cyldiameter;
    }

    public void setCyldiameter(BigDecimal cyldiameter) {
        this.cyldiameter = cyldiameter;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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
        ProductPhysics other = (ProductPhysics) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getLength() == null ? other.getLength() == null : this.getLength().equals(other.getLength()))
            && (this.getWidth() == null ? other.getWidth() == null : this.getWidth().equals(other.getWidth()))
            && (this.getHeight() == null ? other.getHeight() == null : this.getHeight().equals(other.getHeight()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getNetweight() == null ? other.getNetweight() == null : this.getNetweight().equals(other.getNetweight()))
            && (this.getAfterpackagingLength() == null ? other.getAfterpackagingLength() == null : this.getAfterpackagingLength().equals(other.getAfterpackagingLength()))
            && (this.getAfterpackagingWidth() == null ? other.getAfterpackagingWidth() == null : this.getAfterpackagingWidth().equals(other.getAfterpackagingWidth()))
            && (this.getAfterpackagingHeight() == null ? other.getAfterpackagingHeight() == null : this.getAfterpackagingHeight().equals(other.getAfterpackagingHeight()))
            && (this.getCyldiameter() == null ? other.getCyldiameter() == null : this.getCyldiameter().equals(other.getCyldiameter()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getLength() == null) ? 0 : getLength().hashCode());
        result = prime * result + ((getWidth() == null) ? 0 : getWidth().hashCode());
        result = prime * result + ((getHeight() == null) ? 0 : getHeight().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getNetweight() == null) ? 0 : getNetweight().hashCode());
        result = prime * result + ((getAfterpackagingLength() == null) ? 0 : getAfterpackagingLength().hashCode());
        result = prime * result + ((getAfterpackagingWidth() == null) ? 0 : getAfterpackagingWidth().hashCode());
        result = prime * result + ((getAfterpackagingHeight() == null) ? 0 : getAfterpackagingHeight().hashCode());
        result = prime * result + ((getCyldiameter() == null) ? 0 : getCyldiameter().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        return result;
    }
}