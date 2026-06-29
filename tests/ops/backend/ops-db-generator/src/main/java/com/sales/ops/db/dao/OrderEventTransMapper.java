package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderEventTrans;
import com.sales.ops.db.entity.OrderEventTransExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderEventTransMapper {
    long countByExample(OrderEventTransExample example);

    int deleteByExample(OrderEventTransExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderEventTrans record);

    int insertSelective(OrderEventTrans record);

    List<OrderEventTrans> selectByExampleWithBLOBs(OrderEventTransExample example);

    List<OrderEventTrans> selectByExample(OrderEventTransExample example);

    OrderEventTrans selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderEventTrans record, @Param("example") OrderEventTransExample example);

    int updateByExampleWithBLOBs(@Param("record") OrderEventTrans record, @Param("example") OrderEventTransExample example);

    int updateByExample(@Param("record") OrderEventTrans record, @Param("example") OrderEventTransExample example);

    int updateByPrimaryKeySelective(OrderEventTrans record);

    int updateByPrimaryKeyWithBLOBs(OrderEventTrans record);

    int updateByPrimaryKey(OrderEventTrans record);
}