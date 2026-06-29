package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0401a;
import com.sales.ops.db.entity.Testinv0401aExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0401aMapper {
    long countByExample(Testinv0401aExample example);

    int deleteByExample(Testinv0401aExample example);

    int insert(Testinv0401a record);

    int insertSelective(Testinv0401a record);

    List<Testinv0401a> selectByExample(Testinv0401aExample example);

    int updateByExampleSelective(@Param("record") Testinv0401a record, @Param("example") Testinv0401aExample example);

    int updateByExample(@Param("record") Testinv0401a record, @Param("example") Testinv0401aExample example);
}