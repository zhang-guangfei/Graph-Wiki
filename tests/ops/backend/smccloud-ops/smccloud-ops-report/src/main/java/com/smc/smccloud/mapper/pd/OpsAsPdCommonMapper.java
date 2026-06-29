package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.smccloud.model.inventory.InventoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/8/12 13:35
 * @Descripton TODO
 */
@Mapper
@DS("opsdb")
public interface OpsAsPdCommonMapper {

    @Select(" select  a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno," +
            " a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag " +
            " From ops_core.dbo.ops_inventory a  with(nolock) " +
            " left join ops_core.dbo.ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " left join ops_report.dbo.ops_pd_no_adjust n on n.inventory_id = a.inventory_id and n.is_valid = '1' " +
            " where a.warehouse_code = #{warehouseCode} and a.modelno = #{modelNo} and n.inventory_id is null and  a.prepare_quantity-a.quantity > 0 " +
            " and a.delflag = '0' and b.delflag = '0'  " +
            " order by a.quantity - a.prepare_quantity asc ")
    List<InventoryVO> getInventoryList(@Param("modelNo") String modelNo, @Param("warehouseCode") String warehouseCode);

    @Select(" select  a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno," +
            " a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag " +
            " From ops_core.dbo.ops_inventory a  with(nolock) " +
            " left join ops_core.dbo.ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " where a.modelno = #{modelNo} and b.customer_no = '95002' and a.warehouse_code = #{warehouseCode} and b.inventory_type_code = 'GK-TY' and a.quantity-a.prepare_quantity > 0 " +
            " and a.delflag = '0' and b.delflag = '0' ")
    List<InventoryVO> getInventoryListByModelNo(@Param("modelNo") String modelNo, @Param("warehouseCode")String warehouseCode);

    @Select(" select  a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno," +
            " a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag " +
            " From ops_core.dbo.ops_inventory a  with(nolock) " +
            " left join ops_core.dbo.ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " where a.modelno = #{modelNo} and a.warehouse_code = #{warehouseCode} and b.inventory_type_code = 'TY' " +
            " and a.delflag = '0' and b.delflag = '0' and a.quantity-a.prepare_quantity > 0 ")
    List<InventoryVO> getInventoryListByModelNoAndWarehouseWithTy(@Param("modelNo") String modelNo,@Param("warehouseCode")String warehouseCode);


    @Select(" select  a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno," +
            " a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag " +
            " From ops_core.dbo.ops_inventory a  with(nolock) " +
            " left join ops_core.dbo.ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " where a.modelno = #{modelNo} and a.warehouse_code = #{warehouseCode} and b.inventory_type_code = 'TY' " +
            " and a.delflag = '0' and b.delflag = '0' ")
    List<InventoryVO> getInventoryListByModelNoAndWarehouseWithTyAddQty(@Param("modelNo") String modelNo,@Param("warehouseCode")String warehouseCode);

    @Select(" select  a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno," +
            " a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag " +
            " From ops_core.dbo.ops_inventory a  with(nolock) " +
            " left join ops_core.dbo.ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " where a.modelno = #{modelNo} and a.warehouse_code = #{warehouseCode} and b.inventory_type_code = 'TY' " +
            " and a.delflag = '0' and b.delflag = '0'  ")
    List<InventoryVO> getInventoryListByModelNoAndWarehouseWithTyQuantity(@Param("modelNo") String modelNo,@Param("warehouseCode")String warehouseCode);


    @Select(" select  a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno," +
            " a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag " +
            " From ops_core.dbo.ops_inventory a  with(nolock) " +
            " left join ops_core.dbo.ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " left join ops_report.dbo.ops_pd_no_adjust n on n.inventory_id = a.inventory_id and n.is_valid = '1' " +
            " where a.warehouse_code = #{warehouseCode} and a.modelno = #{modelNo} and n.inventory_id is null and  a.quantity-a.prepare_quantity > 0 " +
            " and a.delflag = '0' and b.delflag = '0' and b.customer_no != '95002' and b.inventory_type_code != 'TY'   " +
            " order by a.quantity - a.prepare_quantity desc ")
    List<InventoryVO> getInventoryWithXXZK(@Param("warehouseCode") String warehouseCode,@Param("modelNo") String modelNo);

    @Select(" select  a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno," +
            " a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag " +
            " From ops_core.dbo.ops_inventory a  with(nolock) " +
            " left join ops_core.dbo.ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " left join ops_report.dbo.ops_pd_no_adjust n on n.inventory_id = a.inventory_id " +
            " where a.warehouse_code = #{warehouseCode} and a.modelno = #{modelNo} and n.inventory_id is null and  a.quantity-a.prepare_quantity > 0 " +
            " and a.delflag = '0' and b.delflag = '0' and n.is_valid = '1' and b.customer_no != '95002' and b.inventory_type_code != 'TY'   " +
            " order by a.quantity desc ")
    List<InventoryVO> getInventoryWithXXZK2(@Param("warehouseCode") String warehouseCode,@Param("modelNo") String modelNo);


    @Select(" select  a.inventory_id as id,a.warehouse_code,a.inventory_status,a.quantity,a.prepare_quantity,a.quantity-a.prepare_quantity as avaQty,a.unit,a.qa_status,a.modelno," +
            " a.inventory_property_id as propertyId,b.inventory_type_code,b.customer_no,b.ppl,b.project_code,b.group_customer_no,b.sales_info_no,a.in_time,a.version,a.delflag " +
            " From ops_core.dbo.ops_inventory a  with(nolock) " +
            " left join ops_core.dbo.ops_inventory_property b on a.inventory_property_id=b.inventory_property_id " +
            " where a.modelno = #{modelNo} and a.warehouse_code = #{warehouseCode} and b.inventory_type_code = #{inventoryType} " +
            " and a.delflag = '0' and b.delflag = '0' ")
    List<InventoryVO> getInventoryIdByModelNoAndWarehouse(@Param("modelNo") String modelNo, @Param("warehouseCode")String warehouseCode,@Param("inventoryType")String inventoryType);


    /**
     * 获取盘点库存期初表里是否存在历史盘点库存
     */
    @Select("select batch_no from ops_report.dbo.ops_inventory_opening where batch_no like 'PD%'  group by batch_no order by batch_no desc")
    List<String> getHistoryInventoryBatchNo();

    @Select("select batch_no from ops_report.dbo.ops_inventory_opening where batch_no like 'YPD%'  group by batch_no order by batch_no desc")
    List<String> getYdHistoryInventoryBatchNo();

    @Update("update ops_report.dbo.ops_inventory_opening set initial_quantity = quantity where batch_no like 'PD%' ")
    void updateInventoryinitialQuantity();


    @Update("update ops_report.dbo.ops_inventory_opening set initial_quantity = quantity where batch_no like 'YPD%' ")
    void ydUpdateInventoryinitialQuantity();


    // 更新ops_as_pd_bill_data表第一次录入型号&数量至第二次盘点录入的型号&数量
    @Update("update ops_report.dbo.ops_as_pd_bill_data set pd_qty_2 = pd_qty_1,model_no_2 = model_no_1 where pd_batch_no = #{pdBatchNo}")
    void updateOpsAsPdBillDataWithPdBatchNo(@Param("pdBatchNo")String pdBatchNo);


    @Update("UPDATE oio\n" +
            "SET oio.initial_quantity = a.quantity\n" +
            "FROM ops_report.dbo.ops_inventory_opening AS oio\n" +
            "INNER JOIN ops_report.dbo.ops_inventory_opening AS a ON oio.inventory_id = a.inventory_id\n" +
            "WHERE a.batch_no = #{lastPdBatchNo} AND oio.batch_no = #{curPdBatchNo} ")
    void updateIventoryOpenIngLastInitQty(@Param("curPdBatchNo")String curPdBatchNo,@Param("lastPdBatchNo")String lastPdBatchNo);


    @Update("UPDATE curVer\n" +
            "SET curVer.last_initial_quantity = lastVer.ops_sum_qty\n" +
            "FROM ops_report.dbo.ops_as_pd_three_report_ware AS curVer\n" +
            "INNER JOIN ops_report.dbo.ops_as_pd_three_report_ware AS lastVer\n" +
            "ON curVer.warehouse_code = lastVer.warehouse_code\n" +
            "AND curVer.model_no = lastVer.model_no\n" +
            "WHERE lastVer.pd_batch_no = #{lastPdBatchNo}\n" +
            "AND curVer.pd_batch_no = #{curPdBatchNo}")
    void updateOpsAsPdThreeReportWare(@Param("curPdBatchNo")String curPdBatchNo,@Param("lastPdBatchNo")String lastPdBatchNo);

}
