package com.sales.ops.service.mail;

import java.util.List;
import java.util.Map;

import com.sales.ops.db.entity.OpsRequestpurchase;

public interface PurchaseMailService {

	public void sendMailJP(String path, String to) throws Exception;

	public void sendMailOverSea(List<String> paths, String to) throws Exception;

	// bug15266未登录型号发邮件
	public void sendUnKnownModel(Map<String, List<OpsRequestpurchase>> modelMap);

}
