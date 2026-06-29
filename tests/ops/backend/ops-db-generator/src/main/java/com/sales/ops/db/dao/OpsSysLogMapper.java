package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsSysLog;
import com.sales.ops.db.entity.OpsSysLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsSysLogMapper {
    long countByExample(OpsSysLogExample example);

    int deleteByExample(OpsSysLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsSysLog record);

    int insertSelective(OpsSysLog record);

    List<OpsSysLog> selectByExample(OpsSysLogExample example);

    OpsSysLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsSysLog record, @Param("example") OpsSysLogExample example);

    int updateByExample(@Param("record") OpsSysLog record, @Param("example") OpsSysLogExample example);

    int updateByPrimaryKeySelective(OpsSysLog record);

    int updateByPrimaryKey(OpsSysLog record);
}