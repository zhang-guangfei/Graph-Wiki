package com.sales.ops.db.extdao;

import com.sales.ops.dto.order.OrderStatusViewVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderStatusViewDao {

    @Select("SELECT i.order_id, i.order_item, i.split_no, i.pco_item, s.split_type, s.modelno, s.qty AS qty_do, i.qty, i.qty_in, i.qty_out, " +
            " i.associate_no, i.status, i.status_desc, i.status_info, s.wm_order_id, i.inventory_id, i.inventory_table, s.wl_date,  s.warehouse_code, s.modify_time " +
            "FROM order_status s " +
            "INNER JOIN order_status_item i ON s.order_id = i.order_id AND s.order_item = i.order_item " +
            "AND s.split_no = i.split_no AND s.pco_item = i.pco_item " +
            "where s.order_id=#{orderNo} and s.order_item=#{orderItem} ")
    List<OrderStatusViewVO> selectOrderStatusViewList(String orderNo, Integer orderItem);

}
