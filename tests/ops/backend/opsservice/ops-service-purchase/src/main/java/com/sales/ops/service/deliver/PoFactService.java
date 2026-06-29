package com.sales.ops.service.deliver;

import java.util.List;

import com.sales.ops.dto.purchase.PurchaseReplyInfo;

public interface PoFactService {

	// 供应商返信处理时写入fact表
	void insertFactReply(List<PurchaseReplyInfo> info);

	// 预到货时发票写入fact表
	void insertFactPre(PurchaseReplyInfo o);

}
