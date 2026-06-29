package com.sales.ops.db.extdao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.sales.ops.db.entity.OpsPurchaseorder;

public interface PurchaseReplyDao {

	// bug13395 获取返信延期的采购单
	@Select("select a.* from ops_purchaseOrder a left join ops_purchaseInvoice b"
			+ " on a.orderno=b.orderno and a.itemno=b.itemno and isnull(a.splititemno,0)=isnull(b.splitItemNo,0)"
			+ " where b.invoiceNo is null and FacExpdate is null and"
			+ " a.stateCode<'5' and ((DATEDIFF(day, replyExportDate, getdate()) > 0)"
			+ " or (FORMAT(replyExportDate, 'yyyy') <> '9999' and FORMAT(replyExportDate, 'yyyy') <> '9900'"
			+ " and substring(FORMAT(replyExportDate, 'yyyy'),1,2) = '99'))")
	public List<OpsPurchaseorder> getDelayPurchase();
}
