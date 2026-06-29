package com.sales.ops.db.dao;

import com.sales.ops.db.entity.MigInventoryKwf;
import com.sales.ops.db.entity.MigInventoryKwfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MigInventoryKwfMapper {
    long countByExample(MigInventoryKwfExample example);

    int deleteByExample(MigInventoryKwfExample example);

    int insert(MigInventoryKwf record);

    int insertSelective(MigInventoryKwf record);

    List<MigInventoryKwf> selectByExampleWithBLOBs(MigInventoryKwfExample example);

    List<MigInventoryKwf> selectByExample(MigInventoryKwfExample example);

    int updateByExampleSelective(@Param("record") MigInventoryKwf record, @Param("example") MigInventoryKwfExample example);

    int updateByExampleWithBLOBs(@Param("record") MigInventoryKwf record, @Param("example") MigInventoryKwfExample example);

    int updateByExample(@Param("record") MigInventoryKwf record, @Param("example") MigInventoryKwfExample example);
}