package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0422a;
import com.sales.ops.db.entity.Testinv0422aExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0422aMapper {
    long countByExample(Testinv0422aExample example);

    int deleteByExample(Testinv0422aExample example);

    int insert(Testinv0422a record);

    int insertSelective(Testinv0422a record);

    List<Testinv0422a> selectByExample(Testinv0422aExample example);

    int updateByExampleSelective(@Param("record") Testinv0422a record, @Param("example") Testinv0422aExample example);

    int updateByExample(@Param("record") Testinv0422a record, @Param("example") Testinv0422aExample example);
}