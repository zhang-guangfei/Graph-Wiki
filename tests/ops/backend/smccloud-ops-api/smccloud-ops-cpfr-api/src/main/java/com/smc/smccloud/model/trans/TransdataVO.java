package com.smc.smccloud.model.trans;

import lombok.Data;

import java.util.Date;

@Data
public class TransdataVO {

    private Long id;

    private String invoiceNo;

    /**
     * 订单号  --必填
     */
    private String orderNo;

    private Integer itemNo;

    /**
     * 型号  --必填
     */
    private String modelNo;

    /**
     * 数量  --必填
     */
    private Integer quantity;

    /**
     *   必填
     */
    private Integer transType;

    /**
     *   必填
     */
    private Date transTime;

    private String transPsn;

    private String remark;

    private String corderNo;

    private String customerNo;

    private String userNo;

    /**
     * 1-采购订单入库, 2-客户订单出库, 3-调库, 4-组换, 5-bin补库, 6-先行在库补库, 7-委托在库补库  --必填
     */
    private Integer fromType;
}
