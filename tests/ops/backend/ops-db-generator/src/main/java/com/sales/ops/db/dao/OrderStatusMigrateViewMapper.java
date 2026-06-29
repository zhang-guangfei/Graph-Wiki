package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderStatusMigrateView;
import com.sales.ops.db.entity.OrderStatusMigrateViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderStatusMigrateViewMapper {
    long countByExample(OrderStatusMigrateViewExample example);

    int deleteByExample(OrderStatusMigrateViewExample example);

    int insert(OrderStatusMigrateView record);

    int insertSelective(OrderStatusMigrateView record);

    List<OrderStatusMigrateView> selectByExample(OrderStatusMigrateViewExample example);

    int updateByExampleSelective(@Param("record") OrderStatusMigrateView record, @Param("example") OrderStatusMigrateViewExample example);

    int updateByExample(@Param("record") OrderStatusMigrateView record, @Param("example") OrderStatusMigrateViewExample example);
}