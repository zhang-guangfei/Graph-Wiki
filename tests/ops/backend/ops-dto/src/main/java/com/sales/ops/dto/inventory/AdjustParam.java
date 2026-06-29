package com.sales.ops.dto.inventory;

import com.sales.ops.dto.util.UserDto;

import java.util.List;

/**
 * @author C12961
 * @date 2023/4/7 14:48
 */
public class AdjustParam {

    private List<AdjustItemDTO> adjustItems;
    private UserDto userDto;

    public AdjustParam() {
    }

    public AdjustParam(List<AdjustItemDTO> adjustItems, UserDto userDto) {
        this.adjustItems = adjustItems;
        this.userDto = userDto;
    }

    public List<AdjustItemDTO> getAdjustItems() {
        return adjustItems;
    }

    public void setAdjustItems(List<AdjustItemDTO> adjustItems) {
        this.adjustItems = adjustItems;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
