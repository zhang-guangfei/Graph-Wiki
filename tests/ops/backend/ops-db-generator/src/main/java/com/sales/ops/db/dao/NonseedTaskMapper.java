package com.sales.ops.db.dao;

import com.sales.ops.db.entity.NonseedTask;
import com.sales.ops.db.entity.NonseedTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NonseedTaskMapper {
    long countByExample(NonseedTaskExample example);

    int deleteByExample(NonseedTaskExample example);

    int insert(NonseedTask record);

    int insertSelective(NonseedTask record);

    List<NonseedTask> selectByExample(NonseedTaskExample example);

    int updateByExampleSelective(@Param("record") NonseedTask record, @Param("example") NonseedTaskExample example);

    int updateByExample(@Param("record") NonseedTask record, @Param("example") NonseedTaskExample example);
}