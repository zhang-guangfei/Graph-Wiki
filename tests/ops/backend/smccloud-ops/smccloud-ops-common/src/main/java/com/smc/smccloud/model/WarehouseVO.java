package com.smc.smccloud.model;

import lombok.Data;

import java.util.Date;

@Data
public class WarehouseVO {

    private String warehouseCode;

    private String warehouseType;

    private String warehouseName;

    private String adress;

    private Integer postCode;

    private String linkman;

    private String mail;

    private String customerNo;

    private String customerLinkman;

    private String customerPhone;

    private String customerMail;

    private String smcLinkman;

    private String smcPhone;

    private String smcMail;

    private Integer disableFlag;

    private Integer assemblyFlag;

    private Integer centralizeFlag;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private Integer purchaseFlag;

    private String parentCode;

    private String rcvLinkman;

    private String rcvLinkTel;

    private String rcvLinkEmail;

    private String englishAddress;

    private String englishLinkman;
    // 退货能力
    private Integer returnableFlag;
    // 调库能力
    private Integer transferFlag;
    // 补库能力
    private Integer prestockFlag;
}
