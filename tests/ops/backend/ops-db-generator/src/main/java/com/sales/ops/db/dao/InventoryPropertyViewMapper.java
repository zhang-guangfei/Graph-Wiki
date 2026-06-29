package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InventoryPropertyView;
import com.sales.ops.db.entity.InventoryPropertyViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryPropertyViewMapper {
    long countByExample(InventoryPropertyViewExample example);

    int deleteByExample(InventoryPropertyViewExample example);

    int insert(InventoryPropertyView record);

    int insertSelective(InventoryPropertyView record);

    List<InventoryPropertyView> selectByExample(InventoryPropertyViewExample example);

    int updateByExampleSelective(@Param("record") InventoryPropertyView record, @Param("example") InventoryPropertyViewExample example);

    int updateByExample(@Param("record") InventoryPropertyView record, @Param("example") InventoryPropertyViewExample example);
}