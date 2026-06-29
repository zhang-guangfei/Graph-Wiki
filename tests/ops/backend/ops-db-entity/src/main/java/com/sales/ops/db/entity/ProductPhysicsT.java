package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductPhysicsT implements Serializable {
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

    private BigDecimal cylminlen;

    private BigDecimal cylmaxlen;

    private BigDecimal msizemin;

    private BigDecimal msizemax;

    private String midsize;

    private Boolean isDeleted;

    private Date createdDate;

    private String createdUser;

    private Date updatedDate;

    private String updatedUser;

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

    public BigDecimal getCylminlen() {
        return cylminlen;
    }

    public void setCylminlen(BigDecimal cylminlen) {
        this.cylminlen = cylminlen;
    }

    public BigDecimal getCylmaxlen() {
        return cylmaxlen;
    }

    public void setCylmaxlen(BigDecimal cylmaxlen) {
        this.cylmaxlen = cylmaxlen;
    }

    public BigDecimal getMsizemin() {
        return msizemin;
    }

    public void setMsizemin(BigDecimal msizemin) {
        this.msizemin = msizemin;
    }

    public BigDecimal getMsizemax() {
        return msizemax;
    }

    public void setMsizemax(BigDecimal msizemax) {
        this.msizemax = msizemax;
    }

    public String getMidsize() {
        return midsize;
    }

    public void setMidsize(String midsize) {
        this.midsize = midsize == null ? null : midsize.trim();
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
        ProductPhysicsT other = (ProductPhysicsT) that;
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
            && (this.getCylminlen() == null ? other.getCylminlen() == null : this.getCylminlen().equals(other.getCylminlen()))
            && (this.getCylmaxlen() == null ? other.getCylmaxlen() == null : this.getCylmaxlen().equals(other.getCylmaxlen()))
            && (this.getMsizemin() == null ? other.getMsizemin() == null : this.getMsizemin().equals(other.getMsizemin()))
            && (this.getMsizemax() == null ? other.getMsizemax() == null : this.getMsizemax().equals(other.getMsizemax()))
            && (this.getMidsize() == null ? other.getMidsize() == null : this.getMidsize().equals(other.getMidsize()))
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
        result = prime * result + ((getCylminlen() == null) ? 0 : getCylminlen().hashCode());
        result = prime * result + ((getCylmaxlen() == null) ? 0 : getCylmaxlen().hashCode());
        result = prime * result + ((getMsizemin() == null) ? 0 : getMsizemin().hashCode());
        result = prime * result + ((getMsizemax() == null) ? 0 : getMsizemax().hashCode());
        result = prime * result + ((getMidsize() == null) ? 0 : getMidsize().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getCreatedUser() == null) ? 0 : getCreatedUser().hashCode());
        result = prime * result + ((getUpdatedDate() == null) ? 0 : getUpdatedDate().hashCode());
        result = prime * result + ((getUpdatedUser() == null) ? 0 : getUpdatedUser().hashCode());
        return result;
    }
}