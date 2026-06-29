package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderStatus;
import com.sales.ops.db.entity.OrderStatusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderStatusMapper {
    long countByExample(OrderStatusExample example);

    int deleteByExample(OrderStatusExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderStatus record);

    int insertSelective(OrderStatus record);

    List<OrderStatus> selectByExample(OrderStatusExample example);

    OrderStatus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderStatus record, @Param("example") OrderStatusExample example);

    int updateByExample(@Param("record") OrderStatus record, @Param("example") OrderStatusExample example);

    int updateByPrimaryKeySelective(OrderStatus record);

    int updateByPrimaryKey(OrderStatus record);
}