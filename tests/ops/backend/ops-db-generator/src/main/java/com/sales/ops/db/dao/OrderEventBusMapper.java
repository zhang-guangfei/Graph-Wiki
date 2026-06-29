package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderEventBus;
import com.sales.ops.db.entity.OrderEventBusExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderEventBusMapper {
    long countByExample(OrderEventBusExample example);

    int deleteByExample(OrderEventBusExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderEventBus record);

    int insertSelective(OrderEventBus record);

    List<OrderEventBus> selectByExampleWithBLOBs(OrderEventBusExample example);

    List<OrderEventBus> selectByExample(OrderEventBusExample example);

    OrderEventBus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderEventBus record, @Param("example") OrderEventBusExample example);

    int updateByExampleWithBLOBs(@Param("record") OrderEventBus record, @Param("example") OrderEventBusExample example);

    int updateByExample(@Param("record") OrderEventBus record, @Param("example") OrderEventBusExample example);

    int updateByPrimaryKeySelective(OrderEventBus record);

    int updateByPrimaryKeyWithBLOBs(OrderEventBus record);

    int updateByPrimaryKey(OrderEventBus record);
}