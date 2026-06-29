package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderdlvdataBj;
import com.sales.ops.db.entity.OrderdlvdataBjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderdlvdataBjMapper {
    long countByExample(OrderdlvdataBjExample example);

    int deleteByExample(OrderdlvdataBjExample example);

    int insert(OrderdlvdataBj record);

    int insertSelective(OrderdlvdataBj record);

    List<OrderdlvdataBj> selectByExample(OrderdlvdataBjExample example);

    int updateByExampleSelective(@Param("record") OrderdlvdataBj record, @Param("example") OrderdlvdataBjExample example);

    int updateByExample(@Param("record") OrderdlvdataBj record, @Param("example") OrderdlvdataBjExample example);
}