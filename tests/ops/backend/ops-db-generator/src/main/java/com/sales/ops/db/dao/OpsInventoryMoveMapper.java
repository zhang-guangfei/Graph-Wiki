package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.db.entity.OpsInventoryMoveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryMoveMapper {
    long countByExample(OpsInventoryMoveExample example);

    int deleteByExample(OpsInventoryMoveExample example);

    int deleteByPrimaryKey(Long inventoryId);

    int insert(OpsInventoryMove record);

    int insertSelective(OpsInventoryMove record);

    List<OpsInventoryMove> selectByExample(OpsInventoryMoveExample example);

    OpsInventoryMove selectByPrimaryKey(Long inventoryId);

    int updateByExampleSelective(@Param("record") OpsInventoryMove record, @Param("example") OpsInventoryMoveExample example);

    int updateByExample(@Param("record") OpsInventoryMove record, @Param("example") OpsInventoryMoveExample example);

    int updateByPrimaryKeySelective(OpsInventoryMove record);

    int updateByPrimaryKey(OpsInventoryMove record);
}