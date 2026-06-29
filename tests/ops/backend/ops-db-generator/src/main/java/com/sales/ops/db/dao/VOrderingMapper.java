package com.sales.ops.db.dao;

import com.sales.ops.db.entity.VOrdering;
import com.sales.ops.db.entity.VOrderingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VOrderingMapper {
    long countByExample(VOrderingExample example);

    int deleteByExample(VOrderingExample example);

    int insert(VOrdering record);

    int insertSelective(VOrdering record);

    List<VOrdering> selectByExample(VOrderingExample example);

    int updateByExampleSelective(@Param("record") VOrdering record, @Param("example") VOrderingExample example);

    int updateByExample(@Param("record") VOrdering record, @Param("example") VOrderingExample example);
}