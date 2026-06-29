package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPrepareOrder;
import com.sales.ops.db.entity.OpsPrepareOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsPrepareOrderMapper {
    long countByExample(OpsPrepareOrderExample example);

    int deleteByExample(OpsPrepareOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPrepareOrder record);

    int insertSelective(OpsPrepareOrder record);

    List<OpsPrepareOrder> selectByExample(OpsPrepareOrderExample example);

    OpsPrepareOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPrepareOrder record, @Param("example") OpsPrepareOrderExample example);

    int updateByExample(@Param("record") OpsPrepareOrder record, @Param("example") OpsPrepareOrderExample example);

    int updateByPrimaryKeySelective(OpsPrepareOrder record);

    int updateByPrimaryKey(OpsPrepareOrder record);
}