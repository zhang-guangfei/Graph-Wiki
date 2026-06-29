package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPrepareOrderReconciliation;
import com.sales.ops.db.entity.OpsPrepareOrderReconciliationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsPrepareOrderReconciliationMapper {
    long countByExample(OpsPrepareOrderReconciliationExample example);

    int deleteByExample(OpsPrepareOrderReconciliationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPrepareOrderReconciliation record);

    int insertSelective(OpsPrepareOrderReconciliation record);

    List<OpsPrepareOrderReconciliation> selectByExample(OpsPrepareOrderReconciliationExample example);

    OpsPrepareOrderReconciliation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPrepareOrderReconciliation record, @Param("example") OpsPrepareOrderReconciliationExample example);

    int updateByExample(@Param("record") OpsPrepareOrderReconciliation record, @Param("example") OpsPrepareOrderReconciliationExample example);

    int updateByPrimaryKeySelective(OpsPrepareOrderReconciliation record);

    int updateByPrimaryKey(OpsPrepareOrderReconciliation record);
}