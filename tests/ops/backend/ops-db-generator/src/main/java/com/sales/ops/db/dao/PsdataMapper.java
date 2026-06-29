package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Psdata;
import com.sales.ops.db.entity.PsdataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PsdataMapper {
    long countByExample(PsdataExample example);

    int deleteByExample(PsdataExample example);

    int insert(Psdata record);

    int insertSelective(Psdata record);

    List<Psdata> selectByExample(PsdataExample example);

    int updateByExampleSelective(@Param("record") Psdata record, @Param("example") PsdataExample example);

    int updateByExample(@Param("record") Psdata record, @Param("example") PsdataExample example);
}