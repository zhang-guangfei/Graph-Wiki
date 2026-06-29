package com.sales.ops.dto.util;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：邮件实体
 * @date ：Created in 2021/11/4 9:04
 */
public class MailDto implements Serializable {

	private static final long serialVersionUID = -7683416508676363846L;
	private String id;// 邮件id
	private String from;// 邮件发送人
	private String to;// 邮件接收人 多个用，隔开
	private String subject;// 邮件主题
	private String text;// 邮件内容

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sentDate;// 发送时间
	private String cc; // 抄送 多个用，隔开
	private String bcc; // 密送 多个用，隔开
	private String status; // 状态
	private String error; // 报错信息
	private String nickName;// 发件人名称
	private String attachments; // 附件url 多个用，隔开
	private String fileList;// 新增附件json字段 对应实体MailFileDto

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public String getFileList() {
		return fileList;
	}

	public void setFileList(String fileList) {
		this.fileList = fileList;
	}

}
