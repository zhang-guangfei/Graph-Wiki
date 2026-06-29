package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsImpdataInsert;
import com.sales.ops.db.entity.OpsImpdataInsertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsImpdataInsertMapper {
    long countByExample(OpsImpdataInsertExample example);

    int deleteByExample(OpsImpdataInsertExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsImpdataInsert record);

    int insertSelective(OpsImpdataInsert record);

    List<OpsImpdataInsert> selectByExample(OpsImpdataInsertExample example);

    OpsImpdataInsert selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsImpdataInsert record, @Param("example") OpsImpdataInsertExample example);

    int updateByExample(@Param("record") OpsImpdataInsert record, @Param("example") OpsImpdataInsertExample example);

    int updateByPrimaryKeySelective(OpsImpdataInsert record);

    int updateByPrimaryKey(OpsImpdataInsert record);
}