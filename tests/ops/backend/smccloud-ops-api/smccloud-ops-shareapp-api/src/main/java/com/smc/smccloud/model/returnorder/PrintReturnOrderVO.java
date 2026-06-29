package com.smc.smccloud.model.returnorder;

import lombok.Data;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2022/2/8 11:39
 * @Descripton TODO
 */

@Data
public class PrintReturnOrderVO {

//    // 申请单号
//    private String applyNo;
//    // 审批日期
//    private String passTime;
//    // 退货公司
//    private String returnCompany;
//    // 打印时间
//    private String printTime;
//    // 退货人
//    private String contactPsn;
//    // 电话
//    private String contactTelno;

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
