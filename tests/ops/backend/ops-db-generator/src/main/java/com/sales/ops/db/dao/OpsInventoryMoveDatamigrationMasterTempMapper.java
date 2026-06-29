package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryMoveDatamigrationMasterTemp;
import com.sales.ops.db.entity.OpsInventoryMoveDatamigrationMasterTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryMoveDatamigrationMasterTempMapper {
    long countByExample(OpsInventoryMoveDatamigrationMasterTempExample example);

    int deleteByExample(OpsInventoryMoveDatamigrationMasterTempExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInventoryMoveDatamigrationMasterTemp record);

    int insertSelective(OpsInventoryMoveDatamigrationMasterTemp record);

    List<OpsInventoryMoveDatamigrationMasterTemp> selectByExample(OpsInventoryMoveDatamigrationMasterTempExample example);

    OpsInventoryMoveDatamigrationMasterTemp selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInventoryMoveDatamigrationMasterTemp record, @Param("example") OpsInventoryMoveDatamigrationMasterTempExample example);

    int updateByExample(@Param("record") OpsInventoryMoveDatamigrationMasterTemp record, @Param("example") OpsInventoryMoveDatamigrationMasterTempExample example);

    int updateByPrimaryKeySelective(OpsInventoryMoveDatamigrationMasterTemp record);

    int updateByPrimaryKey(OpsInventoryMoveDatamigrationMasterTemp record);
}