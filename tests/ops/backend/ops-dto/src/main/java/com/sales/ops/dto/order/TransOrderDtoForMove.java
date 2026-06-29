package com.sales.ops.dto.order;

import java.io.Serializable;

/**
 * @author C12961
 * @date 2022/8/27 15:12
 */
public class TransOrderDtoForMove implements Serializable {

    // 调库单号
    private String transNo;
    // 项号
    private Integer itemNo;
    // 来源单号:先行在库申请单号
    private String fromNo;
    // 来源ID:关联先行在库于调库的id
    private Long fromId;
    // 来源类型:来源类型 1-bin补库，2-调库申请，3先行在库申请 4委托在库
    private Integer fromType;
    // 希望调库数量
    private Integer hopeQty;
    // 型号
    private String modelNo;
    // 是否必须回到目标仓库 0：同仓调库 1：异仓调拨，必须回到目标仓
    private Boolean transFlag;
    // 调出库存ID：想要预约的move库存inventoryId
    private Long fromInventoryId;
    // 调出库存的状态：想要预约的库存状态，校验用（查询时返回可用，真正预约时有可能不可用）
    private String fromInventoryStatus;
    // 来源关联单号：采购单号
    private String fromAssociateNo;
    private Integer fromAssociateNoItem;
    private Integer fromAssociateNoSplit;
    private String toWarehouseCode;
    private String toInventoryTypeCode;
    private String toCustomerNo;
    private String toPpl;
    private String toProjectNo;
    private String toGroupCustomerNo;

    private String corderNo;
    private String cproductNo;

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo;
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public Integer getHopeQty() {
        return hopeQty;
    }

    public void setHopeQty(Integer hopeQty) {
        this.hopeQty = hopeQty;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Boolean getTransFlag() {
        return transFlag;
    }

    public void setTransFlag(Boolean transFlag) {
        this.transFlag = transFlag;
    }

    public Long getFromInventoryId() {
        return fromInventoryId;
    }

    public void setFromInventoryId(Long fromInventoryId) {
        this.fromInventoryId = fromInventoryId;
    }

    public String getFromInventoryStatus() {
        return fromInventoryStatus;
    }

    public void setFromInventoryStatus(String fromInventoryStatus) {
        this.fromInventoryStatus = fromInventoryStatus;
    }

    public String getFromAssociateNo() {
        return fromAssociateNo;
    }

    public void setFromAssociateNo(String fromAssociateNo) {
        this.fromAssociateNo = fromAssociateNo;
    }

    public Integer getFromAssociateNoItem() {
        return fromAssociateNoItem;
    }

    public void setFromAssociateNoItem(Integer fromAssociateNoItem) {
        this.fromAssociateNoItem = fromAssociateNoItem;
    }

    public Integer getFromAssociateNoSplit() {
        return fromAssociateNoSplit;
    }

    public void setFromAssociateNoSplit(Integer fromAssociateNoSplit) {
        this.fromAssociateNoSplit = fromAssociateNoSplit;
    }

    public String getToWarehouseCode() {
        return toWarehouseCode;
    }

    public void setToWarehouseCode(String toWarehouseCode) {
        this.toWarehouseCode = toWarehouseCode;
    }

    public String getToInventoryTypeCode() {
        return toInventoryTypeCode;
    }

    public void setToInventoryTypeCode(String toInventoryTypeCode) {
        this.toInventoryTypeCode = toInventoryTypeCode;
    }

    public String getToCustomerNo() {
        return toCustomerNo;
    }

    public void setToCustomerNo(String toCustomerNo) {
        this.toCustomerNo = toCustomerNo;
    }

    public String getToPpl() {
        return toPpl;
    }

    public void setToPpl(String toPpl) {
        this.toPpl = toPpl;
    }

    public String getToProjectNo() {
        return toProjectNo;
    }

    public void setToProjectNo(String toProjectNo) {
        this.toProjectNo = toProjectNo;
    }

    public String getToGroupCustomerNo() {
        return toGroupCustomerNo;
    }

    public void setToGroupCustomerNo(String toGroupCustomerNo) {
        this.toGroupCustomerNo = toGroupCustomerNo;
    }
}
