package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Bindata;
import com.sales.ops.db.entity.BindataExample;
import com.sales.ops.db.entity.BindataKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BindataMapper {
    long countByExample(BindataExample example);

    int deleteByExample(BindataExample example);

    int deleteByPrimaryKey(BindataKey key);

    int insert(Bindata record);

    int insertSelective(Bindata record);

    List<Bindata> selectByExample(BindataExample example);

    Bindata selectByPrimaryKey(BindataKey key);

    int updateByExampleSelective(@Param("record") Bindata record, @Param("example") BindataExample example);

    int updateByExample(@Param("record") Bindata record, @Param("example") BindataExample example);

    int updateByPrimaryKeySelective(Bindata record);

    int updateByPrimaryKey(Bindata record);
}