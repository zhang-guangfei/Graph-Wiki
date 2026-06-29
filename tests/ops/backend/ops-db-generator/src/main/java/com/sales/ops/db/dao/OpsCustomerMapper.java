package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCustomer;
import com.sales.ops.db.entity.OpsCustomerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCustomerMapper {
    long countByExample(OpsCustomerExample example);

    int deleteByExample(OpsCustomerExample example);

    int deleteByPrimaryKey(String customerNo);

    int insert(OpsCustomer record);

    int insertSelective(OpsCustomer record);

    List<OpsCustomer> selectByExample(OpsCustomerExample example);

    OpsCustomer selectByPrimaryKey(String customerNo);

    int updateByExampleSelective(@Param("record") OpsCustomer record, @Param("example") OpsCustomerExample example);

    int updateByExample(@Param("record") OpsCustomer record, @Param("example") OpsCustomerExample example);

    int updateByPrimaryKeySelective(OpsCustomer record);

    int updateByPrimaryKey(OpsCustomer record);
}