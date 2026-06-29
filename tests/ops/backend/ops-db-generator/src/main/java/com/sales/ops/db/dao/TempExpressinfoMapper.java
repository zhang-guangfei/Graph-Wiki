package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TempExpressinfo;
import com.sales.ops.db.entity.TempExpressinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TempExpressinfoMapper {
    long countByExample(TempExpressinfoExample example);

    int deleteByExample(TempExpressinfoExample example);

    int insert(TempExpressinfo record);

    int insertSelective(TempExpressinfo record);

    List<TempExpressinfo> selectByExample(TempExpressinfoExample example);

    int updateByExampleSelective(@Param("record") TempExpressinfo record, @Param("example") TempExpressinfoExample example);

    int updateByExample(@Param("record") TempExpressinfo record, @Param("example") TempExpressinfoExample example);
}