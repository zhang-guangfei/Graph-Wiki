package com.sales.ops.db.dao;

import com.sales.ops.db.entity.BjShikomiCustomerlist;
import com.sales.ops.db.entity.BjShikomiCustomerlistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BjShikomiCustomerlistMapper {
    long countByExample(BjShikomiCustomerlistExample example);

    int deleteByExample(BjShikomiCustomerlistExample example);

    int insert(BjShikomiCustomerlist record);

    int insertSelective(BjShikomiCustomerlist record);

    List<BjShikomiCustomerlist> selectByExample(BjShikomiCustomerlistExample example);

    int updateByExampleSelective(@Param("record") BjShikomiCustomerlist record, @Param("example") BjShikomiCustomerlistExample example);

    int updateByExample(@Param("record") BjShikomiCustomerlist record, @Param("example") BjShikomiCustomerlistExample example);
}