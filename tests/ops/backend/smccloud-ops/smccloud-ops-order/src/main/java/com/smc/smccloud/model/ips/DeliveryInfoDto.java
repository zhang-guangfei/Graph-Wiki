package com.smc.smccloud.model.ips;

import lombok.Data;

/**
 * @description:
 * gateNo    门号
 * zoneMark	区域
 * sortNo	分类编号，客户订单：客户代码、补库订单、货位号前10位
 * shelfNo	客户货架号
 * enduserRemarks	打印在指示上,提醒制造现场注意:1.二次电池;2.重点客户:vipcode;3.组装;4.营业所名称;5.>30的型号全型号写入
 * simpleModel	简易型号
 * receivingAddress	物流收货地址（根据物料号配置的收货工厂地址配置好，确定收货地址）
 * receivingConnector	物流收货人
 * receivingDepartmentTeleno	物流收货联系电话
 * "recDlvyDate": "",//纳期
 * "dlvyWay": "",//指定运输方式
 * "place": "",//指定纳入地
 *
 * expectedReceiveAddress  指定地址
 *
 * @author: B91717
 * @time: 2024/12/17 16:29
 */
@Data
public class DeliveryInfoDto {

    private String gateNo;

    private String zoneMark;

    private String sortNo;

    private String shelfNo;

    private String enduserRemarks;

    private String simpleModel;

    private String receivingAddress;

    private String receivingConnector;

    private String receivingDepartmentTeleno;

    private String reqDlvyDate;

    private String dlvyWay;

    private String place;

    private String expectedReceiveAddress;

}
