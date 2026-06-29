package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InventoryMoveReport;
import com.sales.ops.db.entity.InventoryMoveReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryMoveReportMapper {
    long countByExample(InventoryMoveReportExample example);

    int deleteByExample(InventoryMoveReportExample example);

    int insert(InventoryMoveReport record);

    int insertSelective(InventoryMoveReport record);

    List<InventoryMoveReport> selectByExample(InventoryMoveReportExample example);

    int updateByExampleSelective(@Param("record") InventoryMoveReport record, @Param("example") InventoryMoveReportExample example);

    int updateByExample(@Param("record") InventoryMoveReport record, @Param("example") InventoryMoveReportExample example);
}