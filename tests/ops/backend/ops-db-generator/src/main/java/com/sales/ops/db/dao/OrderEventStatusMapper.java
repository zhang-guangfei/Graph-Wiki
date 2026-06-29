package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderEventStatus;
import com.sales.ops.db.entity.OrderEventStatusExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderEventStatusMapper {
    long countByExample(OrderEventStatusExample example);

    int deleteByExample(OrderEventStatusExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderEventStatus record);

    int insertSelective(OrderEventStatus record);

    List<OrderEventStatus> selectByExampleWithBLOBs(OrderEventStatusExample example);

    List<OrderEventStatus> selectByExample(OrderEventStatusExample example);

    OrderEventStatus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderEventStatus record, @Param("example") OrderEventStatusExample example);

    int updateByExampleWithBLOBs(@Param("record") OrderEventStatus record, @Param("example") OrderEventStatusExample example);

    int updateByExample(@Param("record") OrderEventStatus record, @Param("example") OrderEventStatusExample example);

    int updateByPrimaryKeySelective(OrderEventStatus record);

    int updateByPrimaryKeyWithBLOBs(OrderEventStatus record);

    int updateByPrimaryKey(OrderEventStatus record);
}