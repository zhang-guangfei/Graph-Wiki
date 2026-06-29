package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsAddressDetails;
import com.sales.ops.db.entity.OpsAddressDetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsAddressDetailsMapper {
    long countByExample(OpsAddressDetailsExample example);

    int deleteByExample(OpsAddressDetailsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsAddressDetails record);

    int insertSelective(OpsAddressDetails record);

    List<OpsAddressDetails> selectByExample(OpsAddressDetailsExample example);

    OpsAddressDetails selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsAddressDetails record, @Param("example") OpsAddressDetailsExample example);

    int updateByExample(@Param("record") OpsAddressDetails record, @Param("example") OpsAddressDetailsExample example);

    int updateByPrimaryKeySelective(OpsAddressDetails record);

    int updateByPrimaryKey(OpsAddressDetails record);
}