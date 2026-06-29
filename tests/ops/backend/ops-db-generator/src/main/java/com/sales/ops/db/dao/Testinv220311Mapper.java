package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv220311;
import com.sales.ops.db.entity.Testinv220311Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv220311Mapper {
    long countByExample(Testinv220311Example example);

    int deleteByExample(Testinv220311Example example);

    int insert(Testinv220311 record);

    int insertSelective(Testinv220311 record);

    List<Testinv220311> selectByExample(Testinv220311Example example);

    int updateByExampleSelective(@Param("record") Testinv220311 record, @Param("example") Testinv220311Example example);

    int updateByExample(@Param("record") Testinv220311 record, @Param("example") Testinv220311Example example);
}