package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderdataBj;
import com.sales.ops.db.entity.OrderdataBjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderdataBjMapper {
    long countByExample(OrderdataBjExample example);

    int deleteByExample(OrderdataBjExample example);

    int insert(OrderdataBj record);

    int insertSelective(OrderdataBj record);

    List<OrderdataBj> selectByExample(OrderdataBjExample example);

    int updateByExampleSelective(@Param("record") OrderdataBj record, @Param("example") OrderdataBjExample example);

    int updateByExample(@Param("record") OrderdataBj record, @Param("example") OrderdataBjExample example);
}