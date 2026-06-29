package com.smc.smccloud.model.notice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sales_sys_notice_reader")
public class NoticeReader {

    @TableId(value = "ID",type = IdType.AUTO)
    private String id;

    @TableField(value = "NOTICE_ID")
    private String noticeId;

    @TableField(value = "USERNAME")
    private String username;

    @TableField(value = "READ_STATUS")
    private String readStatus;
}
