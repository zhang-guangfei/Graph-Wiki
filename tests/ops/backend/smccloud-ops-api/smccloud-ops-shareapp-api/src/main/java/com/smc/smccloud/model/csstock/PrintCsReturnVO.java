package com.smc.smccloud.model.csstock;

import lombok.Data;

/**
 * 打印退货单
 *
 * @author wsf
 * @version 1.0
 * @date 2022/2/10 10:51
 */
@Data
public class PrintCsReturnVO {
    //订单号
    private String orderNo;
    private String modelNo;
    // 退货数量
    private Integer returnQty;
    // 良品数量
    private Integer rcvFineQty;
    // 不良品数量
    private Integer rcvBadQty;
    // 组装订单
    private String expStockType;
    private String remark;
}
