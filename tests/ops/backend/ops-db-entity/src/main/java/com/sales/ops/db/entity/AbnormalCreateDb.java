package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class AbnormalCreateDb implements Serializable {
    private String doId;

    private String pcoId;

    private Integer pcoItem;

    private String signWarehouseCode;

    private String roId;

    private String yyBatchid;

    private Integer handFlag;

    private String handMsg;

    private Date createTime;

    private String orderId;

    private Integer orderItem;

    private Integer num;

    private Integer assNum;

    private String doSource;

    private String inventoryTableType;

    private String optStatus;

    private String yyOrderno;

    private String remark;

    private String shelfNo;

    private String shelfModelNo;

    private Integer shelfQty;

    private static final long serialVersionUID = 1L;

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId == null ? null : pcoId.trim();
    }

    public Integer getPcoItem() {
        return pcoItem;
    }

    public void setPcoItem(Integer pcoItem) {
        this.pcoItem = pcoItem;
    }

    public String getSignWarehouseCode() {
        return signWarehouseCode;
    }

    public void setSignWarehouseCode(String signWarehouseCode) {
        this.signWarehouseCode = signWarehouseCode == null ? null : signWarehouseCode.trim();
    }

    public String getRoId() {
        return roId;
    }

    public void setRoId(String roId) {
        this.roId = roId == null ? null : roId.trim();
    }

    public String getYyBatchid() {
        return yyBatchid;
    }

    public void setYyBatchid(String yyBatchid) {
        this.yyBatchid = yyBatchid == null ? null : yyBatchid.trim();
    }

    public Integer getHandFlag() {
        return handFlag;
    }

    public void setHandFlag(Integer handFlag) {
        this.handFlag = handFlag;
    }

    public String getHandMsg() {
        return handMsg;
    }

    public void setHandMsg(String handMsg) {
        this.handMsg = handMsg == null ? null : handMsg.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAssNum() {
        return assNum;
    }

    public void setAssNum(Integer assNum) {
        this.assNum = assNum;
    }

    public String getDoSource() {
        return doSource;
    }

    public void setDoSource(String doSource) {
        this.doSource = doSource == null ? null : doSource.trim();
    }

    public String getInventoryTableType() {
        return inventoryTableType;
    }

    public void setInventoryTableType(String inventoryTableType) {
        this.inventoryTableType = inventoryTableType == null ? null : inventoryTableType.trim();
    }

    public String getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(String optStatus) {
        this.optStatus = optStatus == null ? null : optStatus.trim();
    }

    public String getYyOrderno() {
        return yyOrderno;
    }

    public void setYyOrderno(String yyOrderno) {
        this.yyOrderno = yyOrderno == null ? null : yyOrderno.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo == null ? null : shelfNo.trim();
    }

    public String getShelfModelNo() {
        return shelfModelNo;
    }

    public void setShelfModelNo(String shelfModelNo) {
        this.shelfModelNo = shelfModelNo == null ? null : shelfModelNo.trim();
    }

    public Integer getShelfQty() {
        return shelfQty;
    }

    public void setShelfQty(Integer shelfQty) {
        this.shelfQty = shelfQty;
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
        AbnormalCreateDb other = (AbnormalCreateDb) that;
        return (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getPcoId() == null ? other.getPcoId() == null : this.getPcoId().equals(other.getPcoId()))
            && (this.getPcoItem() == null ? other.getPcoItem() == null : this.getPcoItem().equals(other.getPcoItem()))
            && (this.getSignWarehouseCode() == null ? other.getSignWarehouseCode() == null : this.getSignWarehouseCode().equals(other.getSignWarehouseCode()))
            && (this.getRoId() == null ? other.getRoId() == null : this.getRoId().equals(other.getRoId()))
            && (this.getYyBatchid() == null ? other.getYyBatchid() == null : this.getYyBatchid().equals(other.getYyBatchid()))
            && (this.getHandFlag() == null ? other.getHandFlag() == null : this.getHandFlag().equals(other.getHandFlag()))
            && (this.getHandMsg() == null ? other.getHandMsg() == null : this.getHandMsg().equals(other.getHandMsg()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getAssNum() == null ? other.getAssNum() == null : this.getAssNum().equals(other.getAssNum()))
            && (this.getDoSource() == null ? other.getDoSource() == null : this.getDoSource().equals(other.getDoSource()))
            && (this.getInventoryTableType() == null ? other.getInventoryTableType() == null : this.getInventoryTableType().equals(other.getInventoryTableType()))
            && (this.getOptStatus() == null ? other.getOptStatus() == null : this.getOptStatus().equals(other.getOptStatus()))
            && (this.getYyOrderno() == null ? other.getYyOrderno() == null : this.getYyOrderno().equals(other.getYyOrderno()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getShelfNo() == null ? other.getShelfNo() == null : this.getShelfNo().equals(other.getShelfNo()))
            && (this.getShelfModelNo() == null ? other.getShelfModelNo() == null : this.getShelfModelNo().equals(other.getShelfModelNo()))
            && (this.getShelfQty() == null ? other.getShelfQty() == null : this.getShelfQty().equals(other.getShelfQty()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getPcoId() == null) ? 0 : getPcoId().hashCode());
        result = prime * result + ((getPcoItem() == null) ? 0 : getPcoItem().hashCode());
        result = prime * result + ((getSignWarehouseCode() == null) ? 0 : getSignWarehouseCode().hashCode());
        result = prime * result + ((getRoId() == null) ? 0 : getRoId().hashCode());
        result = prime * result + ((getYyBatchid() == null) ? 0 : getYyBatchid().hashCode());
        result = prime * result + ((getHandFlag() == null) ? 0 : getHandFlag().hashCode());
        result = prime * result + ((getHandMsg() == null) ? 0 : getHandMsg().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getAssNum() == null) ? 0 : getAssNum().hashCode());
        result = prime * result + ((getDoSource() == null) ? 0 : getDoSource().hashCode());
        result = prime * result + ((getInventoryTableType() == null) ? 0 : getInventoryTableType().hashCode());
        result = prime * result + ((getOptStatus() == null) ? 0 : getOptStatus().hashCode());
        result = prime * result + ((getYyOrderno() == null) ? 0 : getYyOrderno().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getShelfNo() == null) ? 0 : getShelfNo().hashCode());
        result = prime * result + ((getShelfModelNo() == null) ? 0 : getShelfModelNo().hashCode());
        result = prime * result + ((getShelfQty() == null) ? 0 : getShelfQty().hashCode());
        return result;
    }
}