package com.smc.smccloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsPoTranstype;
import com.sales.ops.db.entity.OpsPoTranstypeConfig;
import com.sales.ops.db.entity.OpsPoWarehouseDeliveryday;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfig;
import com.smc.smccloud.model.adapter.DeliveryInventory;
import com.smc.smccloud.model.delivery.ProductSupplyInfo;

@DS("opsdb")
@Mapper
public interface DeliveryMapper {
	// bug9804查询客户可用委托在库
	@Select("<script>SELECT a.modelno modelNo,a.quantity-a.prepare_quantity canuse,a.warehouse_code stockCode,"
			+ "b.inventory_type_code stockType,d.warehouse_type warehouseType FROM ops_inventory a"
			+ " inner join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id"
			+ " inner join ops_warehouse d on d.warehouse_code=a.warehouse_code"
			+ " where a.quantity-a.prepare_quantity>0 and a.quantity>0 and "
			+ " a.inventory_status='N' and a.qa_status='0' and a.delflag='0'"
			+ " and b.delflag='0' and d.customer_no =#{customerNo} and a.modelno in "
			+ " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>"
			+ "  #{item}   </foreach> and warehouse_type='WT' and (inventory_type_code='TY' or inventory_type_code='GK-TY') </script>")
	List<DeliveryInventory> getInventoryWTCanUse(@Param("customerNo") String customerNo,
			@Param("modelNos") List<String> modelNos);

	// bug8531 查询时筛选库存数量大于零，且可用数量大于零的库存 mateng
	// 获取ppl及pj可用库存
	@Select("<script>SELECT a.quantity-a.prepare_quantity canuse,a.warehouse_code stockCode,"
			+ "b.inventory_type_code stockType,d.warehouse_type warehouseType FROM ops_inventory a"
			+ " inner join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id"
			+ " inner join ops_warehouse_salesbranch_config c on a.warehouse_code=c.warehouse_code"
			+ " inner join ops_warehouse d on d.warehouse_code=a.warehouse_code"
			+ " where a.quantity-a.prepare_quantity>0 and a.quantity>0 and c.sales_branch_id=#{deptNo} and a.inventory_status='N' and a.qa_status='0'"
			+ " and a.delflag='0' and b.delflag='0' and b.customer_no = #{customerNo} <if test='ppl!=null'> and b.ppl=#{ppl} </if>"
			+ " <if test='pj!=null'> and b.project_code=#{pj} </if> and a.modelno=#{modelNo} </script>")
	List<DeliveryInventory> getInventoryCanUse(@Param("deptNo") String deptNo, @Param("modelNo") String modelNo,
			@Param("customerNo") String customerNo, @Param("ppl") String ppl, @Param("pj") String pj);

	// 获取通用库存
	@Select("<script>SELECT a.modelno,a.quantity-a.prepare_quantity canuse,a.warehouse_code stockCode,"
			+ "'TY' stockType,d.warehouse_type warehouseType FROM ops_inventory a"
			+ " inner join ops_warehouse_salesbranch_config c on a.warehouse_code=c.warehouse_code"
			+ " inner join ops_warehouse d on d.warehouse_code=a.warehouse_code where"
			+ " a.quantity-a.prepare_quantity>0 and a.quantity>0 and a.modelno in "
			+ " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>"
			+ "   #{item}   </foreach> and c.sales_branch_id=#{deptNo} and a.delflag='0' and"
			+ " (a.inventory_property_id = 1) and a.inventory_status='N' and a.qa_status='0' "
			+ " order by c.priority</script>")
	List<DeliveryInventory> getInventoryCanUseCommonTY(@Param("deptNo") String deptNo,
			@Param("customerNo") String customerNo, @Param("modelNos") List<String> modelNos);

	// 顾客通用
	@Select("<script>SELECT a.modelno,a.quantity-a.prepare_quantity canuse,a.warehouse_code stockCode,"
			+ "b.inventory_type_code stockType,d.warehouse_type warehouseType FROM ops_inventory a"
			+ " inner join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id"
			+ " inner join ops_warehouse_salesbranch_config c on a.warehouse_code=c.warehouse_code"
			+ " inner join ops_warehouse d on d.warehouse_code=a.warehouse_code where"
			+ " a.quantity-a.prepare_quantity>0 and a.quantity>0 and a.modelno in "
			+ " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>"
			+ "   #{item}   </foreach> and c.sales_branch_id=#{deptNo} and a.delflag='0' and b.delflag='0' and"
			+ " ((b.inventory_type_code='GK-TY'"
			+ " and b.customer_no = #{customerNo})) and a.inventory_status='N' and a.qa_status='0' "
			+ " order by c.priority</script>")
	List<DeliveryInventory> getInventoryCanUseCommonGK(@Param("deptNo") String deptNo,
			@Param("customerNo") String customerNo, @Param("modelNos") List<String> modelNos);

    @Select("<script>"
            + " SELECT a.modelno,a.quantity-a.prepare_quantity canuse,a.warehouse_code stockCode,"
            + " b.inventory_type_code stockType,d.warehouse_type warehouseType "
            + " FROM ops_inventory a "
            + " inner join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id"
            + " inner join ops_warehouse_salesbranch_config c on a.warehouse_code=c.warehouse_code"
            + " inner join ops_warehouse d on d.warehouse_code=a.warehouse_code "
            + " where "
            + " a.quantity-a.prepare_quantity>0 and a.quantity>0  "
            + " and a.modelno in "
            + " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>"
            + "   #{item}  "
            + " </foreach> "
            + " and c.sales_branch_id=#{deptNo} and a.delflag='0' and b.delflag='0' "
            + " <if test = 'customerNo != null and customerNo != \"\" ' >"
            + " and b.customer_no = #{customerNo} "
            + " </if> "
            + "  and a.inventory_status='N' and a.qa_status='0' "
            + " order by c.priority"
            + "</script>")
    List<DeliveryInventory> getInventoryCanUseForDelivery(@Param("deptNo") String deptNo,
                                                       @Param("customerNo") String customerNo, @Param("modelNos") List<String> modelNos);

	// 获取集团客户可用库存
	@Select("<script> SELECT a.modelno,a.quantity-a.prepare_quantity canuse,a.warehouse_code stockCode,"
			+ "b.inventory_type_code stockType,d.warehouse_type warehouseType" + " FROM ops_inventory a"
			+ " inner join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id"
			+ " inner join ops_warehouse_salesbranch_config c on a.warehouse_code=c.warehouse_code "
			+ " inner join ops_warehouse d on d.warehouse_code=a.warehouse_code where "
			+ " a.quantity-a.prepare_quantity>0 and a.quantity>0 and a.modelno in "
			+ " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>"
			+ "   #{item}   </foreach> and c.sales_branch_id=#{deptNo} and a.delflag='0' and b.delflag='0'"
			+ " and b.group_customer_no = #{groupCustomer} and a.inventory_status='N' and a.qa_status='0' "
			+ " order by c.priority</script>")
	List<DeliveryInventory> getInventoryCanUseCommonGroup(@Param("deptNo") String deptNo,
			@Param("modelNos") List<String> modelNos, @Param("groupCustomer") String groupCustomer);


    @Select("<script> SELECT a.modelno,a.quantity-a.prepare_quantity canuse,a.warehouse_code stockCode,"
            + "b.inventory_type_code stockType,d.warehouse_type warehouseType" + " FROM ops_inventory a"
            + " inner join ops_inventory_property b on a.inventory_property_id=b.inventory_property_id"
            + " inner join ops_warehouse_salesbranch_config c on a.warehouse_code=c.warehouse_code "
            + " inner join ops_warehouse d on d.warehouse_code=a.warehouse_code where "
            + " a.quantity-a.prepare_quantity>0 and a.quantity>0 and a.modelno in "
            + " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>"
            + "   #{item} "
            + "  </foreach>"
            + " and c.sales_branch_id=#{deptNo} and a.delflag='0' and b.delflag='0'"
            + " and b.group_customer_no in "
            + " <foreach collection='groupCustomer' item='item' index='index' open='(' separator=',' close=')'>"
            + "   #{item} "
            + " </foreach>"
            + " and a.inventory_status='N' and a.qa_status='0' "
            + " order by c.priority </script>")
    List<DeliveryInventory> getInventoryCanUseCommonGroupByDelivery(@Param("deptNo") String deptNo,
                                                          @Param("modelNos") List<String> modelNos, @Param("groupCustomer") List<String> groupCustomer);

	// 获取优先级最高的可采购仓库的运输时间
	@Select("select top 1 a.* from ops_warehouse_salesbranch_config a"
			+ " inner join ops_warehouse b on a.warehouse_code=b.warehouse_code"
			+ " where sales_branch_id=#{deptNo} and b.purchase_flag='1' and a.delflag = 0 and b.delflag = 0 order by a.priority")
	OpsWarehouseSalesbranchConfig getFirstPurchaseWarehouse(@Param("deptNo") String deptNo);

	@Select("select * from ops_warehouse_salesbranch_config where sales_branch_id=#{deptNo} and delflag = 0 order by priority")
	List<OpsWarehouseSalesbranchConfig> getWarehouseSalesbranchConfig(@Param("deptNo") String deptNo);

	@Select("select a.warehouse_code from ops_warehouse_salesbranch_config a"
			+ " inner join ops_warehouse b on a.warehouse_code=b.warehouse_code"
			+ " where sales_branch_id=#{deptNo} and a.delflag = 0 and b.delflag = 0 order by a.priority")
	List<String> getPurchaseWarehousePriority(@Param("deptNo") String deptNo);

	// 海运优先配置表
	@Select("select * from ops_po_transtype_config")
	List<OpsPoTranstypeConfig> shipModelConfig();

	// 运输方式
	@Select("select id, name from ops_po_transtype")
	List<OpsPoTranstype> transtype();

	// 获取仓间调拨天数
	@Select("select * from ops_po_warehouse_deliveryday")
	List<OpsPoWarehouseDeliveryday> warehouseTransWarehouse();

	// 获取型号信息及主供应商信息及主供应商库存
	@Select("<script>select a.modelNo,a.supplyId,c.nstdProductManuDay,c.stdProductManuDay,c.shipDeliveryDay,"
			+ "c.fstDeliveryDay,c.fstTransTypeId,b.quantity,d.nonstandard_sized_product,d.DesignTypeId,a.warehouseCode"
			+ " from (select modelNo,supplyId,warehouseCode from product_delivery"
			+ " inner join ops_warehouse_supplier_config on supplierId=supplyId"
			+ " inner join ops_warehouse on warehouse_code=warehouseCode where modelNo in "
			+ "<foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'>"
			+ "   #{item}  "
            + " </foreach> and isPrimary=1 and purchase_flag=1 and ops_warehouse_supplier_config.priority=1"
			+ ") a inner join supplier c on a.supplyId=c.id inner join product d on a.modelNo=d.ModelNo"
			+ " left join inventory_supplier b on a.supplyId=b.supplierId and a.modelNo=b.modelNo</script>")
	List<ProductSupplyInfo> selectProductSupplyInfo(@Param("modelNos") List<String> modelNos);

}
