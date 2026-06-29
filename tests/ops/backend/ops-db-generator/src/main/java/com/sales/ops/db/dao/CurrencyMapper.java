package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Currency;
import com.sales.ops.db.entity.CurrencyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CurrencyMapper {
    long countByExample(CurrencyExample example);

    int deleteByExample(CurrencyExample example);

    int deleteByPrimaryKey(String id);

    int insert(Currency record);

    int insertSelective(Currency record);

    List<Currency> selectByExample(CurrencyExample example);

    Currency selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Currency record, @Param("example") CurrencyExample example);

    int updateByExample(@Param("record") Currency record, @Param("example") CurrencyExample example);

    int updateByPrimaryKeySelective(Currency record);

    int updateByPrimaryKey(Currency record);
}