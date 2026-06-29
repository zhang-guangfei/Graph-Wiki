package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderInventoryRelation;
import com.sales.ops.db.entity.OrderInventoryRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderInventoryRelationMapper {
    long countByExample(OrderInventoryRelationExample example);

    int deleteByExample(OrderInventoryRelationExample example);

    int insert(OrderInventoryRelation record);

    int insertSelective(OrderInventoryRelation record);

    List<OrderInventoryRelation> selectByExample(OrderInventoryRelationExample example);

    int updateByExampleSelective(@Param("record") OrderInventoryRelation record, @Param("example") OrderInventoryRelationExample example);

    int updateByExample(@Param("record") OrderInventoryRelation record, @Param("example") OrderInventoryRelationExample example);
}