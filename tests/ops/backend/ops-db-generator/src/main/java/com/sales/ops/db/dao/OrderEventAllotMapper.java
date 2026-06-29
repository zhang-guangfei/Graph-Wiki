package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderEventAllot;
import com.sales.ops.db.entity.OrderEventAllotExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderEventAllotMapper {
    long countByExample(OrderEventAllotExample example);

    int deleteByExample(OrderEventAllotExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderEventAllot record);

    int insertSelective(OrderEventAllot record);

    List<OrderEventAllot> selectByExampleWithBLOBs(OrderEventAllotExample example);

    List<OrderEventAllot> selectByExample(OrderEventAllotExample example);

    OrderEventAllot selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderEventAllot record, @Param("example") OrderEventAllotExample example);

    int updateByExampleWithBLOBs(@Param("record") OrderEventAllot record, @Param("example") OrderEventAllotExample example);

    int updateByExample(@Param("record") OrderEventAllot record, @Param("example") OrderEventAllotExample example);

    int updateByPrimaryKeySelective(OrderEventAllot record);

    int updateByPrimaryKeyWithBLOBs(OrderEventAllot record);

    int updateByPrimaryKey(OrderEventAllot record);
}