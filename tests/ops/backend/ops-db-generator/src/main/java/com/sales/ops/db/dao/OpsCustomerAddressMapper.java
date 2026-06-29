package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCustomerAddress;
import com.sales.ops.db.entity.OpsCustomerAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCustomerAddressMapper {
    long countByExample(OpsCustomerAddressExample example);

    int deleteByExample(OpsCustomerAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsCustomerAddress record);

    int insertSelective(OpsCustomerAddress record);

    List<OpsCustomerAddress> selectByExample(OpsCustomerAddressExample example);

    OpsCustomerAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsCustomerAddress record, @Param("example") OpsCustomerAddressExample example);

    int updateByExample(@Param("record") OpsCustomerAddress record, @Param("example") OpsCustomerAddressExample example);

    int updateByPrimaryKeySelective(OpsCustomerAddress record);

    int updateByPrimaryKey(OpsCustomerAddress record);
}