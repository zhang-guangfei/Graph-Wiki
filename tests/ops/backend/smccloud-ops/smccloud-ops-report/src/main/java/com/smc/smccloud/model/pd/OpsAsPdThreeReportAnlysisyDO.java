package com.smc.smccloud.model.pd;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_pd_three_report_anlysisy")
public class OpsAsPdThreeReportAnlysisyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("pd_batch_no")
    private String pdBatchNo;

    @TableField("all_modelno_count")
    private Integer allModelnoCount;

    @TableField("three_equal_count")
    private Integer threeEqualCount;

    @TableField("F_no_equal_W_count")
    private Integer fNoEqualWCount;

    @TableField("F_no_equal_O_count")
    private Integer fNoEqualOCount;

    @TableField("W_no_equal_O_count")
    private Integer wNoEqualOCount;

    @TableField("three_no_equal_count")
    private Integer threeNoEqualCount;

    @TableField("three_equal_ratio")
    private BigDecimal threeEqualRatio;

    @TableField("F_no_equal_W_ratio")
    private BigDecimal fNoEqualWRatio;

    @TableField("F_no_equal_O_ratio")
    private BigDecimal fNoEqualORatio;

    @TableField("W_no_equal_O_ratio")
    private BigDecimal wNoEqualORatio;

    @TableField("three_no_equal_ratio")
    private BigDecimal threeNoEqualRatio;

    @TableField("create_time")
    private String createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private String updateTime;


}
