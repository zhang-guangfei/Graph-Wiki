package com.sales.ops.db.dao;

import com.sales.ops.db.entity.DoConfirmKsh;
import com.sales.ops.db.entity.DoConfirmKshExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DoConfirmKshMapper {
    long countByExample(DoConfirmKshExample example);

    int deleteByExample(DoConfirmKshExample example);

    int insert(DoConfirmKsh record);

    int insertSelective(DoConfirmKsh record);

    List<DoConfirmKsh> selectByExample(DoConfirmKshExample example);

    int updateByExampleSelective(@Param("record") DoConfirmKsh record, @Param("example") DoConfirmKshExample example);

    int updateByExample(@Param("record") DoConfirmKsh record, @Param("example") DoConfirmKshExample example);
}