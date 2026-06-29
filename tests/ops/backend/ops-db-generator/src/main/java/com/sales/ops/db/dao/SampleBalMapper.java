package com.sales.ops.db.dao;

import com.sales.ops.db.entity.SampleBal;
import com.sales.ops.db.entity.SampleBalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SampleBalMapper {
    long countByExample(SampleBalExample example);

    int deleteByExample(SampleBalExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SampleBal record);

    int insertSelective(SampleBal record);

    List<SampleBal> selectByExample(SampleBalExample example);

    SampleBal selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SampleBal record, @Param("example") SampleBalExample example);

    int updateByExample(@Param("record") SampleBal record, @Param("example") SampleBalExample example);

    int updateByPrimaryKeySelective(SampleBal record);

    int updateByPrimaryKey(SampleBal record);
}