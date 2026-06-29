package com.sales.ops.db.dao;

import com.sales.ops.db.entity.InventoryReport;
import com.sales.ops.db.entity.InventoryReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InventoryReportMapper {
    long countByExample(InventoryReportExample example);

    int deleteByExample(InventoryReportExample example);

    int insert(InventoryReport record);

    int insertSelective(InventoryReport record);

    List<InventoryReport> selectByExample(InventoryReportExample example);

    int updateByExampleSelective(@Param("record") InventoryReport record, @Param("example") InventoryReportExample example);

    int updateByExample(@Param("record") InventoryReport record, @Param("example") InventoryReportExample example);
}