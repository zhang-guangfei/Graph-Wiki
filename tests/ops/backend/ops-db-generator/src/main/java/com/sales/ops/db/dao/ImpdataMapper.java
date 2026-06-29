package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Impdata;
import com.sales.ops.db.entity.ImpdataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImpdataMapper {
    long countByExample(ImpdataExample example);

    int deleteByExample(ImpdataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Impdata record);

    int insertSelective(Impdata record);

    List<Impdata> selectByExample(ImpdataExample example);

    Impdata selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Impdata record, @Param("example") ImpdataExample example);

    int updateByExample(@Param("record") Impdata record, @Param("example") ImpdataExample example);

    int updateByPrimaryKeySelective(Impdata record);

    int updateByPrimaryKey(Impdata record);
}