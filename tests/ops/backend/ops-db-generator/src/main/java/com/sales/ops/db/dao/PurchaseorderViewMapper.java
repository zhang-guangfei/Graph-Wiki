package com.sales.ops.db.dao;

import com.sales.ops.db.entity.PurchaseorderView;
import com.sales.ops.db.entity.PurchaseorderViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchaseorderViewMapper {
    long countByExample(PurchaseorderViewExample example);

    int deleteByExample(PurchaseorderViewExample example);

    int insert(PurchaseorderView record);

    int insertSelective(PurchaseorderView record);

    List<PurchaseorderView> selectByExample(PurchaseorderViewExample example);

    int updateByExampleSelective(@Param("record") PurchaseorderView record, @Param("example") PurchaseorderViewExample example);

    int updateByExample(@Param("record") PurchaseorderView record, @Param("example") PurchaseorderViewExample example);
}