package com.sales.ops.db.dao;

import com.sales.ops.db.entity.JobLog;
import com.sales.ops.db.entity.JobLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JobLogMapper {
    long countByExample(JobLogExample example);

    int deleteByExample(JobLogExample example);

    int insert(JobLog record);

    int insertSelective(JobLog record);

    List<JobLog> selectByExample(JobLogExample example);

    int updateByExampleSelective(@Param("record") JobLog record, @Param("example") JobLogExample example);

    int updateByExample(@Param("record") JobLog record, @Param("example") JobLogExample example);
}