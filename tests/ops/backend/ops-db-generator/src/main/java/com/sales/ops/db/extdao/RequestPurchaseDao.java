package com.sales.ops.db.extdao;

import java.util.Date;
import java.util.List;

import com.sales.ops.dto.po.PoOrderInfoDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sales.ops.db.entity.OpsPoDestinationConfig;
import com.sales.ops.db.entity.OpsPoTranstype;
import com.sales.ops.dto.purchase.RequestPurchaseSupplierInfo;

public interface RequestPurchaseDao {

	/**
	 * bugid:17646 20250526 c14717
	 * 请购拦截状态的客单列表
	 * 测试时 写TOP1
	 * @return
	 */
	@Select("SELECT wmTag,infojson,orderNo ,itemNo ,splitItemNo from ops_requestPurchase where stateCode ='4' and purchaseType ='A'")
	List<PoOrderInfoDto> getInterceptPoList();

	/**
	 * bugid:17646 20250526 c14717 配置表是否自动出库
	 * @param Id
	 * @return
	 */
	@Select("SELECT typeId  from ops_requestPurchase_intercept_config where auto_stock_out =1 and id = #{Id}")
	String getInterceptConfig(@Param("Id") Integer Id);




	@Update({
			"UPDATE dbo.ops_requestPurchase SET StateCode='0',releaseReason = #{releaseReason},updateTime = getdate(),smccode = #{smccode},purchasewarehouseid = #{purchasewarehouseid},operator = #{operator} WHERE id = #{id} and orderNo=#{orderNo} and StateCode = '4' " })
	public int updateStateCode(@Param("id") Long id, @Param("orderNo") String orderNo,
			@Param("supplierId") String supplierId, @Param("releaseReason") String releaseReason,
			@Param("smccode") String smccode, @Param("purchasewarehouseid") String purchasewarehouseid,
			@Param("operator") String operator);

	@Update({
			"UPDATE dbo.ops_requestPurchase SET updateTime = getdate(),StateCode='1',supplierid=#{supplierId},transtype=#{transtype},hopeexportdate =#{hopeexportdate},smccode = #{smccode},purchasewarehouseid = #{purchasewarehouseid},operator = #{operator}  WHERE id = #{id} and orderNo=#{orderNo} and StateCode = '4' " })
	public int updateStateCode2(@Param("id") Long id, @Param("orderNo") String orderNo,
			@Param("supplierId") String supplierId, @Param("transtype") String transtype,
			@Param("hopeexportdate") Date hopeexportdate, @Param("smccode") String smccode,
			@Param("purchasewarehouseid") String purchasewarehouseid, @Param("operator") String operator);

	@Update({
			"UPDATE dbo.ops_requestPurchase SET StateCode='0',shikomiAnswerNo = #{shikomiNo},releaseReason = #{releaseReason}, shikomiRelease=#{shikomiRelase},updateTime = getdate(),operator = #{operator}  WHERE id = #{id} and orderNo=#{orderNo} and StateCode = '5' " })
	public int updateShikomiStateCode(@Param("id") Long id, @Param("orderNo") String orderNo,
			@Param("shikomiNo") String shikomiNo, @Param("releaseReason") String releaseReason,
			@Param("shikomiRelase") String shikomiRelase, @Param("operator") String operator);

	@Update({
			"UPDATE dbo.ops_requestPurchase SET StateCode='0',smcCode = null,supplierId = null,hopeExportDate = null,purchaseWarehouseId = null,transType = null,updateTime = getdate(),operator = #{operator}   WHERE id=#{id}  and orderNo=#{orderNo} and StateCode = '4' " })
	public int interceptRestore(@Param("id") Long id, @Param("orderNo") String orderNo,
			@Param("operator") String operator);
    // 不清空指定制造出荷日
    @Update({
            "UPDATE dbo.ops_requestPurchase SET StateCode='0',smcCode = null,supplierId = null,purchaseWarehouseId = null,transType = null,updateTime = getdate(),operator = #{operator}   WHERE id=#{id}  and orderNo=#{orderNo} and StateCode = '4' " })
    public int interceptRestore1(@Param("id") Long id, @Param("orderNo") String orderNo,
                                @Param("operator") String operator);
    // 不清空运输方式
    @Update({
            "UPDATE dbo.ops_requestPurchase SET StateCode='0',smcCode = null,supplierId = null,hopeExportDate = null,purchaseWarehouseId = null,updateTime = getdate(),operator = #{operator}   WHERE id=#{id}  and orderNo=#{orderNo} and StateCode = '4' " })
    public int interceptRestore2(@Param("id") Long id, @Param("orderNo") String orderNo,
                                @Param("operator") String operator);

    // 不清空运输方式 不清空指定制造出荷日
    @Update({
            "UPDATE dbo.ops_requestPurchase SET StateCode='0',smcCode = null,supplierId = null,purchaseWarehouseId = null,updateTime = getdate(),operator = #{operator}   WHERE id=#{id}  and orderNo=#{orderNo} and StateCode = '4' " })
    public int interceptRestore3(@Param("id") Long id, @Param("orderNo") String orderNo,
                                 @Param("operator") String operator);

	@Update({
			"UPDATE dbo.ops_requestPurchase SET isEdited=1,supplierid=#{supplierid},transtype=#{transtype},hopeexportdate =#{hopeexportdate},serverremark = #{serverremark},producttag = #{producttag},updateTime = getdate()  WHERE id=#{id} and orderNo=#{orderNo} " })
	public int updateSuppily(@Param("id") Long id, @Param("orderNo") String orderNo,
			@Param("supplierid") String supplierid, @Param("transtype") String transtype,
			@Param("hopeexportdate") Date hopeexportdate, @Param("serverremark") String serverremark,
			@Param("producttag") String producttag);

	@Select("  select A.ID from supplier a inner join supplier_company b on a.companyId = b.Id WHERE b.CountryId = #{CountryCode} ")
	public List<String> getSuppilyByCountry(@Param("CountryCode") String CountryCode);

	/**
	 * 后续基础数据存入缓存中，将替换以下两方法，不需从数据库中反复读取
	 *
	 * 库房与供应商配置表、库存表、库房与营业所配置表
	 *
	 * MT
	 */
	// 20221026 取消根据主供应商排序,a.isPrimary desc，主供应商更偏向于是否可大批量生产，采购时更在乎营业所属地
	@Select("SELECT a.modelNo,a.supplyId,a.maxProdQty,a.enableMaxProdQty,b.warehouseCode,b.matchPattern"
			+ ",d.priority as warehousePriority,b.priority as supplierPriority,a.supplierPartNo,b.day"
			+ "  FROM product_delivery a inner join ops_warehouse_supplier_config b on a.supplyId=b.supplierId"
			+ "  INNER JOIN ops_warehouse c on b.warehouseCode=c.warehouse_code"
			+ "  INNER JOIN ops_warehouse_salesbranch_config d on b.warehouseCode=d.warehouse_code"
			+ "  where ModelNo=#{modelNo} and c.purchase_flag=1 and d.sales_branch_id=#{deptNo} and a.is_deleted=0"
			+ "  order by d.priority asc")
	public List<RequestPurchaseSupplierInfo> getByModelAndDept(@Param("modelNo") String modelNo,
			@Param("deptNo") String deptNo);

	@Select("SELECT b.supplierId as supplyId,b.warehouseCode,b.matchPattern,d.priority,b.day FROM ops_warehouse_supplier_config b"
			+ " INNER JOIN ops_warehouse c on b.warehouseCode=c.warehouse_code "
			+ " INNER JOIN ops_warehouse_salesbranch_config d on b.warehouseCode=d.warehouse_code"
			+ " where c.purchase_flag=1 and d.sales_branch_id=#{deptNo} and supplierId=#{supplierId} "
			+ " order by d.priority asc")
	public List<RequestPurchaseSupplierInfo> getBySupplierAndDept(@Param("supplierId") String supplierId,
			@Param("deptNo") String deptNo);

	// 15023天津直发，增加是否拆分判断
	@Select("select * from ops_po_destination_config "
			+ " where warehouseid=#{warehouseCode} and supplierId=#{supplierId}"
			+ " and (productType=#{productType} or productType is null) and"
			+ " (customerNo=#{customerNo} or customerNo is null) and"
			+ " (orderType=#{purchaseType} or orderType=#{ordType}) and (wmTag is null or wmTag=#{wmTag})")
	public List<OpsPoDestinationConfig> getSmccode(@Param("supplierId") String supplierId,
			@Param("warehouseCode") String warehouseCode, @Param("productType") Integer productType,
			@Param("customerNo") String customerNo, @Param("purchaseType") String purchaseType,
			@Param("ordType") String ordType, @Param("wmTag") String wmTag);

	// bug10440 马腾 更新合并请购单更新状态
	@Update({ "UPDATE dbo.ops_requestPurchase SET updateTime = getdate(),StateCode=#{status},"
			+ "qtyImport=#{qtyimp},operator = 'dr',finishTime=getdate()  from ops_req_po_mapping"
			+ " where id=RequestPurchaseId and PurchaseOrderNo=#{orderNo} " })
	public int updateMergeRequest(@Param("orderNo") String orderNo, @Param("status") String status,
			@Param("qtyimp") Integer qtyimp);

	// bug10563 马腾 获取温控器对应的smccode
	@Select("select distinct smccode from ops_po_destination_config where productType=1")
	public List<String> getSmccodeWenkongqi();

	// bug10617 马腾 使用请购仓替代部门来筛选采购仓
	@Select("SELECT a.modelNo,a.supplyId,a.maxProdQty,a.enableMaxProdQty,b.warehouseCode,b.matchPattern"
			+ ",b.priority as supplierPriority,a.supplierPartNo,b.day FROM product_delivery a"
			+ " inner join ops_warehouse_supplier_config b on a.supplyId=b.supplierId"
			+ " INNER JOIN (select case when purchase_flag=1 then warehouse_code else parent_code end"
			+ " warehouse_code from ops_warehouse where warehouse_code=#{requestWarehouseId}) d on"
			+ " b.warehouseCode=d.warehouse_code where ModelNo=#{modelNo}  and a.is_deleted=0")
	public List<RequestPurchaseSupplierInfo> getByModelAndRequest(@Param("modelNo") String modelNo,
			@Param("requestWarehouseId") String requestWarehouseId);

	// bug10617 马腾 使用请购仓替代部门来筛选采购仓
	@Select("SELECT b.supplierId as supplyId,b.warehouseCode,b.matchPattern,b.day"
			+ " FROM ops_warehouse_supplier_config b"
			+ " INNER JOIN ops_warehouse c on b.warehouseCode=c.warehouse_code"
			+ " INNER JOIN (select case when purchase_flag=1 then warehouse_code else parent_code end"
			+ " warehouse_code from ops_warehouse where warehouse_code=#{requestWarehouseId}) d on"
			+ " b.warehouseCode=d.warehouse_code where c.purchase_flag=1  and supplierId=#{supplierId}")
	public List<RequestPurchaseSupplierInfo> getBySupplierAndRequest(@Param("supplierId") String supplierId,
			@Param("requestWarehouseId") String requestWarehouseId);
}
