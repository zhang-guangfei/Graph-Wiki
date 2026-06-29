package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryAdj;
import com.sales.ops.db.entity.OpsInventoryDiff;
import com.sales.ops.db.entity.StockTransferPlan;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author C12961
 * @date 2022/11/4 15:43
 */
public interface AdjustInventoryQtyDao {


    @Select("SELECT sum(plan_qty) FROM stock_transfer_plan where delflag =0 and associate_no = #{associateNo} " +
            "and associate_no_item = #{itemNo} and associate_no_splitNo = #{splitNo} group by associate_no," +
            "associate_no_item,associate_no_splitNo ")
    Integer sumStockTransferPlanPlanQty(String associateNo ,Integer itemNo,Integer splitNo);

    @Select("SELECT count(id) FROM stock_transfer_plan where delflag =0 and associate_no = #{associateNo} " +
            "and associate_no_item = #{itemNo} and associate_no_splitNo = #{splitNo} ")
    Integer countStockTransferPlan(String associateNo ,Integer itemNo,Integer splitNo);

    @Select("SELECT sum(plan_qty) FROM stock_transfer_plan where delflag =0 and associate_no = #{associateNo} " +
            "and associate_no_item = #{itemNo} and associate_no_splitNo = #{splitNo} ")
    Integer sumStockTransferPlan(String associateNo ,Integer itemNo,Integer splitNo);

    @Select("SELECT count(id) FROM stock_transfer_plan where delflag =0 and doId = #{doId} ")
    Integer countStockTransferPlanByDoid(String doId);

    @Select("SELECT * FROM stock_transfer_plan where delflag =0 and associate_no = #{associateNo} " +
            "and associate_no_item = #{itemNo} and associate_no_splitNo = #{splitNo} order by create_time ")
    List<StockTransferPlan> selectStockTransferPlan(String associateNo , Integer itemNo, Integer splitNo);

    @Select("SELECT DISTINCT associate_no ,associate_no_item ,associate_no_splitNo  from stock_transfer_plan where status != 2 and delflag =0")
    List<StockTransferPlan> getStockTransferPlanNotFinish();

    @Select("SELECT * FROM stock_transfer_plan where delflag =0 and doId = #{doId} order by create_time ")
    List<StockTransferPlan> selectStockTransferPlanByDoId(String doId);

    @Update("UPDATE stock_transfer_plan SET status = #{status} , finish_qty =#{finishQty}  ,update_time = getDate(),updator= #{updator} where plan_no = #{planNo}")
    Long updateStockTransferPlan(Integer status ,Integer finishQty, String planNo,String updator);

    @Update("UPDATE stock_transfer_plan SET status = 2 ,update_time = getDate(),updator= #{updator} " +
            "where delflag =0 and status != 2 and associate_no = #{associateNo} and associate_no_item = #{itemNo} " +
            "and associate_no_splitNo = #{splitNo}")
    Long updateStockTransferPlanFinish(String updator,String associateNo , Integer itemNo, Integer splitNo);


    @Select("exec createInventoryDiff")
    void createInventoryDiff();

    @Select("select top ${num} * from ops_inventory_diff where quantity_diff < 0 and status=0 order by id")
    List<OpsInventoryDiff> OpsInventoryDiff(Integer num);

    @Select("select * from ops_inventory i " +
            "inner join ops_inventory_property p on i.inventory_property_id=p.inventory_property_id " +
            "inner join ops_warehouse h on h.warehouse_code=i.warehouse_code and h.warehouse_type !='WT' and h.delflag=0 " +
            "where i.delflag=0 and p.delflag=0 and (p.customer_no not in ('95001' ,'95002') or p.customer_no is null) " +
            "and modelno=#{modelno} and warehouse_code=#{warehouse} " +
            "order by i.quantity desc ")
    List<OpsInventory> findInventory(String modelno, String warehouse);


    @Select("select top ${num} * from ops_inventory_adj where status=0 and quantity_adj>0")
    List<OpsInventoryAdj> OpsInventoryAdj(Integer num);

    @Update("update ops_inventory set quantity = quantity - #{qty},modifier= 'C12961' , modify_time = getdate() where inventory_id = #{id}")
    Integer adjustInventoryQty(Long id, Integer qty);

    @Select("select count(*) from ops_inventory i " +
            "inner join ops_inventory_property p on i.inventory_property_id=p.inventory_property_id " +
            "where i.prepare_quantity>0 and  i.inventory_id = #{id} ")
    Integer findQBInventory(Long id);


    @Select("select * from ops_inventory " +
            "where delflag=0 and modelno=#{modelno} and warehouse_code=#{warehouse} and inventory_property_id=1 ")
    List<OpsInventory> findInventoryTY(String modelno, String warehouse);

    @Select("select i.* " +
            "from ops_inventory i " +
            "inner join ops_inventory_property p on i.inventory_property_id=p.inventory_property_id and i.delflag=0 and p.delflag=0 " +
            "inner join ops_warehouse h on h.warehouse_code=i.warehouse_code and h.warehouse_type !='WT' and h.delflag=0 " +
            "where (p.customer_no not in ('95001' ,'95002') or p.customer_no is null) " +
            "and i.modelno=#{modelno} and i.warehouse_code=#{warehouse} " +
            "and i.inventory_property_id!=1 and i.inventory_id not in ( " +
            "select i.inventory_id " +
            "from ops_inventory i " +
            "inner join ops_inventory_property p on i.inventory_property_id=p.inventory_property_id and i.delflag=0 and p.delflag=0 " +
            "inner join ops_warehouse h on h.warehouse_code=i.warehouse_code and h.warehouse_type !='WT' and h.delflag=0 " +
            "where i.modelno=#{modelno} and i.warehouse_code=#{warehouse} " +
            "and p.inventory_type_code = 'QB_NO' and i.prepare_quantity>0) " +
            "order by i.quantity desc ")
    List<OpsInventory> findInventoryGK(String modelno, String warehouse);

    @Select("select i.* " +
            "from ops_inventory i " +
            "inner join ops_inventory_property p on i.inventory_property_id=p.inventory_property_id and i.delflag=0 and p.delflag=0 " +
            "inner join ops_warehouse h on h.warehouse_code=i.warehouse_code and h.warehouse_type !='WT' and h.delflag=0 " +
            "where i.modelno=#{modelno} and i.warehouse_code=#{warehouse} " +
            "and p.inventory_type_code = 'QB_NO' and i.prepare_quantity>0 ")
    List<OpsInventory> findInventoryQB(String modelno, String warehouse);


}
