package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TempModifyAddressOrder0831;
import com.sales.ops.db.entity.TempModifyAddressOrder0831Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TempModifyAddressOrder0831Mapper {
    long countByExample(TempModifyAddressOrder0831Example example);

    int deleteByExample(TempModifyAddressOrder0831Example example);

    int deleteByPrimaryKey(String rorderFno);

    int insert(TempModifyAddressOrder0831 record);

    int insertSelective(TempModifyAddressOrder0831 record);

    List<TempModifyAddressOrder0831> selectByExample(TempModifyAddressOrder0831Example example);

    TempModifyAddressOrder0831 selectByPrimaryKey(String rorderFno);

    int updateByExampleSelective(@Param("record") TempModifyAddressOrder0831 record, @Param("example") TempModifyAddressOrder0831Example example);

    int updateByExample(@Param("record") TempModifyAddressOrder0831 record, @Param("example") TempModifyAddressOrder0831Example example);

    int updateByPrimaryKeySelective(TempModifyAddressOrder0831 record);

    int updateByPrimaryKey(TempModifyAddressOrder0831 record);
}