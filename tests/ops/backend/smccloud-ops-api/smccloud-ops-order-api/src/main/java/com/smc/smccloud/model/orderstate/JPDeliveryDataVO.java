package com.smc.smccloud.model.orderstate;

import lombok.Data;

import java.util.Date;

@Data
public class JPDeliveryDataVO {

    private String orderNo;
    private String item;
    private  Integer qty;
    private  String dlvDate;
    private  String jpDlvDate;
    private  String factory; // 出在库
    private String baling; // 已捆包
    private  String supplierOrderNo;
    private  Integer finishQty;
    private String modelNo;
    private  String transType;
    private  Date updDate;
    // 采购类型
    private String purchaseType;
    private String shikomiNo;
    private String rohs;
    private String rcvDate;
    /**
     * 0-空白日期
     * 1-正确日期
     * 2-特殊字符
     * 9-异常状态
     */
    private int jpDlvDateType;
    private Date jpDlvDateValue;
    /*
      特殊交货期说明
     */
    private  String jpDlvDateDes;

}
