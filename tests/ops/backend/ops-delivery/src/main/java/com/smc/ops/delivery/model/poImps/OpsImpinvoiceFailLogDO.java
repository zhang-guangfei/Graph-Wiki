package com.smc.ops.delivery.model.poImps;

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
 * @since 2024-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_impinvoice_fail_log")
public class OpsImpinvoiceFailLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("source_id")
    private String sourceId;

    @TableField("source_type")
    private String sourceType;

    @TableField("source_table")
    private String sourceTable;

    @TableField("content")
    private String content;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("handle_status")
    private Integer handleStatus;

    @TableField("handle_time")
    private Date handleTime;

    @TableField("remark")
    private String remark;

}
