package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @author C14717
 * @date 2022/10/20
 */
public interface OpsDoPostDao {

    @Select("select express_child_no from ops_do_post where do_id = #{expressChildNo}")
    List<String> getOpsDoPostExpressChildNoByDoId(@Param("expressChildNo") String expressChildNo);

    @Select("select do_id from ops_do where delflag = 0 and order_id = #{orderId} and order_item = #{orderItem} ")
    List<String> getDoIdList(@Param("orderId") String orderId, @Param("orderItem") String orderItem);

    @Select("select use_qty from ops_do_item_inventory where do_id =#{doId} and inventory_table_type = 'N' AND delflag =0")
    List<Integer> getDoItemInvByNList(@Param("doId") String doId);

    @Select("select * from ops_do_item_inventory where inventory_id =#{invId} and inventory_table_type = 'T' AND delflag =0")
    List<OpsDoItemInventory> getDoItemInvByTList(@Param("invId") Long invId);

    @Select("select * from ops_do_item where do_id =#{doId} and delflag =0")
    OpsDoItem getDoItem(@Param("doId") String doId);

    @Select("select warehouse_type  from ops_warehouse where warehouse_code =#{warehouseCode} and delflag =0")
    String getWarehouseType(@Param("warehouseCode") String warehouseCode);

    @Select("select * from ops_do where do_id =#{doId} and delflag =0")
    OpsDo getDo(@Param("doId") String doId);

    @Select("select sum(use_qty) from ops_do_item_inventory where do_id =#{doId} and delflag =0 ")
    Integer getDoItemInvSumQty(@Param("doId") String doId);

    @Select("select * from ops_ro where order_id =#{orderId} and order_item =#{orderItem} and num =#{num} " +
            "and ass_num =#{assNum} and delflag =0 and ro_type = 'CGDBRK' ")
    OpsRo getRo(@Param("orderId") String orderId, @Param("orderItem") String orderItem,@Param("num") Integer num, @Param("assNum") Integer assNum);

    @Select("select * from ops_ro_item where ro_id =#{roId} AND delflag =0")
    OpsRoItem getRoItem(@Param("roId") String roId);


    @Update("update ops_do set wait_type = 'OK',modifier='CG完纳',modify_time=getdate() where do_id = #{doId} and delflag = 0 ")
    int updateDoWaitTypeOk(String doId);


    @Update("update ops_do set delflag = 1,modify_time=getdate() where do_id = #{doId} and delflag = 0 ")
    int updateDoToDel(String doId);

    @Update("update ops_do_item set delflag = 1,modify_time=getdate() where do_id = #{doId} and delflag = 0 ")
    int updateDoItemToDel(String doId);

    @Update("update ops_do_item_inventory set delflag = 1,modify_time=getdate() where id = #{Id} and delflag = 0 ")
    int updateDoToItemInvDel(Long Id);

    @Update("update ops_ro set delflag = 1,modify_time=getdate() where ro_id = #{roId} and delflag = 0 ")
    int updateROToDel(String roId);

    @Update("update ops_ro_item set delflag = 1,modify_time=getdate() where ro_id = #{roId} and delflag = 0 ")
    int updateRoItemDel(String roId);

    @Update("update ops_do_item set qty = #{qty},modify_time=getdate() where do_id = #{doId} and delflag = 0 ")
    int updateDoItemQTY(String doId,Integer qty);

    @Update("update ops_do_item_inventory set use_qty = #{qty},modify_time=getdate() where id = #{Id} and delflag = 0 ")
    int updateDoToItemInvQTY(Long Id,Integer qty);

    @Update("update ops_ro_item set qty = #{qty},modify_time=getdate() where ro_id = #{roId} and delflag = 0 ")
    int updateRoItemQTY(String roId,Integer qty);

}
