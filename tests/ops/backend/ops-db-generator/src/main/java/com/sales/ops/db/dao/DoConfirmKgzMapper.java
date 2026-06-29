package com.sales.ops.db.dao;

import com.sales.ops.db.entity.DoConfirmKgz;
import com.sales.ops.db.entity.DoConfirmKgzExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DoConfirmKgzMapper {
    long countByExample(DoConfirmKgzExample example);

    int deleteByExample(DoConfirmKgzExample example);

    int insert(DoConfirmKgz record);

    int insertSelective(DoConfirmKgz record);

    List<DoConfirmKgz> selectByExample(DoConfirmKgzExample example);

    int updateByExampleSelective(@Param("record") DoConfirmKgz record, @Param("example") DoConfirmKgzExample example);

    int updateByExample(@Param("record") DoConfirmKgz record, @Param("example") DoConfirmKgzExample example);
}