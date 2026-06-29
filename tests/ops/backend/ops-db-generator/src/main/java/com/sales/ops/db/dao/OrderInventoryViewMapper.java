package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderInventoryView;
import com.sales.ops.db.entity.OrderInventoryViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderInventoryViewMapper {
    long countByExample(OrderInventoryViewExample example);

    int deleteByExample(OrderInventoryViewExample example);

    int insert(OrderInventoryView record);

    int insertSelective(OrderInventoryView record);

    List<OrderInventoryView> selectByExample(OrderInventoryViewExample example);

    int updateByExampleSelective(@Param("record") OrderInventoryView record, @Param("example") OrderInventoryViewExample example);

    int updateByExample(@Param("record") OrderInventoryView record, @Param("example") OrderInventoryViewExample example);
}