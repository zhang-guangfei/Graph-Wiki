package com.smc.smccloud.model.product;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-05-25 11:14
 * Description: 特殊型号拦截DTO
 */
@Data
public class ProductInterceptRuleDTO {

    /* 拦截验证信息 */

    /**
     * 1-先行在库补货, 2-bin
     */
    private Integer applyType;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 供应商
     */
    private String supplierId;

    /**
     * 仓库
     */
    private String warehouseCode;

    /**
     * 客户
     */
    private String customerNo;

    /**
     * 数量
     */
    private int quantity;

    /* 拦截验证返回结果 */

    /**
     * 是否拦截
     */
    private boolean isIntercept;

    /**
     * 拦截规则id
     */
    private Integer id;

    /**
     * 拦截规则名称
     */
    private String name;

    /**
     * 1-拦截转人工, 2-自动变更, 3-自动退回, 4-转等待处理
     */
    private Integer actionType;

    /**
     * 变更供应商
     */
    private String toSupplierId;

    /**
     * 变更仓库
     */
    private String toWarehouseCode;

    /**
     * 最小数量
     */
    private Integer minQty;

    /**
     * 最大数量
     */
    private Integer maxQty;

    /**
     * 备注
     */
    private String remark;

    /**
     * 1-拦截后定时检测重启
     */
    private String restartFlag;
}
