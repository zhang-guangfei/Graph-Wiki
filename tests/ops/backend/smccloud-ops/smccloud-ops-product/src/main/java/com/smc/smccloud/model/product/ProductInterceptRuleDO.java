package com.smc.smccloud.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: ProductInterceptRuleDO
 * @date 2022/05/20 13:20
 */
@Data
@TableName("tbl_ProductInterceptRule")
public class ProductInterceptRuleDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 特殊产品拦截规则名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 规则状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 1-先行在库补货, 2-bin
     */
    @TableField(value = "applyType")
    private Integer applyType;

    /**
     * 型号或系列，多系列用;分隔
     */
    @TableField(value = "modelNo")
    private String modelNo;

    /**
     * 1- 完整型号, 2-系列
     */
    @TableField(value = "modelType")
    private Integer modelType;

    /**
     * 供应商
     */
    @TableField(value = "supplierId")
    private String supplierId;

    /**
     * 原产地
     */
    @TableField(value = "origin")
    private String origin;

    /**
     * 仓库
     */
    @TableField(value = "warehouseCode")
    private String warehouseCode;

    /**
     * 许可客户,多个用;分隔
     */
    @TableField(value = "allowCustomerNos")
    private String allowCustomerNos;

    /**
     * 最小数量
     */
    @TableField(value = "minQty")
    private Integer minQty;

    /**
     * 最大数量
     */
    @TableField(value = "maxQty")
    private Integer maxQty;

    /**
     * 1-拦截转人工, 2-自动变更, 3-自动退回, 4-转等待处理
     */
    @TableField(value = "actionType")
    private Integer actionType;

    /**
     * 变更仓库
     */
    @TableField(value = "toWarehouseCode")
    private String toWarehouseCode;

    /**
     * 变更供应商
     */
    @TableField(value = "toSupplierId")
    private String toSupplierId;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 1-拦截后定时检测重启
     */
    @TableField(value = "restartFlag")
    private String restartFlag;

    /**
     * 型号匹配正则表达式
     */
    @TableField(value = "matchModelNo")
    private String matchModelNo;

    @TableField(value = "updateUser")
    private String updateUser;

    @TableField(value = "createUser")
    private String createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "createTime")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "updateTime")
    private Date updateTime;

}
