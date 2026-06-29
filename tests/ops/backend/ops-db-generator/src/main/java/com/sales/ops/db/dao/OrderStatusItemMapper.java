package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderStatusItem;
import com.sales.ops.db.entity.OrderStatusItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderStatusItemMapper {
    long countByExample(OrderStatusItemExample example);

    int deleteByExample(OrderStatusItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderStatusItem record);

    int insertSelective(OrderStatusItem record);

    List<OrderStatusItem> selectByExample(OrderStatusItemExample example);

    OrderStatusItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderStatusItem record, @Param("example") OrderStatusItemExample example);

    int updateByExample(@Param("record") OrderStatusItem record, @Param("example") OrderStatusItemExample example);

    int updateByPrimaryKeySelective(OrderStatusItem record);

    int updateByPrimaryKey(OrderStatusItem record);
}