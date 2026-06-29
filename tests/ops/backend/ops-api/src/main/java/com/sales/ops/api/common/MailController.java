package com.sales.ops.api.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sales.ops.api.service.MailService;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.MailDto;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：邮件发送服务
 * @date ：Created in 2021/11/4 10:46
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/opsMail")
public class MailController {

	@Autowired
	private MailService mailService;

	/**
	 * 测试发送 邮件 *
	 * 
	 * @return
	 */
	@RequestMapping("/doTest")
	public void doTe() {
		mailService.tSendMail();
	}

	/**
	 * 测试发送 邮件 存入数据库 *
	 * 
	 * @return
	 */
	@RequestMapping("/doTestSaveDb")
	public void doTestSaveDb() {
		mailService.doTestSaveDb();
	}

	/**
	 * 发送邮件内容存入数据库，后续定时任务发送邮件
	 * 
	 * @param opsMail
	 * @return
	 */
	@RequestMapping("/sendMailToDb")
	public CommonResult<String> sendEmailToDb(@RequestBody MailDto opsMail) {
		try {
			Integer result = mailService.sendMail(opsMail);
			return result <= 0 ? CommonResult.failure("保存邮件失败")
					: CommonResult.success("保存数据库成功，已保存数据库 " + result + "条数据");
		} catch (OpsException e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 查询数据库待发送邮寄 ，返回结果
	 * 
	 * @return
	 */
	@RequestMapping("/selectMailByDb")
	public CommonResult<List<OpsMail>> selectMailByDb() {
		try {
			// bugid:11421 c14717 20230712
			List<OpsMail> list = mailService.selectMailByDbTop2();
			return CollectionUtils.isEmpty(list) ? CommonResult.failure("无邮件发送数据") : CommonResult.success(list);
		} catch (OpsException e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * job 定时任务发送邮件
	 * 
	 * @param opsMail
	 * @return
	 */
	@RequestMapping("/jobSendMail")
	public CommonResult jobSendMail(@RequestBody OpsMail opsMail,
			@RequestParam(required = false, defaultValue = "false") boolean isPurchaseSend) {
		try {
			mailService.jobSendMail(opsMail, isPurchaseSend);
			return CommonResult.success("发送成功");
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	/**
	 * 更新数据库数据
	 * 
	 * @param opsMail
	 * @return
	 */
	@RequestMapping("/updateMailDb")
	public CommonResult updateMailDb(@RequestBody OpsMail opsMail) {
		try {
			Integer result = mailService.updateMailDb(opsMail);
			return result <= 0 ? CommonResult.failure("无更新") : CommonResult.success("更新成功，更新数据库 " + result + "条数据");
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	// bug11473 自动发单 采购邮件未成功发送的重试机制
	@RequestMapping("/sendPurchaseJob")
	public CommonResult<Object> sendPurchaseJob() {
		try {
			Integer num = mailService.sendPurchaseJob();
			return CommonResult.success(num);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}

	// bug 11473 自动发单 保存采购发单邮件到数据库
	@RequestMapping("/savePurchaseMail")
	public CommonResult<OpsMail> savePurchaseMail(@RequestBody OpsMail mail) {
		try {
			mail = mailService.savePurchaseMail(mail);
			return CommonResult.success(mail);
		} catch (Exception e) {
			return CommonResult.failure(e.getMessage());
		}
	}
}
