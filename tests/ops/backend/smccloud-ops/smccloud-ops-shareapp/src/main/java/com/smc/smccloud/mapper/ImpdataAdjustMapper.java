package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.cost.ImpdataAdjustDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-03-03 14:30
 * Description:
 */
@DS("costdb")
@Mapper
public interface ImpdataAdjustMapper extends BaseMapper<ImpdataAdjustDO> {
    @Select("<script> " +
            " INSERT INTO Impdata_Adjust  " +
            "  (ImpDate, InvoiceNo, InvDesc, Ecode, OrderNo, CustomerNo, ModelNo, Quantity, OptCode, DataSource, StockCode,OwnerCompanyId ) " +
            "  values" +
            " <if test='list !=null and list.size() &gt; 0'>" +
            "  <foreach collection='list' item='item' index='index'  separator=',' > " +
            "   ( #{item.impDate,jdbcType=TIMESTAMP},#{item.invoiceNo,jdbcType=VARCHAR}, " +
            "    #{item.invDesc,jdbcType=VARCHAR},#{item.eCode,jdbcType=VARCHAR}, " +
            "    #{item.orderNo,jdbcType=VARCHAR},#{item.customerNo,jdbcType=VARCHAR}, " +
            "    #{item.modelNo,jdbcType=VARCHAR},#{item.quantity,jdbcType=INTEGER}, " +
            "    #{item.optCode,jdbcType=VARCHAR},#{item.dataSource,jdbcType=VARCHAR}, " +
            "    #{item.stockCode,jdbcType=VARCHAR},#{item.ownerCompanyId,jdbcType=VARCHAR} " +
            "     ) " +
            "  </foreach>" +
            " </if>" +
            "</script>")
    Integer insertBatch(List<ImpdataAdjustDO> List);
}
