package com.smc.smccloud.model.VSalesManuorder;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/5/19 12:18
 * @Descripton TODO
 */
@Data
public class OPSVRequisitionStausToSalesDO {

    // 订单号
    private String orderNo;
    private String itemNo;

    // 型号
    private String modelNo;
    // 数量
    private Integer quantity;

    // 接单日期
    private Date orderDate;

    // 预计交货期
    private Date esDlvDate;

    // 开始生产日期
    private Date startProductionDate;

    private String remark;

    private String invoiceNo;

    private String customerNo;

    // 装箱日期
    private Date packDate;

    // 出货状态
    private String shipStatus;

    private String orderType;

    private Date updateTime;

    private String poHolon;

    private  String staus;

    private String refOrderNo;

    // 返信状态
    private String returnMsgStatus;

    // 不确定返信
    private String notSureReturnDate;

    // 预计生产完成日
    private Date expectedProductionCompletionDateH;
    // 预计入物流日
    private Date expectedLogisticsArrivalDateW;

}
