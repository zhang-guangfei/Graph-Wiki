package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCtcUpdate;
import com.sales.ops.db.entity.OpsCtcUpdateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCtcUpdateMapper {
    long countByExample(OpsCtcUpdateExample example);

    int deleteByExample(OpsCtcUpdateExample example);

    int deleteByPrimaryKey(Integer logid);

    int insert(OpsCtcUpdate record);

    int insertSelective(OpsCtcUpdate record);

    List<OpsCtcUpdate> selectByExample(OpsCtcUpdateExample example);

    OpsCtcUpdate selectByPrimaryKey(Integer logid);

    int updateByExampleSelective(@Param("record") OpsCtcUpdate record, @Param("example") OpsCtcUpdateExample example);

    int updateByExample(@Param("record") OpsCtcUpdate record, @Param("example") OpsCtcUpdateExample example);

    int updateByPrimaryKeySelective(OpsCtcUpdate record);

    int updateByPrimaryKey(OpsCtcUpdate record);
}