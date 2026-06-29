package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Salesinfo;
import com.sales.ops.db.entity.SalesinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SalesinfoMapper {
    long countByExample(SalesinfoExample example);

    int deleteByExample(SalesinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Salesinfo record);

    int insertSelective(Salesinfo record);

    List<Salesinfo> selectByExample(SalesinfoExample example);

    Salesinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Salesinfo record, @Param("example") SalesinfoExample example);

    int updateByExample(@Param("record") Salesinfo record, @Param("example") SalesinfoExample example);

    int updateByPrimaryKeySelective(Salesinfo record);

    int updateByPrimaryKey(Salesinfo record);
}