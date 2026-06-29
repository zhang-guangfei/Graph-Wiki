package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Testinv202203113;
import com.sales.ops.db.entity.Testinv202203113Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Testinv202203113Mapper {
    long countByExample(Testinv202203113Example example);

    int deleteByExample(Testinv202203113Example example);

    int insert(Testinv202203113 record);

    int insertSelective(Testinv202203113 record);

    List<Testinv202203113> selectByExample(Testinv202203113Example example);

    int updateByExampleSelective(@Param("record") Testinv202203113 record, @Param("example") Testinv202203113Example example);

    int updateByExample(@Param("record") Testinv202203113 record, @Param("example") Testinv202203113Example example);
}