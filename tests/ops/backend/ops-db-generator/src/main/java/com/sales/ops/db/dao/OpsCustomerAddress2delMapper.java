package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsCustomerAddress2del;
import com.sales.ops.db.entity.OpsCustomerAddress2delExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsCustomerAddress2delMapper {
    long countByExample(OpsCustomerAddress2delExample example);

    int deleteByExample(OpsCustomerAddress2delExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OpsCustomerAddress2del record);

    int insertSelective(OpsCustomerAddress2del record);

    List<OpsCustomerAddress2del> selectByExample(OpsCustomerAddress2delExample example);

    OpsCustomerAddress2del selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OpsCustomerAddress2del record, @Param("example") OpsCustomerAddress2delExample example);

    int updateByExample(@Param("record") OpsCustomerAddress2del record, @Param("example") OpsCustomerAddress2delExample example);

    int updateByPrimaryKeySelective(OpsCustomerAddress2del record);

    int updateByPrimaryKey(OpsCustomerAddress2del record);
}