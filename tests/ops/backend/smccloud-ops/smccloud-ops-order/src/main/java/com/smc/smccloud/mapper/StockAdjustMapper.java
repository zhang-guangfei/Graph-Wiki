package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.adjust.StockAdjustDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opsdb")
public interface StockAdjustMapper extends BaseMapper<StockAdjustDO> {
    @Select("<script> " +
            "insert into  stock_adjust(invoiceNo,adjustDate,fullOrderNo,orderNo,itemNo,splitItemNo,modelNo,quantity," +
            " adjustType,optCode,createTime,createUser,exchangeRate,price,amount,warehouseCode,pplNo,projectNo,customerNo, "+
            " groupCustomerNo,inventoryTypeCode ,supplierCode,currency, reason) VALUES" +
            "<if test = 'data != null and  data.size() &gt; 0' >" +
            "  <foreach collection='data' item='item' index='index'  separator=',' > " +
            " (#{item.invoiceNo}, #{item.adjustDate,jdbcType=DATE}, #{item.fullOrderNo},#{item.orderNo},#{item.itemNo},#{item.splitItemNo},#{item.modelNo},#{item.quantity}, " +
            " #{item.adjustType},#{item.optCode},#{item.createTime},#{item.createUser},#{item.exchangeRate}, #{item.price,jdbcType=NUMERIC}, #{item.amount,jdbcType=NUMERIC},"+
            " #{item.warehouseCode},#{item.pplNo},#{item.projectNo},#{item.customerNo}," +
            " #{item.groupCustomerNo},#{item.inventoryTypeCode},#{item.supplierCode},#{item.currency},#{item.reason})" +
            "  </foreach>" +
            "</if>" +
            "</script>")
    void  InsertByBatch(@Param("data") List<StockAdjustDO> list);
}
