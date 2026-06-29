package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsAddressType;
import com.sales.ops.db.entity.OpsAddressTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsAddressTypeMapper {
    long countByExample(OpsAddressTypeExample example);

    int deleteByExample(OpsAddressTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsAddressType record);

    int insertSelective(OpsAddressType record);

    List<OpsAddressType> selectByExample(OpsAddressTypeExample example);

    OpsAddressType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsAddressType record, @Param("example") OpsAddressTypeExample example);

    int updateByExample(@Param("record") OpsAddressType record, @Param("example") OpsAddressTypeExample example);

    int updateByPrimaryKeySelective(OpsAddressType record);

    int updateByPrimaryKey(OpsAddressType record);
}