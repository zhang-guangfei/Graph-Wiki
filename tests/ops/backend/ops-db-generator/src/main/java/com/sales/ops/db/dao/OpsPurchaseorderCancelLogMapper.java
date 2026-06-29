package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPurchaseorderCancelLog;
import com.sales.ops.db.entity.OpsPurchaseorderCancelLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPurchaseorderCancelLogMapper {
    long countByExample(OpsPurchaseorderCancelLogExample example);

    int deleteByExample(OpsPurchaseorderCancelLogExample example);

    int insert(OpsPurchaseorderCancelLog record);

    int insertSelective(OpsPurchaseorderCancelLog record);

    List<OpsPurchaseorderCancelLog> selectByExample(OpsPurchaseorderCancelLogExample example);

    int updateByExampleSelective(@Param("record") OpsPurchaseorderCancelLog record, @Param("example") OpsPurchaseorderCancelLogExample example);

    int updateByExample(@Param("record") OpsPurchaseorderCancelLog record, @Param("example") OpsPurchaseorderCancelLogExample example);
}