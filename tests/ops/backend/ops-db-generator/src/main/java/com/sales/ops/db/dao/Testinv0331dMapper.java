package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0331d;
import com.sales.ops.db.entity.Testinv0331dExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0331dMapper {
    long countByExample(Testinv0331dExample example);

    int deleteByExample(Testinv0331dExample example);

    int insert(Testinv0331d record);

    int insertSelective(Testinv0331d record);

    List<Testinv0331d> selectByExample(Testinv0331dExample example);

    int updateByExampleSelective(@Param("record") Testinv0331d record, @Param("example") Testinv0331dExample example);

    int updateByExample(@Param("record") Testinv0331d record, @Param("example") Testinv0331dExample example);
}