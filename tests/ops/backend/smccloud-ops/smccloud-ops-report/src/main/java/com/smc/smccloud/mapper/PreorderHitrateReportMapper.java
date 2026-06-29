package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsInventoryLog;
import com.smc.smccloud.model.PreorderHitrateReportDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-12-23
 */
@Mapper
@DS("reportdb")
public interface PreorderHitrateReportMapper extends BaseMapper<PreorderHitrateReportDO> {


    /**
     * 统计范围为顾客在库、战略在库和服务备库，不含SHIKOMI。
     */
    @Select("<script> " +
            "SELECT\n" +
            "\ta.inventory_id,\n" +
            "\ta.warehouse_code,\n" +
            "\ta.modelno,\n" +
            "\tb.inventory_type_code AS inventory_type,\n" +
            "\tb.customer_no,\n" +
            "\tb.ppl AS bom,\n" +
            "\tb.project_code AS pj,\n" +
            "\tb.group_customer_no \n" +
            "FROM\n" +
            "\tops_report.dbo.ops_inventory_with_hitrate a\n" +
            "\tINNER JOIN ops_core.dbo.ops_inventory_property b ON a.inventory_property_id= b.inventory_property_id\n" +
            "\tINNER JOIN ops_core.dbo.ops_warehouse w ON a.warehouse_code = w.warehouse_code \n" +
            "WHERE\n" +
            "\ta.delflag= 0 \n" +
            "\tAND w.delflag = 0 \n" +
            "\tAND ( b.inventory_type_code LIKE 'GK%' OR b.inventory_type_code LIKE 'ZL%' OR ( b.inventory_type_code = 'TY' AND w.warehouse_type = 'WT' ) ) \n" +
            "  <if test = ' inventoryIdList == null '> " +
            " AND a.quantity > 0 " +
            " </if> " +
            " and a.batch_no = #{batchNo} " +
            " <if  test = ' inventoryIdList != null  and  inventoryIdList.size() &gt; 0 '  > " +
            " and a.inventory_id in  " +
            "   <foreach collection = 'inventoryIdList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "   </foreach>" +
            "</if> " +
            "</script>")
    List<PreorderHitrateReportDO> getPreorderHitrateReport(@Param("batchNo") String batchNo,@Param("inventoryIdList") List<Long> inventoryIdList);


    @Select("<script> " +
            "SELECT\n" +
            "\ta.inventory_id,\n" +
            "\ta.warehouse_code,\n" +
            "\ta.modelno,\n" +
            "\tb.inventory_type_code AS inventory_type,\n" +
            "\tb.customer_no,\n" +
            "\tb.ppl AS bom,\n" +
            "\tb.project_code AS pj,\n" +
            "\tb.group_customer_no \n" +
            "FROM\n" +
            "\t ops_core.dbo.ops_inventory a\n" +
            "\tINNER JOIN ops_core.dbo.ops_inventory_property b ON a.inventory_property_id= b.inventory_property_id\n" +
            "\tINNER JOIN ops_core.dbo.ops_warehouse w ON a.warehouse_code = w.warehouse_code \n" +
            "WHERE\n" +
            "\ta.delflag= 0 \n" +
            "\tAND w.delflag = 0 \n" +
            "\tAND ( b.inventory_type_code LIKE 'GK%' OR b.inventory_type_code LIKE 'ZL%' OR ( b.inventory_type_code = 'TY' AND w.warehouse_type = 'WT' ) ) \n" +
            "  <if test = ' inventoryIdList == null '> " +
            " AND a.quantity > 0 " +
            " </if> " +
            " <if  test = ' inventoryIdList != null  and  inventoryIdList.size() &gt; 0 '  > " +
            " and a.inventory_id in  " +
            "   <foreach collection = 'inventoryIdList' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "   </foreach>" +
            "</if> " +
            "</script>")
    List<PreorderHitrateReportDO> getInventoryInfo(@Param("batchNo") String batchNo,@Param("inventoryIdList") List<Long> inventoryIdList);


    @Select("SELECT\n" +
            "\ta.inventory_id,\n" +
            "\ta.warehouse_code,\n" +
            "\ta.modelno,\n" +
            "\tb.inventory_type_code AS inventory_type,\n" +
            "\tb.customer_no,\n" +
            "\tb.ppl AS bom,\n" +
            "\tb.project_code AS pj,\n" +
            "\tb.group_customer_no \n" +
            "FROM\n" +
            "\tops_report.dbo.ops_inventory_with_hitrate a\n" +
            "\tINNER JOIN ops_core.dbo.ops_inventory_property b ON a.inventory_property_id= b.inventory_property_id\n" +
            "\tINNER JOIN ops_core.dbo.ops_warehouse w ON a.warehouse_code = w.warehouse_code \n" +
            "WHERE\n" +
            "\ta.delflag= 0 \n" +
            "\tAND w.delflag = 0 \n" +
            "\tAND ( b.inventory_type_code LIKE 'GK%' OR b.inventory_type_code LIKE 'ZL%' OR ( b.inventory_type_code = 'TY' AND w.warehouse_type = 'WT' ) ) \n" +
            "\tAND a.quantity > 0 and a.batch_no = #{batchNo} ")
    List<PreorderHitrateReportDO> getPreorderHitrateReportByDate(@Param("dateStr") String dateStr,@Param("batchNo") String batchNo);


    @Select("<script>" +
            " select ISNULL(sum(quantity) , 0) as quantity  from ops_core.dbo.ops_inventory_log where inventory_id = #{invId} and type = #{type} " +
            " and voucher_type in " +
            "   <foreach collection = 'voucherType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and cre_time between #{startTime} and #{endTime}" +
            "</script>")
    int  getInventoryLogList(@Param("invId") Long invId, @Param("type") String type,
                             @Param("voucherType") List<String> voucherType,@Param("startTime") String startTime,
                             @Param("endTime") String endTime );


    // 2. 新增方法，专门用于接收invIds列表
    @Select("<script>" +
            " select inventory_id, ISNULL(sum(quantity) , 0) as quantity " +
            " from ops_core.dbo.ops_inventory_log " +
            " where type = #{type} " +
            "   and inventory_id in " +
            "   <foreach collection = 'invIds' item = 'id' open='(' separator = ',' close=')' > " +
            "     #{id}" +
            "   </foreach>" +
            "   and voucher_type in " +
            "   <foreach collection = 'voucherType' item = 'item' open='(' separator = ',' close=')' > " +
            "     #{item}" +
            "   </foreach>" +
            "   and cre_time between #{startTime} and #{endTime}" +
            " group by inventory_id" +
            "</script>")
    List<OpsInventoryLog> getInventoryLogList2(@Param("invIds") List<Long> invIds, @Param("type") String type,
                                               @Param("voucherType") List<String> voucherType,
                                               @Param("startTime") String startTime,
                                               @Param("endTime") String endTime);

    @Select("select top(1) EPrice,modelNo from ops_core.dbo.product_price where modelNo = #{modelNo} and is_deleted = 0 order by eprice asc ")
    BigDecimal getPriceByModelNo(@Param("modelNo") String modelNo);

    @Select( "<script>" +
            " SELECT modelNo, MIN(EPrice) as eprice "+
            " FROM ops_core.dbo.product_price "+
            " WHERE is_deleted = 0 "+
            " AND modelNo IN "+
            "<foreach collection='modelNos' item='modelNo' open='(' separator=',' close=')'>"+
            "   #{modelNo}"+
            "</foreach>"+
            " GROUP BY modelNo"+
            "</script>")
    List<PreorderHitrateReportDO> getPricesByModelNos(@Param("modelNos") List<String> modelNos);

    @Select("<script>" +
            "select DISTINCT inventory_id from ops_core.dbo.ops_inventory_log l \n" +
            " INNER JOIN ops_core.dbo.ops_inventory_property b ON l.property_id= b.inventory_property_id\n" +
            " INNER JOIN ops_core.dbo.ops_warehouse w ON l.warehouse_code = w.warehouse_code \n" +
            " where " +
            " l.voucher_type in " +
            "   <foreach collection = 'voucherType' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "       #{item}" +
            "   </foreach>" +
            " and l.cre_time between #{startTime} and #{endTime} " +
            " AND ( b.inventory_type_code LIKE 'GK%' OR b.inventory_type_code LIKE 'ZL%' OR ( b.inventory_type_code = 'TY' AND w.warehouse_type = 'WT' ) ) " +
            "</script>")
    List<Long> getInventoryIdByTimeAndvoucherType(@Param("voucherType") List<String> voucherType,
                                                    @Param("startTime") String startTime,
                                                    @Param("endTime") String endTime);
}
