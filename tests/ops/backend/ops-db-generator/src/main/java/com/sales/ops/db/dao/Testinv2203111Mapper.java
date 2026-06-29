package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv2203111;
import com.sales.ops.db.entity.Testinv2203111Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv2203111Mapper {
    long countByExample(Testinv2203111Example example);

    int deleteByExample(Testinv2203111Example example);

    int insert(Testinv2203111 record);

    int insertSelective(Testinv2203111 record);

    List<Testinv2203111> selectByExample(Testinv2203111Example example);

    int updateByExampleSelective(@Param("record") Testinv2203111 record, @Param("example") Testinv2203111Example example);

    int updateByExample(@Param("record") Testinv2203111 record, @Param("example") Testinv2203111Example example);
}