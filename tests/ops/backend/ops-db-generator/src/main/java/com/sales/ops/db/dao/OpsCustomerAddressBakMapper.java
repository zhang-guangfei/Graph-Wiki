package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCustomerAddressBak;
import com.sales.ops.db.entity.OpsCustomerAddressBakExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCustomerAddressBakMapper {
    long countByExample(OpsCustomerAddressBakExample example);

    int deleteByExample(OpsCustomerAddressBakExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsCustomerAddressBak record);

    int insertSelective(OpsCustomerAddressBak record);

    List<OpsCustomerAddressBak> selectByExample(OpsCustomerAddressBakExample example);

    OpsCustomerAddressBak selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsCustomerAddressBak record, @Param("example") OpsCustomerAddressBakExample example);

    int updateByExample(@Param("record") OpsCustomerAddressBak record, @Param("example") OpsCustomerAddressBakExample example);

    int updateByPrimaryKeySelective(OpsCustomerAddressBak record);

    int updateByPrimaryKey(OpsCustomerAddressBak record);
}