package com.smc.smccloud.model;

import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-22 09:31
 * Description: 邮件解析
 */
@Data
public class ParseEmailDTO {

    /**
     * uid
     */
    private String uid;

    /**
     * 主题
     */
    private String subject;

    /**
     * 发件人
     */
    private String from;

    /**
     * 收件人
     */
    private String to;

    /**
     * 邮件发送时间
     */
    private String sentDate;

    /**
     * 是否已读
     */
    private boolean isSeen;

    /**
     * 邮件优先级
     */
    private String priority;

    /**
     * 是否需要回执
     */
    private boolean isReplySign;

    /**
     * 邮件大小
     */
    private String size;

    /**
     * 是否包含附件
     */
    private boolean isContainAttachment;

    /**
     * 邮件正文
     */
    private String content;

    /**
     * 邮件附件
     */
    private List<File> files;

}
