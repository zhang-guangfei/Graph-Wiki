package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.OpsRequestpurchaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRequestpurchaseMapper {
    long countByExample(OpsRequestpurchaseExample example);

    int deleteByExample(OpsRequestpurchaseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsRequestpurchase record);

    int insertSelective(OpsRequestpurchase record);

    List<OpsRequestpurchase> selectByExample(OpsRequestpurchaseExample example);

    OpsRequestpurchase selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsRequestpurchase record, @Param("example") OpsRequestpurchaseExample example);

    int updateByExample(@Param("record") OpsRequestpurchase record, @Param("example") OpsRequestpurchaseExample example);

    int updateByPrimaryKeySelective(OpsRequestpurchase record);

    int updateByPrimaryKey(OpsRequestpurchase record);
}