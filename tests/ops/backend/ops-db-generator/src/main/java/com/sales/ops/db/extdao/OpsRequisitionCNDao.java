package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsCustomer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OpsRequisitionCNDao {

//    @Select("SELECT Remark FROM RcvDetail WHERE rorder_no = #{orderno} and rorder_item = #{itemno} and Remark like '%二次电池%' ")
//    String getRemark(@Param("orderno") String orderno,@Param("itemno") String itemno);

	@Select("SELECT vipCode FROM ops_customer_property where customerNo = #{customerNo}  ")
	String getCustomerVipCode(@Param("customerNo") String customerNo);

	@Select("SELECT * FROM ops_Customer where customer_no = #{customerNo} ")
	OpsCustomer getCustomers(@Param("customerNo") String customerNo);

	@Select("SELECT vipCode FROM ops_customer_property where customerNo = #{customerNo} ")
	public List<String> getVIPCode(@Param("customerNo") String customerNo);

	@Select("SELECT Description FROM Tbl_CustGrade where Code = #{code} ")
	String getTblCustGrade(@Param("code") String code);

	// 查询shikomi残数
	@Select("SELECT (residueno-qtyordpre) as contractremnant  FROM SHIKOMI_new WHERE ModelNo = #{modelNo} AND Optstate <> '9' AND (residueno - qtyordpre)>#{quantity}   ")
	Integer getShikomi(@Param("modelNo") String modelNo, @Param("quantity") Integer quantity);

	@Select("SELECT NAME FROM hr_organization WHERE id = #{id} ")
	String getDepartName(@Param("id") String id);

	/**
	 *
	 * @param cnno
	 * @return 写入制造中间表 [10.116.2.23].Manufacture.dbo.TempRequisition
	 *         orderno->替换为pono
	 */
	@Insert("INSERT INTO [ops_sharedb].[dbo].[TempRequisition] " + "                      (Customercode,\n"
			+ "                       AcceptCustomerCode,\n" + "                       requestno,\n"
			+ "                       itemno,\n" + "                       JpKoGO,\n"
			+ "                       JpKOGOitem,\n" + "                       Requestmodel,\n"
			+ "                       RequestQty,\n" + "                       Reqdlvydate,\n"
			+ "                       Boxfixedqty,\n" + "                       BoxType,\n"
			+ "                       JporderNo,\n" + "                       JpItemNo,\n"
			+ "                       DlvyWay,\n" + "                       Accepter,\n"
			+ "                       JPRemarks,\n" + "                       JpShelfNo,\n"
			+ "                       Issuedate,\n" + "                       SimpleModel,\n"
			+ "                       Packtype,\n" + "                       GateNo,\n"
			+ "                       ZoneMark,\n" + "                       SortNo,\n"
			+ "                       GreenMark,\n" + "                       InstrType,\n"
			+ "                       JpSerialNoMark,\n" + "                       RequestType,\n"
			+ "                       ContractNo,\n" + "                       ConTractRemNant)\n"
			+ "          SELECT smcCode,\n" + "                 smcCode,\n" + "                 'YY'+poNo,\n"
			+ "                 itemNo,\n" + "                 CnNo,\n" + "                 '',\n"
			+ "                 modelNo,\n" + "                 quantity,\n" + "                 hopeExportDate,\n"
			+ "                 Boxfixedqty,\n" + "                 BoxType,\n" + "                 left(poNo,7),\n"
			+ "                 itemNo,\n" + "                 'LAND',\n" + "                 Accepter,\n"
			+ "                 JPRemarks,\n" + "                 JpShelfNo,\n" + "                 getDate(),\n"
			+ "                 SimpleModel,\n" + "                 Packtype,\n" + "                 GateNo,\n"
			+ "                 ZoneMark,\n" + "                 SortNo,\n" + "                 GreenCode,\n"
			+ "                 'P/O',\n" + "                 JpSerialNoMark,\n"
			+ "                 ops_tomanu_config.RequestType,\n" + "                 ContractNo,"
			+ "                 ConTractRemNant"
			+ "          FROM   ops_purchaseInvoice INNER JOIN ops_tomanu_config ON ops_purchaseInvoice.supplierId = ops_tomanu_config.supplierId WHERE cnno =#{cnno} and stateCode = '0' ")
	Integer InsertRequisition(@Param("cnno") String cnno);

	/**
	 *
	 * @param cnno
	 * @return 写入CTC中间表 [10.116.0.44].YDSFSoftware.dbo.SMC_Order
	 */
	@Insert("INSERT INTO [ops_sharedb].[dbo].[SMC_Order] (OrderNo, Prjno,ModelNo,qty,Remarks,Deliverydate,orderdate,dbname,status,ProducePlace) "
			+ " SELECT 'YY'+poNo,itemno, modelNo,quantity,JPRemarks,hopeExportDate,GETDATE(),'SMCPDM','0',ops_tomanu_config.RequestType "
			+ "  FROM   ops_purchaseInvoice INNER JOIN ops_tomanu_config ON ops_purchaseInvoice.supplierId = ops_tomanu_config.supplierId  WHERE cnno =#{cnno} and stateCode = '1'  ")
	Integer InsertCTC(@Param("cnno") String cnno);

	/**
	 *
	 * @param cnno
	 * @return 筛选制6的订单，写入制造的视图V_DeliveryConsulting V_DeliveryConsulting
	 */
	@Insert("INSERT INTO [ops_sharedb].[dbo].[V_DeliveryConsulting] "
			+ "(Query_Type,OrderNumber,Model,Quantity,QueryDlvyDate,Query_Psnid,QueryDateTime,CustomerNo,OrderDeliveryDate,Answer_remarks,Processcode,AnswerDlvydate,StateCode)"
			+ " SELECT 'InA',CASE WHEN Datalength(left(poNo,7) + itemNo) > 10 THEN Substring(left(poNo,7) + itemNo, 4, 10)"
			+ " WHEN Datalength(left(poNo,7) + itemNo) <= 10 THEN left(poNo,7) + itemNo  END,"
			+ "modelNo,quantity,hopeExportDate, '99999',Getdate(),LEFT(JpShelfNo, 5),hopeExportDate,LEFT(JPRemarks, 50),'540789',CONVERT(SMALLDATETIME, hopeExportDate),'1'"
			+ "  FROM   ops_purchaseInvoice INNER JOIN ops_tomanu_config ON ops_purchaseInvoice.supplierId = ops_tomanu_config.supplierId "
			+ "WHERE  ops_tomanu_config.RequestType = '2' AND stateCode = '2' AND cnNo = #{cnno} AND Datalength(modelNo) <= 30")
	Integer InsertDeliveryConsulting(@Param("cnno") String cnno);

	/**
	 *
	 * @param id
	 * @return 订单发送，写到广州订单 V_G_Ordersales_CN
	 */
	@Insert("Insert Into V_G_Ordersales_CN (RorderNo,rorderitem,Workday,Status,CustomerNo,ModelNo,Quantity,price,Account,"
			+ "Dlvdate,RcvClassify,StockCode,ExpInvCode,OrdTransType,DlvEntire,TransFee,TransChannel,OptDate,OrderSourceEntity "
			+ "Select poNo,itemNo,GETDATE(),'0',CustomerNo,ModelNo,quantity,0,0,hopeExportDate,'1','KGZ',"
			+ "'3','2','0','2','1',GETDATE() from ops_purchaseInvoice where id = #{id} ")
	Integer InsertToGZ(@Param("id") Long id);
}
