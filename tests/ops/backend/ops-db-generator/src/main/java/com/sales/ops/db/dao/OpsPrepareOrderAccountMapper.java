package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPrepareOrderAccount;
import com.sales.ops.db.entity.OpsPrepareOrderAccountExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsPrepareOrderAccountMapper {
    long countByExample(OpsPrepareOrderAccountExample example);

    int deleteByExample(OpsPrepareOrderAccountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPrepareOrderAccount record);

    int insertSelective(OpsPrepareOrderAccount record);

    List<OpsPrepareOrderAccount> selectByExample(OpsPrepareOrderAccountExample example);

    OpsPrepareOrderAccount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPrepareOrderAccount record, @Param("example") OpsPrepareOrderAccountExample example);

    int updateByExample(@Param("record") OpsPrepareOrderAccount record, @Param("example") OpsPrepareOrderAccountExample example);

    int updateByPrimaryKeySelective(OpsPrepareOrderAccount record);

    int updateByPrimaryKey(OpsPrepareOrderAccount record);
}