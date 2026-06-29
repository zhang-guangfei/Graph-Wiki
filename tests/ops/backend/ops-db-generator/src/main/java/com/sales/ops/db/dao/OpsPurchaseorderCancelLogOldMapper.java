package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPurchaseorderCancelLogOld;
import com.sales.ops.db.entity.OpsPurchaseorderCancelLogOldExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPurchaseorderCancelLogOldMapper {
    long countByExample(OpsPurchaseorderCancelLogOldExample example);

    int deleteByExample(OpsPurchaseorderCancelLogOldExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPurchaseorderCancelLogOld record);

    int insertSelective(OpsPurchaseorderCancelLogOld record);

    List<OpsPurchaseorderCancelLogOld> selectByExample(OpsPurchaseorderCancelLogOldExample example);

    OpsPurchaseorderCancelLogOld selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPurchaseorderCancelLogOld record, @Param("example") OpsPurchaseorderCancelLogOldExample example);

    int updateByExample(@Param("record") OpsPurchaseorderCancelLogOld record, @Param("example") OpsPurchaseorderCancelLogOldExample example);

    int updateByPrimaryKeySelective(OpsPurchaseorderCancelLogOld record);

    int updateByPrimaryKey(OpsPurchaseorderCancelLogOld record);
}