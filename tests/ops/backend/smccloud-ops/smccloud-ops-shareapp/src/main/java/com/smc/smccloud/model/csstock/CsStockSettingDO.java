package com.smc.smccloud.model.csstock;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 寄售型号清单设置
 *
 * @author smc
 * @since 2021-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("cs_stock_setting")
public class CsStockSettingDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 客户代码
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     * 库房
     */
    @TableField("stock_code")
    private String stockCode;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 设置备库数
     */
    @TableField("init_stock_qty")
    private Integer initStockQty;

    /**
     * 规格单位
     */
    @TableField("init_unit_qty")
    private Integer initUnitQty;

    /**
     * 设置月份
     */
    @TableField("init_stock_month")
    private Integer initStockMonth;

    /**
     * 货架位
     */
    @TableField("location_no")
    private String locationNo;

    /**
     * 补货类型
     * 0自动周转 默认
     * 1不周转
     */
    @TableField("repl_type")
    private Integer replType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(value = "create_time",  fill = FieldFill.INSERT)
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(value = "update_time",  fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    /**
     * 状态
     * 1启用 0停用
     */
    @TableField("status")
    private Integer status;

    /**
     * 提案人
     */
    @TableField("sponsor")
    private String sponsor;

    /**
     * E价格
     */
    @TableField("eprice")
    private BigDecimal Eprice;

    /**
     * 营业所代码
     */
    @TableField("dept_no")
    private String deptNo;

    @TableField("stock_type")
    private Integer stockType;

    /**
     * 申请中数量
     */
    @TableField("apply_qty")
    private Integer applyQty;

    /**
     * 订货中数量
     */
    @TableField("ord_qty")
    private Integer ordQty;

    @TableField("trans_qty")
    private Integer transQty;

    @TableField("qty_onhand")
    private Integer qtyOnhand;

    @TableField("qty_prepare")
    private Integer qtyPrepare;

}
