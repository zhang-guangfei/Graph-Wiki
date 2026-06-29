package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.poDeliver.PoDeliverSelectDto;
import com.sales.ops.dto.poDeliver.SelectFilter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PoDeliverDao {

	@Select("<script>"
			+ "select o.id, a.orderNo,a.itemNo,a.splitItemNo,a.modelNo,a.supplierId,o.stateCode,a.detailStatusCode,a.statusDescription,a.quantity"
			+ ",a.produceFactory,o.transType,a.transtype_fact,a.beginProduceDate,a.ordtype"
			+ ",a.dlvModDate1,a.dlvModDate2,a.dlvModDate3,a.dlvModDate1Time,a.dlvModDate2Time,a.dlvModDate3Time,a.reasonRemark"
			+ ",a.hopeReceiveWarehouse,o.hopeDeliveryDate ,a.replyOrderDate,o.shikomiAnswerNo,o.deptNo,o.customerNo,o.userNo, o.end_user,o.prepareOrderNo"
			+ ",isnull(a.port_arrivedate,i.port_arrivedate) as portarrivedate"
			+ ",isnull(a.customs_date,i.customs_date) as customsdate,a.replyOrderNo,a.purchaseDate"
			+ ",a.hopeExportDate,imDateTheory as preArriveDate"
			+ ",imdatetheoryafter as imdatetheoryafter"
			+ ",isnull(imDateInFact,i.arrive_date) as arriveDate,"
			+"isnull(a.src_delivery_time,replyExportDate)"
			+ " as deliveryPlanA,o.mergeflag,o.specMark,a.receiveWarehouseId from ops_purchaseInvoice a with(nolock)\n"
			+ " left join ops_purchaseOrder o on a.orderno=o.orderno and a.itemno=o.itemno and "
			+ " isnull(a.splitItemNo,0)=isnull(o.splitItemNo,0) \n"
			+ " left join imp_invoice_master i on a.invoiceNo=i.invoice_no and a.invoiceId=i.id \n" +
			"<where> "+

			" <if test='orderNoInfoList != null  and orderNoInfoList.size() >0'> " +
			"EXISTS (\n" +
			"        SELECT 1\n" +
			"        FROM (VALUES\n" +
			"        <foreach collection=\"orderNoInfoList\" item=\"item\" separator=\",\">\n" +
			"            (#{item.orderNo}, #{item.itemNo}, #{item.splitNo})\n" +
			"        </foreach>" +
			"        ) AS t(orderno, itemno, splitItemNo)\n" +
			"        WHERE \n" +
			"            a.orderno = t.orderno\n" +
			"            AND a.itemno = t.itemno\n" +
			"            AND (\n" +
			"                a.splitItemNo = t.splitItemNo\n" +
			"                OR (a.splitItemNo IS NULL AND t.splitItemNo IS NULL)\n" +
			"            )\n" +
			"    )"+
			"</if>"
			+ " <if test='stateCodeList != null  and stateCodeList.size() >0'> "
			+ " and o.stateCode in "
			+ "<foreach collection='stateCodeList' item='item' separator=',' open='(' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ "</if>"
			+ " <if test='detailStatusCodeList != null  and detailStatusCodeList.size() >0'> "
			+ " and a.detailStatusCode in "
			+ "<foreach collection='detailStatusCodeList' item='item' separator=',' open='(' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ "</if>"
			+ " <if test='purchasetypeList != null  and purchasetypeList.size() >0'> "
			+ " and a.purchasetype in "
			+ "<foreach collection='purchasetypeList' item='item' separator=',' open='(' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ "</if>"
			+ " <if test='ordtypeList != null  and ordtypeList.size() >0'> "
			+ " and a.ordtype in "
			+ "<foreach collection='ordtypeList' item='item' separator=',' open='(' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ "</if>"
			+ " <if test='supplierIdList != null  and supplierIdList.size() >0'> "
			+ " and a.supplierId in "
			+ "<foreach collection='supplierIdList' item='item' separator=',' open='(' close=')'>"
			+ "#{item}"
			+ "</foreach>"
			+ "</if>"
			+ " <if test='orderno != null  and orderno != \"\"'> and a.orderno=#{orderno} </if>"
			+ " <if test='itemno != null'> and a.itemno=#{itemno} </if>"
			+ " <if test='splitno != null'> and a.splitItemNo=#{splitno} </if>"
			+ " <if test='modelNo != null and modelNo != \"\"'> and a.modelNo=#{modelNo} </if>"
			+ " <if test='purchasetype != null'> and a.purchasetype=#{purchasetype} </if>"
			+ " <if test='ordtype != null'> and a.ordtype=#{ordtype} </if>"
			+ " <if test='customerno != null and customerno != \"\"'> and a.customerno=#{customerno} </if>"
			+ " <if test='supplierId != null'> and a.supplierId=#{supplierId} </if>"
			+ " <if test='produceFactory != null'> and a.produceFactory=#{produceFactory} </if>"
			+ " <if test='stateCode != null'> and o.stateCode=#{stateCode} </if> "
			+ " <if test='detailStatusCode != null'> and a.detailStatusCode=#{detailStatusCode} </if> "
			+ " <if test='purchaseDateStart != null'> and o.purchaseDate<![CDATA[ >= ]]>#{purchaseDateStart} </if> "
			+ " <if test='purchaseDateEnd != null'> and o.purchaseDate<![CDATA[ <= ]]>#{purchaseDateEnd} </if> "
			+ "</where> " +
			"</script>")
	List<PoDeliverSelectDto> selectDeliverInfo(SelectFilter info);

	@Select("<script>select * from ops_po_delivery_plan where newest=1 and del_flag=0 and"
			+ "<foreach collection='list' item='item' separator='or' open='(' close=')'>"
			+ " (order_no=#{item.orderNo} and item_no=#{item.itemNo} "
			+ " <if test='item.splitItemNo !=null'> and split_no=#{item.splitItemNo} </if>"
			+ " <if test='item.splitItemNo ==null'> and split_no is null </if>) </foreach></script>")
	List<OpsPoDeliveryPlan> selectPlanByOrderNo(@Param("list") List<PoDeliverSelectDto> list);

	@Select("<script>select * from ops_po_delivery_fact where del_flag=0 and"
			+ "<foreach collection='list' item='item' separator='or' open='(' close=')'>"
			+ " (order_no=#{item.orderNo} and item_no=#{item.itemNo} "
			+ " <if test='item.splitItemNo !=null'> and split_no=#{item.splitItemNo} </if>"
			+ " <if test='item.splitItemNo ==null'> and split_no is null </if>) </foreach></script>")
	List<OpsPoDeliveryFact> selectFactByOrderNo(@Param("list") List<PoDeliverSelectDto> list);

	@Select("<script>select * from ops_impdata where "
			+ "<foreach collection='list' item='item' separator='or' open='(' close=')'>"
			+ " (orderno=#{item.orderNo} and itemno=#{item.itemNo} "
			+ " <if test='item.splitItemNo !=null'> and splitItemNo=#{item.splitItemNo} </if>"
			+ " <if test='item.splitItemNo ==null'> and splitItemNo is null </if>) </foreach></script>")
	List<OpsImpdata> selectImpdataByOrderNo(@Param("list") List<PoDeliverSelectDto> list);

	@Select("<script>select * from Imp_invoice_detail where status='0' and"
			+ "<foreach collection='list' item='item' separator='or' open='(' close=')'>"
			+ " (order_no=#{item.orderNo} and item_no=#{item.itemNo} "
			+ " <if test='item.splitItemNo !=null'> and split_item_no=#{item.splitItemNo} </if>"
			+ " <if test='item.splitItemNo ==null'> and split_item_no is null </if>) </foreach></script>")
	List<ImpInvoiceDetail> selectImpDetailByOrderNo(@Param("list") List<PoDeliverSelectDto> list);

	@Select("<script>select * from ops_requestPurchase where "
			+ "<foreach collection='list' item='item' separator='or' open='(' close=')'>"
			+ " (orderno=#{item.orderNo} and itemno=#{item.itemNo} "
			+ " <if test='item.splitItemNo !=null'> and splitItemNo=#{item.splitItemNo} </if>"
			+ " <if test='item.splitItemNo ==null'> and splitItemNo is null </if>) </foreach></script>")
	List<OpsRequestpurchase> selectRequestPurchaseByOrderNo(@Param("list") List<PoDeliverSelectDto> list);

	@Select("select * from imp_invoice_master where invoice_no=#{invoiceNo} and id=#{invoiceId}")
	List<ImpInvoiceMaster> selectImpInvoiceMasterByInvoiceNo(String invoiceNo,Long invoiceId);
	@Select("select * from ops_po_invoice where invoice_no=#{invoiceNo} and invoice_id=#{invoiceId}")
	List<OpsPoInvoice> selectOpsPoInvoiceByInvoiceNo(String invoiceNo,Long invoiceId);

}
