package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRoPost;
import com.sales.ops.db.entity.OpsRoPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRoPostMapper {
    long countByExample(OpsRoPostExample example);

    int deleteByExample(OpsRoPostExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsRoPost record);

    int insertSelective(OpsRoPost record);

    List<OpsRoPost> selectByExample(OpsRoPostExample example);

    OpsRoPost selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsRoPost record, @Param("example") OpsRoPostExample example);

    int updateByExample(@Param("record") OpsRoPost record, @Param("example") OpsRoPostExample example);

    int updateByPrimaryKeySelective(OpsRoPost record);

    int updateByPrimaryKey(OpsRoPost record);
}