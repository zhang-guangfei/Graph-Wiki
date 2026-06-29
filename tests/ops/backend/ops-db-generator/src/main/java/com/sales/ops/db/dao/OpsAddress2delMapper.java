package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsAddress2del;
import com.sales.ops.db.entity.OpsAddress2delExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsAddress2delMapper {
    long countByExample(OpsAddress2delExample example);

    int deleteByExample(OpsAddress2delExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsAddress2del record);

    int insertSelective(OpsAddress2del record);

    List<OpsAddress2del> selectByExample(OpsAddress2delExample example);

    OpsAddress2del selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsAddress2del record, @Param("example") OpsAddress2delExample example);

    int updateByExample(@Param("record") OpsAddress2del record, @Param("example") OpsAddress2delExample example);

    int updateByPrimaryKeySelective(OpsAddress2del record);

    int updateByPrimaryKey(OpsAddress2del record);
}