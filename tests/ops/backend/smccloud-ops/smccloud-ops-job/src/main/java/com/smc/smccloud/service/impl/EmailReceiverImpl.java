package com.smc.smccloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smc.smccloud.config.EmailReceiveConfig;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.factory.EmailFileParserFactory;
import com.smc.smccloud.model.ConnectEmailCondition;
import com.smc.smccloud.model.EmailAttachment;
import com.smc.smccloud.model.EmailInfo;
import com.smc.smccloud.model.FileParseType;
import com.smc.smccloud.service.EmailFileParser;
import com.smc.smccloud.service.EmailReceiver;
import com.sun.mail.pop3.POP3Folder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.util.*;


@Slf4j
@Service
public class EmailReceiverImpl implements EmailReceiver {

    @Resource
    private RedisManager redisUtil;
    @Resource
    private EmailReceiveConfig emailReceiveConfig;

    @Resource
    private EmailFileParserFactory emailFileParserFactory;

    /**
     * Downloads new messages and saves attachments to disk if any.
     */
//    @Override
//    public ResultVo<String> receiveEmails(ConnectEmailCondition connectEmailCondition) {
//        Properties properties = new Properties();
//        properties.put("mail.pop3.host", connectEmailCondition.getHost());  // 设置邮件服务器主机名
//        properties.put("mail.pop3.port", connectEmailCondition.getPort()); // 服务端口号
//        // SSL setting
//        properties.setProperty("mail.pop3.socketFactory.port", connectEmailCondition.getPort());
//        // connects to the message store
//        Store store = null;
//        Folder folder = null;
//        try {
//            store = Session.getDefaultInstance(properties).getStore("pop3");
//            store.connect(connectEmailCondition.getUserName(), connectEmailCondition.getPassWord());
//            // opens the inbox folder
//            folder = store.getFolder("INBOX");
//            // 打开收件箱
//            folder.open(Folder.READ_ONLY);
//            // 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数
//            log.info("未读邮件数: " + folder.getUnreadMessageCount());
//            // 由于POP3协议无法获知邮件的状态,所以下面得到的结果始终都是为0
//            log.info("删除邮件数: " + folder.getDeletedMessageCount());
//            log.info("新邮件: " + folder.getNewMessageCount());
//            // 获得收件箱中的邮件总数
//            log.info("邮件总数: " + folder.getMessageCount());
//            // 得到收件箱中的所有邮件,并解析
//            return receiveEmails(folder, connectEmailCondition.getSaveDirectory());
//        } catch (MessagingException e) {
//            log.error(e.toString());
//            return ResultVo.failure("读取邮件失败：" + e.getMessage());
//            // throw new BusinessException("邮件下载失败", e);
//        } finally {
//            if (folder != null) {
//                try {
//                    folder.close();
//                } catch (MessagingException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (store != null) {
//                try {
//                    store.close();
//                } catch (MessagingException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    // 自定义比较器：按照邮件发送时间来排序
    static class CalendarComparator implements Comparator<Object> {
        @SneakyThrows  // 自动捕获异常
        public int compare(Object object1, Object object2) {
            Message m1 = (Message) object1;
            Message m2 = (Message) object2;
            return m2.getSentDate().compareTo(m1.getSentDate());

        }
    }

    /**
     * 读取Email
     */
    private ResultVo<String> receiveEmails(Folder folder, String saveDirectory) throws MessagingException {
        // 得到收件箱中的所有邮件,并解析
        POP3Folder inbox = (POP3Folder) folder;

        StringBuilder sbMsg = new StringBuilder();
        Date today = DateUtil.getToday();
        Message message;
        MimeMessage msg;
        EmailInfo emailInfo;
        String strSendDate;
        Object obj;
        EmailInfo errorEmailInfo;
        boolean resultFault;
        File file = null;
        EmailFileParser emailFileParser;
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
                emailInfo.setUid(inbox.getUID(msg));
                emailInfo.setSubject(getSubject(msg));
                emailInfo.setSendTime(msg.getSentDate());
                emailInfo.setStrSendTime(DateUtil.dateToDateTimeString(msg.getSentDate()));
                if (DateUtil.getDiffDay(emailInfo.getSendTime(), today) > emailReceiveConfig.getMaxReceiveDay()) {
                    log.info("只读取到日期的邮件" + emailInfo.getSendTime());
                    sbMsg.append("只读取到日期的邮件：").append(emailInfo.getSendTime());
                    break;
                }

                strSendDate = DateUtil.getFormatDate(emailInfo.getSendTime(), "yyyyMMdd");

                String redisKey = "ops:job:rcvmail:" + strSendDate;
                String errorKey = "ops:job:rcvmailError:" + strSendDate; // 记录解析失败的邮件
                if (redisUtil.hHasKey(redisKey, emailInfo.getUid())) {
                    continue;
                }

                errorEmailInfo = null;
                if (redisUtil.hHasKey(errorKey, emailInfo.getUid())) {
                    obj = redisUtil.hGet(errorKey, emailInfo.getUid());
                    if (null != obj) {
                        errorEmailInfo = JSONObject.parseObject(obj.toString(), EmailInfo.class);
                        if (errorEmailInfo.getParseNum() > 3) {
                            continue;
                        }
                    }
                }

                emailInfo.setSender(getFrom(msg));

                log.info("UID: " + inbox.getUID(msg));
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
                            attachments = this.saveAttachment(msg, saveDirectory);
                            for (EmailAttachment attachement : attachments) {
                                log.info("解析文件 ==> {}, 使用解析器=> {}, 开始执行时间(ms) => {}",
                                        attachement.getFileName(), emailFileParser.keyword(), new Date());
                                emailFileParser.setSender(emailInfo.getSender());
                                file = new File(attachement.getPath());
                                if (!file.exists()) {
                                    continue;
                                }

                                parserResult = emailFileParser.parserFile(file);
                                sbMsg.append(", 解析结果：").append(parserResult.getMessage()).append(" ").append(parserResult.getData());
                                emailInfo.setHasParse(true);
                                emailInfo.setParseResult(parserResult.getData() + parserResult.getMessage());
                                parseCount++;
//                                if (file.delete()) {
//                                    file = null;
//                                }
                                if (!parserResult.isSuccess()) {
                                    resultFault = true;
                                }
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
                    String json = JSON.toJSONString(emailInfo);
                    redisUtil.hPut(redisKey, emailInfo.getUid(), json);
                    redisUtil.expire(redisKey, 5184000);//存60天
                    // 是否已经解析失败过一次
                    boolean exit = redisUtil.hHasKey(errorKey, emailInfo.getUid());

                    /**
                     * 如果该邮件成功解析 则将邮件id存入到缓存里 判断该之前是否有解析失败 如果有,则删除掉错误记录
                     * 如果是第一次解析失败,则存入到错误日志里,解析次数为1,如果继续解析失败,解析次数累加 三次之后不在解析
                     */
                    if (resultFault) {
                        if (exit) {
                            if (errorEmailInfo != null) {
                                errorEmailInfo.setParseNum(errorEmailInfo.getParseNum() + 1);
                            }
                            redisUtil.hPut(errorKey, emailInfo.getUid(), JSONObject.toJSONString(errorEmailInfo));
                        } else {
                            redisUtil.hPut(errorKey, emailInfo.getUid(), json);
                        }
                        redisUtil.expire(errorKey, 5184000);//存60天
                    } else {
                        if (exit) {
                            redisUtil.hdel(errorKey, emailInfo.getUid());
                        }
                    }
                    if (file != null) {
                        file.delete();
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

    /**
     * 获得邮件主题
     *
     * @param msg 邮件内容
     * @return 解码后的邮件主题
     */
    private static String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
        return MimeUtility.decodeText(msg.getSubject());
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
     * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
     * <p>Message.RecipientType.TO  收件人</p>
     * <p>Message.RecipientType.CC  抄送</p>
     * <p>Message.RecipientType.BCC 密送</p>
     *
     * @param msg  邮件内容
     * @param type 收件人类型
     * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
     * @throws MessagingException
     */
    private static String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {
        StringBuilder receiveAddress = new StringBuilder();
        Address[] addresss;
        if (type == null) {
            addresss = msg.getAllRecipients();
        } else {
            addresss = msg.getRecipients(type);
        }

        if (addresss == null || addresss.length < 1)
            throw new MessagingException("没有收件人!");

        for (Address address : addresss) {
            InternetAddress internetAddress = (InternetAddress) address;
            receiveAddress.append(internetAddress.toUnicodeString()).append(",");
        }

        receiveAddress.deleteCharAt(receiveAddress.length() - 1); //删除最后一个逗号

        return receiveAddress.toString();
    }

    /**
     * 获得邮件发送时间
     *
     * @param msg 邮件内容
     * @return yyyy年mm月dd日 星期X HH:mm
     * @throws MessagingException
     */
    private static Date getSentDate(MimeMessage msg) throws MessagingException {
        Date sendDate = msg.getSentDate();

        return sendDate;
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
     * 判断邮件是否已读
     *
     * @param msg 邮件内容
     * @return 如果邮件已读返回true, 否则返回false
     * @throws MessagingException
     */
    private static boolean isSeen(MimeMessage msg) throws MessagingException {
        return msg.getFlags().contains(Flags.Flag.SEEN);
    }

    /**
     * 判断邮件是否需要阅读回执
     *
     * @param msg 邮件内容
     * @return 需要回执返回true, 否则返回false
     * @throws MessagingException
     */
    private static boolean isReplySign(MimeMessage msg) throws MessagingException {
        String[] headers = msg.getHeader("Disposition-Notification-To");
        return headers != null;
    }

    /**
     * 获得邮件的优先级
     *
     * @param msg 邮件内容
     * @return 1(High):紧急  3:普通(Normal)  5:低(Low)
     * @throws MessagingException
     */
    private static String getPriority(MimeMessage msg) throws MessagingException {
        String priority = "普通";
        String[] headers = msg.getHeader("X-Priority");
        if (headers != null) {
            String headerPriority = headers[0];
            if (headerPriority.contains("1") || headerPriority.contains("High"))
                priority = "紧急";
            else if (headerPriority.contains("5") || headerPriority.contains("Low"))
                priority = "低";
            else
                priority = "普通";
        }
        return priority;
    }

    /**
     * 获得邮件文本内容
     *
     * @param part    邮件体
     * @param content 存储邮件文本内容的字符串
     * @throws MessagingException
     * @throws IOException
     */
    private static void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException {
        //如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part) part.getContent(), content);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart, content);
            }
        }
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
     * 保存附件
     *
     * @param part    邮件中多个组合体中的其中一个组合体
     * @param destDir 附件保存目录
     */
    private void saveAttachmentOld(Part part, String destDir) {
        File file = null;
        try {
            if (part.isMimeType("multipart/*")) {
                Multipart multipart = (Multipart) part.getContent();    //复杂体邮件
                //复杂体邮件包含多个邮件体
                int partCount = multipart.getCount();
                String fileName; // 附件文件名
                for (int i = 0; i < partCount; i++) {
                    //获得复杂体邮件中其中一个邮件体
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    //某一个邮件体也有可能是由多个邮件体组成的复杂体
                    String disp = bodyPart.getDisposition();
                    if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                        fileName = decodeText(bodyPart.getFileName());
                        if (FileParseType.isKeyword(fileName)) {
                            String strFileName = System.currentTimeMillis() + "-" + decodeText(bodyPart.getFileName());
                            //file = saveFile(bodyPart.getInputStream(), destDir, strFileName);
                            // 解析文件
                            String keyword = FileParseType.getKeyword(strFileName);
                            EmailFileParser emailFileParser = emailFileParserFactory.getEmailFileParser(keyword);
                            long startTime = System.currentTimeMillis();
                            log.info("解析文件 ==> {}, 使用解析器=> {}, 开始执行时间(ms) => {}", fileName, emailFileParser.keyword(), startTime);
                            emailFileParser.parserFile(file);
                            long endTime = System.currentTimeMillis();
                            log.info("解析文件{}结束, 耗费时间(s)==> {}", fileName, (endTime - startTime) / 1000);
                        }
                    } else if (bodyPart.isMimeType("multipart/*")) {
                        saveAttachment(bodyPart, destDir);
                    }
                }
            } else if (part.isMimeType("message/rfc822")) {
                saveAttachment((Part) part.getContent(), destDir);
            }
        } catch (MessagingException | IOException e) {
            throw new BusinessException("邮件附件下载失败", e);
        } finally {
            if (file != null && file.exists()) {
                file.delete();
            }
        }

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
            log.info("===========下载附件ok===============");
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

    static class UidObj implements Serializable {

        public UidObj(String uid) {
            this.uid = uid;
        }

        private String uid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

//    public static void main(String[] args) {
//        String host = "mail.smcgz.com.cn";
//        String port = "110";
//        String userName = "invoice"; // invoice edp06
//        String password = "IT#190117"; //    hampn#s4
//        String saveDirectory = "E:/Attachment/";
//
//        EmailAttachmentReceiverUtil receiver = new EmailAttachmentReceiverUtil();
//
//        receiver.downloadEmailAttachments(host, port, userName, password,saveDirectory);
//    }
}
