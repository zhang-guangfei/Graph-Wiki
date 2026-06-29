package com.sales.ops.service.wmOrder;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.Orderdlvdata;
import com.sales.ops.dto.util.UserDto;

import java.util.List;

/**
 * @author C12961
 * @date 2022/5/21 11:34
 */
public interface OrderDlvDataService {
    Orderdlvdata getOrderDlvDataByOrder(String orderNo, Integer orderItem) throws OpsException;

    List<Orderdlvdata> getOrderDlvDatasByOrder(String orderNo, List<Integer> orderItems) throws OpsException;

    List<Orderdlvdata> getOrderDlvDataByOrderNo(String orderNo) throws OpsException;

    void updateOrderDlvData(Orderdlvdata update, UserDto userDto) throws OpsException;
}
