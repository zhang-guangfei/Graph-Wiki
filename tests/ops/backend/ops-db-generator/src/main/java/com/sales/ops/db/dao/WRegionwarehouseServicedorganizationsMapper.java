package com.sales.ops.db.dao;

import com.sales.ops.db.entity.WRegionwarehouseServicedorganizations;
import com.sales.ops.db.entity.WRegionwarehouseServicedorganizationsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WRegionwarehouseServicedorganizationsMapper {
    long countByExample(WRegionwarehouseServicedorganizationsExample example);

    int deleteByExample(WRegionwarehouseServicedorganizationsExample example);

    int insert(WRegionwarehouseServicedorganizations record);

    int insertSelective(WRegionwarehouseServicedorganizations record);

    List<WRegionwarehouseServicedorganizations> selectByExample(WRegionwarehouseServicedorganizationsExample example);

    int updateByExampleSelective(@Param("record") WRegionwarehouseServicedorganizations record, @Param("example") WRegionwarehouseServicedorganizationsExample example);

    int updateByExample(@Param("record") WRegionwarehouseServicedorganizations record, @Param("example") WRegionwarehouseServicedorganizationsExample example);
}