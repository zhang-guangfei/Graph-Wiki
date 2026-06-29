package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Customer2del;
import com.sales.ops.db.entity.Customer2delExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Customer2delMapper {
    long countByExample(Customer2delExample example);

    int deleteByExample(Customer2delExample example);

    int deleteByPrimaryKey(String customerno);

    int insert(Customer2del record);

    int insertSelective(Customer2del record);

    List<Customer2del> selectByExample(Customer2delExample example);

    Customer2del selectByPrimaryKey(String customerno);

    int updateByExampleSelective(@Param("record") Customer2del record, @Param("example") Customer2delExample example);

    int updateByExample(@Param("record") Customer2del record, @Param("example") Customer2delExample example);

    int updateByPrimaryKeySelective(Customer2del record);

    int updateByPrimaryKey(Customer2del record);
}