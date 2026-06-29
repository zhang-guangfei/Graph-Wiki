package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.util.Date;

/**
 * 退货明细
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/7 11:53
 */
@Data
public class CsReturnDetailVO {
        private Integer id;
    private Integer applyId;
    private String modelNo;
    private Integer quantity;
    private Integer status;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private String remark;
//    //订单号
//    private String orderNo;
//    private String modelNo;
//    // 退货数量
//    private Integer returnQty;
//    // 良品数量
//    private Integer rcvFineQty;
//    // 不良品数量
//    private Integer rcvBadQty;
//    // 组装订单
//    private String expStockType;
//    private String remark;

    private int itemNo;
    private String returnOrderNo;
}
