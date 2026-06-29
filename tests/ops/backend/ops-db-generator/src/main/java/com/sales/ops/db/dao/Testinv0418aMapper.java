package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0418a;
import com.sales.ops.db.entity.Testinv0418aExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0418aMapper {
    long countByExample(Testinv0418aExample example);

    int deleteByExample(Testinv0418aExample example);

    int insert(Testinv0418a record);

    int insertSelective(Testinv0418a record);

    List<Testinv0418a> selectByExample(Testinv0418aExample example);

    int updateByExampleSelective(@Param("record") Testinv0418a record, @Param("example") Testinv0418aExample example);

    int updateByExample(@Param("record") Testinv0418a record, @Param("example") Testinv0418aExample example);
}