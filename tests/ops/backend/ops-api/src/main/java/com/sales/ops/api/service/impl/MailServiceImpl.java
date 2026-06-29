package com.sales.ops.api.service.impl;

import java.io.File;
import java.util.*;
import com.sales.ops.common.until.OPSRedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.sales.ops.api.service.MailService;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.dao.OpsMailMapper;
import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.db.entity.OpsMailExample;
import com.sales.ops.db.extdao.OpsMailDao;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.MailDto;
import com.sales.ops.dto.util.MailFileDto;
import com.sales.ops.dto.util.MailFileInfo;
import com.sales.ops.enums.SendStatusEnum;
import com.sales.ops.feign.OpsMailApi;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：邮件发送服务
 * @date ：Created in 2021/11/4 9:13
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class MailServiceImpl implements MailService {

	private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	@Qualifier("javaMailSender1")
	private JavaMailSenderImpl mailSender;// 注入邮件工具类

	@Autowired
	@Qualifier("javaMailSender2")
	private JavaMailSenderImpl mailSender2;// 注入邮件工具类

	@Autowired
	private OpsMailMapper opsMailMapper;

	@Autowired
	private OpsMailApi opsMailApi;

	@Autowired
	private OpsMailDao opsMailDao;

	@Autowired
	private OPSRedisUtils opsRedisUtils;

	@Override
	public Integer sendMail(MailDto mail) throws OpsException {
		// 1.检测邮件
		checkMail(mail);
		// 2.保存邮件到数据库
		OpsMail opsMail = new OpsMail();
		opsMail.setMailFrom(mail.getFrom());
		opsMail.setStatus(SendStatusEnum.INIT.getType());// 初始状态是0
		opsMail.setBcc(mail.getBcc());
		opsMail.setCc(mail.getCc());
		opsMail.setSubject(mail.getSubject());
		opsMail.setContext(mail.getText());
		opsMail.setMailTo(mail.getTo());
		opsMail.setNickName(mail.getNickName());
		if (StringUtils.isNotEmpty(mail.getAttachments()) && StringUtils.isNotEmpty(mail.getAttachments().trim())) {// 保存附件
			opsMail.setFileUrls(mail.getAttachments().trim());
		}
		// bug12513 新增附件列表json字段 对应实体MailFileDto
		if (StringUtils.isNotBlank(mail.getFileList())) {
			opsMail.setFileList(mail.getFileList());
		}
		return saveMailToDb(opsMail); // 2.保存邮件
	}

	/**
	 * 定时任务调用 根据opsMail表发送邮件
	 */
	@Override
	public List<OpsMail> selectMailByDb() throws OpsException {
		// add doItem to adapter
		OpsMailExample example = new OpsMailExample();
		OpsMailExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(SendStatusEnum.INIT.getType());// 初始化的
		return opsMailMapper.selectByExample(example);
	}

	/**
	 * 定时任务调用 根据opsMail表发送邮件只取10条
	 */
	@Override
	public List<OpsMail> selectMailByDbTop10() throws OpsException {
		return opsMailDao.selectMailByDbTop10();
	}

	@Override
	public List<OpsMail> selectMailByDbTop2() throws OpsException {
		return opsMailDao.selectMailByDbTop2();
	}

	/**
	 * bug11473 自动发单 采购未发送成功的邮件重试机制
	 */
	@Override
	public Integer sendPurchaseJob() throws Exception {
		List<OpsMail> list = opsMailDao.selectPurchaseMailWait(SendStatusEnum.PURCHASEINIT.getType());
		for (OpsMail i : list) {
			// bug14381 附件为空抛异常后执行下一条发邮件数据
			try {
				jobSendMail(i, true);
			} catch (Exception e) {
				logger.error("采购发单文件异常", e);
			}
		}
		return list.size();
	}

	@Override
	public void jobSendMail(OpsMail opsMail, boolean isPurchaseSend) throws Exception {
		// 发送邮件
		sendMimeMail(opsMail, isPurchaseSend);
		if (isPurchaseSend) {
			// 更新邮件发送状态
			opsMail.setStatus(SendStatusEnum.PURCHASESUCCESS.getType());
			// bug12686 变更更新表的方法
			opsMailMapper.updateByPrimaryKeySelective(opsMail);
		}
	}

	@Override
	public Integer updateMailDb(OpsMail opsMail) throws OpsException {
		checkErrorMsg(opsMail);
		return opsMailMapper.updateByPrimaryKeySelective(opsMail);
	}

	//bugid:15905 c14717 20241128 邮件失败重试
	public void checkErrorMsg(OpsMail opsMail){
		try {
			if(!SendStatusEnum.FAIR.getType().equals(opsMail.getStatus())){
				return;
			}
			//非空
			if(StringUtils.isEmpty(opsMail.getErrorMsg())){
				return;
			}
			boolean repeatFlag = false;
			List<String> msgKeys = new ArrayList<>();
			msgKeys.add("Connection or outbound has closed");
			msgKeys.add("Couldn't connect to host");
			if(CollectionUtils.isNotEmpty(msgKeys)){
				for(String msgKey : msgKeys){
					if ((opsMail.getErrorMsg().contains(msgKey))) {
						repeatFlag = true;
						break;
					}
				}
			}
			//不包含key
			if(!repeatFlag){
				return;
			}
			//设置初始值
			opsMail.setStatus(SendStatusEnum.INIT.getType());
			int maxRetries = 3;
			int thisTimes = 0;
			String mailId = opsMail.getMailId().toString();
			String key ="ops:mail:id:"+mailId;
			Object o = opsRedisUtils.get(key);
			if(Objects.isNull(o)){
				opsRedisUtils.set(key,thisTimes+1);
			}else {
				thisTimes = (int)o;
				if(maxRetries == thisTimes){
					opsMail.setStatus(SendStatusEnum.FAIR.getType());
				}else {
					opsRedisUtils.set(key,thisTimes+1);
				}
			}
			//默认存24小时
			opsRedisUtils.expire(key, 60 * 60 * 24 );
		} catch (Exception e) {
			opsMail.setStatus(SendStatusEnum.FAIR.getType());
			logger.error("邮件重试失败");
		}

	}

	// 检测邮件信息类
	private void checkMail(MailDto mail) throws OpsException {
		if (StringUtils.isBlank(mail.getTo())) {
			throw Exceptions.OpsException("邮件收信人不能为空");
		}
		if (!isEmail(mail.getTo())) {
			throw Exceptions.OpsException("邮件收信人格式不正确");
		}
		if (StringUtils.isBlank(mail.getSubject())) {
			throw Exceptions.OpsException("邮件主题不能为空");
		}
		if (StringUtils.isBlank(mail.getText())) {
			throw Exceptions.OpsException("邮件内容不能为空");
		}
	}

	// 发送邮件
	private void sendMimeMail(OpsMail opsMail, boolean isPurchaseSend) throws Exception {
		MimeMessageHelper messageHelper;
		if (isPurchaseSend) {
			messageHelper = new MimeMessageHelper(mailSender2.createMimeMessage(), true);// true表示支持复杂类型
			opsMail.setMailFrom(mailSender2.getUsername());// 邮件发信人从配置项读取
		} else {
			messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);// true表示支持复杂类型
			opsMail.setMailFrom(mailSender.getUsername());// 邮件发信人从配置项读取
		}

		// nickName
		StringBuffer from = new StringBuffer();

		if (StringUtils.isNotEmpty(opsMail.getNickName())) {
			from.append(opsMail.getNickName());
		}
		from.append("<").append(opsMail.getMailFrom()).append(">");
		messageHelper.setFrom(from.toString());// 邮件发信人
        if (opsMail.getMailTo().contains(";")) {
            messageHelper.setTo(opsMail.getMailTo().split(";"));// 邮件收信人
        }else {
            messageHelper.setTo(opsMail.getMailTo().split(","));// 邮件收信人
        }
		messageHelper.setSubject(opsMail.getSubject());// 邮件主题
		messageHelper.setText(opsMail.getContext(), true);// 邮件内容 html格式
		if (!StringUtils.isBlank(opsMail.getCc())) {// 抄送
            if (opsMail.getCc().contains(";")) {
                messageHelper.setCc(opsMail.getCc().split(";"));// 邮件收信人
            }else {
                messageHelper.setCc(opsMail.getCc().split(","));// 邮件收信人
            }
		}
		if (!StringUtils.isBlank(opsMail.getBcc())) {// 密送
            if (opsMail.getBcc().contains(";")) {
                messageHelper.setBcc(opsMail.getBcc().split(";"));// 邮件收信人
            }else {
                messageHelper.setBcc(opsMail.getBcc().split(","));// 邮件收信人
            }
		}
		if (StringUtils.isNotEmpty(opsMail.getFileUrls()) && StringUtils.isNotEmpty(opsMail.getFileUrls().trim())) {// 保存附件
			List<String> urls = Arrays.asList(opsMail.getFileUrls().split(","));
			for (String url : urls) {
				File file = new File(url);
				// bug14381 判断邮件附件是否有效
				if (url.endsWith("pdf")) {
					try (PDDocument docu = PDDocument.load(file)) {
						PDFTextStripper st = new PDFTextStripper();
						// 测试的这个附件为空时在此句会抛异常
						String text = st.getText(docu);
						docu.close();
						logger.info("empty:" + text.trim().isEmpty());
					} catch (Exception e) {
						// 附件为空则会抛异常
						logger.error("采购附件为空error", e);
						throw e;
					}
				}
				String fileNm = file.getName();
				messageHelper.addAttachment(fileNm, file);
			}
		}
		// bug12513优化邮件附件发送
		if (StringUtils.isNotBlank(opsMail.getFileList())) {
			MailFileDto fileDto = JSONObject.parseObject(opsMail.getFileList(), MailFileDto.class);
			if (fileDto != null && CollectionUtils.isNotEmpty(fileDto.getFileList())) {
				for (MailFileInfo f : fileDto.getFileList()) {
					File file = new File(f.getFilePath() + "/" + f.getRandomFileName());
					String fileNm = f.getRealFileName();
					messageHelper.addAttachment(fileNm, file);
				}
			}
		}
		// 发送时间
		opsMail.setSendDate(new Date());
		messageHelper.setSentDate(opsMail.getSendDate());
		if (isPurchaseSend) {
			mailSender2.send(messageHelper.getMimeMessage());// 正式发送邮件
		} else
			mailSender.send(messageHelper.getMimeMessage());// 正式发送邮件
	}

	// 将邮件保存到数据库..
	private Integer saveMailToDb(OpsMail mail) {
		return opsMailMapper.insertSelective(mail);
	}

	// 验证邮箱地址
	private Boolean isEmail(String str) {
		Boolean isEmail = true;
		if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
			return false;
		} else {
			String expr = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";
			if (str.contains(",")) {
				String[] temp = str.split(",");
				for (String i : temp) {
					if (!i.matches(expr)) {
						isEmail = false;
					}
				}
			} else {
				if (!str.matches(expr)) {
					isEmail = false;
				}
			}
			return isEmail;
		}
	}

	/**
	 * 测试发送邮件
	 */
	@Override
	public void tSendMail() {
		try {

			mailSender.createMimeMessage();

			mailSender2.createMimeMessage();

			System.out.println(123);
			/*
			 * OpsMail opsMail = new OpsMail();
			 * opsMail.setMailTo("1363168197@qq.com");
			 * opsMail.setSubject("主题测试"); opsMail.setNickName("SMC(中国)有限公司");
			 * //opsMail.setFileUrls(
			 * "D:\\\\workSpaceC14717\\\\ops-parent\\\\各子项目说明.txt");
			 * StringBuffer con = new StringBuffer(); con.
			 * append("<h4>您好！</h4>\r\n  <h4 style=\"margin-left:10px;margin-top:5px\">您的用户名：123"
			 * ) .append("，密码是：请查收</h4>\r\n")
			 * .append("  <h4 style=\"margin-left:120px\">SMC(中国)有限公司</h4>");
			 * opsMail.setContext(con.toString()); sendMimeMail(opsMail);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 测试发送邮件 先存入数据库
	@Override
	public void doTestSaveDb() {
		MailDto mailVo = new MailDto();
		mailVo.setTo("1363168197@qq.com");
		mailVo.setSubject("主题测试");
		mailVo.setNickName("SMC(中国)有限公司");
		mailVo.setAttachments("D:\\\\workSpaceC14717\\\\ops-parent\\\\各子项目说明.txt");
		StringBuffer con = new StringBuffer();
		con.append("<h4>您好！</h4>\r\n  <h4 style=\"margin-left:10px;margin-top:5px\">您的用户名：123")
				.append("，密码是：请查收</h4>\r\n").append("  <h4 style=\"margin-left:120px\">SMC(中国)有限公司</h4>");
		mailVo.setText(con.toString());
		CommonResult r = opsMailApi.sendMailToDb(mailVo);
	}

	@Override
	public OpsMail savePurchaseMail(OpsMail mail) {
		// 写入邮件发送表，避免邮件发送出错时邮件无法发送
		mail.setStatus(SendStatusEnum.PURCHASEINIT.getType());
		opsMailDao.insertMail(mail);
		return mail;
	}

}
