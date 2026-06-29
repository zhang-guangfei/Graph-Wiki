package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class TransferInfo implements Serializable {
    private Long id;

    private Long invoiceId;

    private String invoiceNo;

    private Integer logisticsStatus;

    private String endReceiveWarehouse;

    private Date signDate;

    private String signUser;

    private String signWarehouse;

    private Date shipDate;

    private String carried;

    private String expressCode;

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

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public Integer getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(Integer logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public String getEndReceiveWarehouse() {
        return endReceiveWarehouse;
    }

    public void setEndReceiveWarehouse(String endReceiveWarehouse) {
        this.endReceiveWarehouse = endReceiveWarehouse == null ? null : endReceiveWarehouse.trim();
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getSignUser() {
        return signUser;
    }

    public void setSignUser(String signUser) {
        this.signUser = signUser == null ? null : signUser.trim();
    }

    public String getSignWarehouse() {
        return signWarehouse;
    }

    public void setSignWarehouse(String signWarehouse) {
        this.signWarehouse = signWarehouse == null ? null : signWarehouse.trim();
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getCarried() {
        return carried;
    }

    public void setCarried(String carried) {
        this.carried = carried == null ? null : carried.trim();
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode == null ? null : expressCode.trim();
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
        TransferInfo other = (TransferInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getLogisticsStatus() == null ? other.getLogisticsStatus() == null : this.getLogisticsStatus().equals(other.getLogisticsStatus()))
            && (this.getEndReceiveWarehouse() == null ? other.getEndReceiveWarehouse() == null : this.getEndReceiveWarehouse().equals(other.getEndReceiveWarehouse()))
            && (this.getSignDate() == null ? other.getSignDate() == null : this.getSignDate().equals(other.getSignDate()))
            && (this.getSignUser() == null ? other.getSignUser() == null : this.getSignUser().equals(other.getSignUser()))
            && (this.getSignWarehouse() == null ? other.getSignWarehouse() == null : this.getSignWarehouse().equals(other.getSignWarehouse()))
            && (this.getShipDate() == null ? other.getShipDate() == null : this.getShipDate().equals(other.getShipDate()))
            && (this.getCarried() == null ? other.getCarried() == null : this.getCarried().equals(other.getCarried()))
            && (this.getExpressCode() == null ? other.getExpressCode() == null : this.getExpressCode().equals(other.getExpressCode()))
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
        result = prime * result + ((getInvoiceId() == null) ? 0 : getInvoiceId().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getLogisticsStatus() == null) ? 0 : getLogisticsStatus().hashCode());
        result = prime * result + ((getEndReceiveWarehouse() == null) ? 0 : getEndReceiveWarehouse().hashCode());
        result = prime * result + ((getSignDate() == null) ? 0 : getSignDate().hashCode());
        result = prime * result + ((getSignUser() == null) ? 0 : getSignUser().hashCode());
        result = prime * result + ((getSignWarehouse() == null) ? 0 : getSignWarehouse().hashCode());
        result = prime * result + ((getShipDate() == null) ? 0 : getShipDate().hashCode());
        result = prime * result + ((getCarried() == null) ? 0 : getCarried().hashCode());
        result = prime * result + ((getExpressCode() == null) ? 0 : getExpressCode().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}