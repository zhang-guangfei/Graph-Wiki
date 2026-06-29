package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCtcUpdateModelnoLog;
import com.sales.ops.db.entity.OpsCtcUpdateModelnoLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCtcUpdateModelnoLogMapper {
    long countByExample(OpsCtcUpdateModelnoLogExample example);

    int deleteByExample(OpsCtcUpdateModelnoLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsCtcUpdateModelnoLog record);

    int insertSelective(OpsCtcUpdateModelnoLog record);

    List<OpsCtcUpdateModelnoLog> selectByExample(OpsCtcUpdateModelnoLogExample example);

    OpsCtcUpdateModelnoLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsCtcUpdateModelnoLog record, @Param("example") OpsCtcUpdateModelnoLogExample example);

    int updateByExample(@Param("record") OpsCtcUpdateModelnoLog record, @Param("example") OpsCtcUpdateModelnoLogExample example);

    int updateByPrimaryKeySelective(OpsCtcUpdateModelnoLog record);

    int updateByPrimaryKey(OpsCtcUpdateModelnoLog record);
}