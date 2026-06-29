package com.sales.ops.service.purchase;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.inquiry.InquiryQueryPurchaseDto;
import com.sales.ops.dto.purchase.PurchaseModifyApplyInfoDto;

import java.util.List;

public interface PurchaseBatchEditService {

	/**
	 * bug 12360 批量处理采购单修改设计
	 * 提供查询采购信息
	 * @param orderNos
	 * @return
	 */
	List<PurchaseModifyApplyInfoDto> selectRequestInfo(List<String> orderNos) throws OpsException;

	List<InquiryQueryPurchaseDto> selectPurchaseOrder(List<String> orderNos) throws OpsException;
}
