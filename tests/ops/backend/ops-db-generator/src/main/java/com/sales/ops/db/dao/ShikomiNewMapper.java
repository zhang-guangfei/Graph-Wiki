package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ShikomiNew;
import com.sales.ops.db.entity.ShikomiNewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShikomiNewMapper {
    long countByExample(ShikomiNewExample example);

    int deleteByExample(ShikomiNewExample example);

    int deleteByPrimaryKey(String answerno);

    int insert(ShikomiNew record);

    int insertSelective(ShikomiNew record);

    List<ShikomiNew> selectByExample(ShikomiNewExample example);

    ShikomiNew selectByPrimaryKey(String answerno);

    int updateByExampleSelective(@Param("record") ShikomiNew record, @Param("example") ShikomiNewExample example);

    int updateByExample(@Param("record") ShikomiNew record, @Param("example") ShikomiNewExample example);

    int updateByPrimaryKeySelective(ShikomiNew record);

    int updateByPrimaryKey(ShikomiNew record);
}