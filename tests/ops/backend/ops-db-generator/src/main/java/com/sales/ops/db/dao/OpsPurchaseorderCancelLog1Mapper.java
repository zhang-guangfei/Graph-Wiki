package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPurchaseorderCancelLog1;
import com.sales.ops.db.entity.OpsPurchaseorderCancelLog1Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPurchaseorderCancelLog1Mapper {
    long countByExample(OpsPurchaseorderCancelLog1Example example);

    int deleteByExample(OpsPurchaseorderCancelLog1Example example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPurchaseorderCancelLog1 record);

    int insertSelective(OpsPurchaseorderCancelLog1 record);

    List<OpsPurchaseorderCancelLog1> selectByExample(OpsPurchaseorderCancelLog1Example example);

    OpsPurchaseorderCancelLog1 selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPurchaseorderCancelLog1 record, @Param("example") OpsPurchaseorderCancelLog1Example example);

    int updateByExample(@Param("record") OpsPurchaseorderCancelLog1 record, @Param("example") OpsPurchaseorderCancelLog1Example example);

    int updateByPrimaryKeySelective(OpsPurchaseorderCancelLog1 record);

    int updateByPrimaryKey(OpsPurchaseorderCancelLog1 record);
}