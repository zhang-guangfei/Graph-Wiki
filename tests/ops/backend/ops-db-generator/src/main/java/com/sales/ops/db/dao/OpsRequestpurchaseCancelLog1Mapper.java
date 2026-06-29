package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRequestpurchaseCancelLog1;
import com.sales.ops.db.entity.OpsRequestpurchaseCancelLog1Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRequestpurchaseCancelLog1Mapper {
    long countByExample(OpsRequestpurchaseCancelLog1Example example);

    int deleteByExample(OpsRequestpurchaseCancelLog1Example example);

    int insert(OpsRequestpurchaseCancelLog1 record);

    int insertSelective(OpsRequestpurchaseCancelLog1 record);

    List<OpsRequestpurchaseCancelLog1> selectByExample(OpsRequestpurchaseCancelLog1Example example);

    int updateByExampleSelective(@Param("record") OpsRequestpurchaseCancelLog1 record, @Param("example") OpsRequestpurchaseCancelLog1Example example);

    int updateByExample(@Param("record") OpsRequestpurchaseCancelLog1 record, @Param("example") OpsRequestpurchaseCancelLog1Example example);
}