package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrdersalesGz;
import com.sales.ops.db.entity.OrdersalesGzExample;
import com.sales.ops.db.entity.OrdersalesGzKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdersalesGzMapper {
    long countByExample(OrdersalesGzExample example);

    int deleteByExample(OrdersalesGzExample example);

    int deleteByPrimaryKey(OrdersalesGzKey key);

    int insert(OrdersalesGz record);

    int insertSelective(OrdersalesGz record);

    List<OrdersalesGz> selectByExample(OrdersalesGzExample example);

    OrdersalesGz selectByPrimaryKey(OrdersalesGzKey key);

    int updateByExampleSelective(@Param("record") OrdersalesGz record, @Param("example") OrdersalesGzExample example);

    int updateByExample(@Param("record") OrdersalesGz record, @Param("example") OrdersalesGzExample example);

    int updateByPrimaryKeySelective(OrdersalesGz record);

    int updateByPrimaryKey(OrdersalesGz record);
}