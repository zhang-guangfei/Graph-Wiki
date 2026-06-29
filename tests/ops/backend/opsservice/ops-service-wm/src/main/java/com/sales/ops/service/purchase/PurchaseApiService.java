package com.sales.ops.service.purchase;

import java.util.List;
import java.util.Map;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;

public interface PurchaseApiService {

	public boolean addDeletePurchase(String invoiceNo, long invoiceId) throws Exception;

    List<String> manufactureSupplier() throws OpsException;

    public Map<String, String> updateInvoice(List<PurchaseReplyInfo> list) throws Exception;

	public Integer impdata(String invoiceNo, long invoiceId) throws Exception;
}
