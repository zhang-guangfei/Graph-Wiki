package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRo;
import com.sales.ops.db.entity.OpsRoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRoMapper {
    long countByExample(OpsRoExample example);

    int deleteByExample(OpsRoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsRo record);

    int insertSelective(OpsRo record);

    List<OpsRo> selectByExample(OpsRoExample example);

    OpsRo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsRo record, @Param("example") OpsRoExample example);

    int updateByExample(@Param("record") OpsRo record, @Param("example") OpsRoExample example);

    int updateByPrimaryKeySelective(OpsRo record);

    int updateByPrimaryKey(OpsRo record);
}