package com.sales.ops.service.purchase;

import java.util.List;
import java.util.Map;

import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.db.entity.Supplier;

public interface PurchaseExportTxtService {

	public String sendOrder() throws Exception;// 123

	void sendPre(int dealsuppilyid) throws Exception;

	void reCalExportday(int dealsuppilyid) throws Exception;

	public String textInfoJP(String versionLike) throws Exception;

	public String writeToTxtJP(String text) throws Exception;

	public List<String> writeToExcel(String supplierNo, Supplier su, Map<String, OpsWarehouse> opswarehousemap,
			String versionLike) throws Exception;
}
