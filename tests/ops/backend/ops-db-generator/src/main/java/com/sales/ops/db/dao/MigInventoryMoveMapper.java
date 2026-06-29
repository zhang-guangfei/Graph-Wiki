package com.sales.ops.db.dao;

import com.sales.ops.db.entity.MigInventoryMove;
import com.sales.ops.db.entity.MigInventoryMoveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MigInventoryMoveMapper {
    long countByExample(MigInventoryMoveExample example);

    int deleteByExample(MigInventoryMoveExample example);

    int insert(MigInventoryMove record);

    int insertSelective(MigInventoryMove record);

    List<MigInventoryMove> selectByExample(MigInventoryMoveExample example);

    int updateByExampleSelective(@Param("record") MigInventoryMove record, @Param("example") MigInventoryMoveExample example);

    int updateByExample(@Param("record") MigInventoryMove record, @Param("example") MigInventoryMoveExample example);
}