package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Orderdata;
import com.sales.ops.db.entity.OrderdataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderdataMapper {
    long countByExample(OrderdataExample example);

    int deleteByExample(OrderdataExample example);

    int insert(Orderdata record);

    int insertSelective(Orderdata record);

    List<Orderdata> selectByExample(OrderdataExample example);

    int updateByExampleSelective(@Param("record") Orderdata record, @Param("example") OrderdataExample example);

    int updateByExample(@Param("record") Orderdata record, @Param("example") OrderdataExample example);
}