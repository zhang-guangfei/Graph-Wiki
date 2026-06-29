package com.smc.smccloud.model.order;

import lombok.Data;

import java.io.Serializable;

/**
 * 取消订单返回结果
 */
@Data
public class OrderCancelResult implements Serializable {

    private static final long serialVersionUID = -154297340212589222L;

    /**
     * 直销门户出库单号
     */
    private String no;

    /**
     * 子项号
     */
    private String itemNo;

    /**
     * 订单号 (10位)
     */
    private String orderNo;

    /**
     * 直销门户出库单号
     */
    private String saleOrderNo;

    /**
     * 处理结果: 0-申请成功; 1-申请失败; 2-删除成功; 3-删除失败;
     */
    private String result;

    /**
     * 错误原因
     */
    private String message;

    /**
     * 处理人
     */
    private String handlePsnNo;

    private String handlePsnName;

    /**
     * 处理备注
     */
    private String handleRemark;

}
