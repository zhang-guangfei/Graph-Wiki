package com.sales.ops.db.dao;

import com.sales.ops.db.entity.VShikomiTotal;
import com.sales.ops.db.entity.VShikomiTotalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VShikomiTotalMapper {
    long countByExample(VShikomiTotalExample example);

    int deleteByExample(VShikomiTotalExample example);

    int insert(VShikomiTotal record);

    int insertSelective(VShikomiTotal record);

    List<VShikomiTotal> selectByExampleWithBLOBs(VShikomiTotalExample example);

    List<VShikomiTotal> selectByExample(VShikomiTotalExample example);

    int updateByExampleSelective(@Param("record") VShikomiTotal record, @Param("example") VShikomiTotalExample example);

    int updateByExampleWithBLOBs(@Param("record") VShikomiTotal record, @Param("example") VShikomiTotalExample example);

    int updateByExample(@Param("record") VShikomiTotal record, @Param("example") VShikomiTotalExample example);
}