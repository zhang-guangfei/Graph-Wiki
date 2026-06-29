package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderModidata;
import com.sales.ops.db.entity.OrderModidataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderModidataMapper {
    long countByExample(OrderModidataExample example);

    int deleteByExample(OrderModidataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderModidata record);

    int insertSelective(OrderModidata record);

    List<OrderModidata> selectByExample(OrderModidataExample example);

    OrderModidata selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderModidata record, @Param("example") OrderModidataExample example);

    int updateByExample(@Param("record") OrderModidata record, @Param("example") OrderModidataExample example);

    int updateByPrimaryKeySelective(OrderModidata record);

    int updateByPrimaryKey(OrderModidata record);
}