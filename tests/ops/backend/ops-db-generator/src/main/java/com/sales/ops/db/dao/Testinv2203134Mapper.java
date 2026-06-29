package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv2203134;
import com.sales.ops.db.entity.Testinv2203134Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv2203134Mapper {
    long countByExample(Testinv2203134Example example);

    int deleteByExample(Testinv2203134Example example);

    int insert(Testinv2203134 record);

    int insertSelective(Testinv2203134 record);

    List<Testinv2203134> selectByExample(Testinv2203134Example example);

    int updateByExampleSelective(@Param("record") Testinv2203134 record, @Param("example") Testinv2203134Example example);

    int updateByExample(@Param("record") Testinv2203134 record, @Param("example") Testinv2203134Example example);
}