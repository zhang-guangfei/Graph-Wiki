package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrdersalesBj;
import com.sales.ops.db.entity.OrdersalesBjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdersalesBjMapper {
    long countByExample(OrdersalesBjExample example);

    int deleteByExample(OrdersalesBjExample example);

    int insert(OrdersalesBj record);

    int insertSelective(OrdersalesBj record);

    List<OrdersalesBj> selectByExample(OrdersalesBjExample example);

    int updateByExampleSelective(@Param("record") OrdersalesBj record, @Param("example") OrdersalesBjExample example);

    int updateByExample(@Param("record") OrdersalesBj record, @Param("example") OrdersalesBjExample example);
}