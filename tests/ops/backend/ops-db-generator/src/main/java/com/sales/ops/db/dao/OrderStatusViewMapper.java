package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderStatusView;
import com.sales.ops.db.entity.OrderStatusViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderStatusViewMapper {
    long countByExample(OrderStatusViewExample example);

    int deleteByExample(OrderStatusViewExample example);

    int insert(OrderStatusView record);

    int insertSelective(OrderStatusView record);

    List<OrderStatusView> selectByExample(OrderStatusViewExample example);

    int updateByExampleSelective(@Param("record") OrderStatusView record, @Param("example") OrderStatusViewExample example);

    int updateByExample(@Param("record") OrderStatusView record, @Param("example") OrderStatusViewExample example);
}