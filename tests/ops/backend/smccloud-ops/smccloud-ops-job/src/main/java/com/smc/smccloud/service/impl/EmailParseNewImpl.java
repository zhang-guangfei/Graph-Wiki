package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.factory.EmailFileParserFactory;
import com.smc.smccloud.mapper.EmailParseInfoMapper;
import com.smc.smccloud.model.*;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.EmailFileParser;
import com.smc.smccloud.service.EmailParseNew;
import com.sun.mail.imap.IMAPFolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;

/**
 * @Author lyc
 * @Date 2024/7/30 17:11
 * @Descripton TODO
 */
@Service
@Slf4j
public class EmailParseNewImpl implements EmailParseNew {

    @Value("${office365EmailParser.tanantId}")
    private String tanantId;

    @Value("${office365EmailParser.mailAddress}")
    private String  mailAddress;

    @Value("${office365EmailParser.clientId}")
    private String clientId;

    @Value("${office365EmailParser.client_secret}")
    private String client_secret;

    @Value("${office365EmailParser.password}")
    private String password;

    @Value("${office365EmailParser.filesavepath}")
    private String filesavepath;

    @Value("${email.max-receive-day}")
    private Integer maxReceiveDay;

    @Resource
    private EmailFileParserFactory emailFileParserFactory;

    @Resource
    private EmailParseInfoMapper emailParseInfoMapper;

    @Override
    public ResultVo<String> emailParseNew() {
        try {
            // 创建忽略SSL验证的上下文
            SSLContext sslContext = createIgnoreVerifySSL();
            SSLContext.setDefault(sslContext); // 设置全局SSL上下文

            Properties props = new Properties();

            props.put("mail.store.protocol", "imap");
            props.put("mail.imap.host", "imap.partner.outlook.cn");
            props.put("mail.imap.port", "993");
            props.put("mail.imap.starttls.enable", "true");
            props.put("mail.imap.auth", "true");
            props.put("mail.imap.auth.mechanisms", "XOAUTH2");
            props.put("mail.imap.user", mailAddress);
            props.put("mail.debug", "true");
            props.put("mail.debug.auth", "true");
            props.put("mail.imap.ssl.enable", "true");

            props.put("mail.imap.sasl.enable", "true");
            props.put("mail.imap.sasl.mechanisms", "XOAUTH2");
            props.put("mail.imap.auth.login.disable", "true");
            props.put("mail.imap.auth.plain.disable", "true");

            // 应用自定义SSL上下文到邮件配置
            props.put("mail.imap.ssl.socketFactory", sslContext.getSocketFactory());
            props.put("mail.imap.ssl.checkserveridentity", "false"); // 禁用主机名验证

            // open mailbox....
            String token = getAuthToken(tanantId, clientId, client_secret, mailAddress, password);
            Session session = Session.getInstance(props);
            session.setDebug(true);
            props.put("auth", token);
            Store store = session.getStore("imap");
            store.connect("imap.partner.outlook.cn", mailAddress, token);
            return readEmail(store);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ResultVo<String> readEmail(Store store) throws MessagingException {
        // Folder folder = store.getFolder("INBOX");
        IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
        // 打开收件箱
        folder.open(Folder.READ_ONLY);
        return resolveEmails(folder, filesavepath);
    }


    private ResultVo<String> resolveEmails(IMAPFolder folder, String saveDirectory) throws MessagingException {
        // 得到收件箱中的所有邮件,并解析
        // POP3Folder inbox = (POP3Folder) folder;
        StringBuilder sbMsg = new StringBuilder();
        Date today = DateUtil.getToday();
        Message message;
        MimeMessage msg;
        EmailInfo emailInfo;
        String strSendDate;
        Object obj;
        EmailInfo errorEmailInfo;
        boolean resultFault;
        EmailFileParser emailFileParser;
        File file = null;
        List<EmailAttachment> attachments;
        ResultVo<String> parserResult;
        // 解析所有邮件
        try {
            int emailCount = folder.getMessageCount();
            sbMsg.append("共有邮件：").append(emailCount);
            int readCount = 0;
            int parseCount = 0;
            int mailIndex = emailCount;

            while (true) {
                if (mailIndex == 0) {
                    break;
                }
                message = folder.getMessage(mailIndex--);
                if (message == null) {
                    continue;
                }
                msg = (MimeMessage) message;

                emailInfo = new EmailInfo();
                emailInfo.setUid(String.valueOf(folder.getUID(message)));  // 关键修改
                emailInfo.setSubject(getSubject(msg));
                emailInfo.setSendTime(msg.getSentDate());


                emailInfo.setStrSendTime(DateUtil.dateToDateTimeString(msg.getSentDate()));
                if (DateUtil.getDiffDay(emailInfo.getSendTime(), today) > maxReceiveDay) {
                    log.info("只读取到日期的邮件 {}, 邮件已超时效期" ,emailInfo.getSendTime());
                    sbMsg.append("只读取到日期的邮件(邮件已超时效期)：").append(emailInfo.getSendTime());
                    break;
                }

                strSendDate = DateUtil.getFormatDate(emailInfo.getSendTime(), "yyyyMMdd");
                LambdaQueryWrapper<EmailParseInfoDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(EmailParseInfoDO::getUid, emailInfo.getUid());

                EmailParseInfoDO emailParseInfoDO = emailParseInfoMapper.selectOne(queryWrapper);

                if (emailParseInfoDO != null) {
                    // 解析成功的不再解析
                    if ("1".equals(emailParseInfoDO.getParseStatus())) {
                        continue;
                    }
                    // 解析失败3次以上不再解析
                    if (emailParseInfoDO.getParseNum() > 3) {
                        continue;
                    }
                }

                emailInfo.setSender(getFrom(msg));

                log.info("UID: " + emailInfo.getUid());
                log.info("主题: " + emailInfo.getSubject());
                log.info("发件人: " + emailInfo.getSender());
                log.info("发送时间：" + strSendDate);

                emailInfo.setHasAttachment(isContainAttachment(msg));

                resultFault = false;
                file = null;
                try {
                    if (emailInfo.isHasAttachment()) {
                        sbMsg.append(", 开始解析:").append(emailInfo.getSubject());
                        emailFileParser = emailFileParserFactory.getEmailFileParser(emailInfo.getSubject());
                        if (emailFileParser != null) {
                            emailFileParser.setEmailInfo(emailInfo);
                            attachments = saveAttachment(msg, saveDirectory);
                            for (EmailAttachment attachement : attachments) {
                                log.info("解析文件 ==> {}, 使用解析器=> {}, 开始执行时间(ms) => {}", attachement.getFileName(), emailFileParser.keyword(), new Date());
                                emailFileParser.setSender(emailInfo.getSender());
                                file = new File(attachement.getPath());
                                if (!file.exists()) {
                                    continue;
                                }
                                parserResult = emailFileParser.parserFile(file);

                                if (!parserResult.isSuccess()) {
                                    resultFault = true;
                                    sbMsg.append(", 解析结果：").append(parserResult.getMessage());
                                    emailInfo.setParseResult(parserResult.getMessage());
                                } else {
                                    resultFault = false;
                                    sbMsg.append(", 解析结果：").append(parserResult.getData());
                                    emailInfo.setParseResult(parserResult.getData());
                                }
                                emailInfo.setParseNum(emailInfo.getParseNum() + 1);
                                emailInfo.setHasParse(true);
                                parseCount++;
                            }
                        }
                    }
                    readCount++;
                } catch (Exception ex) {
                    sbMsg.append(", 解析邮件出错:").append(ex);
                    emailInfo.setParseResult("解析出错：" + ex.getMessage());
                } finally {
                    emailInfo.setParseTime(new Date());
                    emailInfo.setStrParseTime(DateUtil.dateToDateTimeString(new Date()));
                    /**
                     * 如果邮件管理表存在,状态为解析失败,解析次数小于3,则累加解析次数
                     */
                    LambdaQueryWrapper<EmailParseInfoDO> queryWrapperEmail = new LambdaQueryWrapper<>();
                    queryWrapperEmail.eq(EmailParseInfoDO::getUid, emailInfo.getUid());
                    EmailParseInfoDO info = emailParseInfoMapper.selectOne(queryWrapperEmail);
                    if(info != null && info.getParseStatus().equals("9") && info.getParseNum() <= 3) {
                        LambdaUpdateWrapper<EmailParseInfoDO> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(EmailParseInfoDO::getId, info.getId());
                        updateWrapper.set(EmailParseInfoDO::getParseNum, info.getParseNum()+1)
                                .set(EmailParseInfoDO::getParseResult, emailInfo.getParseResult())
                                .set(EmailParseInfoDO::getParseTime, emailInfo.getParseTime());
                        if (resultFault) {
                            updateWrapper.set(EmailParseInfoDO::getParseStatus, "9");
                        } else {
                            updateWrapper.set(EmailParseInfoDO::getParseStatus, "1");
                        }
                        emailParseInfoMapper.update(null, updateWrapper);

                    } else if (info == null) {
                        // 写入邮件解析管理
                        EmailParseInfoDO  emailParseInfo = new EmailParseInfoDO();
                        emailParseInfo.setSourceType("email");
                        emailParseInfo.setUid(emailInfo.getUid());
                        emailParseInfo.setSendTime(emailInfo.getSendTime());
                        emailParseInfo.setStrSendTime(emailInfo.getStrSendTime());
                        emailParseInfo.setReceiveTime(emailInfo.getReceiveTime());
                        emailParseInfo.setSubject(emailInfo.getSubject());
                        emailParseInfo.setSender(emailInfo.getSender());
                        emailParseInfo.setParseTime(emailInfo.getParseTime());
                        emailParseInfo.setStrParseTime(emailInfo.getStrParseTime());
                        emailParseInfo.setHasAttachment(emailInfo.isHasAttachment());
                        emailParseInfo.setHasParse(emailInfo.isHasParse());
                        emailParseInfo.setParseResult(emailInfo.getParseResult());
                        if (resultFault) {
                            emailParseInfo.setParseStatus("9");
                        } else {
                            emailParseInfo.setParseStatus("1");
                        }
                        emailParseInfo.setParseNum(emailInfo.getParseNum());
                        emailParseInfo.setDelflag("0");
                        emailParseInfoMapper.insert(emailParseInfo);
                    }
                }
            }
            sbMsg.append("读取邮件数：").append(readCount).append("\r\n").append("解析邮件数：").append(parseCount);
            log.info(sbMsg.toString());
        } catch (IOException e) {
            if (file != null) {
                file.delete();
            }
            throw new BusinessException("邮件解析失败 =>" + e);
        }

        return ResultVo.success(sbMsg.toString());
    }

    private List<EmailAttachment> saveAttachment(Part part, String destDir) {
        List<EmailAttachment> files = new ArrayList<>();
        List<EmailAttachment> subFiles;

        try {
            if (part.isMimeType("multipart/*")) {
                Multipart multipart = (Multipart) part.getContent();    //复杂体邮件
                //复杂体邮件包含多个邮件体
                int partCount = multipart.getCount();
                BodyPart bodyPart;
                String disp;
                String fileName;
                EmailAttachment attachment;
                String strFileName;
                String path;

                for (int i = 0; i < partCount; i++) {
                    //获得复杂体邮件中其中一个邮件体
                    bodyPart = multipart.getBodyPart(i);
                    //某一个邮件体也有可能是由多个邮件体组成的复杂体
                    disp = bodyPart.getDisposition();
                    if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                        fileName = decodeText(bodyPart.getFileName());
                        attachment = new EmailAttachment();
                        attachment.setFileName(fileName);
                        strFileName = System.currentTimeMillis() + "-" + fileName;
                        path = destDir + File.separator + DateUtil.getYearMonthDay(new Date()) + File.separator + strFileName;

                        if (saveFile(bodyPart.getInputStream(), destDir + File.separator + DateUtil.getYearMonthDay(new Date()), strFileName)) {
                            attachment.setPath(path);
                            attachment.setFileName(fileName);
                            files.add(attachment);
                        }
                    } else if (bodyPart.isMimeType("multipart/*")) {
                        subFiles = saveAttachment(bodyPart, destDir);
                        if (subFiles.size() > 0) {
                            files.addAll(0, subFiles);
                        }
                    }
                }
            } else if (part.isMimeType("message/rfc822")) {
                subFiles = saveAttachment((Part) part.getContent(), destDir);
                if (subFiles.size() > 0) {
                    files.addAll(0, subFiles);
                }
            }
        } catch (MessagingException | IOException e) {
            throw new BusinessException("邮件附件下载失败", e);
        }

        return files;
    }

    /**
     * 读取输入流中的数据保存至指定目录
     *
     * @param is       输入流
     * @param fileName 文件名
     * @param destDir  文件存储目录
     */
    private static boolean saveFile(InputStream is, String destDir, String fileName) {
        //如果文件夹不存在则创建
        File file = new File(destDir);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        log.info("po适配器delivery文件保存位置{}",destDir +File.separator+ fileName);
        File saveFile = new File(destDir +File.separator+ fileName);
        BufferedInputStream bis = null;
        FileOutputStream fos;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(saveFile);
            bos = new BufferedOutputStream(fos);
            byte[] buf = new byte[1024 * 4];
            int len;
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            bos.flush();
            bos.close();
            fos.close();
            log.info("===========下载{}附件ok===== path: {}",fileName,destDir +File.separator+ fileName);
            return true;
        } catch (IOException e) {
            if (bos != null) {
                try {
                    bos.close();
                    bos = null;
                } catch (IOException ex) {
                    e.printStackTrace();
                }
            }
            try {
                bis.close();
                bis = null;
            } catch (IOException ex) {
                e.printStackTrace();
            }
            saveFile.delete();
            throw new BusinessException("邮件附件保存失败", e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文本解码
     *
     * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本
     * @return 解码后的文本
     * @throws UnsupportedEncodingException
     */
    private static String decodeText(String encodeText) throws UnsupportedEncodingException {
        if (encodeText == null || "".equals(encodeText)) {
            return "";
        } else {
            return MimeUtility.decodeText(encodeText);
        }
    }

    /**
     * 判断邮件中是否包含附件
     * <p>
     * //* @param msg 邮件内容
     *
     * @return 邮件中存在附件返回true，不存在返回false
     * @throws MessagingException
     * @throws IOException
     */
    private static boolean isContainAttachment(Part part) throws MessagingException, IOException {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = isContainAttachment(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.contains("application")) {
                        flag = true;
                    }
                    if (contentType.contains("name")) {
                        flag = true;
                    }
                }

                if (flag) break;
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = isContainAttachment((Part) part.getContent());
        }
        return flag;
    }

    /**
     * 获得邮件发件人
     *
     * @param msg 邮件内容
     * @return 姓名 <Email地址>
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private static String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        String from;
        Address[] froms = msg.getFrom();
        if (froms.length < 1)
            throw new MessagingException("没有发件人!");

        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }
        from = person + "<" + address.getAddress() + ">";

        return from;
    }

    /**
     * 获得邮件主题
     *
     * @param msg 邮件内容
     * @return 解码后的邮件主题
     */
    private static String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
        return MimeUtility.decodeText(msg.getSubject());
    }

    public static String getAuthToken(String tanantId, String clientId, String client_secret, String userName, String password) throws Exception {
        // CloseableHttpClient client = HttpClients.createDefault();
        // 使用自定义SSL上下文创建HttpClient
        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(createIgnoreVerifySSL())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        HttpPost loginPost = new HttpPost("https://login.partner.microsoftonline.cn/" + tanantId + "/oauth2/v2.0/token");
        String scopes = "https://partner.outlook.cn/.default";
        String encodedBody = "client_id=" + clientId + "&scope=" + scopes + "&client_secret=" + client_secret + "&username=" + userName + "&password=" + password
                + "&grant_type=password";
        loginPost.setEntity(new StringEntity(encodedBody, ContentType.APPLICATION_FORM_URLENCODED));
        loginPost.addHeader(new BasicHeader("cache-control", "no-cache"));
        CloseableHttpResponse loginResponse = client.execute(loginPost);
        InputStream inputStream = loginResponse.getEntity().getContent();
        // byte[] response = inputStream.readAllBytes();
        byte[] response = readInputStreamWithoutOverflow(inputStream);
        String string = new String(response, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.constructType(
                objectMapper.getTypeFactory().constructParametricType(Map.class, String.class, String.class));
        Map<String, String> parsed = new ObjectMapper().readValue(response, type);
        return parsed.get("access_token");
    }

    public static byte[] readInputStreamWithoutOverflow(InputStream inputStream) throws Exception {
        BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int byteRead;
        while ((byteRead = bufferedStream.read()) != -1) {
            byteArrayOutputStream.write(byteRead);
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("TLS");
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }
}
