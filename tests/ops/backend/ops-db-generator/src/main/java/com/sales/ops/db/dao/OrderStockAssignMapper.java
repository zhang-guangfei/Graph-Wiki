package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderStockAssign;
import com.sales.ops.db.entity.OrderStockAssignExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderStockAssignMapper {
    long countByExample(OrderStockAssignExample example);

    int deleteByExample(OrderStockAssignExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderStockAssign record);

    int insertSelective(OrderStockAssign record);

    List<OrderStockAssign> selectByExample(OrderStockAssignExample example);

    OrderStockAssign selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderStockAssign record, @Param("example") OrderStockAssignExample example);

    int updateByExample(@Param("record") OrderStockAssign record, @Param("example") OrderStockAssignExample example);

    int updateByPrimaryKeySelective(OrderStockAssign record);

    int updateByPrimaryKey(OrderStockAssign record);
}