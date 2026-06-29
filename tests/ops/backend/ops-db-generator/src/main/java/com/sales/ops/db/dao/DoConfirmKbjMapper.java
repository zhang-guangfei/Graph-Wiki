package com.sales.ops.db.dao;

import com.sales.ops.db.entity.DoConfirmKbj;
import com.sales.ops.db.entity.DoConfirmKbjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DoConfirmKbjMapper {
    long countByExample(DoConfirmKbjExample example);

    int deleteByExample(DoConfirmKbjExample example);

    int insert(DoConfirmKbj record);

    int insertSelective(DoConfirmKbj record);

    List<DoConfirmKbj> selectByExample(DoConfirmKbjExample example);

    int updateByExampleSelective(@Param("record") DoConfirmKbj record, @Param("example") DoConfirmKbjExample example);

    int updateByExample(@Param("record") DoConfirmKbj record, @Param("example") DoConfirmKbjExample example);
}