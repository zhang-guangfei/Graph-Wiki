package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Orderdlvdata;
import com.sales.ops.db.entity.OrderdlvdataExample;
import com.sales.ops.db.entity.OrderdlvdataKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderdlvdataMapper {
    long countByExample(OrderdlvdataExample example);

    int deleteByExample(OrderdlvdataExample example);

    int deleteByPrimaryKey(OrderdlvdataKey key);

    int insert(Orderdlvdata record);

    int insertSelective(Orderdlvdata record);

    List<Orderdlvdata> selectByExample(OrderdlvdataExample example);

    Orderdlvdata selectByPrimaryKey(OrderdlvdataKey key);

    int updateByExampleSelective(@Param("record") Orderdlvdata record, @Param("example") OrderdlvdataExample example);

    int updateByExample(@Param("record") Orderdlvdata record, @Param("example") OrderdlvdataExample example);

    int updateByPrimaryKeySelective(Orderdlvdata record);

    int updateByPrimaryKey(Orderdlvdata record);
}