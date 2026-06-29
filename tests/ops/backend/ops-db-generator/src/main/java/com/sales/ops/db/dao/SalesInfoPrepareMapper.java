package com.sales.ops.db.dao;

import com.sales.ops.db.entity.SalesInfoPrepare;
import com.sales.ops.db.entity.SalesInfoPrepareExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SalesInfoPrepareMapper {
    long countByExample(SalesInfoPrepareExample example);

    int deleteByExample(SalesInfoPrepareExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SalesInfoPrepare record);

    int insertSelective(SalesInfoPrepare record);

    List<SalesInfoPrepare> selectByExample(SalesInfoPrepareExample example);

    SalesInfoPrepare selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SalesInfoPrepare record, @Param("example") SalesInfoPrepareExample example);

    int updateByExample(@Param("record") SalesInfoPrepare record, @Param("example") SalesInfoPrepareExample example);

    int updateByPrimaryKeySelective(SalesInfoPrepare record);

    int updateByPrimaryKey(SalesInfoPrepare record);
}