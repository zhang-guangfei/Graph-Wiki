package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRequestpurchaseCancelLog;
import com.sales.ops.db.entity.OpsRequestpurchaseCancelLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRequestpurchaseCancelLogMapper {
    long countByExample(OpsRequestpurchaseCancelLogExample example);

    int deleteByExample(OpsRequestpurchaseCancelLogExample example);

    int insert(OpsRequestpurchaseCancelLog record);

    int insertSelective(OpsRequestpurchaseCancelLog record);

    List<OpsRequestpurchaseCancelLog> selectByExample(OpsRequestpurchaseCancelLogExample example);

    int updateByExampleSelective(@Param("record") OpsRequestpurchaseCancelLog record, @Param("example") OpsRequestpurchaseCancelLogExample example);

    int updateByExample(@Param("record") OpsRequestpurchaseCancelLog record, @Param("example") OpsRequestpurchaseCancelLogExample example);
}