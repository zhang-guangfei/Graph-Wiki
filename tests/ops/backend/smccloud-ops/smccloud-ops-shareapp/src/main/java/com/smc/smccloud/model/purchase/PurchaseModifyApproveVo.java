package com.smc.smccloud.model.purchase;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseModifyApproveVo {
    // 批次号
    private List<String > batchNo;
    // 处理内容
    private String text;
    // 处理人
    private String optUserNo;
    // 主键id
    private List<Long> idList;
    // 状态
    private Integer status;
}
