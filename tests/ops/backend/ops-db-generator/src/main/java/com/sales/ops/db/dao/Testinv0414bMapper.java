package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0414b;
import com.sales.ops.db.entity.Testinv0414bExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0414bMapper {
    long countByExample(Testinv0414bExample example);

    int deleteByExample(Testinv0414bExample example);

    int insert(Testinv0414b record);

    int insertSelective(Testinv0414b record);

    List<Testinv0414b> selectByExample(Testinv0414bExample example);

    int updateByExampleSelective(@Param("record") Testinv0414b record, @Param("example") Testinv0414bExample example);

    int updateByExample(@Param("record") Testinv0414b record, @Param("example") Testinv0414bExample example);
}