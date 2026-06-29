package com.smc.smccloud.model.inventory;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/10/26 11:29
 * @Descripton TODO
 */

@Data
public class SpecStatisticsRequest
{

    // 客户
    private String customerNo;

    private List<String> deptNos;

    // 仓库编码
    private String warehouseCode;

    private String modelNo;

    // 供应商id
    private String supplierId;

    // 库房分类
    private String warehouseType;
}
