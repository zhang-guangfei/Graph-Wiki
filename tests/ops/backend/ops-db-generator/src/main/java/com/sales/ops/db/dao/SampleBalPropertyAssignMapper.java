package com.sales.ops.db.dao;

import com.sales.ops.db.entity.SampleBalPropertyAssign;
import com.sales.ops.db.entity.SampleBalPropertyAssignExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SampleBalPropertyAssignMapper {
    long countByExample(SampleBalPropertyAssignExample example);

    int deleteByExample(SampleBalPropertyAssignExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SampleBalPropertyAssign record);

    int insertSelective(SampleBalPropertyAssign record);

    List<SampleBalPropertyAssign> selectByExample(SampleBalPropertyAssignExample example);

    SampleBalPropertyAssign selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SampleBalPropertyAssign record, @Param("example") SampleBalPropertyAssignExample example);

    int updateByExample(@Param("record") SampleBalPropertyAssign record, @Param("example") SampleBalPropertyAssignExample example);

    int updateByPrimaryKeySelective(SampleBalPropertyAssign record);

    int updateByPrimaryKey(SampleBalPropertyAssign record);
}