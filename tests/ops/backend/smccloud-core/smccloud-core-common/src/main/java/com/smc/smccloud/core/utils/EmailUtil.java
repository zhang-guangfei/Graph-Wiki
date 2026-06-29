package com.smc.smccloud.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2020-07-27 09:27
 * Description: 邮件发送工具
 */
@Slf4j
@Component
public class EmailUtil {

    @PostConstruct
    public void init() {
        // 解决因文件名太长被截断所导致的文件名中文乱码问题
        System.setProperty("mail.mime.splitlongparameters", "false");
        // 解决因base64加密所导致的文件名中文乱码问题
        System.setProperty("mail.mime.charset", "UTF-8");
    }

    /**
     * @param mailSender @Resource private JavaMailSender mailSender;
     * @param to         收件人
     * @param cc         抄送人
     * @param subject    主题
     * @param content    正文内容
     * @return true/false
     */
    public static boolean send(JavaMailSenderImpl mailSender, String to, String cc, String subject, String content) {
        return send(mailSender, to, cc, null, subject, content);
    }

    /**
     * @param mailSender @Resource private JavaMailSender mailSender;
     * @param t          收件人
     * @param c          抄送人
     * @param bc         密送人
     * @param subject    主题
     * @param content    正文内容
     * @return true/false
     */
    public static boolean send(JavaMailSenderImpl mailSender, String t, String c, String bc, String subject, String content) {
        String[] to = convertTo(t);
        if (to == null || to.length == 0) {
            log.error("邮件发送失败-收件人to is NULL");
            return false;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String[] cc = convertTo(c);
            if (cc != null && cc.length > 0) {
                helper.setCc(cc);
            }

            helper.setFrom("SMC(中国)有限公司<" + mailSender.getUsername() + ">");
            helper.setTo(to);

            String[] bcc = convertTo(bc);
            if (bcc != null && bcc.length > 0) {
                helper.setBcc(bcc);
            }

            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setSentDate(new Date());

            mailSender.send(message);

            log.info("发送邮件-[{}] 至: {}", subject, Arrays.deepToString(to));
            if (cc != null && cc.length > 0) {
                log.info("抄送邮件至: {}", Arrays.deepToString(cc));
            }
        } catch (MailException | MessagingException e) {
            log.error("简单邮件发送失败: {}", e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * @param mailSender @Resource private JavaMailSender mailSender;
     * @param t          收件人
     * @param c          抄送人
     * @param subject    主题
     * @param content    正文内容
     * @param filePath   附件地址
     * @return true/false
     */
    public static boolean send(JavaMailSenderImpl mailSender, String t, String c, String bc, String subject, String content,
                               String filePath) {
        String[] to = convertTo(t);
        if (to == null || to.length == 0) {
            log.error("附件邮件发送失败-收件人to is NULL");
            return false;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String[] cc = convertTo(c);
            if (cc != null && cc.length > 0) {
                helper.setCc(cc);
            }

            helper.setTo(to);
            helper.setFrom("SMC(中国)有限公司<" + mailSender.getUsername() + ">");
            helper.setSubject(subject);
            helper.setText(content, true);

            // 添加附件
            if (PublicUtil.isNotEmpty(filePath)) {
                FileSystemResource file = new FileSystemResource(new File(filePath));
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                helper.addAttachment(fileName, file);
            }

            helper.setSentDate(new Date());
            mailSender.send(message);

            log.info("发送(filePath)附件邮件-[{}] 至 :{}", subject, Arrays.deepToString(to));
            if (cc != null && cc.length > 0) {
                log.info("抄送附件邮件至: {}", Arrays.deepToString(cc));
            }
        } catch (MessagingException e) {
            log.error("附件邮件发送失败: {}", e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * @param mailSender @Resource private JavaMailSender mailSender;
     * @param to         收件人
     * @param cc         抄送人
     * @param subject    主题
     * @param content    正文内容
     * @param attachment 附件 Map<String, InputStream> attachment = new LinkedHashMap<>();
     * @return true/false
     */
    public static boolean send(JavaMailSenderImpl mailSender, String to, String cc, String subject, String content,
                               Map<String, InputStream> attachment) {
        return send(mailSender, to, cc, null, subject, content, attachment);
    }

    /**
     * @param mailSender @Resource private JavaMailSender mailSender;
     * @param t          收件人
     * @param c          抄送人
     * @param bc         密送人
     * @param subject    主题
     * @param content    正文内容
     * @param attachment 附件 Map<String, InputStream> attachment = new LinkedHashMap<>();
     * @return true/false
     */
    public static boolean send(JavaMailSenderImpl mailSender, String t, String c, String bc, String subject, String content,
                               Map<String, InputStream> attachment) {
        String[] to = convertTo(t);
        if (to == null || to.length == 0) {
            log.error("附件邮件发送失败-收件人to is empty");
            return false;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);

            String[] cc = convertTo(c);
            if (cc != null && cc.length > 0) {
                helper.setCc(cc);
            }

            String[] bcc = convertTo(bc);
            if (bcc != null && bcc.length > 0) {
                helper.setBcc(bcc);
            }
            helper.setFrom("SMC(中国)有限公司<" + mailSender.getUsername() + ">");
            helper.setSubject(subject);
            helper.setText(content, true);

            String fileName;
            InputStream inputStream;
            for (Map.Entry<String, InputStream> entry : attachment.entrySet()) {
                fileName = entry.getKey();
                inputStream = entry.getValue();
                if (fileName != null && !"".equals(fileName)) {
                    helper.addAttachment(MimeUtility.encodeText(fileName, "UTF-8", "B"),
                            new ByteArrayResource(IOUtils.toByteArray(inputStream)),
                            ContentType.APPLICATION_OCTET_STREAM.getMimeType());
                    log.info("添加附件: [{}]", fileName);
                }
            }
            helper.setSentDate(new Date());
            mailSender.send(message);

            log.info("发送附件邮件-[{}] 至: {}", subject, Arrays.deepToString(to));
            if (cc != null && cc.length > 0) {
                log.info("抄送附件邮件至: {}", Arrays.deepToString(cc));
            }
        } catch (MessagingException | IOException e) {
            log.error("附件邮件发送失败: {}", e.getMessage(), e);
            return false;
        } finally {
            InputStream inputStream;
            try {
                for (Map.Entry<String, InputStream> entry : attachment.entrySet()) {
                    inputStream = entry.getValue();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 追加邮件地址
     *
     * @param mail       邮件地址
     * @param appendMail 追加邮件地址
     * @return 邮件地址
     */
    public static String appendMail(String mail, String appendMail) {
        if (mail == null || "".equals(mail)) {
            return appendMail;
        } else {
            if (mail.startsWith(";")) {
                mail = mail.substring(mail.indexOf(";") + 1);
            }
            if (appendMail == null || "".equals(appendMail)) {
                return mail;
            }
            if (mail.endsWith(";")) {
                return mail + appendMail;
            } else {
                return mail + ";" + appendMail;
            }
        }
    }

    /**
     * 将收件人地址字符串转换为收件人地址数组
     *
     * @param mail 邮件地址
     * @return 邮件地址数组
     */
    private static String[] convertTo(String mail) {
        return mail == null || "".equals(mail) ? null : mail.split(";");
    }
}
