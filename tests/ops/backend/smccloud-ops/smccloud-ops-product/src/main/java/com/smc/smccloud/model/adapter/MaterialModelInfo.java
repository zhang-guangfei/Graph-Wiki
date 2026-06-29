package com.smc.smccloud.model.adapter;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-11-08 10:13
 * Description:门户物料号维护信息
 */
@Data
public class MaterialModelInfo {

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 可用库存
     */
    private int avaQty;
}
