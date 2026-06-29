package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInqbDetail;
import com.sales.ops.db.entity.OpsInqbDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsInqbDetailMapper {
    long countByExample(OpsInqbDetailExample example);

    int deleteByExample(OpsInqbDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInqbDetail record);

    int insertSelective(OpsInqbDetail record);

    List<OpsInqbDetail> selectByExample(OpsInqbDetailExample example);

    OpsInqbDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInqbDetail record, @Param("example") OpsInqbDetailExample example);

    int updateByExample(@Param("record") OpsInqbDetail record, @Param("example") OpsInqbDetailExample example);

    int updateByPrimaryKeySelective(OpsInqbDetail record);

    int updateByPrimaryKey(OpsInqbDetail record);
}