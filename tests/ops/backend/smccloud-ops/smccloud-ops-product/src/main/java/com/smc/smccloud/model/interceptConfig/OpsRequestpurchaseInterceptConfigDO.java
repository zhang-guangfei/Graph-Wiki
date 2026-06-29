package com.smc.smccloud.model.interceptConfig;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_requestPurchase_intercept_config")
public class OpsRequestpurchaseInterceptConfigDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 1:模糊匹配 0：完全匹配, 修改：匹配类别，0：按型号；1：按客户
     */
    @TableField("typeId")
    private String typeId;

    /**
     * 拦截关键字，支持通配模块
     */
    @TableField("ruleKey")
    private String ruleKey;

    /**
     * 自定义拦截 客户
     */
    @TableField("rule_key1")
    private String ruleKey1;

    /**
     * 自定义拦截 数量
     */
    @TableField("rule_key2")
    private Integer ruleKey2;

    /**
     * 是否自动出库
     */
    @TableField("auto_stock_out")
    private Boolean autoStockOut;

    /**
     * 追加原因
     */
    @TableField("reason")
    private String reason;

    /**
     * 限制条件（备注）
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否生效
     */
    @TableField("enable")
    private Boolean enable;

    /**
     * 默认设置请购单状态，字典编号：2083
     */
    @TableField("defaultAction")
    private String defaultAction;

    /**
     * 担当
     */
    @TableField("operator")
    private String operator;

    /**
     * 更新日期
     */
    @TableField("updateTime")
    private Date updateTime;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


}
