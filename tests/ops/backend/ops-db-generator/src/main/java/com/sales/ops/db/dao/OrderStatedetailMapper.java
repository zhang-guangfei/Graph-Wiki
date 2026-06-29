package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OrderStatedetail;
import com.sales.ops.db.entity.OrderStatedetailExample;
import com.sales.ops.db.entity.OrderStatedetailKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderStatedetailMapper {
    long countByExample(OrderStatedetailExample example);

    int deleteByExample(OrderStatedetailExample example);

    int deleteByPrimaryKey(OrderStatedetailKey key);

    int insert(OrderStatedetail record);

    int insertSelective(OrderStatedetail record);

    List<OrderStatedetail> selectByExample(OrderStatedetailExample example);

    OrderStatedetail selectByPrimaryKey(OrderStatedetailKey key);

    int updateByExampleSelective(@Param("record") OrderStatedetail record, @Param("example") OrderStatedetailExample example);

    int updateByExample(@Param("record") OrderStatedetail record, @Param("example") OrderStatedetailExample example);

    int updateByPrimaryKeySelective(OrderStatedetail record);

    int updateByPrimaryKey(OrderStatedetail record);
}