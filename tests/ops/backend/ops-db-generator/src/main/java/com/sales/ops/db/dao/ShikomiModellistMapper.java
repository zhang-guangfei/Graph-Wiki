package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ShikomiModellist;
import com.sales.ops.db.entity.ShikomiModellistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShikomiModellistMapper {
    long countByExample(ShikomiModellistExample example);

    int deleteByExample(ShikomiModellistExample example);

    int insert(ShikomiModellist record);

    int insertSelective(ShikomiModellist record);

    List<ShikomiModellist> selectByExample(ShikomiModellistExample example);

    int updateByExampleSelective(@Param("record") ShikomiModellist record, @Param("example") ShikomiModellistExample example);

    int updateByExample(@Param("record") ShikomiModellist record, @Param("example") ShikomiModellistExample example);
}