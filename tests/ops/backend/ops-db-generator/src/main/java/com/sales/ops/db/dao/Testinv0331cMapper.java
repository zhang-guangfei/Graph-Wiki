package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv0331c;
import com.sales.ops.db.entity.Testinv0331cExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv0331cMapper {
    long countByExample(Testinv0331cExample example);

    int deleteByExample(Testinv0331cExample example);

    int insert(Testinv0331c record);

    int insertSelective(Testinv0331c record);

    List<Testinv0331c> selectByExample(Testinv0331cExample example);

    int updateByExampleSelective(@Param("record") Testinv0331c record, @Param("example") Testinv0331cExample example);

    int updateByExample(@Param("record") Testinv0331c record, @Param("example") Testinv0331cExample example);
}