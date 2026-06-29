package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Samplemodel;
import com.sales.ops.db.entity.SamplemodelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SamplemodelMapper {
    long countByExample(SamplemodelExample example);

    int deleteByExample(SamplemodelExample example);

    int deleteByPrimaryKey(String samplemodelno);

    int insert(Samplemodel record);

    int insertSelective(Samplemodel record);

    List<Samplemodel> selectByExample(SamplemodelExample example);

    Samplemodel selectByPrimaryKey(String samplemodelno);

    int updateByExampleSelective(@Param("record") Samplemodel record, @Param("example") SamplemodelExample example);

    int updateByExample(@Param("record") Samplemodel record, @Param("example") SamplemodelExample example);

    int updateByPrimaryKeySelective(Samplemodel record);

    int updateByPrimaryKey(Samplemodel record);
}