package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv20220311;
import com.sales.ops.db.entity.Testinv20220311Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv20220311Mapper {
    long countByExample(Testinv20220311Example example);

    int deleteByExample(Testinv20220311Example example);

    int insert(Testinv20220311 record);

    int insertSelective(Testinv20220311 record);

    List<Testinv20220311> selectByExample(Testinv20220311Example example);

    int updateByExampleSelective(@Param("record") Testinv20220311 record, @Param("example") Testinv20220311Example example);

    int updateByExample(@Param("record") Testinv20220311 record, @Param("example") Testinv20220311Example example);
}