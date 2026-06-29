package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0331e;
import com.sales.ops.db.entity.Testinv0331eExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0331eMapper {
    long countByExample(Testinv0331eExample example);

    int deleteByExample(Testinv0331eExample example);

    int insert(Testinv0331e record);

    int insertSelective(Testinv0331e record);

    List<Testinv0331e> selectByExample(Testinv0331eExample example);

    int updateByExampleSelective(@Param("record") Testinv0331e record, @Param("example") Testinv0331eExample example);

    int updateByExample(@Param("record") Testinv0331e record, @Param("example") Testinv0331eExample example);
}