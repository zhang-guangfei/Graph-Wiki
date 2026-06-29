package com.sales.ops.db.dao;

import com.sales.ops.db.entity.BranchInventoryTransaction;
import com.sales.ops.db.entity.BranchInventoryTransactionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BranchInventoryTransactionMapper {
    long countByExample(BranchInventoryTransactionExample example);

    int deleteByExample(BranchInventoryTransactionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BranchInventoryTransaction record);

    int insertSelective(BranchInventoryTransaction record);

    List<BranchInventoryTransaction> selectByExample(BranchInventoryTransactionExample example);

    BranchInventoryTransaction selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BranchInventoryTransaction record, @Param("example") BranchInventoryTransactionExample example);

    int updateByExample(@Param("record") BranchInventoryTransaction record, @Param("example") BranchInventoryTransactionExample example);

    int updateByPrimaryKeySelective(BranchInventoryTransaction record);

    int updateByPrimaryKey(BranchInventoryTransaction record);
}