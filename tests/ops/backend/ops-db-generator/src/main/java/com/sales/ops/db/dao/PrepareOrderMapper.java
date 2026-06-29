package com.sales.ops.db.dao;

import com.sales.ops.db.entity.PrepareOrder;
import com.sales.ops.db.entity.PrepareOrderExample;
import com.sales.ops.db.entity.PrepareOrderKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PrepareOrderMapper {
    long countByExample(PrepareOrderExample example);

    int deleteByExample(PrepareOrderExample example);

    int deleteByPrimaryKey(PrepareOrderKey key);

    int insert(PrepareOrder record);

    int insertSelective(PrepareOrder record);

    List<PrepareOrder> selectByExample(PrepareOrderExample example);

    PrepareOrder selectByPrimaryKey(PrepareOrderKey key);

    int updateByExampleSelective(@Param("record") PrepareOrder record, @Param("example") PrepareOrderExample example);

    int updateByExample(@Param("record") PrepareOrder record, @Param("example") PrepareOrderExample example);

    int updateByPrimaryKeySelective(PrepareOrder record);

    int updateByPrimaryKey(PrepareOrder record);
}