package com.smc.smccloud.model.purchase;

import lombok.Data;

import java.io.Serializable;

/**
 * @author B91717
 * @date 2022/5/8
 * @apiNote
 */
@Data
public class SMCOrderDO implements Serializable {

    private String orderNo;

    private String prjno;

    private String modelNo;

    private  Integer qty;

    private String remarks;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING, timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String deliverydate;

    //营业型号
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = Shape.STRING, timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String orderdate;

    private String dbname;

    private String status;

    private String producePlace;

}
