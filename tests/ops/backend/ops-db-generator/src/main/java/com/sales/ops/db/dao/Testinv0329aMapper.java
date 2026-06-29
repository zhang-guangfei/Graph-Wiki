package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0329a;
import com.sales.ops.db.entity.Testinv0329aExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0329aMapper {
    long countByExample(Testinv0329aExample example);

    int deleteByExample(Testinv0329aExample example);

    int insert(Testinv0329a record);

    int insertSelective(Testinv0329a record);

    List<Testinv0329a> selectByExample(Testinv0329aExample example);

    int updateByExampleSelective(@Param("record") Testinv0329a record, @Param("example") Testinv0329aExample example);

    int updateByExample(@Param("record") Testinv0329a record, @Param("example") Testinv0329aExample example);
}