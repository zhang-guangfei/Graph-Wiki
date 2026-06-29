package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0329b;
import com.sales.ops.db.entity.Testinv0329bExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0329bMapper {
    long countByExample(Testinv0329bExample example);

    int deleteByExample(Testinv0329bExample example);

    int insert(Testinv0329b record);

    int insertSelective(Testinv0329b record);

    List<Testinv0329b> selectByExample(Testinv0329bExample example);

    int updateByExampleSelective(@Param("record") Testinv0329b record, @Param("example") Testinv0329bExample example);

    int updateByExample(@Param("record") Testinv0329b record, @Param("example") Testinv0329bExample example);
}