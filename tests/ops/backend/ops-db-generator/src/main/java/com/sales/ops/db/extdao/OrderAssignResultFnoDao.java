package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderAssignResultFnoDao {

    @Select("select order_fno from ops_temp.dbo.ops_order_assign_result_fno17550 where status=0")
    List<String> selectFnoByOrderAssignResultFno();

    @Select("update ops_temp.dbo.ops_order_assign_result_fno17550 set status=1 where status=0 and order_fno=#{orderFno}")
    List<String> updateStatusByOrderFno(String orderFno);
}
