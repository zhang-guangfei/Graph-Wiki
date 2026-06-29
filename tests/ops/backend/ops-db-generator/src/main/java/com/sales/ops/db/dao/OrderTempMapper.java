package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderTemp;
import com.sales.ops.db.entity.OrderTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderTempMapper {
    long countByExample(OrderTempExample example);

    int deleteByExample(OrderTempExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderTemp record);

    int insertSelective(OrderTemp record);

    List<OrderTemp> selectByExample(OrderTempExample example);

    OrderTemp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderTemp record, @Param("example") OrderTempExample example);

    int updateByExample(@Param("record") OrderTemp record, @Param("example") OrderTempExample example);

    int updateByPrimaryKeySelective(OrderTemp record);

    int updateByPrimaryKey(OrderTemp record);
}