package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryDiff;
import com.sales.ops.db.entity.OpsInventoryDiffExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryDiffMapper {
    long countByExample(OpsInventoryDiffExample example);

    int deleteByExample(OpsInventoryDiffExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInventoryDiff record);

    int insertSelective(OpsInventoryDiff record);

    List<OpsInventoryDiff> selectByExample(OpsInventoryDiffExample example);

    OpsInventoryDiff selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInventoryDiff record, @Param("example") OpsInventoryDiffExample example);

    int updateByExample(@Param("record") OpsInventoryDiff record, @Param("example") OpsInventoryDiffExample example);

    int updateByPrimaryKeySelective(OpsInventoryDiff record);

    int updateByPrimaryKey(OpsInventoryDiff record);
}