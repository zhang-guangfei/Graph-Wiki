package com.smc.smccloud.model.ordermodify;

import com.smc.smccloud.model.OrderSales.OrderDlvDataVO;
import com.smc.smccloud.model.receiveorder.RcvMasterVO;
import lombok.Data;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-04-01 09:50
 * Description:
 */
@Data
public class OrderDeliveryModifyInfo {

    RcvMasterVO rcvMasterVO;

    OrderDlvDataVO orderDlvDataVO;

    List<String> orderNoList;
}
