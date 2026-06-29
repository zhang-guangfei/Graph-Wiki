package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ShikomiCustomer;
import com.sales.ops.db.entity.ShikomiCustomerExample;
import com.sales.ops.db.entity.ShikomiCustomerKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShikomiCustomerMapper {
    long countByExample(ShikomiCustomerExample example);

    int deleteByExample(ShikomiCustomerExample example);

    int deleteByPrimaryKey(ShikomiCustomerKey key);

    int insert(ShikomiCustomer record);

    int insertSelective(ShikomiCustomer record);

    List<ShikomiCustomer> selectByExample(ShikomiCustomerExample example);

    ShikomiCustomer selectByPrimaryKey(ShikomiCustomerKey key);

    int updateByExampleSelective(@Param("record") ShikomiCustomer record, @Param("example") ShikomiCustomerExample example);

    int updateByExample(@Param("record") ShikomiCustomer record, @Param("example") ShikomiCustomerExample example);

    int updateByPrimaryKeySelective(ShikomiCustomer record);

    int updateByPrimaryKey(ShikomiCustomer record);
}