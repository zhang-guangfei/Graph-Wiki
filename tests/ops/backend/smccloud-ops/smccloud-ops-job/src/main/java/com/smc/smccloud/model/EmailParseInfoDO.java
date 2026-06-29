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
 * @since 2025-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("email_parse_info")
public class EmailParseInfoDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("send_time")
    private Date sendTime;

    @TableField("delflag")
    private String delflag;

    @TableField("parse_result")
    private String parseResult;

    @TableField("parse_status")
    private String parseStatus;

    @TableField("str_receive_time")
    private String strReceiveTime;

    @TableField("has_attachment")
    private Boolean hasAttachment;

    @TableField("parse_time")
    private Date parseTime;

    @TableField("str_parse_time")
    private String strParseTime;

    @TableField("parse_num")
    private Integer parseNum;

    @TableField("has_parse")
    private Boolean hasParse;

    @TableField("str_send_time")
    private String strSendTime;

    @TableField("subject")
    private String subject;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("sender")
    private String sender;

    @TableField("receive_time")
    private Date receiveTime;

    @TableField("uid")
    private String uid;

    @TableField("source_type")
    private String sourceType;


}
