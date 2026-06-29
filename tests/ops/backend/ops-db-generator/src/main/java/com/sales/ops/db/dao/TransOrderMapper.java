package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TransOrder;
import com.sales.ops.db.entity.TransOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransOrderMapper {
    long countByExample(TransOrderExample example);

    int deleteByExample(TransOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TransOrder record);

    int insertSelective(TransOrder record);

    List<TransOrder> selectByExample(TransOrderExample example);

    TransOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TransOrder record, @Param("example") TransOrderExample example);

    int updateByExample(@Param("record") TransOrder record, @Param("example") TransOrderExample example);

    int updateByPrimaryKeySelective(TransOrder record);

    int updateByPrimaryKey(TransOrder record);
}