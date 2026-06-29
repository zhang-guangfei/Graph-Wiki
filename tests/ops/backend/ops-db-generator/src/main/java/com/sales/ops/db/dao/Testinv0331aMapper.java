package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0331a;
import com.sales.ops.db.entity.Testinv0331aExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0331aMapper {
    long countByExample(Testinv0331aExample example);

    int deleteByExample(Testinv0331aExample example);

    int insert(Testinv0331a record);

    int insertSelective(Testinv0331a record);

    List<Testinv0331a> selectByExample(Testinv0331aExample example);

    int updateByExampleSelective(@Param("record") Testinv0331a record, @Param("example") Testinv0331aExample example);

    int updateByExample(@Param("record") Testinv0331a record, @Param("example") Testinv0331aExample example);
}