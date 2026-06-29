package com.sales.ops.serviceimpl.purchase;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.dto.purchase.RequestPurchaseInfo;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.MailDto;
import com.sales.ops.dto.util.MailFileDto;
import com.sales.ops.dto.util.MailFileInfo;
import com.sales.ops.feign.OpsMailApi;
import com.sales.ops.service.po.BasePoService;
import com.sales.ops.service.purchase.MinPriceService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import com.smc.smccloud.service.OpsAttachedFileManageFeignApi;

/**
 * bug12457 最低售价附件发送
 * 
 * @author SMC892N
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MinPriceServiceImpl implements MinPriceService {

	private final static Logger logger = LoggerFactory.getLogger(MinPriceServiceImpl.class);

	@Autowired
	private OpsAttachedFileManageFeignApi opsAttachedFileManageFeignApi;

	@Autowired
	private BasePoService basePoService;

	@Autowired
	private OpsMailApi opsMailApi;

	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;

	public void insertFile(OpsPurchaseorder item, String mailJp, String textToJP, String mailSales, String textToSales,
			String cc) {
		MailFileDto fileDto = new MailFileDto();
		List<MailFileInfo> fileList = new ArrayList<MailFileInfo>();
		// 是否最低售价
		int flag = 0;
		// 标识：0发给日本、1发给业务
		int flagTo = 0;
		// 合并的情况
		// if (item.getMergeflag()) {
		// bug13662 请购表增加采购单号字段，取消合并表
		List<OpsRequestpurchase> rList = basePoService.getRequestPurchaseByPurchase(item.getOrderno(), item.getItemno(),
				item.getSplititemno());
		for (OpsRequestpurchase i : rList) {
			// 判断是否有最低售价
			if (StringUtils.isNotBlank(i.getInfojson())) {
				RequestPurchaseInfo info = JSONObject.parseObject(i.getInfojson(), RequestPurchaseInfo.class);
				if (info.isMinPrice()) {
					flag = 1;
					if (CollectionUtils.isNotEmpty(info.getFileList())) {
						info.getFileList().forEach(a -> {
							MailFileInfo temp = new MailFileInfo();
							a.setBusinessType("po");
							a.setCreateUser("po");
							if (i.getSplititemno() == null) {
								a.setBusinessKeyValue(i.getOrderno() + i.getItemno().toString());
							} else {
								a.setBusinessKeyValue(i.getOrderno() + i.getItemno().toString() + i.getSplititemno());
							}
							temp = temp.convert(a);
							fileList.add(temp);
						});
						opsAttachedFileManageFeignApi.insertFileInfo(info.getFileList());
					}
				}
			}
			if (StringUtils.equals("A", i.getWmtag())) {
				flagTo = 1;
			}
		}
		// } else {
		// OpsRequestpurchase request =
		// basePoService.getRequestPurchase(item.getOrderno(), item.getItemno(),
		// item.getSplititemno());
		// // 非整型号订货，发邮件给业务
		// if (StringUtils.equals("A", request.getWmtag())) {
		// flagTo = 1;
		// }
		// if (StringUtils.isNotBlank(request.getInfojson())) {
		// RequestPurchaseInfo info =
		// JSONObject.parseObject(request.getInfojson(),
		// RequestPurchaseInfo.class);
		// if (info.isMinPrice()) {
		// flag = 1;
		// if (CollectionUtils.isNotEmpty(info.getFileList())) {
		// info.getFileList().forEach(a -> {
		// MailFileInfo temp = new MailFileInfo();
		// a.setBusinessType("po");
		// a.setCreateUser("po");
		// if (request.getSplititemno() == null) {
		// a.setBusinessKeyValue(request.getOrderno() + "-" +
		// request.getItemno().toString());
		// } else {
		// a.setBusinessKeyValue(request.getOrderno() + "-" +
		// request.getItemno().toString() + "-"
		// + request.getSplititemno().toString());
		// }
		//
		// temp = temp.convert(a);
		// fileList.add(temp);
		// });
		// opsAttachedFileManageFeignApi.insertFileInfo(info.getFileList());
		// }
		// }
		// }
		// }
		// 没有最低售价订单
		if (flag == 0) {
			return;
		}

		// 生成邮件实体内容
		MailDto mail = new MailDto();
		mail.setCc(cc);
		if (item.getSplititemno() == null) {
			mail.setSubject("订单号" + item.getOrderno() + "-" + item.getItemno().toString() + "价格限制品的订单合同");
		} else {
			mail.setSubject("订单号" + item.getOrderno() + "-" + item.getItemno().toString() + "-"
					+ item.getSplititemno().toString() + "价格限制品的订单合同");
		}
		if (flagTo == 0) {
			// 发送邮件给日本
			mail.setText(textToJP);
			mail.setTo(mailJp);
		} else {
			// 型号拆分订货发邮件给业务
			mail.setTo(mailSales);
			mail.setText(textToSales);
		}
		// 需要发邮件的附件实体
		if (CollectionUtils.isNotEmpty(fileList)) {
			fileDto.setFileList(fileList);
			mail.setFileList(JSON.toJSONString(fileDto));
		}
		CommonResult<String> result = opsMailApi.sendMailToDb(mail);
		if (!result.isSuccess()) {
			// 保存失败
			logger.error("最低售价邮件实体保存数据库失败！" + result.getMessage());

		}
	}

	@Override
	public void minPriceOperate(List<OpsPurchaseorder> items) {
		String mailJp = "";
		String mailSales = "";
		String cc = "";
		String textToJP = "<h4>李文延先生，您好！</h4><h4>附件是价格限制品的合同。</h4><h4>价格高于最低售价，请查收。</h4><h4>订单请接入，谢谢！</h4><h4>系统自动发送，回复请联络 smccnorder@smc.com.cn wangyanping@smc.com.cn</h4>";
		String textToSales = "<h4>您好！</h4><h4>附件为型号拆分订单对应的最低售价附件，请确认！</h4>";
		ResultVo<List<DataCodeVO>> mail = dictDataServiceFeignApi.getDataCodes("3004");
		if (mail.isSuccess() && mail.getData() != null && mail.getData().size() == 3) {
			for (DataCodeVO v : mail.getData()) {
				if (StringUtils.equals("jp", v.getCode())) {
					mailJp = v.getCodeName();
				}
				if (StringUtils.equals("sales", v.getCode())) {
					mailSales = v.getCodeName();
				}
				if (StringUtils.equals("cc", v.getCode())) {
					cc = v.getCodeName();
					if (StringUtils.isNotBlank(v.getExtNote1())) {
						cc = cc + "," + v.getExtNote1();
					}
					if (StringUtils.isNotBlank(v.getExtNote2())) {
						cc = cc + "," + v.getExtNote2();
					}
					if (StringUtils.isNotBlank(v.getExtNote3())) {
						cc = cc + "," + v.getExtNote3();
					}
				}
			}

		} else {
			mailJp = "mateng@smc.com.cn";
			mailSales = "mateng@smc.com.cn";
			cc = "hesai@smc.com.cn";
			textToJP = "字典中未获取到邮箱信息，需要手工进行处理，需确认是正式数据还是测试数据，是否需要发送至日本。";
			textToSales = "字典中未获取到邮箱信息，需要手工进行处理，需确认是正式数据还是测试数据，是否需要发送至业务确认。";
		}

		for (OpsPurchaseorder o : items) {
			insertFile(o, mailJp, textToJP, mailSales, textToSales, cc);
		}

	}

}
