package com.sales.ops.db.dao;

import com.sales.ops.db.entity.CsAgentExpData;
import com.sales.ops.db.entity.CsAgentExpDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CsAgentExpDataMapper {
    long countByExample(CsAgentExpDataExample example);

    int deleteByExample(CsAgentExpDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CsAgentExpData record);

    int insertSelective(CsAgentExpData record);

    List<CsAgentExpData> selectByExample(CsAgentExpDataExample example);

    CsAgentExpData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CsAgentExpData record, @Param("example") CsAgentExpDataExample example);

    int updateByExample(@Param("record") CsAgentExpData record, @Param("example") CsAgentExpDataExample example);

    int updateByPrimaryKeySelective(CsAgentExpData record);

    int updateByPrimaryKey(CsAgentExpData record);
}