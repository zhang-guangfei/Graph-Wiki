package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCustomerAddressBak2del;
import com.sales.ops.db.entity.OpsCustomerAddressBak2delExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCustomerAddressBak2delMapper {
    long countByExample(OpsCustomerAddressBak2delExample example);

    int deleteByExample(OpsCustomerAddressBak2delExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsCustomerAddressBak2del record);

    int insertSelective(OpsCustomerAddressBak2del record);

    List<OpsCustomerAddressBak2del> selectByExample(OpsCustomerAddressBak2delExample example);

    OpsCustomerAddressBak2del selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsCustomerAddressBak2del record, @Param("example") OpsCustomerAddressBak2delExample example);

    int updateByExample(@Param("record") OpsCustomerAddressBak2del record, @Param("example") OpsCustomerAddressBak2delExample example);

    int updateByPrimaryKeySelective(OpsCustomerAddressBak2del record);

    int updateByPrimaryKey(OpsCustomerAddressBak2del record);
}