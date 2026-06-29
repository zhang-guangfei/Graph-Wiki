package com.sales.ops.db.dao;

import com.sales.ops.db.entity.BjShikomi;
import com.sales.ops.db.entity.BjShikomiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BjShikomiMapper {
    long countByExample(BjShikomiExample example);

    int deleteByExample(BjShikomiExample example);

    int insert(BjShikomi record);

    int insertSelective(BjShikomi record);

    List<BjShikomi> selectByExample(BjShikomiExample example);

    int updateByExampleSelective(@Param("record") BjShikomi record, @Param("example") BjShikomiExample example);

    int updateByExample(@Param("record") BjShikomi record, @Param("example") BjShikomiExample example);
}