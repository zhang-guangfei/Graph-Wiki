package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInqbApply;
import com.sales.ops.db.entity.OpsInqbApplyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsInqbApplyMapper {
    long countByExample(OpsInqbApplyExample example);

    int deleteByExample(OpsInqbApplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInqbApply record);

    int insertSelective(OpsInqbApply record);

    List<OpsInqbApply> selectByExample(OpsInqbApplyExample example);

    OpsInqbApply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInqbApply record, @Param("example") OpsInqbApplyExample example);

    int updateByExample(@Param("record") OpsInqbApply record, @Param("example") OpsInqbApplyExample example);

    int updateByPrimaryKeySelective(OpsInqbApply record);

    int updateByPrimaryKey(OpsInqbApply record);
}