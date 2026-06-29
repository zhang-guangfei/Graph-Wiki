package com.sales.ops.dto.ips;

import com.sales.ops.dto.util.MailFileInfo;

import java.util.List;

/**
 * @description: 附件邮件信息
 * "to": "",//主送人
 * "cc": ""//抄送人
 * "subject": ""//主题
 * "body": ""//正文
 * "signature": ""//签名
 * "attachmentpath":["path1","path2"]//是否需要附件
 * "emailImportLevel": ""//邮件重要性
 */
public class IpsAddEmailInfoDto {

    private String to; // 主送人

    private String cc; // 抄送人

    private String subject; // 邮件主题

    private String body; // 邮件内容

    private String signature;

    private List<String> attachmentpath; // 后续根据需要IPS将提供给各系统挂载路径

    private String emailImportLevel;

    private List<PsiFileInfoDto> fileList; // 新增附件json字段 对应实体PsiFileInfoDto，存储实际文件名，以及随机文件名

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<String> getAttachmentpath() {
        return attachmentpath;
    }

    public void setAttachmentpath(List<String> attachmentpath) {
        this.attachmentpath = attachmentpath;
    }

    public String getEmailImportLevel() {
        return emailImportLevel;
    }

    public void setEmailImportLevel(String emailImportLevel) {
        this.emailImportLevel = emailImportLevel;
    }

    public List<PsiFileInfoDto> getFileList() {
        return fileList;
    }

    public void setFileList(List<PsiFileInfoDto> fileList) {
        this.fileList = fileList;
    }
}
