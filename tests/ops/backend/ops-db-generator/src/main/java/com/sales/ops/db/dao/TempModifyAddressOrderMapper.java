package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TempModifyAddressOrder;
import com.sales.ops.db.entity.TempModifyAddressOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TempModifyAddressOrderMapper {
    long countByExample(TempModifyAddressOrderExample example);

    int deleteByExample(TempModifyAddressOrderExample example);

    int deleteByPrimaryKey(String rorderFno);

    int insert(TempModifyAddressOrder record);

    int insertSelective(TempModifyAddressOrder record);

    List<TempModifyAddressOrder> selectByExample(TempModifyAddressOrderExample example);

    TempModifyAddressOrder selectByPrimaryKey(String rorderFno);

    int updateByExampleSelective(@Param("record") TempModifyAddressOrder record, @Param("example") TempModifyAddressOrderExample example);

    int updateByExample(@Param("record") TempModifyAddressOrder record, @Param("example") TempModifyAddressOrderExample example);

    int updateByPrimaryKeySelective(TempModifyAddressOrder record);

    int updateByPrimaryKey(TempModifyAddressOrder record);
}