package com.smc.smccloud.model.csstock;


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
public class CsStockSettingVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 库房
     */
    private String stockCode;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 设置备库数
     */
    private Integer initStockQty;

    /**
     * 规格单位
     */
    private Integer initUnitQty;

    /**
     * 设置月份
     */
    private Integer initStockMonth;

    /**
     * 货架位
     */
    private String locationNo;

    /**
     * 补货类型
     * 0自动周转 默认
     * 1不周转
     */
    private Integer replType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;

    private String createUser;

    private String updateUser;

    /**
     * 状态
     * 1启用 0停用
     */
    private Integer status;

    /**
     * 提案人
     */
    private String sponsor;

    /**
     * E价格
     */
    private BigDecimal Eprice;



    /**
     * 营业所代码
     */
    private String deptNo;



}
