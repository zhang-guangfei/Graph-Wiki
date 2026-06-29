package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPrepareOrderReserveInfo;
import com.sales.ops.db.entity.OpsPrepareOrderReserveInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsPrepareOrderReserveInfoMapper {
    long countByExample(OpsPrepareOrderReserveInfoExample example);

    int deleteByExample(OpsPrepareOrderReserveInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPrepareOrderReserveInfo record);

    int insertSelective(OpsPrepareOrderReserveInfo record);

    List<OpsPrepareOrderReserveInfo> selectByExample(OpsPrepareOrderReserveInfoExample example);

    OpsPrepareOrderReserveInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPrepareOrderReserveInfo record, @Param("example") OpsPrepareOrderReserveInfoExample example);

    int updateByExample(@Param("record") OpsPrepareOrderReserveInfo record, @Param("example") OpsPrepareOrderReserveInfoExample example);

    int updateByPrimaryKeySelective(OpsPrepareOrderReserveInfo record);

    int updateByPrimaryKey(OpsPrepareOrderReserveInfo record);
}