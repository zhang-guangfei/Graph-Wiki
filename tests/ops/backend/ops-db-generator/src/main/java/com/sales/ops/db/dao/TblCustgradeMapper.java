package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TblCustgrade;
import com.sales.ops.db.entity.TblCustgradeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblCustgradeMapper {
    long countByExample(TblCustgradeExample example);

    int deleteByExample(TblCustgradeExample example);

    int insert(TblCustgrade record);

    int insertSelective(TblCustgrade record);

    List<TblCustgrade> selectByExample(TblCustgradeExample example);

    int updateByExampleSelective(@Param("record") TblCustgrade record, @Param("example") TblCustgradeExample example);

    int updateByExample(@Param("record") TblCustgrade record, @Param("example") TblCustgradeExample example);
}