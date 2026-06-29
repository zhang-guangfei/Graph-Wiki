package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Ordersales;
import com.sales.ops.db.entity.OrdersalesExample;
import com.sales.ops.db.entity.OrdersalesKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrdersalesMapper {
    long countByExample(OrdersalesExample example);

    int deleteByExample(OrdersalesExample example);

    int deleteByPrimaryKey(OrdersalesKey key);

    int insert(Ordersales record);

    int insertSelective(Ordersales record);

    List<Ordersales> selectByExample(OrdersalesExample example);

    Ordersales selectByPrimaryKey(OrdersalesKey key);

    int updateByExampleSelective(@Param("record") Ordersales record, @Param("example") OrdersalesExample example);

    int updateByExample(@Param("record") Ordersales record, @Param("example") OrdersalesExample example);

    int updateByPrimaryKeySelective(Ordersales record);

    int updateByPrimaryKey(Ordersales record);
}