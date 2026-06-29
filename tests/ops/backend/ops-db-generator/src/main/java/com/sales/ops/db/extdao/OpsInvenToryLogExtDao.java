package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

/**
 * @author ：c02483
 * @date ：Created in 2022/2/5 13:03
 * @description：库存操作
 */
public interface OpsInvenToryLogExtDao {

    @Insert(" insert into dbo.ops_inventory_log_bak ( warehouse_code, inventory_status, quantity, qa_status, prepare_quantity, modelno, inventory_property_id, version, delflag, order_id, order_item, inventory_id ) " +
            "  SELECT warehouse_code, inventory_status, quantity, qa_status, prepare_quantity, modelno, inventory_property_id, version, delflag, #{orderid}, #{orderItem}, inventory_id  FROM dbo.ops_inventory  " +
            " WHERE modelno=#{modelno} AND delflag=0 ")
    void insertopsInventoryLogBak(@Param("orderid") String orderid,@Param("orderItem") String orderItem,@Param("modelno") String modelno);
    @Insert(" insert into dbo.ops_inventory_move_log_bak ( warehouse_code, inventory_status, quantity, qa_status, prepare_quantity, modelno, inventory_property_id, version, delflag, order_id, order_item, inventory_id ) " +
            "  SELECT warehouse_code, inventory_status, quantity, qa_status, prepare_quantity, modelno, inventory_property_id, version, delflag, #{orderid}, #{orderItem}, inventory_id  FROM dbo.ops_inventory_move  " +
            " WHERE modelno=#{modelno} AND delflag=0 ")
    void insertopsInventoryMoveLogBak(@Param("orderid") String orderid,@Param("orderItem") String orderItem,@Param("modelno") String modelno);
}
