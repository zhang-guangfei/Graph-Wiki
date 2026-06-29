package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class TempDoconfirm implements Serializable {
    private String rorderNo;

    private String rorderItem;

    private String doId;

    private Integer qty;

    private Integer opsOutQty;

    private Integer wmsOutQty;

    private Integer qtyDiff;

    private String doDelflag;

    private String status;

    private String gatherWarehouseCode;

    private Date shipDate;

    private String orderId13;

    private String optFlg;

    private String optStatus;

    private String optMsg;

    private static final long serialVersionUID = 1L;

    public String getRorderNo() {
        return rorderNo;
    }

    public void setRorderNo(String rorderNo) {
        this.rorderNo = rorderNo == null ? null : rorderNo.trim();
    }

    public String getRorderItem() {
        return rorderItem;
    }

    public void setRorderItem(String rorderItem) {
        this.rorderItem = rorderItem == null ? null : rorderItem.trim();
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getOpsOutQty() {
        return opsOutQty;
    }

    public void setOpsOutQty(Integer opsOutQty) {
        this.opsOutQty = opsOutQty;
    }

    public Integer getWmsOutQty() {
        return wmsOutQty;
    }

    public void setWmsOutQty(Integer wmsOutQty) {
        this.wmsOutQty = wmsOutQty;
    }

    public Integer getQtyDiff() {
        return qtyDiff;
    }

    public void setQtyDiff(Integer qtyDiff) {
        this.qtyDiff = qtyDiff;
    }

    public String getDoDelflag() {
        return doDelflag;
    }

    public void setDoDelflag(String doDelflag) {
        this.doDelflag = doDelflag == null ? null : doDelflag.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode == null ? null : gatherWarehouseCode.trim();
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getOrderId13() {
        return orderId13;
    }

    public void setOrderId13(String orderId13) {
        this.orderId13 = orderId13 == null ? null : orderId13.trim();
    }

    public String getOptFlg() {
        return optFlg;
    }

    public void setOptFlg(String optFlg) {
        this.optFlg = optFlg == null ? null : optFlg.trim();
    }

    public String getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(String optStatus) {
        this.optStatus = optStatus == null ? null : optStatus.trim();
    }

    public String getOptMsg() {
        return optMsg;
    }

    public void setOptMsg(String optMsg) {
        this.optMsg = optMsg == null ? null : optMsg.trim();
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
        TempDoconfirm other = (TempDoconfirm) that;
        return (this.getRorderNo() == null ? other.getRorderNo() == null : this.getRorderNo().equals(other.getRorderNo()))
            && (this.getRorderItem() == null ? other.getRorderItem() == null : this.getRorderItem().equals(other.getRorderItem()))
            && (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getOpsOutQty() == null ? other.getOpsOutQty() == null : this.getOpsOutQty().equals(other.getOpsOutQty()))
            && (this.getWmsOutQty() == null ? other.getWmsOutQty() == null : this.getWmsOutQty().equals(other.getWmsOutQty()))
            && (this.getQtyDiff() == null ? other.getQtyDiff() == null : this.getQtyDiff().equals(other.getQtyDiff()))
            && (this.getDoDelflag() == null ? other.getDoDelflag() == null : this.getDoDelflag().equals(other.getDoDelflag()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getGatherWarehouseCode() == null ? other.getGatherWarehouseCode() == null : this.getGatherWarehouseCode().equals(other.getGatherWarehouseCode()))
            && (this.getShipDate() == null ? other.getShipDate() == null : this.getShipDate().equals(other.getShipDate()))
            && (this.getOrderId13() == null ? other.getOrderId13() == null : this.getOrderId13().equals(other.getOrderId13()))
            && (this.getOptFlg() == null ? other.getOptFlg() == null : this.getOptFlg().equals(other.getOptFlg()))
            && (this.getOptStatus() == null ? other.getOptStatus() == null : this.getOptStatus().equals(other.getOptStatus()))
            && (this.getOptMsg() == null ? other.getOptMsg() == null : this.getOptMsg().equals(other.getOptMsg()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRorderNo() == null) ? 0 : getRorderNo().hashCode());
        result = prime * result + ((getRorderItem() == null) ? 0 : getRorderItem().hashCode());
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getOpsOutQty() == null) ? 0 : getOpsOutQty().hashCode());
        result = prime * result + ((getWmsOutQty() == null) ? 0 : getWmsOutQty().hashCode());
        result = prime * result + ((getQtyDiff() == null) ? 0 : getQtyDiff().hashCode());
        result = prime * result + ((getDoDelflag() == null) ? 0 : getDoDelflag().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getGatherWarehouseCode() == null) ? 0 : getGatherWarehouseCode().hashCode());
        result = prime * result + ((getShipDate() == null) ? 0 : getShipDate().hashCode());
        result = prime * result + ((getOrderId13() == null) ? 0 : getOrderId13().hashCode());
        result = prime * result + ((getOptFlg() == null) ? 0 : getOptFlg().hashCode());
        result = prime * result + ((getOptStatus() == null) ? 0 : getOptStatus().hashCode());
        result = prime * result + ((getOptMsg() == null) ? 0 : getOptMsg().hashCode());
        return result;
    }
}