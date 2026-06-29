package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCtcNotypeidModelno;
import com.sales.ops.db.entity.OpsCtcNotypeidModelnoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCtcNotypeidModelnoMapper {
    long countByExample(OpsCtcNotypeidModelnoExample example);

    int deleteByExample(OpsCtcNotypeidModelnoExample example);

    int insert(OpsCtcNotypeidModelno record);

    int insertSelective(OpsCtcNotypeidModelno record);

    List<OpsCtcNotypeidModelno> selectByExample(OpsCtcNotypeidModelnoExample example);

    int updateByExampleSelective(@Param("record") OpsCtcNotypeidModelno record, @Param("example") OpsCtcNotypeidModelnoExample example);

    int updateByExample(@Param("record") OpsCtcNotypeidModelno record, @Param("example") OpsCtcNotypeidModelnoExample example);
}