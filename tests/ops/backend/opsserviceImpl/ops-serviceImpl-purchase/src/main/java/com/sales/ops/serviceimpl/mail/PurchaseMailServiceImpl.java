package com.sales.ops.serviceimpl.mail;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smc.smccloud.model.DataTypeVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sales.ops.db.entity.OpsMail;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.mail.PurchaseMailService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;

@Service
@Transactional
public class PurchaseMailServiceImpl implements PurchaseMailService {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseMailServiceImpl.class);

	@Autowired
	private com.sales.ops.feign.OpsMailApi OpsMailApi;

	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;

	@Override
	public void sendMailJP(String path, String to) throws Exception {
		OpsMail opsMail = new OpsMail();
		opsMail.setMailTo(to);
		LocalDate today = new LocalDate(DateUtils.truncate(new Date(), Calendar.DATE));
		opsMail.setSubject("order" + today.toString());
		// opsMail.setNickName("SMC(中国)有限公司");
		opsMail.setFileUrls(path);
		// bug12338修改内容及抄送
		opsMail.setCc("cn_sales@smc.com.cn,mateng@smc.com.cn,zhangjing@smc.com.cn");
		// bug 需要将抄送人，增加到字典配置中，新增加字典3017
		ResultVo<DataTypeVO> ccData = dictDataServiceFeignApi.getDataTypeCodesInfo("3017", "JP");
		if (ccData.isSuccess() && StringUtils.isNotBlank(ccData.getData().getExtNote2())) {
			opsMail.setCc(ccData.getData().getExtNote2());
		}
		opsMail.setContext(" ");
		sendOperate(opsMail);
	}

	@Override
	public void sendMailOverSea(List<String> paths, String to) throws Exception {
		OpsMail opsMail = new OpsMail();
		opsMail.setMailTo(to);
		LocalDate today = new LocalDate(DateUtils.truncate(new Date(), Calendar.DATE));
		opsMail.setSubject("order" + today.toString());
		// opsMail.setNickName("SMC(中国)有限公司");
		paths.forEach(i -> {
			if (StringUtils.isBlank(opsMail.getFileUrls())) {
				opsMail.setFileUrls(i);
			} else
				opsMail.setFileUrls(opsMail.getFileUrls() + "," + i);
		});
		// bug12338修改内容及抄送
		//bug16880 WTSR2025000107 修改正文联络邮箱
		opsMail.setContext("<h4>Dear Sir or Madam,</h4><h4>Please find attached the orders of SMC China.</h4>"
				+ "<h4>THIS IS A COMPUTER GENERATED MAIL. PLEASE <font color=\"red\"> DO NOT REPLY </font> TO THIS MAIL.</h4> "
				+ " <h4 style=\"margin-top:5px\">For order confirmation please contact "
				+ "<font color=\"red\">deliveryoverseas@smc.com.cn.</font></h4>");
		opsMail.setCc("deliveryoverseas@smc.com.cn,cn_sales@smc.com.cn,mateng@smc.com.cn,zhangjing@smc.com.cn");
		// bug 需要将抄送人，增加到字典配置中，新增加字典3017
		ResultVo<DataTypeVO> ccData = dictDataServiceFeignApi.getDataTypeCodesInfo("3017", "OverSea");
		if (ccData.isSuccess() && StringUtils.isNotBlank(ccData.getData().getExtNote2())) {
			opsMail.setCc(ccData.getData().getExtNote2());
		}
		sendOperate(opsMail);
	}

	public void sendOperate(OpsMail opsMail) throws Exception {
		CommonResult<OpsMail> mail = OpsMailApi.savePurchaseMail(opsMail);
		if (mail.isSuccess()) {
			opsMail = mail.getData();
		} else {
			throw new Exception(mail.getMessage());
		}
		CommonResult<String> result = OpsMailApi.jobSendMailPurchase(opsMail, true);
		if (result.isSuccess()) {
			return;
		} else {
			// bug12686 若抛异常不回滚了，自动作业会报错
			// throw new Exception(result.getMessage());
			logger.error("自动发单发送邮件失败：" + result.getMessage());
		}
	}

	/**
	 * bug15266预处理结束，未登录型号不拦截，直接发邮件
	 */
	@Override
	public void sendUnKnownModel(Map<String, List<OpsRequestpurchase>> modelMap) {
		OpsMail opsMail = new OpsMail();
		String cc = "";
		String to = "";
		ResultVo<List<DataCodeVO>> ccData = dictDataServiceFeignApi.getDataCodes("3014");
		if (ccData.isSuccess() && CollectionUtils.isNotEmpty(ccData.getData())) {
			for (DataCodeVO v : ccData.getData()) {
				if (StringUtils.equals("to", v.getCode())) {
					if (StringUtils.isNotBlank(v.getCodeName()))
						to = v.getCodeName();
					if (StringUtils.isNotBlank(v.getExtNote1()))
						to = to + (StringUtils.isBlank(to) ? "" : ",") + v.getExtNote1();
					if (StringUtils.isNotBlank(v.getExtNote2()))
						to = to + (StringUtils.isBlank(to) ? "" : ",") + v.getExtNote2();
					if (StringUtils.isNotBlank(v.getExtNote3()))
						to = to + (StringUtils.isBlank(to) ? "" : ",") + v.getExtNote3();
				}
				if (StringUtils.equals("cc", v.getCode())) {
					if (StringUtils.isNotBlank(v.getCodeName()))
						cc = v.getCodeName();
					if (StringUtils.isNotBlank(v.getExtNote1()))
						cc = cc + (StringUtils.isBlank(cc) ? "" : ",") + v.getExtNote1();
					if (StringUtils.isNotBlank(v.getExtNote2()))
						cc = cc + (StringUtils.isBlank(cc) ? "" : ",") + v.getExtNote2();
					if (StringUtils.isNotBlank(v.getExtNote3()))
						cc = cc + (StringUtils.isBlank(cc) ? "" : ",") + v.getExtNote3();
				}
			}
		} else {
			to = "mateng@smc.com.cn";
			cc = "renyan@smc.com.cn,zhangjing@smc.com.cn";
		}
		opsMail.setMailTo(to);
		opsMail.setCc(cc);
		LocalDate today = new LocalDate(DateUtils.truncate(new Date(), Calendar.DATE));
		opsMail.setSubject("未登录型号提醒" + today.toString());
		StringBuffer content = new StringBuffer();
		content.append(
				"<h3>您好！</h3>\r\n  <h4 style=\"margin-left:20px;margin-top:10px\">此次采购处理中产品价格等信息缺失的数据如下：</h4>\n");
		content.append("<table>");
		content.append("<tr><th>型号</th><th>请购单号</th></tr>");
		modelMap.entrySet().forEach(a -> {
			content.append("<tr>");
			content.append("<td align=\"center\" style=\"width:300px\">" + a.getKey() + "</td>");
			Map<String, Boolean> orderno = new HashMap<String, Boolean>();
			for (OpsRequestpurchase o : a.getValue()) {
				orderno.put((o.getOrderno() + "-" + o.getItemno().toString()
						+ (o.getSplititemno() == null ? "" : ("-" + o.getSplititemno().toString()))), true);
			}
			content.append("<td style=\"word-wrap:break-word;word-break:break-all;\">" + orderno.keySet() + "</td>");
			content.append("</tr>");
		});
		content.append("</table>");
		content.append("\n\n\n\n <h4 style=\"margin-left:30px\">请及时进行数据维护，谢谢！</h4>\n");
		opsMail.setContext(content.toString());
		try {
			sendOperate(opsMail);
		} catch (Exception e) {
			logger.error("未登录型号发邮件提醒失败，其中型号有：" + modelMap.keySet(), e);
		}

	}

}
