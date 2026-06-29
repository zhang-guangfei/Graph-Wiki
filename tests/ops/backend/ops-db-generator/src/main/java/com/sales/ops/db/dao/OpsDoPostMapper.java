package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDoPost;
import com.sales.ops.db.entity.OpsDoPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsDoPostMapper {
    long countByExample(OpsDoPostExample example);

    int deleteByExample(OpsDoPostExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsDoPost record);

    int insertSelective(OpsDoPost record);

    List<OpsDoPost> selectByExample(OpsDoPostExample example);

    OpsDoPost selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsDoPost record, @Param("example") OpsDoPostExample example);

    int updateByExample(@Param("record") OpsDoPost record, @Param("example") OpsDoPostExample example);

    int updateByPrimaryKeySelective(OpsDoPost record);

    int updateByPrimaryKey(OpsDoPost record);
}