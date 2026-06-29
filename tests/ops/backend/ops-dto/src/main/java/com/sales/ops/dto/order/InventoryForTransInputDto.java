package com.sales.ops.dto.order;

import com.sales.ops.dto.inventory.InventoryForTransDto;
import com.sales.ops.dto.util.UserDto;

import java.util.Date;
import java.util.List;

/**
 * @author C02483
 * @version 1.0
 * @description: 调库单
 * @date 2021/10/25 11:58
 */
public class InventoryForTransInputDto {

   private List<InventoryForTransDto> dtoList;
   private UserDto userDto;

   private Date wmsDlvDate;//计算结果 指定物流交货期


    public List<InventoryForTransDto> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<InventoryForTransDto> dtoList) {
        this.dtoList = dtoList;
    }


    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Date getWmsDlvDate() {
        return wmsDlvDate;
    }

    public void setWmsDlvDate(Date wmsDlvDate) {
        this.wmsDlvDate = wmsDlvDate;
    }
}
