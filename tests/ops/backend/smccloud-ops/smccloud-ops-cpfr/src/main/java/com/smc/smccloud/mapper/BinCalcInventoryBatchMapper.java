package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.*;
import com.sales.ops.dto.assembly.StockAssemblyDetailView;
import com.smc.smccloud.model.binorder.PreStockResultDO;
import com.smc.smccloud.model.trans.TransOrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opsdb")
public interface BinCalcInventoryBatchMapper extends BaseMapper<OpsInventory> {


    @Select("SELECT [inventory_id],[warehouse_code],[quantity],[prepare_quantity],[modelno],[inventory_property_id]\n" +
            "FROM ops_inventory\n" +
            "where delflag=0 and modelno=#{modelno} and quantity >0 and inventory_property_id=1")
    List<OpsInventory> selectNormalAvailableInventoryAllWarehouse(String modelno);

    // 批量加载本次计算涉及型号的通用可用在库，替代 detail 循环内按 modelNo 单查。
    @Select("<script>" +
            "SELECT [inventory_id],[warehouse_code],[quantity],[prepare_quantity],[modelno],[inventory_property_id]\n" +
            "FROM ops_inventory\n" +
            "where delflag=0 and quantity >0 and inventory_property_id=1 and modelno in " +
            "<foreach item='modelNo' collection='modelNos' open='(' separator=',' close=')'>#{modelNo}</foreach>" +
            "</script>")
    List<OpsInventory> selectNormalAvailableInventoryByModelNos(@Param("modelNos") List<String> modelNos);

    @Select("SELECT [inventory_id],[warehouse_code],[quantity],[prepare_quantity],[modelno],[inventory_property_id],\n" +
            "source_type,sign_warehouse_code,associate_no,associate_no_item,associate_no_splitNo,cre_time,supplierId \n" +
            "FROM ops_inventory_move\n" +
            "where delflag=0 and modelno=#{modelno} and quantity >0 and inventory_property_id=1")
    List<OpsInventoryMove> selectMoveAvailableInventoryAllWarehouse(String modelno);

    // 批量加载本次计算涉及型号的通用可用在途，保留后续计算所需 move 来源字段。
    @Select("<script>" +
            "SELECT [inventory_id],[warehouse_code],[quantity],[prepare_quantity],[modelno],[inventory_property_id],\n" +
            "source_type,sign_warehouse_code,associate_no,associate_no_item,associate_no_splitNo,cre_time,supplierId \n" +
            "FROM ops_inventory_move\n" +
            "where delflag=0 and quantity >0 and inventory_property_id=1 and modelno in " +
            "<foreach item='modelNo' collection='modelNos' open='(' separator=',' close=')'>#{modelNo}</foreach>" +
            "</script>")
    List<OpsInventoryMove> selectMoveAvailableInventoryByModelNos(@Param("modelNos") List<String> modelNos);

    //0 待处理 1 处理中 2 待采购 3 已发送采购  4 业务拦截 5 shikomi拦截 6 已完成
    @Select("SELECT * FROM ops_requestPurchase where stateCode in ('0','1') and inventoryTypeCode='TY' ")
    List<OpsRequestpurchase> selectRequestPurchaseAllWarehouse();

    //0 待处理 1 已发送 2 已接单 3 运输中
    @Select("select * FROM ops_purchaseOrder where  stateCode in (0,1,2,3) and isnull(inventoryPropertyId,1)=1 ")
    List<OpsPurchaseorder> selectPurchaseOrderAllWarehouse();

    @Select("select * from trans_order where to_inventory_type_code='TY' and status in (0,1,2) order by id")
    List<TransOrderDO> selectTransOrderAllWarehouse();

    @Select("select * from ops_sharedb.dbo.stock_assembly_detail_view\n" +
            "where isTransOut=0  and optCode in (3,5) and inventory_keys like 'TY%' ")
    List<StockAssemblyDetailView> selectStockAssemblyDetailAllWarehouse();

  @Select("select * from ops_sharedb.dbo.prestock_result where prepare_order is not null ")
    List<PreStockResultDO> selectPreStockOrderAllWarehouse();


    @Select("SELECT i.order_id, i.order_item, i.split_no, i.pco_item, s.split_type, s.modelno, s.qty AS qty_do, i.qty, i.qty_in, i.qty_out, \n" +
            "i.associate_no, i.status, i.status_desc, s.wm_order_id, i.inventory_id, i.inventory_table, s.wl_date, s.warehouse_code \n" +
            "FROM order_status s \n" +
            "INNER JOIN order_status_item i ON s.order_id = i.order_id AND s.order_item = i.order_item AND s.split_no = i.split_no AND \n" +
            "ISNULL(s.pco_item, 0) = ISNULL(i.pco_item, 0)\n" +
            "where  status in ('Purchase','Request','RequestIntercept','WMReceive') and isnull(inventory_type,'TY')='TY' ")
    List<OrderStatusView> selectOrderStatusItemAllWarehouse();

}
