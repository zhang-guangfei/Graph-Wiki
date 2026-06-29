package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0415b;
import com.sales.ops.db.entity.Testinv0415bExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0415bMapper {
    long countByExample(Testinv0415bExample example);

    int deleteByExample(Testinv0415bExample example);

    int insert(Testinv0415b record);

    int insertSelective(Testinv0415b record);

    List<Testinv0415b> selectByExample(Testinv0415bExample example);

    int updateByExampleSelective(@Param("record") Testinv0415b record, @Param("example") Testinv0415bExample example);

    int updateByExample(@Param("record") Testinv0415b record, @Param("example") Testinv0415bExample example);
}