package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPayTerm;
import com.sales.ops.db.entity.OpsPayTermExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPayTermMapper {
    long countByExample(OpsPayTermExample example);

    int deleteByExample(OpsPayTermExample example);

    int deleteByPrimaryKey(String customerno);

    int insert(OpsPayTerm record);

    int insertSelective(OpsPayTerm record);

    List<OpsPayTerm> selectByExample(OpsPayTermExample example);

    OpsPayTerm selectByPrimaryKey(String customerno);

    int updateByExampleSelective(@Param("record") OpsPayTerm record, @Param("example") OpsPayTermExample example);

    int updateByExample(@Param("record") OpsPayTerm record, @Param("example") OpsPayTermExample example);

    int updateByPrimaryKeySelective(OpsPayTerm record);

    int updateByPrimaryKey(OpsPayTerm record);
}