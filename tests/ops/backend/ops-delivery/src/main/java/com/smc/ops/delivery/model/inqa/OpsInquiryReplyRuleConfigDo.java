package com.smc.ops.delivery.model.inqa;

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
 * @since 2024-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_inquiry_reply_rule_config")
public class OpsInquiryReplyRuleConfigDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("service_type")
    private String serviceType; // 业务类别：INQA/INQB

    @TableField("supplier_id")
    private String supplierId; // 供应商代码

    @TableField("rule_type")
    private String ruleType; // 规则类型：催促状态/结果描述/异常提示

    @TableField("rule_name")
    private String ruleName; // 规则名称中文名，如“已回复”

    @TableField("expression")
    private String expression; // expression表达式

    @TableField("result")
    private String result; // 返回结果，如“已回复”、“超预期”

    @TableField("priority")
    private Integer priority; //执行优先级

    @TableField("error_warn")
    private Boolean error_warn; // 预留字段：是否增加预警

    @TableField("remark")
    private String remark;

    /**
     * 是否有效
     */
    @TableField("is_deleted")
    private Integer isDeleted;


    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField("update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;


}
