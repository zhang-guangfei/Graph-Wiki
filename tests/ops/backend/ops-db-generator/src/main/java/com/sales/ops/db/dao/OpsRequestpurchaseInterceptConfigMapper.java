package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsRequestpurchaseInterceptConfig;
import com.sales.ops.db.entity.OpsRequestpurchaseInterceptConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsRequestpurchaseInterceptConfigMapper {
    long countByExample(OpsRequestpurchaseInterceptConfigExample example);

    int deleteByExample(OpsRequestpurchaseInterceptConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsRequestpurchaseInterceptConfig record);

    int insertSelective(OpsRequestpurchaseInterceptConfig record);

    List<OpsRequestpurchaseInterceptConfig> selectByExample(OpsRequestpurchaseInterceptConfigExample example);

    OpsRequestpurchaseInterceptConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsRequestpurchaseInterceptConfig record, @Param("example") OpsRequestpurchaseInterceptConfigExample example);

    int updateByExample(@Param("record") OpsRequestpurchaseInterceptConfig record, @Param("example") OpsRequestpurchaseInterceptConfigExample example);

    int updateByPrimaryKeySelective(OpsRequestpurchaseInterceptConfig record);

    int updateByPrimaryKey(OpsRequestpurchaseInterceptConfig record);
}