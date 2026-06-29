package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryMoveDatamigrationDetail;
import com.sales.ops.db.entity.OpsInventoryMoveDatamigrationDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryMoveDatamigrationDetailMapper {
    long countByExample(OpsInventoryMoveDatamigrationDetailExample example);

    int deleteByExample(OpsInventoryMoveDatamigrationDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInventoryMoveDatamigrationDetail record);

    int insertSelective(OpsInventoryMoveDatamigrationDetail record);

    List<OpsInventoryMoveDatamigrationDetail> selectByExample(OpsInventoryMoveDatamigrationDetailExample example);

    OpsInventoryMoveDatamigrationDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInventoryMoveDatamigrationDetail record, @Param("example") OpsInventoryMoveDatamigrationDetailExample example);

    int updateByExample(@Param("record") OpsInventoryMoveDatamigrationDetail record, @Param("example") OpsInventoryMoveDatamigrationDetailExample example);

    int updateByPrimaryKeySelective(OpsInventoryMoveDatamigrationDetail record);

    int updateByPrimaryKey(OpsInventoryMoveDatamigrationDetail record);
}