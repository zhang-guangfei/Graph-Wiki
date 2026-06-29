package com.smc.smccloud.model.notice;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("sales_sys_notice")
public class Notice
{
    @TableId(value = "ID",type = IdType.AUTO)
    private String id;
    /**
     * 标题
     */
    @TableField(value = "HEADING")
    private String heading;
    /**
     * 读取数量
     */
    @TableField(value = "READED_NUM")
    private Integer readedNum;
    /**
     * 可读总人数
     */
    @TableField(value = "TOTAL_NOTICE")
    private Integer totalNotice;
    /**
     * 发布人姓名
     */
    @TableField(value = "CREATE_NAME")
    private String createName;
    /**
     * 接收人
     */
    @TableField(value = "READEDABLE_USER")
    private String readedableUser;

    /**
     * 发布时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "NEWSDATE")
    private Date newsDate;
    /**
     * 公告状态 草稿 draft、发布 published
     */
    @TableField(value = "EDITSTATUS")
    private String editStatus;
    /**
     * 公告信息
     */
    @TableField(value = "NOTICE_CONTENT")
    private String noticeContent;

    // private List<String> readers;

    // private String readStatus;
}
