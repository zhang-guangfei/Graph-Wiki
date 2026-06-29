package com.smc.smccloud.model.notice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 公告附件表
 */
@Data
@TableName("sales_sys_notice_attachedfile")
public class NoticeAttachedFile
{
    @TableId(value = "ID",type = IdType.AUTO)
    private String id;

    @TableField(value = "NOTICE_ID")
    private String noticeId;

    @TableField(value = "FILENAME")
    private String fileName;

    @TableField(value = "RANDOMFILENAME")
    private String randomFileName;

    @TableField(value = "FILEPATH")
    private String filePath;
}
