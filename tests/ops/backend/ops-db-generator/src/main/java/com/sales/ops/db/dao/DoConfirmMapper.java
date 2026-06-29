package com.sales.ops.db.dao;

import com.sales.ops.db.entity.DoConfirm;
import com.sales.ops.db.entity.DoConfirmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DoConfirmMapper {
    long countByExample(DoConfirmExample example);

    int deleteByExample(DoConfirmExample example);

    int insert(DoConfirm record);

    int insertSelective(DoConfirm record);

    List<DoConfirm> selectByExample(DoConfirmExample example);

    int updateByExampleSelective(@Param("record") DoConfirm record, @Param("example") DoConfirmExample example);

    int updateByExample(@Param("record") DoConfirm record, @Param("example") DoConfirmExample example);
}