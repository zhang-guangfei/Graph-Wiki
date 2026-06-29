package com.smc.smccloud.model.orderstate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 供应商回复订单状态
 */
@Data
public class OrderSupplierReplyState {

    /**
     * 订单号，包括项号
     */
    private  String orderNo;

    /**
     * 供应商系统内的订单号
     */
    private  String supplierOrderNo;

    private String corderNo;

    /**
     * 供应商代码
     */
    private  String supplierId;

    /**
     * 状态代码
     * 1-已接入订单
     * 2-生产中
     * 3-已完工货齐
     * 4-已发货
     * 9-取消订单
     */
    private  Integer stateCode;

    /**
     * 状态描述
     */
    private  String stateDes;

    /**
     * 操作人代码
     */
    private  String optUser;
    /**
     * 操作人名称
     */
    private  String optUserName;

    private  String holon;

    /**
     * 变更后的交货期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    private Date dlvDate;

    /**
     * 发货日期
     */
    private  Date shipDate;

    /**
     * 接单日期
     */
    private  Date recieveDate;

    /**
     * 操作时间
     */
    private  Date optTime;

    //开始生产日期
    private Date startProductionDate;

    //订单残数(未发数量)
    private Integer remainingQuantity;

    // 捆包标识 0 未捆包 1 已捆包
    private Integer baleFlag ;
    // 捆包时间
    private Date baleDate;



}
