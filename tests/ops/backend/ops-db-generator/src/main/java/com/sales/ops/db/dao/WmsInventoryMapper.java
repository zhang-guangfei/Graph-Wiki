package com.sales.ops.db.dao;

import com.sales.ops.db.entity.WmsInventory;
import com.sales.ops.db.entity.WmsInventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsInventoryMapper {
    long countByExample(WmsInventoryExample example);

    int deleteByExample(WmsInventoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WmsInventory record);

    int insertSelective(WmsInventory record);

    List<WmsInventory> selectByExample(WmsInventoryExample example);

    WmsInventory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WmsInventory record, @Param("example") WmsInventoryExample example);

    int updateByExample(@Param("record") WmsInventory record, @Param("example") WmsInventoryExample example);

    int updateByPrimaryKeySelective(WmsInventory record);

    int updateByPrimaryKey(WmsInventory record);
}