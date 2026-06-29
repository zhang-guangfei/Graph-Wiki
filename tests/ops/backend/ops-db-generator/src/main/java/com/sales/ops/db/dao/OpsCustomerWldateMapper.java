package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCustomerWldate;
import com.sales.ops.db.entity.OpsCustomerWldateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCustomerWldateMapper {
    long countByExample(OpsCustomerWldateExample example);

    int deleteByExample(OpsCustomerWldateExample example);

    int deleteByPrimaryKey(String customerNo);

    int insert(OpsCustomerWldate record);

    int insertSelective(OpsCustomerWldate record);

    List<OpsCustomerWldate> selectByExample(OpsCustomerWldateExample example);

    OpsCustomerWldate selectByPrimaryKey(String customerNo);

    int updateByExampleSelective(@Param("record") OpsCustomerWldate record, @Param("example") OpsCustomerWldateExample example);

    int updateByExample(@Param("record") OpsCustomerWldate record, @Param("example") OpsCustomerWldateExample example);

    int updateByPrimaryKeySelective(OpsCustomerWldate record);

    int updateByPrimaryKey(OpsCustomerWldate record);
}