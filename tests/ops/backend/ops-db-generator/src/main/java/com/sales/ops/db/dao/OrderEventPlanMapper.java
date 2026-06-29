package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderEventPlan;
import com.sales.ops.db.entity.OrderEventPlanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderEventPlanMapper {
    long countByExample(OrderEventPlanExample example);

    int deleteByExample(OrderEventPlanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderEventPlan record);

    int insertSelective(OrderEventPlan record);

    List<OrderEventPlan> selectByExampleWithBLOBs(OrderEventPlanExample example);

    List<OrderEventPlan> selectByExample(OrderEventPlanExample example);

    OrderEventPlan selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderEventPlan record, @Param("example") OrderEventPlanExample example);

    int updateByExampleWithBLOBs(@Param("record") OrderEventPlan record, @Param("example") OrderEventPlanExample example);

    int updateByExample(@Param("record") OrderEventPlan record, @Param("example") OrderEventPlanExample example);

    int updateByPrimaryKeySelective(OrderEventPlan record);

    int updateByPrimaryKeyWithBLOBs(OrderEventPlan record);

    int updateByPrimaryKey(OrderEventPlan record);
}