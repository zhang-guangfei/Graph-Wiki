package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class InvoiceSplit implements Serializable {
    private Long id;

    private String originalInvoiceNo;

    private Long mergeInvoiceId;

    private Long splitInvoiceId;

    private String splitInvoiceNo;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String updator;

    private Integer delflag;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalInvoiceNo() {
        return originalInvoiceNo;
    }

    public void setOriginalInvoiceNo(String originalInvoiceNo) {
        this.originalInvoiceNo = originalInvoiceNo == null ? null : originalInvoiceNo.trim();
    }

    public Long getMergeInvoiceId() {
        return mergeInvoiceId;
    }

    public void setMergeInvoiceId(Long mergeInvoiceId) {
        this.mergeInvoiceId = mergeInvoiceId;
    }

    public Long getSplitInvoiceId() {
        return splitInvoiceId;
    }

    public void setSplitInvoiceId(Long splitInvoiceId) {
        this.splitInvoiceId = splitInvoiceId;
    }

    public String getSplitInvoiceNo() {
        return splitInvoiceNo;
    }

    public void setSplitInvoiceNo(String splitInvoiceNo) {
        this.splitInvoiceNo = splitInvoiceNo == null ? null : splitInvoiceNo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        InvoiceSplit other = (InvoiceSplit) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOriginalInvoiceNo() == null ? other.getOriginalInvoiceNo() == null : this.getOriginalInvoiceNo().equals(other.getOriginalInvoiceNo()))
            && (this.getMergeInvoiceId() == null ? other.getMergeInvoiceId() == null : this.getMergeInvoiceId().equals(other.getMergeInvoiceId()))
            && (this.getSplitInvoiceId() == null ? other.getSplitInvoiceId() == null : this.getSplitInvoiceId().equals(other.getSplitInvoiceId()))
            && (this.getSplitInvoiceNo() == null ? other.getSplitInvoiceNo() == null : this.getSplitInvoiceNo().equals(other.getSplitInvoiceNo()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOriginalInvoiceNo() == null) ? 0 : getOriginalInvoiceNo().hashCode());
        result = prime * result + ((getMergeInvoiceId() == null) ? 0 : getMergeInvoiceId().hashCode());
        result = prime * result + ((getSplitInvoiceId() == null) ? 0 : getSplitInvoiceId().hashCode());
        result = prime * result + ((getSplitInvoiceNo() == null) ? 0 : getSplitInvoiceNo().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}