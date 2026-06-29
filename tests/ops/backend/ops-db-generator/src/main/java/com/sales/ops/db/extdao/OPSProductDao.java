package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.*;
import com.sales.ops.dto.product.BomDto;
import com.sales.ops.dto.product.ProductMdmDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops产品相关
 * @date ：Created in 2021/10/18 17:19
 */
// bug10493删除product表中不用字段 2023/4/17
public interface OPSProductDao {

	/**
	 * @param modelNo
	 * @return
	 * @author C14717 根据型号查询产品
	 */

	// bug15515 增加删除字段的筛选
	@Select("SELECT  *  FROM dbo.PRODUCT WHERE MODELNO=#{modelNo} and [is_deleted]=0")
	public Product queryProductByModelNo(@Param("modelNo") String modelNo);

	@Select("SELECT PRODUCT_ECODE.INVNAME, PRODUCT_COMPETITIVENESS_TYPE.NAME competitivenessname, PRODUCT.MODELNO,"
			+ "PRODUCT.ECODE, CHINESENAME, ENGLISHNAME, TYPEID, CLASSIFYCODE1, CLASSIFYCODE2, CLASSIFYCODE3, COMPETITIVENESSID,"
			+ " PRODUCT.IS_DELETED, PRODUCT.is_eos, " + " PRODUCT.MIN_PACK_UNIT,"
			+ "product_design_type.NAME AS DESIGNTYPEID,PRODUCT.min_quantity  FROM PRODUCT LEFT JOIN PRODUCT_ECODE ON PRODUCT.ECODE = PRODUCT_ECODE.ECODE"
			+ " LEFT JOIN PRODUCT_COMPETITIVENESS_TYPE ON PRODUCT.COMPETITIVENESSID = PRODUCT_COMPETITIVENESS_TYPE.ID"
			+ " LEFT JOIN product_design_type ON product_design_type.ID = PRODUCT.DESIGNTYPEID "
			+ " WHERE  PRODUCT.MODELNO = #{modelNo} and  PRODUCT.IS_DELETED = '0' ")
	ProductMdmDTO queryProductMdmDTOByModelNo(@Param("modelNo") String modelNo);

	@Select(" SELECT PRODUCT_SOURCE_TYPE.ID ,PRODUCT_SOURCE_TYPE.NAME FROM PRODUCT_SOURCE_TYPE INNER JOIN (SELECT DISTINCT MODELNO,SOURCETYPEID FROM PRODUCT_TYPE_COST) AS PRODUCT_TYPE_COST "
			+ "  ON PRODUCT_SOURCE_TYPE.ID = PRODUCT_TYPE_COST.SOURCETYPEID WHERE MODELNO=#{modelNo}")
	ProductSourceType findSourceByModelNo(@Param("modelNo") String modelNo);

	/**
	 * 前端查询用 通过型号模糊查询产品信息列表前十条
	 *
	 * @param modelNo
	 * @return
	 */
	@Select("SELECT TOP 10 * FROM dbo.PRODUCT WHERE MODELNO like CONCAT(#{modelNo},'%')")
	List<Product> queryProductByModelNoTop10(@Param("modelNo") String modelNo);


    @Select("SELECT b.bomId,b.ModelNo,b.Priority_Complete,b.Priority_level,b.IsValid ,d.ModelNo as sub_model_no" +
            ",d.Quantity as sub_qty,d.Classify as sub_classify\n" +
            "FROM PRODUCT_BOM b INNER JOIN product_bom_detail d " +
            "    ON d.bomId = b.bomId " +
            "WHERE   b.ISvALID = 1 and b.MODELNO = #{modelNo} ")
    List<BomDto> queryBomAndDetailByModelNo(@Param("modelNo") String modelNo);

	/**
	 * @param modelNo
	 * @return
	 * @author C14717 根据型号差bom
	 */
	@Select("SELECT bomId,ModelNo,Priority_Complete,Priority_level,update_time as updatetime,IsValid,is_wms FROM PRODUCT_BOM WHERE MODELNO=#{modelNo} AND ISvALID = 1 ORDER BY Priority_level")
	public List<ProductBom> queryProductBomByModelNo(@Param("modelNo") String modelNo);

	@Select("SELECT bomId,ModelNo,Priority_Complete,Priority_level,update_time as updatetime,IsValid,is_wms FROM PRODUCT_BOM WHERE MODELNO=#{modelNo} AND ISvALID = 1")
	public List<ProductBom> queryProductBomByModelNotOrderBY(@Param("modelNo") String modelNo);

	@Select("SELECT bomId FROM PRODUCT_BOM WHERE MODELNO=#{modelNo} AND ISvALID = 1 AND Priority_level IN (1,3)")
	public List<Long> queryProductBomByModelNoAndPriorityLevel(@Param("modelNo") String modelNo);

	/**
	 * 插入productBom 返回 bomId
	 *
	 * @param productBom
	 * @return
	 */
	@Insert("insert into PRODUCT_BOM (MODELNO,update_time,update_user,Priority_level,create_time,create_user)"
			+ " values (#{modelno},getdate(),#{updateuser},#{priorityLevel},getdate(),#{createuser})")
	@Options(useGeneratedKeys = true, keyProperty = "bomid")
	Long insertProductBom(ProductBom productBom);

	/**
	 * @param bomId
	 * @return
	 * @author C14717 根据bomid查detail
	 */
	@Select("SELECT id,bomId,ModelNo,Quantity,Classify,update_time,update_user,create_time,create_user FROM PRODUCT_BOM_DETAIL WHERE BOMID=#{bomId} ")
	public List<ProductBomDetail> queryProductBomDetailByModelNo(@Param("bomId") Long bomId);

	/**
	 * 根据型号查询bomdetail
	 *
	 * @param modelNo
	 * @return
	 */
	@Select(" SELECT  A.Priority_level,B.* FROM PRODUCT_BOM  A INNER JOIN PRODUCT_BOM_DETAIL B ON  A.bomId = B.bomId WHERE A.MODELNO=#{modelNo} AND A.ISvALID = 1 ")
	public List<Map<String, Object>> queryProductBomDetailsByModelNo(@Param("modelNo") String modelNo);

	/**
	 * @return
	 * @author C14717 根据最近一小时新增或更新来删除redis product table
	 */
	// @Select("SELECT DISTINCT MODELNO FROM dbo.PRODUCT where
	// DateDiff(mi,CREATED_DATE,getDate())<=#{time} UNION ALL SELECT DISTINCT
	// MODELNO FROM dbo.PRODUCT where
	// DateDiff(mi,UPDATETIME,getDate())<=#{time}")
	public List<String> queryProductModelNoByCrreateDateUnionUpdateDate(@Param("time") Long time);

	/**
	 * @return
	 * @author C14717 根据最近一小时新增或更新来删除redis bom table
	 */
	@Select("SELECT DISTINCT MODELNO FROM dbo.PRODUCT_BOM where DateDiff(mi,update_time,getDate())<=#{time}")
	public List<String> queryProductBomModelNoByCrreateDateUnionUpdateDate(@Param("time") Long time);

	/**
	 * @return
	 * @author C14717 根据最近一小时新增或更新来删除redis detail table
	 */
	@Select("SELECT DISTINCT MODELNO FROM dbo.PRODUCT_BOM_DETAIL where DateDiff(mi,update_time,getDate())<=#{time}")
	public List<String> queryProductBomDetailBomIdByCrreateDateUnionUpdateDate(@Param("time") Long time);

	// 获取多段价格
	@Select(" SELECT MODELNO,MINQUANTITY,EPRICE FROM PRODUCT_PRICE WHERE MODELNO=#{modelNo}")
	List<ProductPrice> findProductPriceByModelNo(@Param("modelNo") String modelNo);

	/**
	 * @return
	 * @author C14717 查询多段价格 根据最近一小时新增或更新来删除redis table
	 */
	@Select("SELECT DISTINCT MODELNO FROM dbo.PRODUCT_PRICE where DateDiff(mi,UPDATE_TIME,getDate())<=#{time}")
	public List<String> refreshProductPriceData(@Param("time") Long time);

	// 获取错误型号
	@Select("SELECT top 1 MODELNO  FROM PRODUCT_ERROR WHERE MODELNO=#{modelNo} and is_deleted='0'")
	ProductError findProductErrorByModelNo(@Param("modelNo") String modelNo);

	/**
	 * @return
	 * @author C14717 查询 错误型号 根据最近一小时新增或更新来删除redis table
	 */
	@Select("SELECT DISTINCT MODELNO FROM dbo.PRODUCT_ERROR "
			+ " UNION ALL SELECT DISTINCT MODELNO FROM dbo.PRODUCT_ERROR"
			+ " where DateDiff(mi,UPDATED_TIME,getDate())<=#{time}")
	public List<String> refreshProductErrorData(@Param("time") Long time);

	// 获取收敛品
	@Select("SELECT top 1 * FROM PRODUCT_EOS WHERE MODELNO=#{modelNo} and is_deleted='0'")
	ProductEos findProductEosByModelNo(@Param("modelNo") String modelNo);

	/**
	 * @return
	 * @author C14717 查询 收敛品 根据最近一小时新增或更新来删除redis table
	 */
	@Select("SELECT DISTINCT MODELNO FROM dbo.PRODUCT_EOS where DateDiff(mi,UPDATED_TIME,getDate())<=#{time} ")
	public List<String> refreshProductEosData(@Param("time") Long time);

	// 获取贩卖限制
	@Select(" SELECT DISTINCT MODELNO FROM PRODUCT_RESTRICT WHERE MODELNO =#{modelNo} and is_deleted='0'")
	String findProductRestrictModelNoByModelNo(@Param("modelNo") String modelNo);

	// 获取贩卖限制完整信息
	// bug 11129 产品查询，贩卖限制品 删除标识为1时，页面还显示为贩卖限制品
	@Select(" SELECT * FROM PRODUCT_RESTRICT WHERE MODELNO =#{modelNo} and  is_deleted='0' ")
	ProductRestrict findProductRestrictDetailByModelNo(@Param("modelNo") String modelNo);

	/**
	 * @return
	 * @author C14717 查询 收敛品 根据最近一小时新增或更新来删除redis table
	 */
	@Select("SELECT DISTINCT MODELNO FROM dbo.PRODUCT_RESTRICT " + "  UNION ALL SELECT DISTINCT MODELNO"
			+ " FROM dbo.PRODUCT_RESTRICT where DateDiff(mi,UPDATED_time,getDate())<=#{time}")
	public List<String> refreshProductRestrictData(@Param("time") Long time);

	// 获取白名单
	@Select(" SELECT DISTINCT MODELNO FROM PRODUCT_RESTRICT_CUSTOMER_WHITELIST where customerNo = #{customerNo} and is_deleted='0'")
	List<String> findProductRestrictCustomerWhiteListByCustomerNo(@Param("customerNo") String customerNo);

	// bug7782 B91717 获取白名单客户部门等信息
	// bug14047 产品查询界面贩卖限制品白名单，增加软删除的判断
	@Select("select customerNo,b.name as customerName,HRUnitId as deptNo,c.Name as deptName,mainCustomer"
			+ " from product_restrict_customer_whitelist a inner join ops_customer b"
			+ " on a.CustomerNo = b.customer_no\n" + "\tinner join hr_organization c on  b.HRUnitId = c.Id\n"
			+ "\twhere ModelNo =#{modelNo}  and a.is_deleted = '0'  ")
	public List<Map<String, Object>> getRestrictCustomerWhiteList(@Param("modelNo") String modelNo);

	/**
	 * @return
	 * @author C14717 查询 白名单 根据最近一小时新增或更新来删除redis table
	 */
	@Select("SELECT DISTINCT CUSTOMERNO FROM dbo.PRODUCT_RESTRICT_CUSTOMER_WHITELIST "
			+ "  UNION ALL SELECT DISTINCT CUSTOMERNO" + " FROM dbo.PRODUCT_RESTRICT_CUSTOMER_WHITELIST")
	public List<String> refreshProductRestrictCustomerWhiteListData(@Param("time") Long time);

	/*
	 * @Select(" select a.*,b.warehouse_name from \n" +
	 * " (select a.warehouse_code,a.inventorycount,b.inventorymovecount from (select warehouse_code,SUM(quantity-prepare_quantity) as inventorycount from ops_inventory  where modelno = #{modelNo} group by warehouse_code) a\n"
	 * +
	 * " left join (select warehouse_code,SUM(quantity-prepare_quantity) as inventorymovecount from ops_inventory_move where modelno = #{modelNo} group by warehouse_code) b\n"
	 * +
	 * " on a.warehouse_code = b.warehouse_code ) a left join ops_warehouse b on a.warehouse_code=b.warehouse_code "
	 * ) public List<Map<String, Object>> getInventoryCount(@Param("modelNo")
	 * String modelNo);
	 */

	@Select(" SELECT\n" + " a.*,\n" + " b.warehouse_name ,\n" + " bin.safeQty,bin.qtybin,bin.BinCell\n" + "FROM\n"
			+ " (\n" + " SELECT\n" + "  a.warehouse_code,\n" + "  a.inventorycount,\n" + "  b.inventorymovecount \n"
			+ " FROM\n"
			+ "  ( SELECT warehouse_code, SUM ( quantity - prepare_quantity ) AS inventorycount FROM ops_inventory WHERE modelno = #{modelNo} GROUP BY warehouse_code ) a\n"
			+ "  LEFT JOIN ( SELECT warehouse_code, SUM ( quantity - prepare_quantity ) AS inventorymovecount FROM ops_inventory_move WHERE modelno = #{modelNo} GROUP BY warehouse_code ) b ON a.warehouse_code = b.warehouse_code \n"
			+ " ) a\n" + " LEFT JOIN ops_warehouse b ON a.warehouse_code= b.warehouse_code\n"
			+ " LEFT JOIN ( SELECT * FROM Bindata WHERE modelno = #{modelNo} AND StockType= 1 AND delflag= 0 ) AS bin ON b.warehouse_code= bin.warehouse_code\n"
			+ "\t ")
	public List<Map<String, Object>> getInventoryCount(@Param("modelNo") String modelNo);

	@Select("SELECT DISTINCT MODELNO FROM dbo.product_physics " + "  UNION ALL"
			+ " SELECT DISTINCT MODELNO FROM dbo.product_physics ")
	public List<String> refreshProductPhysicsData(@Param("time") Long time);

	@Select(" SELECT Modelno,CustomerNo product_restrict  where MODELNO =#{modelNo} and is_deleted='0'")
	public List<ProductRestrict> getCustomerRestrict(@Param("modelNo") String modelNo);

	// @Select(" SELECT supplier_company.Id,country.Name FROM supplier_company
	// inner join country on supplier_company.CountryId = country.Id group by
	// supplier_company.Id,country.Name\n ")
	// public List<Map<String, Object>> getSuppliercompany();

	@Select("  select id,name from country ")
	public List<Map<String, Object>> getSuppliercompany();
}
