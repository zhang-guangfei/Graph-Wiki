package com.sales.ops.service.deliver;

import java.util.List;

import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.dto.util.CommonResult;

public interface ChangeLogService {

	// 接单返信
	CommonResult<Integer> operateMainLog();

	List<OpsPurchaseinvoice> getPurchaseInvoice(String orderno, Integer itemno, Integer splitno);

	List<OpsPurchaseinvoice> getPurchaseInvoiceByPono(String pono, Integer lineitem);
}
