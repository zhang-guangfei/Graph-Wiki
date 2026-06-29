package com.sales.ops.db.dao;

import com.sales.ops.db.entity.PurchaseReplyPushJob;
import com.sales.ops.db.entity.PurchaseReplyPushJobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchaseReplyPushJobMapper {
    long countByExample(PurchaseReplyPushJobExample example);

    int deleteByExample(PurchaseReplyPushJobExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PurchaseReplyPushJob record);

    int insertSelective(PurchaseReplyPushJob record);

    List<PurchaseReplyPushJob> selectByExample(PurchaseReplyPushJobExample example);

    PurchaseReplyPushJob selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PurchaseReplyPushJob record, @Param("example") PurchaseReplyPushJobExample example);

    int updateByExample(@Param("record") PurchaseReplyPushJob record, @Param("example") PurchaseReplyPushJobExample example);

    int updateByPrimaryKeySelective(PurchaseReplyPushJob record);

    int updateByPrimaryKey(PurchaseReplyPushJob record);
}