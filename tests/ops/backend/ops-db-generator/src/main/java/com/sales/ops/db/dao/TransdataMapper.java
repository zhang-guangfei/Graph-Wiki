package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Transdata;
import com.sales.ops.db.entity.TransdataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransdataMapper {
    long countByExample(TransdataExample example);

    int deleteByExample(TransdataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Transdata record);

    int insertSelective(Transdata record);

    List<Transdata> selectByExample(TransdataExample example);

    Transdata selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Transdata record, @Param("example") TransdataExample example);

    int updateByExample(@Param("record") Transdata record, @Param("example") TransdataExample example);

    int updateByPrimaryKeySelective(Transdata record);

    int updateByPrimaryKey(Transdata record);
}