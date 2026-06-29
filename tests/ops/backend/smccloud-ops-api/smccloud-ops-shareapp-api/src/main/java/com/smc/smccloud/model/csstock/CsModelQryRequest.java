package com.smc.smccloud.model.csstock;

import lombok.Data;

@Data
public class CsModelQryRequest {
    private  String[] modelNos;
    private String customerNo;
    private  String warehouseCode;
    private String calcType; // 计算类型,1-SMC提案 2-代理提案
    private String userNo;
}
