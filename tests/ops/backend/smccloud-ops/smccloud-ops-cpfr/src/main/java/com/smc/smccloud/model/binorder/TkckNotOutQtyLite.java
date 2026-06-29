package com.smc.smccloud.model.binorder;

import lombok.Data;

/**
 * Task 9：
 * TKCK 未出库数量批量查询返回对象，只保留计算需要的订单号和数量。
 */
@Data
public class TkckNotOutQtyLite {

    private String orderFno;

    private Integer quantity;
}
