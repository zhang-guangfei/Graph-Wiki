package com.smc.smccloud.model;

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
 * @since 2024-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sales_customer_cluster")
public class SalesCustomerClusterDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 群号
     */
    @TableField("customer_base_no")
    private String customerBaseNo;

    /**
     * 群名称
     */
    @TableField("customer_base_name")
    private String customerBaseName;

    /**
     * 客户担当编码
     */
    @TableField("customer_take_id")
    private String customerTakeId;

    /**
     * 客户担当姓名
     */
    @TableField("customer_take_name")
    private String customerTakeName;

    /**
     * 所属部门编码
     */
    @TableField("dept_code")
    private String deptCode;

    /**
     * 所属部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 创建人编码
     */
    @TableField("create_id")
    private String createId;

    /**
     * 创建人姓名
     */
    @TableField("create_name")
    private String createName;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新人编码
     */
    @TableField("update_id")
    private String updateId;

    /**
     * 更新人姓名
     */
    @TableField("update_name")
    private String updateName;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    @TableField("description")
    private String description;


}
