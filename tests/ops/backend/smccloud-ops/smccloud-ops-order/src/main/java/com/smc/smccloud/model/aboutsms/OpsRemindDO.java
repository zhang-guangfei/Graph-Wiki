package com.smc.smccloud.model.aboutsms;

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
 * @since 2023-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_remind")
public class OpsRemindDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 批次号
     */
    @TableField("batch_no")
    private String batchNo;

    /**
     * 提醒类型
     */
    @TableField("remind_type")
    private String remindType;

    /**
     * 提醒类型名称
     */
    @TableField("remind_type_name")
    private String remindTypeName;

    /**
     * 提醒内容
     */
    @TableField("remind_content")
    private String remindContent;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user")
    private String createUser;


}
