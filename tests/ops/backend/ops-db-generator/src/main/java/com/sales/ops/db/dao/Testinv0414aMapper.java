package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0414a;
import com.sales.ops.db.entity.Testinv0414aExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0414aMapper {
    long countByExample(Testinv0414aExample example);

    int deleteByExample(Testinv0414aExample example);

    int insert(Testinv0414a record);

    int insertSelective(Testinv0414a record);

    List<Testinv0414a> selectByExample(Testinv0414aExample example);

    int updateByExampleSelective(@Param("record") Testinv0414a record, @Param("example") Testinv0414aExample example);

    int updateByExample(@Param("record") Testinv0414a record, @Param("example") Testinv0414aExample example);
}