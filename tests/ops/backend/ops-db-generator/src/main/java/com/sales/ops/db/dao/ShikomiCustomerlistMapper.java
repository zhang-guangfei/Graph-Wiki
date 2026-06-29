package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ShikomiCustomerlist;
import com.sales.ops.db.entity.ShikomiCustomerlistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShikomiCustomerlistMapper {
    long countByExample(ShikomiCustomerlistExample example);

    int deleteByExample(ShikomiCustomerlistExample example);

    int insert(ShikomiCustomerlist record);

    int insertSelective(ShikomiCustomerlist record);

    List<ShikomiCustomerlist> selectByExample(ShikomiCustomerlistExample example);

    int updateByExampleSelective(@Param("record") ShikomiCustomerlist record, @Param("example") ShikomiCustomerlistExample example);

    int updateByExample(@Param("record") ShikomiCustomerlist record, @Param("example") ShikomiCustomerlistExample example);
}