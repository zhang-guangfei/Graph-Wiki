package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryMoveLogBak;
import com.sales.ops.db.entity.OpsInventoryMoveLogBakExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryMoveLogBakMapper {
    long countByExample(OpsInventoryMoveLogBakExample example);

    int deleteByExample(OpsInventoryMoveLogBakExample example);

    int deleteByPrimaryKey(Long logId);

    int insert(OpsInventoryMoveLogBak record);

    int insertSelective(OpsInventoryMoveLogBak record);

    List<OpsInventoryMoveLogBak> selectByExample(OpsInventoryMoveLogBakExample example);

    OpsInventoryMoveLogBak selectByPrimaryKey(Long logId);

    int updateByExampleSelective(@Param("record") OpsInventoryMoveLogBak record, @Param("example") OpsInventoryMoveLogBakExample example);

    int updateByExample(@Param("record") OpsInventoryMoveLogBak record, @Param("example") OpsInventoryMoveLogBakExample example);

    int updateByPrimaryKeySelective(OpsInventoryMoveLogBak record);

    int updateByPrimaryKey(OpsInventoryMoveLogBak record);
}