package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsPco implements Serializable {
    private Long id;

    private String pcoId;

    private String orderId;

    private String orderItem;

    private Integer assNum;

    private Integer num;

    private Integer pcoSource;

    private Integer pcoType;

    private String prodType;

    private String warehouseCode;

    private String customerNo;

    private String modelNo;

    private BigDecimal weight;

    private Integer qty;

    private Integer pcoStatus;

    private Date wlDate;

    private Date hopeDate;

    private String userNo;

    private String greenCode;

    private Integer isWms;

    private Long bomid;

    private Integer spceialFlag;

    private Integer outQty;

    private String remark;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private String roId;

    private Long specialBomId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId == null ? null : pcoId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem == null ? null : orderItem.trim();
    }

    public Integer getAssNum() {
        return assNum;
    }

    public void setAssNum(Integer assNum) {
        this.assNum = assNum;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPcoSource() {
        return pcoSource;
    }

    public void setPcoSource(Integer pcoSource) {
        this.pcoSource = pcoSource;
    }

    public Integer getPcoType() {
        return pcoType;
    }

    public void setPcoType(Integer pcoType) {
        this.pcoType = pcoType;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType == null ? null : prodType.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getPcoStatus() {
        return pcoStatus;
    }

    public void setPcoStatus(Integer pcoStatus) {
        this.pcoStatus = pcoStatus;
    }

    public Date getWlDate() {
        return wlDate;
    }

    public void setWlDate(Date wlDate) {
        this.wlDate = wlDate;
    }

    public Date getHopeDate() {
        return hopeDate;
    }

    public void setHopeDate(Date hopeDate) {
        this.hopeDate = hopeDate;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getGreenCode() {
        return greenCode;
    }

    public void setGreenCode(String greenCode) {
        this.greenCode = greenCode == null ? null : greenCode.trim();
    }

    public Integer getIsWms() {
        return isWms;
    }

    public void setIsWms(Integer isWms) {
        this.isWms = isWms;
    }

    public Long getBomid() {
        return bomid;
    }

    public void setBomid(Long bomid) {
        this.bomid = bomid;
    }

    public Integer getSpceialFlag() {
        return spceialFlag;
    }

    public void setSpceialFlag(Integer spceialFlag) {
        this.spceialFlag = spceialFlag;
    }

    public Integer getOutQty() {
        return outQty;
    }

    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public String getRoId() {
        return roId;
    }

    public void setRoId(String roId) {
        this.roId = roId == null ? null : roId.trim();
    }

    public Long getSpecialBomId() {
        return specialBomId;
    }

    public void setSpecialBomId(Long specialBomId) {
        this.specialBomId = specialBomId;
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
        OpsPco other = (OpsPco) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPcoId() == null ? other.getPcoId() == null : this.getPcoId().equals(other.getPcoId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getAssNum() == null ? other.getAssNum() == null : this.getAssNum().equals(other.getAssNum()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getPcoSource() == null ? other.getPcoSource() == null : this.getPcoSource().equals(other.getPcoSource()))
            && (this.getPcoType() == null ? other.getPcoType() == null : this.getPcoType().equals(other.getPcoType()))
            && (this.getProdType() == null ? other.getProdType() == null : this.getProdType().equals(other.getProdType()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getPcoStatus() == null ? other.getPcoStatus() == null : this.getPcoStatus().equals(other.getPcoStatus()))
            && (this.getWlDate() == null ? other.getWlDate() == null : this.getWlDate().equals(other.getWlDate()))
            && (this.getHopeDate() == null ? other.getHopeDate() == null : this.getHopeDate().equals(other.getHopeDate()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getGreenCode() == null ? other.getGreenCode() == null : this.getGreenCode().equals(other.getGreenCode()))
            && (this.getIsWms() == null ? other.getIsWms() == null : this.getIsWms().equals(other.getIsWms()))
            && (this.getBomid() == null ? other.getBomid() == null : this.getBomid().equals(other.getBomid()))
            && (this.getSpceialFlag() == null ? other.getSpceialFlag() == null : this.getSpceialFlag().equals(other.getSpceialFlag()))
            && (this.getOutQty() == null ? other.getOutQty() == null : this.getOutQty().equals(other.getOutQty()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getRoId() == null ? other.getRoId() == null : this.getRoId().equals(other.getRoId()))
            && (this.getSpecialBomId() == null ? other.getSpecialBomId() == null : this.getSpecialBomId().equals(other.getSpecialBomId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPcoId() == null) ? 0 : getPcoId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getAssNum() == null) ? 0 : getAssNum().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getPcoSource() == null) ? 0 : getPcoSource().hashCode());
        result = prime * result + ((getPcoType() == null) ? 0 : getPcoType().hashCode());
        result = prime * result + ((getProdType() == null) ? 0 : getProdType().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getPcoStatus() == null) ? 0 : getPcoStatus().hashCode());
        result = prime * result + ((getWlDate() == null) ? 0 : getWlDate().hashCode());
        result = prime * result + ((getHopeDate() == null) ? 0 : getHopeDate().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getGreenCode() == null) ? 0 : getGreenCode().hashCode());
        result = prime * result + ((getIsWms() == null) ? 0 : getIsWms().hashCode());
        result = prime * result + ((getBomid() == null) ? 0 : getBomid().hashCode());
        result = prime * result + ((getSpceialFlag() == null) ? 0 : getSpceialFlag().hashCode());
        result = prime * result + ((getOutQty() == null) ? 0 : getOutQty().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getRoId() == null) ? 0 : getRoId().hashCode());
        result = prime * result + ((getSpecialBomId() == null) ? 0 : getSpecialBomId().hashCode());
        return result;
    }
}