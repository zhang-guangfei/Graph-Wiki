package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv2203133;
import com.sales.ops.db.entity.Testinv2203133Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv2203133Mapper {
    long countByExample(Testinv2203133Example example);

    int deleteByExample(Testinv2203133Example example);

    int insert(Testinv2203133 record);

    int insertSelective(Testinv2203133 record);

    List<Testinv2203133> selectByExample(Testinv2203133Example example);

    int updateByExampleSelective(@Param("record") Testinv2203133 record, @Param("example") Testinv2203133Example example);

    int updateByExample(@Param("record") Testinv2203133 record, @Param("example") Testinv2203133Example example);
}