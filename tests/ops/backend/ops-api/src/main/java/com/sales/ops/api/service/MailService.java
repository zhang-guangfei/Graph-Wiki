package com.sales.ops.api.service;

import java.util.List;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.dto.util.MailDto;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：邮件发送服务
 * @date ：Created in 2021/11/4 9:09
 */
public interface MailService {

	// bug 11473 自动发单 保存采购发单邮件到数据库
	OpsMail savePurchaseMail(OpsMail mail);

	// 发送邮件 存入数据库不发
	Integer sendMail(MailDto mail) throws OpsException;

	// 查询数据库返回需要发的邮件
	List<OpsMail> selectMailByDb() throws OpsException;

	List<OpsMail> selectMailByDbTop10() throws OpsException;

	void jobSendMail(OpsMail mail, boolean isPurchaseSend) throws Exception;

	Integer updateMailDb(OpsMail mail) throws OpsException;

    List<OpsMail> selectMailByDbTop2() throws OpsException;

    // bug11473 自动发单 采购邮件未成功发送的重试机制
	Integer sendPurchaseJob() throws Exception;

	// 测试发送
	void tSendMail();

	// 测试存入数据库
	void doTestSaveDb();

}
