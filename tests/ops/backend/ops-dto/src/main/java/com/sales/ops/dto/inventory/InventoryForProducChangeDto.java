package com.sales.ops.dto.inventory;

import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.DoTypeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 组换单(同仓)
 * @date 2021/10/15 13:40
 */
public class InventoryForProducChangeDto implements Serializable {

    private String warehouseCode;
    private List<InventoryForAdjustDto> ckList;
    private List<InventoryForAdjustDto> rkList;
    private UserDto userDto;
    /**
     * 组换类型
     * ops and wms
     * ZHCK("ZHCK","ZH","组换出库"),
     * //only wms
     * ZHCKOW("ZHCKOW","ZH","组换出库仅调wms库存"),
     */
    private DoTypeEnum doTypeEnum;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public List<InventoryForAdjustDto> getCkList() {
        return ckList;
    }

    public void setCkList(List<InventoryForAdjustDto> ckList) {
        this.ckList = ckList;
    }

    public List<InventoryForAdjustDto> getRkList() {
        return rkList;
    }


    public void setRkList(List<InventoryForAdjustDto> rkList) {
        this.rkList = rkList;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public DoTypeEnum getDoTypeEnum() {
        return doTypeEnum;
    }

    public void setDoTypeEnum(DoTypeEnum doTypeEnum) {
        this.doTypeEnum = doTypeEnum;
    }
}
