package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TblGroupcustomer;
import com.sales.ops.db.entity.TblGroupcustomerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblGroupcustomerMapper {
    long countByExample(TblGroupcustomerExample example);

    int deleteByExample(TblGroupcustomerExample example);

    int deleteByPrimaryKey(String customerno);

    int insert(TblGroupcustomer record);

    int insertSelective(TblGroupcustomer record);

    List<TblGroupcustomer> selectByExample(TblGroupcustomerExample example);

    TblGroupcustomer selectByPrimaryKey(String customerno);

    int updateByExampleSelective(@Param("record") TblGroupcustomer record, @Param("example") TblGroupcustomerExample example);

    int updateByExample(@Param("record") TblGroupcustomer record, @Param("example") TblGroupcustomerExample example);

    int updateByPrimaryKeySelective(TblGroupcustomer record);

    int updateByPrimaryKey(TblGroupcustomer record);
}