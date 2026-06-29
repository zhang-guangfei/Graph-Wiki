package com.sales.ops.db.dao;

import com.sales.ops.db.entity.VOrderInventoryResult;
import com.sales.ops.db.entity.VOrderInventoryResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VOrderInventoryResultMapper {
    long countByExample(VOrderInventoryResultExample example);

    int deleteByExample(VOrderInventoryResultExample example);

    int insert(VOrderInventoryResult record);

    int insertSelective(VOrderInventoryResult record);

    List<VOrderInventoryResult> selectByExample(VOrderInventoryResultExample example);

    int updateByExampleSelective(@Param("record") VOrderInventoryResult record, @Param("example") VOrderInventoryResultExample example);

    int updateByExample(@Param("record") VOrderInventoryResult record, @Param("example") VOrderInventoryResultExample example);
}