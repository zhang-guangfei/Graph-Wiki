package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderStateLog;
import com.sales.ops.db.entity.OrderStateLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderStateLogMapper {
    long countByExample(OrderStateLogExample example);

    int deleteByExample(OrderStateLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderStateLog record);

    int insertSelective(OrderStateLog record);

    List<OrderStateLog> selectByExample(OrderStateLogExample example);

    OrderStateLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderStateLog record, @Param("example") OrderStateLogExample example);

    int updateByExample(@Param("record") OrderStateLog record, @Param("example") OrderStateLogExample example);

    int updateByPrimaryKeySelective(OrderStateLog record);

    int updateByPrimaryKey(OrderStateLog record);
}