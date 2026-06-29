package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv2203132;
import com.sales.ops.db.entity.Testinv2203132Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv2203132Mapper {
    long countByExample(Testinv2203132Example example);

    int deleteByExample(Testinv2203132Example example);

    int insert(Testinv2203132 record);

    int insertSelective(Testinv2203132 record);

    List<Testinv2203132> selectByExample(Testinv2203132Example example);

    int updateByExampleSelective(@Param("record") Testinv2203132 record, @Param("example") Testinv2203132Example example);

    int updateByExample(@Param("record") Testinv2203132 record, @Param("example") Testinv2203132Example example);
}