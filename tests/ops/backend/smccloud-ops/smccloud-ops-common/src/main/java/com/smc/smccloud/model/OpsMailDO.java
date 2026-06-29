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
 * @since 2024-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_mail")
public class OpsMailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发件人
     */
    @TableField("mail_from")
    private String mailFrom;

    /**
     * 收件人 多个用逗号隔开
     */
    @TableField("mail_to")
    private String mailTo;

    /**
     * 邮件主题
     */
    @TableField("subject")
    private String subject;

    /**
     * 邮件内容
     */
    @TableField("context")
    private String context;

    /**
     * 发送邮件日期
     */
    @TableField("send_date")
    private Date sendDate;

    /**
     * 抄送
     */
    @TableField("cc")
    private String cc;

    /**
     * 密送
     */
    @TableField("bcc")
    private String bcc;

    /**
     * 状态 0初始 1成功 2 失败 定时任务执行状态为0的记录
     */
    @TableField("status")
    private String status;

    /**
     * 错误信息
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 附件地址 多个用逗号隔开
     */
    @TableField("file_urls")
    private String fileUrls;

    /**
     * 发件人昵称
     */
    @TableField("nick_name")
    private String nickName;

    @TableField("file_list")
    private String fileList;

    @TableField("insert_time")
    private Date insertTime;

    @TableId(value = "mail_id", type = IdType.AUTO)
    private Long mailId;


}
