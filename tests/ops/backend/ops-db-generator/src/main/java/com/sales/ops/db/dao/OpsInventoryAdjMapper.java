package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryAdj;
import com.sales.ops.db.entity.OpsInventoryAdjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryAdjMapper {
    long countByExample(OpsInventoryAdjExample example);

    int deleteByExample(OpsInventoryAdjExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInventoryAdj record);

    int insertSelective(OpsInventoryAdj record);

    List<OpsInventoryAdj> selectByExample(OpsInventoryAdjExample example);

    OpsInventoryAdj selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInventoryAdj record, @Param("example") OpsInventoryAdjExample example);

    int updateByExample(@Param("record") OpsInventoryAdj record, @Param("example") OpsInventoryAdjExample example);

    int updateByPrimaryKeySelective(OpsInventoryAdj record);

    int updateByPrimaryKey(OpsInventoryAdj record);
}