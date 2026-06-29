package com.smc.smccloud.model.receiveorder;

import lombok.Data;

import java.util.List;


@Data
public class ReceiveOrderRequest
{
    private String rorderFno;

    private List<String> rorderNos;

    private String modelNo; // 型号

    private String customerNo; // 客户代码

    private Integer status; // 处理状态

    private String rOrdDateStart; // 接单日期（起始）
    private String rOrdDateEnd; // 接单日期（结束）

    private String userNo; // 用户代码

    private String corderNo; // 客户订单号

    private String cproductNo; // 用户产品代码

    private String deptNo; // 营业所代码

    private String tradecompanyId;

    private String areaDeptNo;
}
