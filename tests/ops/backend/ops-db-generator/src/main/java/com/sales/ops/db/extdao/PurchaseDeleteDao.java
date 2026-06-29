package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface PurchaseDeleteDao {

	/**
	 * 写入采购删单表
	 *
	 * @return
	 */
	// bug12284增加外部信息json字段
	// bug19576 增加最终用户字段
	@Insert("insert into ops_PurchaseOrder_cancel_log (id,orderNo,itemNo,splitItemNo,mergeflag,modelNo,"
			+ "quantity,stdPrice,transType,purchaseDate,hopeDeliveryDate,supplierId,ordType,specMark,"
			+ "receiveWarehouseId,remark,hopeExportDate,inqNo,shikomiAnswerNo ,operatorId,deptNo,"
			+ "apply_dept_no,smcCode,invoiceNo,hsCode,greenCode,productType,customerNo,userNo,salesInfoNo,"
			+ "purchaseType,supplierPartNo,importFobPriceOriginal,importCurrencyId,serverremark,updateTime,"
			+ "corderNO,inventoryPropertyId,replyOrderNo,dlvModDate,qtyReceive,finishDate,insertTime,"
			+ "stateCode,operator,hopeReceiveWarehouse,inspectionType,po_price,begin_produce_date,"
			+ "customs_date,port_arrivedate,information,sendVersion,infoJson,end_user) "
			+ "select id,orderNo,itemNo,splitItemNo,mergeflag,modelNo,quantity,stdPrice,transType,"
			+ "purchaseDate,hopeDeliveryDate,supplierId,ordType,specMark,receiveWarehouseId,remark,"
			+ "hopeExportDate,inqNo,shikomiAnswerNo ,operatorId,deptNo,apply_dept_no,smcCode,invoiceNo,"
			+ "hsCode,greenCode,productType,customerNo,userNo,salesInfoNo,purchaseType,supplierPartNo,"
			+ "importFobPriceOriginal,importCurrencyId,serverremark,updateTime,corderNO,inventoryPropertyId,"
			+ "replyOrderNo,dlvModDate,qtyReceive,finishDate,GETDATE(),#{statecode},#{operator},"
			+ "hopeReceiveWarehouse,inspectionType,po_price,begin_produce_date,customs_date,port_arrivedate,information,sendVersion,infoJson,end_user "
			+ "  from ops_PurchaseOrder where id = #{id}")
	public Boolean insertPurchase(@Param("id") Long id, @Param("statecode") String statecode,
			@Param("operator") String operator);

	/**
	 * 写入采购删单表
	 *
	 * @return
	 */
	// bug12284增加外部信息json字段
	// bug13662增加采购单号字段
	// bug19576 增加最终用户字段
	@Insert("insert into ops_requestPurchase_cancel_log(\n "
			+ " id,orderNo,itemNo,splitItemNo,mergeflag,customerNo,userNo,deptNo,apply_dept_no,inqNo ,ordType,modelNo,quantity,stdPrice,specMark,shikomiAnswerNo,shikomiRemainQty,hopeDeliveryDate,remark,salesInfoNo,requestTime,productTag,productTagRemark,requestWarehouseId\n"
			+ "  ,purchaseType,orderDate ,supplierId,purchaseWarehouseId,transType ,hopeExportDate,smcCode,isLot,interceptMsg,netWeight,notUseShikomi,releaseReason,isEdited,productType,warehouseType,industryCode,inventoryTypeCode,ppl,projectCode,groupCustomerNo,wmTag\n"
			+ "  ,hsCode,supplierPartNo,importFobPriceOriginal,importCurrencyId,inventoryPropertyId,serverremark,supplierInventory,corderNO,iseven,minpackunit,nonstandard_sized_product,qtyImport,finishTime,updateTime,insertTime,stateCode,operator,inspectionType,shikomiRelease,information,sendVersion,infoJson,[po_order_no],[po_item_no],[po_split_no],end_user)\n"
			+ "select id,orderNo,itemNo,splitItemNo,mergeflag,customerNo,userNo,deptNo,apply_dept_no,inqNo ,ordType,modelNo,quantity,stdPrice,specMark,shikomiAnswerNo,shikomiRemainQty,hopeDeliveryDate,remark,salesInfoNo,requestTime,productTag,productTagRemark,requestWarehouseId\n"
			+ "  ,purchaseType,orderDate ,supplierId,purchaseWarehouseId,transType ,hopeExportDate,smcCode,isLot,interceptMsg,netWeight,notUseShikomi,releaseReason,isEdited,productType,warehouseType,industryCode,inventoryTypeCode,ppl,projectCode,groupCustomerNo,wmTag\n"
			+ "  ,hsCode,supplierPartNo,importFobPriceOriginal,importCurrencyId,inventoryPropertyId,serverremark,supplierInventory,corderNO,iseven,minpackunit,nonstandard_sized_product,qtyImport,finishTime,updateTime,GETDATE(),#{statecode},#{operator},inspectionType,shikomiRelease,information,sendVersion,infoJson,[po_order_no],[po_item_no],[po_split_no],end_user from ops_requestPurchase   where id = #{id} ")
	public Boolean insertRequest(@Param("id") Long id, @Param("statecode") String statecode,
			@Param("operator") String operator);
}
