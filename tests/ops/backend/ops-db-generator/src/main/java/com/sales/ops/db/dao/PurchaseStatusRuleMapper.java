package com.sales.ops.db.dao;

import com.sales.ops.db.entity.PurchaseStatusRule;
import com.sales.ops.db.entity.PurchaseStatusRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchaseStatusRuleMapper {
    long countByExample(PurchaseStatusRuleExample example);

    int deleteByExample(PurchaseStatusRuleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PurchaseStatusRule record);

    int insertSelective(PurchaseStatusRule record);

    List<PurchaseStatusRule> selectByExample(PurchaseStatusRuleExample example);

    PurchaseStatusRule selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PurchaseStatusRule record, @Param("example") PurchaseStatusRuleExample example);

    int updateByExample(@Param("record") PurchaseStatusRule record, @Param("example") PurchaseStatusRuleExample example);

    int updateByPrimaryKeySelective(PurchaseStatusRule record);

    int updateByPrimaryKey(PurchaseStatusRule record);
}