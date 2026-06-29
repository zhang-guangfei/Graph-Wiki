package com.sales.ops.db.dao;

import com.sales.ops.db.entity.CsAgentImpDetail;
import com.sales.ops.db.entity.CsAgentImpDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CsAgentImpDetailMapper {
    long countByExample(CsAgentImpDetailExample example);

    int deleteByExample(CsAgentImpDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CsAgentImpDetail record);

    int insertSelective(CsAgentImpDetail record);

    List<CsAgentImpDetail> selectByExample(CsAgentImpDetailExample example);

    CsAgentImpDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CsAgentImpDetail record, @Param("example") CsAgentImpDetailExample example);

    int updateByExample(@Param("record") CsAgentImpDetail record, @Param("example") CsAgentImpDetailExample example);

    int updateByPrimaryKeySelective(CsAgentImpDetail record);

    int updateByPrimaryKey(CsAgentImpDetail record);
}