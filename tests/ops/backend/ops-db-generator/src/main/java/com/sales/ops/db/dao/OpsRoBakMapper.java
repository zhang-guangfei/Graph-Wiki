package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRoBak;
import com.sales.ops.db.entity.OpsRoBakExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRoBakMapper {
    long countByExample(OpsRoBakExample example);

    int deleteByExample(OpsRoBakExample example);

    int deleteByPrimaryKey(String roId);

    int insert(OpsRoBak record);

    int insertSelective(OpsRoBak record);

    List<OpsRoBak> selectByExample(OpsRoBakExample example);

    OpsRoBak selectByPrimaryKey(String roId);

    int updateByExampleSelective(@Param("record") OpsRoBak record, @Param("example") OpsRoBakExample example);

    int updateByExample(@Param("record") OpsRoBak record, @Param("example") OpsRoBakExample example);

    int updateByPrimaryKeySelective(OpsRoBak record);

    int updateByPrimaryKey(OpsRoBak record);
}