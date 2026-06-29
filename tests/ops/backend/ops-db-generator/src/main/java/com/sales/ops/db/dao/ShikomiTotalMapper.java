package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ShikomiTotal;
import com.sales.ops.db.entity.ShikomiTotalExample;
import com.sales.ops.db.entity.ShikomiTotalKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShikomiTotalMapper {
    long countByExample(ShikomiTotalExample example);

    int deleteByExample(ShikomiTotalExample example);

    int deleteByPrimaryKey(ShikomiTotalKey key);

    int insert(ShikomiTotal record);

    int insertSelective(ShikomiTotal record);

    List<ShikomiTotal> selectByExampleWithBLOBs(ShikomiTotalExample example);

    List<ShikomiTotal> selectByExample(ShikomiTotalExample example);

    ShikomiTotal selectByPrimaryKey(ShikomiTotalKey key);

    int updateByExampleSelective(@Param("record") ShikomiTotal record, @Param("example") ShikomiTotalExample example);

    int updateByExampleWithBLOBs(@Param("record") ShikomiTotal record, @Param("example") ShikomiTotalExample example);

    int updateByExample(@Param("record") ShikomiTotal record, @Param("example") ShikomiTotalExample example);

    int updateByPrimaryKeySelective(ShikomiTotal record);

    int updateByPrimaryKeyWithBLOBs(ShikomiTotal record);

    int updateByPrimaryKey(ShikomiTotal record);
}