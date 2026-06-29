package com.sales.ops.db.dao;

import com.sales.ops.db.entity.EventTypeConfig;
import com.sales.ops.db.entity.EventTypeConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventTypeConfigMapper {
    long countByExample(EventTypeConfigExample example);

    int deleteByExample(EventTypeConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(EventTypeConfig record);

    int insertSelective(EventTypeConfig record);

    List<EventTypeConfig> selectByExample(EventTypeConfigExample example);

    EventTypeConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") EventTypeConfig record, @Param("example") EventTypeConfigExample example);

    int updateByExample(@Param("record") EventTypeConfig record, @Param("example") EventTypeConfigExample example);

    int updateByPrimaryKeySelective(EventTypeConfig record);

    int updateByPrimaryKey(EventTypeConfig record);
}