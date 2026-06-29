package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TempModifyAddressDo;
import com.sales.ops.db.entity.TempModifyAddressDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TempModifyAddressDoMapper {
    long countByExample(TempModifyAddressDoExample example);

    int deleteByExample(TempModifyAddressDoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TempModifyAddressDo record);

    int insertSelective(TempModifyAddressDo record);

    List<TempModifyAddressDo> selectByExample(TempModifyAddressDoExample example);

    TempModifyAddressDo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TempModifyAddressDo record, @Param("example") TempModifyAddressDoExample example);

    int updateByExample(@Param("record") TempModifyAddressDo record, @Param("example") TempModifyAddressDoExample example);

    int updateByPrimaryKeySelective(TempModifyAddressDo record);

    int updateByPrimaryKey(TempModifyAddressDo record);
}