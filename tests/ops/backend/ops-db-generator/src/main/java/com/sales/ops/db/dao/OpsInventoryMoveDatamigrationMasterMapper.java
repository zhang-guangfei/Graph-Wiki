package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryMoveDatamigrationMaster;
import com.sales.ops.db.entity.OpsInventoryMoveDatamigrationMasterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryMoveDatamigrationMasterMapper {
    long countByExample(OpsInventoryMoveDatamigrationMasterExample example);

    int deleteByExample(OpsInventoryMoveDatamigrationMasterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInventoryMoveDatamigrationMaster record);

    int insertSelective(OpsInventoryMoveDatamigrationMaster record);

    List<OpsInventoryMoveDatamigrationMaster> selectByExample(OpsInventoryMoveDatamigrationMasterExample example);

    OpsInventoryMoveDatamigrationMaster selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInventoryMoveDatamigrationMaster record, @Param("example") OpsInventoryMoveDatamigrationMasterExample example);

    int updateByExample(@Param("record") OpsInventoryMoveDatamigrationMaster record, @Param("example") OpsInventoryMoveDatamigrationMasterExample example);

    int updateByPrimaryKeySelective(OpsInventoryMoveDatamigrationMaster record);

    int updateByPrimaryKey(OpsInventoryMoveDatamigrationMaster record);
}