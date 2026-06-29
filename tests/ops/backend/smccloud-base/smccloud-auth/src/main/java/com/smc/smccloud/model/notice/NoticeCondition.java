package com.smc.smccloud.model.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class NoticeCondition
{
    private String id;
    /**
     * 标题
     */
    private String heading;
    /**
     * 读取数量
     */
    private Integer readedNum;
    /**
     * 可读总人数
     */
    private Integer totalNotice;
    /**
     * 发布人姓名
     */
    private String createName;
    /**
     * 接收人
     */
    private String readedableUser;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date newsDate;
    /**
     * 公告状态 草稿 draft、发布 published
     */
    private String editStatus;
    /**
     * 公告信息
     */
    private String noticeContent;

    /**
     * 接收用户名
     */
    private List<String> readers;

    /**
     * 读取状态
     */
    private String readStatus;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    private String username;
}
