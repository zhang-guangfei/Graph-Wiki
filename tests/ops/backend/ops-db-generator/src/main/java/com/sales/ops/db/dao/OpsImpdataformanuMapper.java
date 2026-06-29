package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsImpdataformanu;
import com.sales.ops.db.entity.OpsImpdataformanuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsImpdataformanuMapper {
    long countByExample(OpsImpdataformanuExample example);

    int deleteByExample(OpsImpdataformanuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsImpdataformanu record);

    int insertSelective(OpsImpdataformanu record);

    List<OpsImpdataformanu> selectByExample(OpsImpdataformanuExample example);

    OpsImpdataformanu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsImpdataformanu record, @Param("example") OpsImpdataformanuExample example);

    int updateByExample(@Param("record") OpsImpdataformanu record, @Param("example") OpsImpdataformanuExample example);

    int updateByPrimaryKeySelective(OpsImpdataformanu record);

    int updateByPrimaryKey(OpsImpdataformanu record);
}