package com.smc.smccloud.model.sampleorder;

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
 * @since 2025-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sample_bal_property_assign")
public class SampleBalPropertyAssignDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联sample_bal表的id字段。
     */
    @TableField("sample_bal_id")
    private Long sampleBalId;

    /**
     * 若为型号拆分，则记录子型号,否则记录整型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 记录资产所属公司ID。可选值:CNG/CNO。
     */
    @TableField("company_id")
    private String companyId;

    /**
     * 默认值:0(未删除)
     */
    @TableField("del_flag")
    private Integer delFlag;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("quantity")
    private Integer quantity;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 版本信息
     */
    @TableField("version")
    private Integer version;

    @TableField("proportion")
    private int proportion;

}
