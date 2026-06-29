package com.sales.ops.db.dao;

import com.sales.ops.db.entity.EventRehandleConfig;
import com.sales.ops.db.entity.EventRehandleConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventRehandleConfigMapper {
    long countByExample(EventRehandleConfigExample example);

    int deleteByExample(EventRehandleConfigExample example);

    int insert(EventRehandleConfig record);

    int insertSelective(EventRehandleConfig record);

    List<EventRehandleConfig> selectByExample(EventRehandleConfigExample example);

    int updateByExampleSelective(@Param("record") EventRehandleConfig record, @Param("example") EventRehandleConfigExample example);

    int updateByExample(@Param("record") EventRehandleConfig record, @Param("example") EventRehandleConfigExample example);
}