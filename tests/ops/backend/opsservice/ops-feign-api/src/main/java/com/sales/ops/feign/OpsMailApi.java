package com.sales.ops.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.MailDto;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops邮件接口
 * @date ：Created in 2021/11/4 13:28
 */
@FeignClient(name = "opsApi", path = "opsApi", contextId = "mail")
public interface OpsMailApi {

	// 送邮件内容存入数据库，后续定时任务发送邮件
	@RequestMapping(value = "/opsMail/sendMailToDb", method = RequestMethod.POST)
	CommonResult<String> sendMailToDb(@RequestBody MailDto param);

	// 查询数据库待发送邮寄 ，返回结果
	@RequestMapping(value = "/opsMail/selectMailByDb", method = RequestMethod.POST)
	CommonResult<List<OpsMail>> selectMailByDb();

	// job 定时任务发送邮件
	@RequestMapping(value = "/opsMail/jobSendMail", method = RequestMethod.POST)
	CommonResult jobSendMail(@RequestBody OpsMail param);

	@RequestMapping(value = "/opsMail/jobSendMail", method = RequestMethod.POST)
	CommonResult<String> jobSendMailPurchase(@RequestBody OpsMail param,
			@RequestParam(required = false, defaultValue = "false") boolean isPurchaseSend);

	// bug 11473 自动发单 保存采购发单邮件到数据库
	@RequestMapping(value = "/opsMail/savePurchaseMail", method = RequestMethod.POST)
	public CommonResult<OpsMail> savePurchaseMail(@RequestBody OpsMail mail);

	// job 更新数据库数据
	@RequestMapping(value = "/opsMail/updateMailDb", method = RequestMethod.POST)
	CommonResult updateMailDb(@RequestBody OpsMail param);

	// bug11473 自动发单 采购邮件未成功发送的重试机制
	@RequestMapping("/opsMail/sendPurchaseJob")
	public CommonResult<Object> sendPurchaseJob();

}
