package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0415a;
import com.sales.ops.db.entity.Testinv0415aExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0415aMapper {
    long countByExample(Testinv0415aExample example);

    int deleteByExample(Testinv0415aExample example);

    int insert(Testinv0415a record);

    int insertSelective(Testinv0415a record);

    List<Testinv0415a> selectByExample(Testinv0415aExample example);

    int updateByExampleSelective(@Param("record") Testinv0415a record, @Param("example") Testinv0415aExample example);

    int updateByExample(@Param("record") Testinv0415a record, @Param("example") Testinv0415aExample example);
}