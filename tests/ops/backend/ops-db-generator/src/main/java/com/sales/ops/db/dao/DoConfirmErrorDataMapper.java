package com.sales.ops.db.dao;

import com.sales.ops.db.entity.DoConfirmErrorData;
import com.sales.ops.db.entity.DoConfirmErrorDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DoConfirmErrorDataMapper {
    long countByExample(DoConfirmErrorDataExample example);

    int deleteByExample(DoConfirmErrorDataExample example);

    int insert(DoConfirmErrorData record);

    int insertSelective(DoConfirmErrorData record);

    List<DoConfirmErrorData> selectByExample(DoConfirmErrorDataExample example);

    int updateByExampleSelective(@Param("record") DoConfirmErrorData record, @Param("example") DoConfirmErrorDataExample example);

    int updateByExample(@Param("record") DoConfirmErrorData record, @Param("example") DoConfirmErrorDataExample example);
}