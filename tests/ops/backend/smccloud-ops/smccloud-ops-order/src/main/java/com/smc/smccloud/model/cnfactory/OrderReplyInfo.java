package com.smc.smccloud.model.cnfactory;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderReplyInfo {
    private  String model;
    private Integer qty;
    private Date issueDate;
    private  Date salesDeliveryDate;
    private Date alterDeliveryDate;
    private String InStorageNo;
    private Date outStorageDate;

    private  String dependencyNo;
    private  String remark;
    private String invoiceNo;
    private String customerNo;
    private  Date packDate;
    private  Date exportDate;
    private  String shipmentStatus;
    private String orderType;
    private  String holon;
    private String replyRemark;



}
