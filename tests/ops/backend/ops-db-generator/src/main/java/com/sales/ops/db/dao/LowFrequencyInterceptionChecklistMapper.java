package com.sales.ops.db.dao;

import com.sales.ops.db.entity.LowFrequencyInterceptionChecklist;
import com.sales.ops.db.entity.LowFrequencyInterceptionChecklistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LowFrequencyInterceptionChecklistMapper {
    long countByExample(LowFrequencyInterceptionChecklistExample example);

    int deleteByExample(LowFrequencyInterceptionChecklistExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LowFrequencyInterceptionChecklist record);

    int insertSelective(LowFrequencyInterceptionChecklist record);

    List<LowFrequencyInterceptionChecklist> selectByExample(LowFrequencyInterceptionChecklistExample example);

    LowFrequencyInterceptionChecklist selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LowFrequencyInterceptionChecklist record, @Param("example") LowFrequencyInterceptionChecklistExample example);

    int updateByExample(@Param("record") LowFrequencyInterceptionChecklist record, @Param("example") LowFrequencyInterceptionChecklistExample example);

    int updateByPrimaryKeySelective(LowFrequencyInterceptionChecklist record);

    int updateByPrimaryKey(LowFrequencyInterceptionChecklist record);
}