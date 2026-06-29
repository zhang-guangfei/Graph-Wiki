package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Country;
import com.sales.ops.db.entity.CountryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountryMapper {
    long countByExample(CountryExample example);

    int deleteByExample(CountryExample example);

    int deleteByPrimaryKey(String id);

    int insert(Country record);

    int insertSelective(Country record);

    List<Country> selectByExample(CountryExample example);

    Country selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Country record, @Param("example") CountryExample example);

    int updateByExample(@Param("record") Country record, @Param("example") CountryExample example);

    int updateByPrimaryKeySelective(Country record);

    int updateByPrimaryKey(Country record);
}