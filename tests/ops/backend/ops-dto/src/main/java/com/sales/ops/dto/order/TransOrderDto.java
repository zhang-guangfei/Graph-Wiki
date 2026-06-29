package com.sales.ops.dto.order;

import com.sales.ops.db.entity.TransOrder;
import com.sales.ops.dto.util.UserDto;

import java.util.List;

/**
 * @author C12961
 * @date 2022/8/27 15:12
 */
public class TransOrderDto {

    private List<TransOrder> transOrderList;

    private UserDto userDto;

    public List<TransOrder> getTransOrderList() {
        return transOrderList;
    }

    public void setTransOrderList(List<TransOrder> transOrderList) {
        this.transOrderList = transOrderList;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
