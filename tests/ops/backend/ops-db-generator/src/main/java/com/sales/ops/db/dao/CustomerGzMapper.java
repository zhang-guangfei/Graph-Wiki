package com.sales.ops.db.dao;

import com.sales.ops.db.entity.CustomerGz;
import com.sales.ops.db.entity.CustomerGzExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomerGzMapper {
    long countByExample(CustomerGzExample example);

    int deleteByExample(CustomerGzExample example);

    int deleteByPrimaryKey(String customerno);

    int insert(CustomerGz record);

    int insertSelective(CustomerGz record);

    List<CustomerGz> selectByExampleWithBLOBs(CustomerGzExample example);

    List<CustomerGz> selectByExample(CustomerGzExample example);

    CustomerGz selectByPrimaryKey(String customerno);

    int updateByExampleSelective(@Param("record") CustomerGz record, @Param("example") CustomerGzExample example);

    int updateByExampleWithBLOBs(@Param("record") CustomerGz record, @Param("example") CustomerGzExample example);

    int updateByExample(@Param("record") CustomerGz record, @Param("example") CustomerGzExample example);

    int updateByPrimaryKeySelective(CustomerGz record);

    int updateByPrimaryKeyWithBLOBs(CustomerGz record);

    int updateByPrimaryKey(CustomerGz record);
}