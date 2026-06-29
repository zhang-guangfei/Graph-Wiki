package com.sales.ops.db.dao;

import com.sales.ops.db.entity.BjShikomiModellist;
import com.sales.ops.db.entity.BjShikomiModellistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BjShikomiModellistMapper {
    long countByExample(BjShikomiModellistExample example);

    int deleteByExample(BjShikomiModellistExample example);

    int insert(BjShikomiModellist record);

    int insertSelective(BjShikomiModellist record);

    List<BjShikomiModellist> selectByExample(BjShikomiModellistExample example);

    int updateByExampleSelective(@Param("record") BjShikomiModellist record, @Param("example") BjShikomiModellistExample example);

    int updateByExample(@Param("record") BjShikomiModellist record, @Param("example") BjShikomiModellistExample example);
}