package com.smc.smccloud.model.purchase;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseModifyRequest {
    private String applyNo; // 申请号

    private String orderNo; // 订单号

    private String orderFno; //完整订单号

    private List<String> orderNoList; // 订单号

    private String modifyType; // 变更类别

    private String modelno; // 型号

    private String status; // 状态

    private String deptNo; // 部门代码

    private String supplierId; // 供应商

    private String transType; // 运输方式

    // 申请时间
    private String applyTimeStart;
    private String applyTimeEnd;

    // 处理时间
    private String handleTimeStart;
    private String handleTimeEnd;

    private List<String> deptNoList;

}
