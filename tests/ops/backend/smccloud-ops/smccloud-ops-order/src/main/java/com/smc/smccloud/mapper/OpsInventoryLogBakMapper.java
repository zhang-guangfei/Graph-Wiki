package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 读取归档库的库存变更日志表
 *
 * @author C12961
 * @date 2025-09-22
 * bug 19059
 */
@Mapper
@DS("archive_core")
public interface OpsInventoryLogBakMapper {

    @Select("select top 1 inventory_id from ops_inventory_log where order_no=#{orderNo} and item_no=#{itemNo} and modelno=#{modelNo} and type=0 ")
    Long getOutInventoryIdByOrderFromBak(@Param("orderNo") String orderNo, @Param("itemNo") Integer itemNo, @Param("modelNo") String modelNo);

    @Select("select top 1 inventory_id from ops_inventory_log where order_no=#{orderNo} and item_no=#{itemNo} and modelno=#{modelNo} and type=1 ")
    Long getImpInventoryIdByOrderFromBak(@Param("orderNo") String orderNo, @Param("itemNo") Integer itemNo, @Param("modelNo") String modelNo);


}
