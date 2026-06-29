package com.sales.ops.db.extdao;
import org.apache.ibatis.annotations.Select;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/28 17:24
 */
public interface OrderDao {

    @Select("SELECT  prod_flag  from rcvdetail where rorder_fno =#{rorderFno}")
    String getOrderProdFlag(String rorderFno);

    @Select("SELECT dlv_entire  from rcvmaster where rorder_no = #{orderFno}")
    String getOrderDlvEntry(String orderFno);

    @Select("SELECT TOP 1 do_source  FROM ops_do WHERE delflag =0 AND order_id = #{orderId} AND order_item = #{orderItem} AND do_type ='JYCK'")
    String getDoSource(String orderId, Integer orderItem);
}
