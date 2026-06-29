package com.sales.ops.db.extdao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sales.ops.db.entity.ImpInvoiceDetailPack;
import com.sales.ops.db.entity.OpsCustomerProperty;

public interface PurchaseOrderDao {

	// bug10705 马腾 增加供应商查询
	// bug12349增加客户所属集团的查询
	@Select("SELECT * FROM ops_customer_property where customerNo = #{customerNo} and"
			+ " ((supplierId like '%'+#{supplierId}+'%') or supplierId is null) order by supplierId DESC")
	public List<OpsCustomerProperty> getVIPCode(@Param("customerNo") String customerNo,
			@Param("supplierId") String supplierId);

	@Select("select distinct supplierId from ops_purchaseInvoice where stateCode='0'"
			+ " and supplierId != 'CN' and supplierId != 'CM' and supplierId != 'YZ' and"
			+ " supplierId!='JP' and supplierId!='GZ' AND supplierId!='CT' AND supplierId!='TZ' ")
	public List<String> getInvoiceSupplierOverSea();

	@Select("select distinct modelNo from OverSeaProd where Pid='Y'")
	public List<String> getPidY();

	/**
	 * 获取配置表中有没有对应数据
	 */
	@Select("SELECT COUNT(*) FROM ops_req_po_mapping WHERE RequestPurchaseId = #{requestIds} }")
	public Integer getOpsPoMapping(@Param("requestIds") Long requestIds);

	@Select("SELECT a.order_no,a.item_no,a.split_item_no,a.model_no,a.supplier_code,a.roHS_code,"
			+ "sum(a.quantity) quantity FROM Imp_invoice_detail_pack a"
			+ "  left join ops_purchaseInvoice b on a.pono=b.poNo and a.poItemNo=b.lineItem"
			+ "  left join ops_purchaseOrder_cancel_log c on a.order_No=c.orderNo and a.item_No=c.itemno and"
			+ " ((a.split_Item_No=c.splitItemNo and a.split_Item_No is not null and c.splitItemNo is not null)"
			+ " or (a.split_Item_No is null and c.splitItemNo is null))"
			+ "  where invoice_no=#{invoiceno} and invoice_id=#{invoiceid} and b.id is null and a.[status] in (1,2)"
			+ " and c.id is null "
			+ " group by a.order_no,a.item_no,a.split_item_no,a.model_no,a.supplier_code,a.roHS_code")
	public List<ImpInvoiceDetailPack> getNoPurchase(@Param("invoiceid") Long invoiceid,
			@Param("invoiceno") String invoiceno);

	// bug8614获取最大单号
	@Select("select max(orderno) from ops_requestPurchase_cancel_log where stateCode='B'")
	public String maxAddPurchaseNo();

	// bug 9920 优化制造接收方字段，改位通过配置表读取 B91717
	@Select(" select  TOP 1  manufactureAccepter from ops_po_destination_config where smccode = #{smccode}  and supplierid = #{supplierId} AND manufactureAccepter<>'' GROUP BY manufactureAccepter ")
	public String getAccepter(@Param("smccode") String smccode, @Param("supplierId") String supplierId);

}
