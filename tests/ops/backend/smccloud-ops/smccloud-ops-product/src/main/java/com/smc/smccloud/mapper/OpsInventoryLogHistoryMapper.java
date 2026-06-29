package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsInventoryLog;
import com.smc.smccloud.model.inventory.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
@DS("archive_core")
@Mapper
public interface OpsInventoryLogHistoryMapper extends BaseMapper<OpsInventoryLogDO> {

    /**
     * 单表查询
     * @param dto
     * @return
     */
    @Select("<script>" +
            "select * from  ops_core.dbo.ops_inventory_log  with(nolock)" +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " <if test='dto.inventoryId!=null and dto.inventoryId!=\"\" '> inventory_id=#{dto.inventoryId} and </if>" +
            " <if test='dto.orderNo!=null and dto.orderNo!=\"\" '> order_no=#{dto.orderNo} and </if>" +
            " <if test='dto.itemNo!=null and dto.itemNo!=\"\" '> item_no=#{dto.itemNo} and </if>" +
            " <if test='dto.modelNo!=null and dto.modelNo!=\"\" '> modelno=#{dto.modelNo} and </if>" +
            " <if test='dto.invoiceNo!=null and dto.invoiceNo!=\"\" '> invoice_no=#{dto.invoiceNo} and </if>" +
            " <if test='dto.voucherType !=null and dto.voucherType.size() &gt; 0 '> " +
            "  voucher_type in " +
            " <foreach collection = 'dto.voucherType' item = 'item' index='index' open='(' separator = ',' close=')' > "+
            "  #{item}" +
            " </foreach>"+
            " and " +
            " </if>" +
            " <if test='dto.warehouseCode!=null and dto.warehouseCode!=\"\" '> warehouse_code=#{dto.warehouseCode} and </if>" +
            " <if test='dto.type!=null '> type=#{dto.type} and </if>" +
            " <if test='dto.crtTimeStartStr != null and dto.crtTimeStartStr!= \"\"  '>" +
            "  cre_time &gt; #{dto.crtTimeStartStr} and cre_time &lt; #{dto.crtTimeEndStr} " +
            " </if>" +
            "</trim>"+
            " order by  ${dto.property} ${dto.sortRule} " +
            "</script>" )
    List<OpsInventoryLogVO> listInventoryLog(@Param("dto") InventoryLogRequstDTO dto);




}
