package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryOld;
import com.sales.ops.db.entity.OpsInventoryOldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryOldMapper {
    long countByExample(OpsInventoryOldExample example);

    int deleteByExample(OpsInventoryOldExample example);

    int deleteByPrimaryKey(Long inventoryId);

    int insert(OpsInventoryOld record);

    int insertSelective(OpsInventoryOld record);

    List<OpsInventoryOld> selectByExample(OpsInventoryOldExample example);

    OpsInventoryOld selectByPrimaryKey(Long inventoryId);

    int updateByExampleSelective(@Param("record") OpsInventoryOld record, @Param("example") OpsInventoryOldExample example);

    int updateByExample(@Param("record") OpsInventoryOld record, @Param("example") OpsInventoryOldExample example);

    int updateByPrimaryKeySelective(OpsInventoryOld record);

    int updateByPrimaryKey(OpsInventoryOld record);
}