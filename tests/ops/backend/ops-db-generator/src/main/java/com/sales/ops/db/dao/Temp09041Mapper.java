package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Temp09041;
import com.sales.ops.db.entity.Temp09041Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Temp09041Mapper {
    long countByExample(Temp09041Example example);

    int deleteByExample(Temp09041Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Temp09041 record);

    int insertSelective(Temp09041 record);

    List<Temp09041> selectByExample(Temp09041Example example);

    Temp09041 selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Temp09041 record, @Param("example") Temp09041Example example);

    int updateByExample(@Param("record") Temp09041 record, @Param("example") Temp09041Example example);

    int updateByPrimaryKeySelective(Temp09041 record);

    int updateByPrimaryKey(Temp09041 record);
}