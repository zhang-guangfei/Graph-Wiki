package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InventoryView;
import com.sales.ops.db.entity.InventoryViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryViewMapper {
    long countByExample(InventoryViewExample example);

    int deleteByExample(InventoryViewExample example);

    int insert(InventoryView record);

    int insertSelective(InventoryView record);

    List<InventoryView> selectByExample(InventoryViewExample example);

    int updateByExampleSelective(@Param("record") InventoryView record, @Param("example") InventoryViewExample example);

    int updateByExample(@Param("record") InventoryView record, @Param("example") InventoryViewExample example);
}