package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderdataGz;
import com.sales.ops.db.entity.OrderdataGzExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderdataGzMapper {
    long countByExample(OrderdataGzExample example);

    int deleteByExample(OrderdataGzExample example);

    int insert(OrderdataGz record);

    int insertSelective(OrderdataGz record);

    List<OrderdataGz> selectByExample(OrderdataGzExample example);

    int updateByExampleSelective(@Param("record") OrderdataGz record, @Param("example") OrderdataGzExample example);

    int updateByExample(@Param("record") OrderdataGz record, @Param("example") OrderdataGzExample example);
}