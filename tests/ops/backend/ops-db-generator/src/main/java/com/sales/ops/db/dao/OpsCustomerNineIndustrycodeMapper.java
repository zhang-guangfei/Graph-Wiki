package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCustomerNineIndustrycode;
import com.sales.ops.db.entity.OpsCustomerNineIndustrycodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCustomerNineIndustrycodeMapper {
    long countByExample(OpsCustomerNineIndustrycodeExample example);

    int deleteByExample(OpsCustomerNineIndustrycodeExample example);

    int insert(OpsCustomerNineIndustrycode record);

    int insertSelective(OpsCustomerNineIndustrycode record);

    List<OpsCustomerNineIndustrycode> selectByExample(OpsCustomerNineIndustrycodeExample example);

    int updateByExampleSelective(@Param("record") OpsCustomerNineIndustrycode record, @Param("example") OpsCustomerNineIndustrycodeExample example);

    int updateByExample(@Param("record") OpsCustomerNineIndustrycode record, @Param("example") OpsCustomerNineIndustrycodeExample example);
}