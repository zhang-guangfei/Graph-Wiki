package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ActionLog;
import com.sales.ops.db.entity.ActionLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActionLogMapper {
    long countByExample(ActionLogExample example);

    int deleteByExample(ActionLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ActionLog record);

    int insertSelective(ActionLog record);

    List<ActionLog> selectByExample(ActionLogExample example);

    ActionLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ActionLog record, @Param("example") ActionLogExample example);

    int updateByExample(@Param("record") ActionLog record, @Param("example") ActionLogExample example);

    int updateByPrimaryKeySelective(ActionLog record);

    int updateByPrimaryKey(ActionLog record);
}