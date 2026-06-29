package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Select;

public interface OrderReturnQtyDao {

    @Select("select sum(return_qty) from ops_sharedb.dbo.return_order where order_no = #{rorderFno} and status = 6 ")
    Integer selectReturnedQtyByRorderFno(String rorderFno);



}
