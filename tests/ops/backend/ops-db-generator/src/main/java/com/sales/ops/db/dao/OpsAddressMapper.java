package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsAddress;
import com.sales.ops.db.entity.OpsAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsAddressMapper {
    long countByExample(OpsAddressExample example);

    int deleteByExample(OpsAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsAddress record);

    int insertSelective(OpsAddress record);

    List<OpsAddress> selectByExample(OpsAddressExample example);

    OpsAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsAddress record, @Param("example") OpsAddressExample example);

    int updateByExample(@Param("record") OpsAddress record, @Param("example") OpsAddressExample example);

    int updateByPrimaryKeySelective(OpsAddress record);

    int updateByPrimaryKey(OpsAddress record);
}