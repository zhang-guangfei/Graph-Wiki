package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsMail implements Serializable {
    private Long mailId;

    private String mailFrom;

    private String mailTo;

    private String subject;

    private String context;

    private Date sendDate;

    private String cc;

    private String bcc;

    private String status;

    private String errorMsg;

    private String fileUrls;

    private String nickName;

    private String fileList;

    private Date insertTime;

    private static final long serialVersionUID = 1L;

    public Long getMailId() {
        return mailId;
    }

    public void setMailId(Long mailId) {
        this.mailId = mailId;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom == null ? null : mailFrom.trim();
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo == null ? null : mailTo.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc == null ? null : cc.trim();
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc == null ? null : bcc.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls == null ? null : fileUrls.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getFileList() {
        return fileList;
    }

    public void setFileList(String fileList) {
        this.fileList = fileList == null ? null : fileList.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OpsMail other = (OpsMail) that;
        return (this.getMailId() == null ? other.getMailId() == null : this.getMailId().equals(other.getMailId()))
            && (this.getMailFrom() == null ? other.getMailFrom() == null : this.getMailFrom().equals(other.getMailFrom()))
            && (this.getMailTo() == null ? other.getMailTo() == null : this.getMailTo().equals(other.getMailTo()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getContext() == null ? other.getContext() == null : this.getContext().equals(other.getContext()))
            && (this.getSendDate() == null ? other.getSendDate() == null : this.getSendDate().equals(other.getSendDate()))
            && (this.getCc() == null ? other.getCc() == null : this.getCc().equals(other.getCc()))
            && (this.getBcc() == null ? other.getBcc() == null : this.getBcc().equals(other.getBcc()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getErrorMsg() == null ? other.getErrorMsg() == null : this.getErrorMsg().equals(other.getErrorMsg()))
            && (this.getFileUrls() == null ? other.getFileUrls() == null : this.getFileUrls().equals(other.getFileUrls()))
            && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
            && (this.getFileList() == null ? other.getFileList() == null : this.getFileList().equals(other.getFileList()))
            && (this.getInsertTime() == null ? other.getInsertTime() == null : this.getInsertTime().equals(other.getInsertTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMailId() == null) ? 0 : getMailId().hashCode());
        result = prime * result + ((getMailFrom() == null) ? 0 : getMailFrom().hashCode());
        result = prime * result + ((getMailTo() == null) ? 0 : getMailTo().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getContext() == null) ? 0 : getContext().hashCode());
        result = prime * result + ((getSendDate() == null) ? 0 : getSendDate().hashCode());
        result = prime * result + ((getCc() == null) ? 0 : getCc().hashCode());
        result = prime * result + ((getBcc() == null) ? 0 : getBcc().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getErrorMsg() == null) ? 0 : getErrorMsg().hashCode());
        result = prime * result + ((getFileUrls() == null) ? 0 : getFileUrls().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getFileList() == null) ? 0 : getFileList().hashCode());
        result = prime * result + ((getInsertTime() == null) ? 0 : getInsertTime().hashCode());
        return result;
    }
}