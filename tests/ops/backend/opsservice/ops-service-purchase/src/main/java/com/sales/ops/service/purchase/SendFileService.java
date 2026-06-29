package com.sales.ops.service.purchase;

import java.util.List;
import java.util.Map;

import com.sales.ops.db.entity.OpsCustomer;
import com.sales.ops.db.entity.OpsPoAutosendRemarkConfig;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.dto.purchase.PurchaseSendPdfDto;

/**
 * bug13409 采购发单增加pdf文件
 * 
 * @author SMC892N
 *
 */
public interface SendFileService {

	public String exportPdfFile(String warehouseid, String supplierNo, List<OpsPurchaseinvoice> list, String fullname,
			Integer payment, OpsWarehouse opswarehouse, Map<String, Boolean> resMap,
			Map<String, OpsCustomer> customerMap, List<OpsPoAutosendRemarkConfig> configList) throws Exception;

	public String savePdf(String warehouse, String supplier, List<PurchaseSendPdfDto> list, Map<String, Object> map)
			throws Exception;
}
