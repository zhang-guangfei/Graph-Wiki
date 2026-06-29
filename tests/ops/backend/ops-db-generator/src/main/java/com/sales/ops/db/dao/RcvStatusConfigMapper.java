package com.sales.ops.db.dao;

import com.sales.ops.db.entity.RcvStatusConfig;
import com.sales.ops.db.entity.RcvStatusConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RcvStatusConfigMapper {
    long countByExample(RcvStatusConfigExample example);

    int deleteByExample(RcvStatusConfigExample example);

    int insert(RcvStatusConfig record);

    int insertSelective(RcvStatusConfig record);

    List<RcvStatusConfig> selectByExample(RcvStatusConfigExample example);

    int updateByExampleSelective(@Param("record") RcvStatusConfig record, @Param("example") RcvStatusConfigExample example);

    int updateByExample(@Param("record") RcvStatusConfig record, @Param("example") RcvStatusConfigExample example);
}