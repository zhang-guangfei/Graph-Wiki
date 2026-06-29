package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0330b;
import com.sales.ops.db.entity.Testinv0330bExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0330bMapper {
    long countByExample(Testinv0330bExample example);

    int deleteByExample(Testinv0330bExample example);

    int insert(Testinv0330b record);

    int insertSelective(Testinv0330b record);

    List<Testinv0330b> selectByExample(Testinv0330bExample example);

    int updateByExampleSelective(@Param("record") Testinv0330b record, @Param("example") Testinv0330bExample example);

    int updateByExample(@Param("record") Testinv0330b record, @Param("example") Testinv0330bExample example);
}