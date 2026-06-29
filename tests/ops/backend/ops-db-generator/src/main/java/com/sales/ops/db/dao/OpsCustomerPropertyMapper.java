package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCustomerProperty;
import com.sales.ops.db.entity.OpsCustomerPropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCustomerPropertyMapper {
    long countByExample(OpsCustomerPropertyExample example);

    int deleteByExample(OpsCustomerPropertyExample example);

    int insert(OpsCustomerProperty record);

    int insertSelective(OpsCustomerProperty record);

    List<OpsCustomerProperty> selectByExample(OpsCustomerPropertyExample example);

    int updateByExampleSelective(@Param("record") OpsCustomerProperty record, @Param("example") OpsCustomerPropertyExample example);

    int updateByExample(@Param("record") OpsCustomerProperty record, @Param("example") OpsCustomerPropertyExample example);
}