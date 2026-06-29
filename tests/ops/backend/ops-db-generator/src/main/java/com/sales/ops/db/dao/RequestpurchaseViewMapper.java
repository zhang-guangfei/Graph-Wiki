package com.sales.ops.db.dao;

import com.sales.ops.db.entity.RequestpurchaseView;
import com.sales.ops.db.entity.RequestpurchaseViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RequestpurchaseViewMapper {
    long countByExample(RequestpurchaseViewExample example);

    int deleteByExample(RequestpurchaseViewExample example);

    int insert(RequestpurchaseView record);

    int insertSelective(RequestpurchaseView record);

    List<RequestpurchaseView> selectByExample(RequestpurchaseViewExample example);

    int updateByExampleSelective(@Param("record") RequestpurchaseView record, @Param("example") RequestpurchaseViewExample example);

    int updateByExample(@Param("record") RequestpurchaseView record, @Param("example") RequestpurchaseViewExample example);
}