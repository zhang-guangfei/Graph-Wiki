package com.sales.ops.dto.order;

import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.sales.ops.dto.util.UserDto;

import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 调账单
 * @date 2021/10/25 11:53
 */
public class InventoryForAdjustInputDto {
   private List<InventoryForAdjustDto> dtoList;
   private String optType;//optType:"+"调整+库存,"-'调账-库存
   private UserDto userDto;

    public List<InventoryForAdjustDto> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<InventoryForAdjustDto> dtoList) {
        this.dtoList = dtoList;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
