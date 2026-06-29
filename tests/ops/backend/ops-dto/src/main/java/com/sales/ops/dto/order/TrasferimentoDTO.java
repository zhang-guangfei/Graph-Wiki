package com.sales.ops.dto.order;

import com.sales.ops.dto.util.UserDto;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1$
 * @description：转定dto
 * @date ：Created in 2022/4/4 11:38
 */
public class TrasferimentoDTO implements Serializable {

    private static final long serialVersionUID = -7309853661010990200L;
    private Integer trasferimentoType;//转定类型 TrasferimentoTypeEnum 0  PO_TO_DO("0","采购转在库"), DO_TO_DO("1","在库转在库"), DO_TO_PO("2","在库转采购");
    private String doSourceEnum;//拆分类型
    private Long fromDoItemInventoryId; //关联表的id
    private String doId;//doId,不拆分型号时提供
    private String pcoId;//pcoId，拆分型号时提供
    private String doDbId;//doDbId，调拨单
    private Integer pcoItem;//pco箱号，拆分型号时提供
    private Integer sortNum;//ITEM_INVENTORY表的sortNum

    private Long fromInventoryID;//库存id
    private String fromInventoryTableType;//库存类型 在库N 在途 T
    private String modelNo;
    private Long toInventoryID;//库存id
    private String toInventoryTableType;//库存类型 在库N 在途 T
    private String toWarehouseCode;//变成仓库号Id
    private UserDto userDto;
    private Integer qty;

    public Integer getTrasferimentoType() {
        return trasferimentoType;
    }

    public void setTrasferimentoType(Integer trasferimentoType) {
        this.trasferimentoType = trasferimentoType;
    }

    public String getDoSourceEnum() {
        return doSourceEnum;
    }

    public void setDoSourceEnum(String doSourceEnum) {
        this.doSourceEnum = doSourceEnum;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId;
    }

    public Integer getPcoItem() {
        return pcoItem;
    }

    public void setPcoItem(Integer pcoItem) {
        this.pcoItem = pcoItem;
    }

    public String getFromInventoryTableType() {
        return fromInventoryTableType;
    }

    public void setFromInventoryTableType(String fromInventoryTableType) {
        this.fromInventoryTableType = fromInventoryTableType;
    }

    public Long getFromInventoryID() {
        return fromInventoryID;
    }

    public void setFromInventoryID(Long fromInventoryID) {
        this.fromInventoryID = fromInventoryID;
    }

    public Long getToInventoryID() {
        return toInventoryID;
    }

    public void setToInventoryID(Long toInventoryID) {
        this.toInventoryID = toInventoryID;
    }

    public String getToInventoryTableType() {
        return toInventoryTableType;
    }

    public void setToInventoryTableType(String toInventoryTableType) {
        this.toInventoryTableType = toInventoryTableType;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Long getFromDoItemInventoryId() {
        return fromDoItemInventoryId;
    }

    public void setFromDoItemInventoryId(Long fromDoItemInventoryId) {
        this.fromDoItemInventoryId = fromDoItemInventoryId;
    }

    public String getToWarehouseCode() {
        return toWarehouseCode;
    }

    public void setToWarehouseCode(String toWarehouseCode) {
        this.toWarehouseCode = toWarehouseCode;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getDoDbId() {
        return doDbId;
    }

    public void setDoDbId(String doDbId) {
        this.doDbId = doDbId;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }
    @Override
    public String toString() {
        return "TrasferimentoDTO{" +
                "trasferimentoType=" + trasferimentoType +
                ", doSourceEnum='" + doSourceEnum + '\'' +
                ", fromDoItemInventoryId=" + fromDoItemInventoryId +
                ", doId='" + doId + '\'' +
                ", pcoId='" + pcoId + '\'' +
                ", doDbId='" + doDbId + '\'' +
                ", pcoItem=" + pcoItem +
                ", sortNum=" + sortNum +
                ", fromInventoryID=" + fromInventoryID +
                ", fromInventoryTableType='" + fromInventoryTableType + '\'' +
                ", modelNo='" + modelNo + '\'' +
                ", toInventoryID=" + toInventoryID +
                ", toInventoryTableType='" + toInventoryTableType + '\'' +
                ", toWarehouseCode='" + toWarehouseCode + '\'' +
                ", userDto=" + userDto +
                ", qty=" + qty +
                '}';
    }


}
