package com.smc.smccloud.model.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: ProductInterceptRuleVO
 * @date 2022/05/20 13:22
 */
@Data
public class ProductInterceptRuleVO {

    private Integer id;

    /**
     * 特殊产品拦截规则名称
     */
    private String name;

    /**
     * 规则状态
     */
    private Integer status;

    /**
     * 1-先行在库补货, 2-bin
     */
    private Integer applyType;

    /**
     * 型号或系列，多系列用;分隔
     */
    private String modelNo;

    /**
     * 1- 完整型号, 2-系列
     */
    private Integer modelType;

    /**
     * 供应商
     */
    private String supplierId;

    /**
     * 原产地
     */
    private String origin;

    /**
     * 仓库
     */
    private String warehouseCode;

    /**
     * 许可客户,多个用;分隔
     */
    private String allowCustomerNos;

    /**
     * 最小数量
     */
    private Integer minQty;

    /**
     * 最大数量
     */
    private Integer maxQty;

    /**
     * 1-拦截转人工, 2-自动变更
     */
    private Integer actionType;

    /**
     * 变更仓库
     */
    private String toWarehouseCode;

    /**
     * 变更供应商
     */
    private String toSupplierId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 1-拦截后定时检测重启
     */
    private String restartFlag;

    /**
     * 型号匹配正则表达式
     */
    private String matchModelNo;

    private String updateUser;

    private String createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
