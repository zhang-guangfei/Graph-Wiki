package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author C18117
 * @title: DailyInventoryMapper
 * @date 2022/11/21 15:15
 */
@DS("reportdb")
@Mapper
public interface DailyInventoryMapper {

    @Select("select top 1 quantity from ops_report.dbo.DailyInventory " +
            "where modelno=#{modelNo} and inventory_id=#{inventoryId} and  convert(varchar(10),saveTime,121)=#{date}")
    Integer getStockQty(@Param("modelNo") String modelNo,@Param("inventoryId") Long inventoryId,@Param("date") String date);
}
