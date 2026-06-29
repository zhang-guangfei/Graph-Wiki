package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderInventory;
import com.sales.ops.db.entity.OrderInventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderInventoryMapper {
    long countByExample(OrderInventoryExample example);

    int deleteByExample(OrderInventoryExample example);

    int insert(OrderInventory record);

    int insertSelective(OrderInventory record);

    List<OrderInventory> selectByExample(OrderInventoryExample example);

    int updateByExampleSelective(@Param("record") OrderInventory record, @Param("example") OrderInventoryExample example);

    int updateByExample(@Param("record") OrderInventory record, @Param("example") OrderInventoryExample example);
}