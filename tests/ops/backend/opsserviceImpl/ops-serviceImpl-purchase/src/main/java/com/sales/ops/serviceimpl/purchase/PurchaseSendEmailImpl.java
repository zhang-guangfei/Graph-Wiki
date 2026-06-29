package com.sales.ops.serviceimpl.purchase;

import com.sales.ops.db.dao.OpsMailMapper;
import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.MailDto;
import com.sales.ops.enums.MailConfigEnum;
import com.sales.ops.feign.OpsMailApi;
import com.sales.ops.service.purchase.PurchaseSendEmail;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseSendEmailImpl implements PurchaseSendEmail {

	@Autowired
	private OpsMailApi opsMailApi;

	@Autowired
	private OpsMailMapper opsMailMapper;

	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;

	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 存入定时发送邮件表中
	 * 
	 * @param path
	 * @return
	 */
	@Override
	public CommonResult sendMailJP(String path) {
		// Todo
		MailDto mailVo = new MailDto();
		mailVo.setTo(MailConfigEnum.JP.getType());
		Date date = new Date(System.currentTimeMillis());
		String subject = "order" + formatter.format(date);
		mailVo.setSubject(subject);
		mailVo.setNickName("SMC(中国)有限公司");
		mailVo.setAttachments(path);
		mailVo.setCc(
				"order@smc.com.cn,renyan@smc.com.cn,zhangjing@smc.com.cn,mateng@smc.com.cn,tangqixu@smc.com.cn,duanxiaofeng@smc.com.cn,wangqian@smc.com.cn,xuxiaoying@smc.com.cn");
		StringBuffer con = new StringBuffer();
		con.append("<h4>JP-order,日本订单发送</h4>\r\n ");
		mailVo.setText(con.toString());
		CommonResult r = opsMailApi.sendMailToDb(mailVo);
		return r;
	}

	@Override
	public List<CommonResult> sendMailOverSea(List<String> paths) {
		MailDto mailVo = new MailDto();
		List<CommonResult> commonResults = new ArrayList<>();
		paths.stream().forEach(path -> {
			// 区分AP行业的发送模板
			if (path.contains("AP")) {
				mailVo.setTo(MailConfigEnum.AP.getType());
			} else if (path.contains("GN")) {
				mailVo.setTo(MailConfigEnum.GN.getType());
			}
			// 海外的模板
			else {
				mailVo.setTo(MailConfigEnum.OVERSEA.getType());
			}
			// 追加邮件标题
			mailVo.setSubject(path.substring(path.length() - 6, path.length() - 4));
			mailVo.setCc(
					"order@smc.com.cn,renyan@smc.com.cn,zhangjing@smc.com.cn,mateng@smc.com.cn,tangqixu@smc.com.cn,duanxiaofeng@smc.com.cn,wangqian@smc.com.cn");
			Date date = new Date(System.currentTimeMillis());
			String subject = "order" + " " + formatter.format(date);
			mailVo.setSubject(subject);
			mailVo.setAttachments(path);
			StringBuffer con = new StringBuffer();
			con.append(
					"<h4>Dear Sir or Madam,</h4>\r\n  <h4 style=\"margin-top:5px\">Attachment is Chinese order.\r\n ")
					.append("To confirm whether you have entered the order,\r\n")
					.append("Pleaser reply to me based on this email.Thanks !\r\n\r\n")
					.append("Best regards!</h4>\r\n");
			mailVo.setText(con.toString());
			CommonResult r = opsMailApi.sendMailToDb(mailVo);
			commonResults.add(r);
		});
		return commonResults;
	}

	@Override
	public void mailMessage(int dealsuppilyid, String message) {
		// bug12827更改抄送为字典配置
		String cc = "";
		ResultVo<List<DataCodeVO>> ccData = dictDataServiceFeignApi.getDataCodes("3005");
		if (ccData.isSuccess() && CollectionUtils.isNotEmpty(ccData.getData())) {
			int i = 1;
			for (DataCodeVO v : ccData.getData()) {
				if (StringUtils.isNotBlank(v.getCodeName())) {
					cc = cc + v.getCodeName();
					if (i < ccData.getData().size()) {
						cc = cc + ",";
					}
				}
				i = i + 1;
			}

		} else {
			cc = "xuxiaoying@smc.com.cn,smccnorder@smc.com.cn,order@smc.com.cn,zhangjing@smc.com.cn,mateng@smc.com.cn,tangqixu@smc.com.cn,duanxiaofeng@smc.com.cn,hesai@smc.com.cn";
		}
		MailDto mailVo = new MailDto();
		mailVo.setTo("renyan@smc.com.cn");
		mailVo.setNickName("SMC(中国)有限公司");
		mailVo.setCc(cc);
		String subject = "";

		switch (dealsuppilyid) {
		case 1:
			// 制造订单发送
			subject = "中国工厂采购订单发送明细";
			break;
		case 3:
			// 制造错误订单发送
			subject = "中国工厂采购订单发送错误";
			mailVo.setTo("duanxiaofeng@smc.com.cn");
			// bug12373抄送增加业务人员邮箱
			mailVo.setCc(
					"smccnorder@smc.com.cn,order@smc.com.cn,renyan@smc.com.cn,zhangjing@smc.com.cn,mateng@smc.com.cn");
			break;
		case 2:
			subject = "广州制造采购订单发送明细";
			break;
		default:
			subject = "采购订单发送明细";
		}
		mailVo.setSubject(subject);

		StringBuffer con = new StringBuffer();
		con.append("<h4>" + message + "</h4>\r\n ");
		mailVo.setText(con.toString());
		CommonResult<String> r = opsMailApi.sendMailToDb(mailVo);
	}


	@Override
	public void PurchaseUnusualMailSend(String msg) {
		//收件人和抄送人
		StringBuilder to = new StringBuilder();
		StringBuilder cc = new StringBuilder();

		//读取字典
		ResultVo<List<DataCodeVO>> ccData = dictDataServiceFeignApi.getDataCodes("3015");
		if (ccData.isSuccess() && CollectionUtils.isNotEmpty(ccData.getData())) {
			for (DataCodeVO data : ccData.getData()) {
				if (StringUtils.isNotBlank(data.getCode()) && "to".equals(data.getCode())) {
					if (StringUtils.isNotBlank(data.getCodeName())) {
						to.append(data.getCodeName());
						to.append(",");
					}
					if (StringUtils.isNotBlank(data.getExtNote1())) {
						to.append(data.getExtNote1());
						to.append(",");
					}
					if (StringUtils.isNotBlank(data.getExtNote2())) {
						to.append(data.getExtNote2());
						to.append(",");
					}
					if (StringUtils.isNotBlank(data.getExtNote3())) {
						to.append(data.getExtNote3());
						to.append(",");
					}
				}
				if (StringUtils.isNotBlank(data.getCode()) && "cc".equals(data.getCode())) {
					if (StringUtils.isNotBlank(data.getCodeName())) {
						cc.append(data.getCodeName());
						cc.append(",");
					}
					if (StringUtils.isNotBlank(data.getExtNote1())) {
						cc.append(data.getExtNote1());
						cc.append(",");
					}
					if (StringUtils.isNotBlank(data.getExtNote2())) {
						cc.append(data.getExtNote2());
						cc.append(",");
					}
					if (StringUtils.isNotBlank(data.getExtNote3())) {
						cc.append(data.getExtNote3());
						cc.append(",");
					}
				}
			}
		}
		//去尾逗号
		if (to.length() > 0 && to.charAt(to.length() - 1) == ',') {
			to.deleteCharAt(to.length() - 1);
		}
		if (cc.length() > 0 && cc.charAt(to.length() - 1) == ',') {
			cc.deleteCharAt(cc.length() - 1);
		}
		//默认值
		if (StringUtils.isBlank(to)) {
			to.append("zhangguangfei@smc.com.cn");
		}
		if (StringUtils.isBlank(cc)) {
			cc.append("zhangguangfei@smc.com.cn");
		}
		//邮件配置
		MailDto mailVo = new MailDto();
		mailVo.setSubject("中国工厂采购订单发送错误");
		mailVo.setNickName("SMC(中国)有限公司");
		mailVo.setTo(to.toString());
		mailVo.setCc(cc.toString());
		mailVo.setText(msg);
		CommonResult<String> r = opsMailApi.sendMailToDb(mailVo);
	}

	/**
	 * 采购删单报错，邮件提醒
	 * 
	 * @param message
	 */
	@Override
	public CommonResult purchaseDelMessage(String message) {
		MailDto mailVo = new MailDto();
		mailVo.setTo("renyan@smc.com.cn");
		mailVo.setNickName("SMC(中国)有限公司");
		mailVo.setCc(
				"xuxiaoying@smc.com.cn,smccnorder@smc.com.cn,order@smc.com.cn,zhangjing@smc.com.cn,mateng@smc.com.cn,tangqixu@smc.com.cn,duanxiaofeng@smc.com.cn,wangqian@smc.com.cn");
		mailVo.setSubject("采购删单发生错误");
		StringBuffer con = new StringBuffer();
		con.append("<h4>" + message + "</h4>\r\n ");
		mailVo.setText(con.toString());
		CommonResult r = opsMailApi.sendMailToDb(mailVo);
		return r;

	}

	/**
	 * 测试邮件发送
	 */
	@Override
	public void sendMailTest() {

		OpsMail opsMail = opsMailMapper.selectByPrimaryKey(Long.valueOf(5));

		CommonResult result = opsMailApi.jobSendMail(opsMail);
	}

}
