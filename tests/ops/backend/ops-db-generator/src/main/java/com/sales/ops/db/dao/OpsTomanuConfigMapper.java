package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsTomanuConfig;
import com.sales.ops.db.entity.OpsTomanuConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsTomanuConfigMapper {
    long countByExample(OpsTomanuConfigExample example);

    int deleteByExample(OpsTomanuConfigExample example);

    int insert(OpsTomanuConfig record);

    int insertSelective(OpsTomanuConfig record);

    List<OpsTomanuConfig> selectByExample(OpsTomanuConfigExample example);

    int updateByExampleSelective(@Param("record") OpsTomanuConfig record, @Param("example") OpsTomanuConfigExample example);

    int updateByExample(@Param("record") OpsTomanuConfig record, @Param("example") OpsTomanuConfigExample example);
}