package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0427a;
import com.sales.ops.db.entity.Testinv0427aExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0427aMapper {
    long countByExample(Testinv0427aExample example);

    int deleteByExample(Testinv0427aExample example);

    int insert(Testinv0427a record);

    int insertSelective(Testinv0427a record);

    List<Testinv0427a> selectByExample(Testinv0427aExample example);

    int updateByExampleSelective(@Param("record") Testinv0427a record, @Param("example") Testinv0427aExample example);

    int updateByExample(@Param("record") Testinv0427a record, @Param("example") Testinv0427aExample example);
}