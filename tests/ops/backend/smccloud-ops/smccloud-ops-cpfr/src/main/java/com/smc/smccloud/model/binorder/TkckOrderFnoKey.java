package com.smc.smccloud.model.binorder;

import lombok.Data;

/**
 * TKCK 未出库数量批量查询入参。
 * orderFno 是 prestock_result.order_no 原值，orderId/orderItem 用于命中 ops_do(order_id, order_item) 索引。
 */
@Data
public class TkckOrderFnoKey {

    private final String orderFno;

    private final String orderId;

    private final String orderItem;
}
