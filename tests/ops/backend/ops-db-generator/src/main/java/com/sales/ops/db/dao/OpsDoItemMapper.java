package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDoItem;
import com.sales.ops.db.entity.OpsDoItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsDoItemMapper {
    long countByExample(OpsDoItemExample example);

    int deleteByExample(OpsDoItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsDoItem record);

    int insertSelective(OpsDoItem record);

    List<OpsDoItem> selectByExample(OpsDoItemExample example);

    OpsDoItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsDoItem record, @Param("example") OpsDoItemExample example);

    int updateByExample(@Param("record") OpsDoItem record, @Param("example") OpsDoItemExample example);

    int updateByPrimaryKeySelective(OpsDoItem record);

    int updateByPrimaryKey(OpsDoItem record);
}