package com.smc.smccloud.mapper.pd;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.model.pd.*;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-06-13
 */
@Mapper
@DS("reportdb")
public interface OpsAsPdBillDataMapper extends BaseMapper<OpsAsPdBillDataDO> {

    // 获取打印到货未入空白票的数据
    @Select("select * from ops_as_pd_bill_data where pd_data_type in (3,4,5) and pd_bill_type = '5' ")
    List<OpsAsPdBillDataDO> findArriveNotIn();

    // 获取打印到货未入空白票的数据
    @Select("select count(*) from ops_as_pd_bill_data where pd_data_type in (3,4,5) and pd_bill_type = '5' ")
    int findArriveNotInCount();

    // 库房名称
    @Select("select d.warehouse_code from ops_as_pd_bill_data d\n" +
            "            left join ops_core.dbo.ops_warehouse w on d.warehouse_code = w.warehouse_code\n" +
            "            where w.delflag = 0\n" +
            "            group by d.warehouse_code order by d.warehouse_code asc")
    List<String> findWarehouseCode();

    @Select("select d.warehouse_code from ops_as_pd_bill_data d \n" +
            "left join ops_core.dbo.ops_warehouse w on d.warehouse_code = w.warehouse_code \n" +
            "where w.delflag = 0 and w.warehouse_type = #{warehouseType}\n" +
            "group by d.warehouse_code order by d.warehouse_code asc")
    List<String> findWarehouseCodeAndType(@Param("warehouseType") String warehouseType);

    @Select("<script> " +
            " select Isnull(sum(bill_qty),'0') from ops_as_pd_bill_data where ISNULL(model_no_1, '') != '' and pd_batch_no = #{pdBatchNo} and del_flag = 0 and warehouse_code = #{code} " +
            "<if test = 'list != null and  list.size() &gt; 0' >" +
            "  and pd_data_type in " +
            " <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            " </foreach>" +
            "</if>"+
            "</script>")
    int findModelQtyCount(@Param("code") String code,@Param("pdBatchNo") String pdBatchNo,@Param("list") List<String> list);

    @Select("<script>" +
            " select isNull(count(model_no_1),'0') from ops_as_pd_bill_data where ISNULL(model_no_1, '') != '' and pd_batch_no = #{pdBatchNo} and del_flag = 0 and warehouse_code = #{code} " +
            "<if test = 'list != null and  list.size() &gt; 0' >" +
            "  and pd_data_type in  " +
            " <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            " </foreach>" +
            "</if>"+
            " </script> ")
    int findModelTypeCount(@Param("code") String code, @Param("pdBatchNo") String pdBatchNo,  @Param("list") List<String> list);

    @Select("<script>" +
            " select isNull(sum(bill_qty),'0') from ops_as_pd_bill_data where ISNULL(model_no_1, '') != '' and pd_batch_no = #{pdBatchNo} and del_flag = 0 and pd_bill_type = 1  and warehouse_code = #{code} " +
            "<if test = 'list != null and  list.size() &gt; 0' >" +
            "  and pd_data_type in " +
            " <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            "  </foreach>" +
            "</if>"+
            "</script>")
    int findXpPdCount(@Param("code") String code,@Param("pdBatchNo") String pdBatchNo, @Param("list") List<String> list);

    @Select("<script>" +
            " select isNull(sum(bill_qty),'0') from ops_as_pd_bill_data where ISNULL(model_no_1, '') !=  '' and pd_batch_no = #{pdBatchNo} and del_flag = 0 and pd_data_type = 2  and warehouse_code = #{code} " +
            "<if test = 'list != null and  list.size() &gt; 0' >" +
            "  and pd_data_type in  " +
            " <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            "  </foreach>" +
            "</if>"+
            "</script>")
    int findGdModelNoCount(@Param("code") String code,@Param("pdBatchNo") String pdBatchNo,  @Param("list") List<String> list);

    @Select("<script>" +
            " select isNull(sum(bill_qty),'0') from ops_as_pd_bill_data where ISNULL(model_no_1, '') != '' and pd_batch_no = #{pdBatchNo} and del_flag = 0 and pd_data_type = 7  and warehouse_code = #{code}" +
            "<if test = 'list != null and  list.size() &gt; 0' >" +
            "  and pd_data_type in  " +
            " <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            "  </foreach>" +
            "</if>"+
            "</script>")
    int findJyModelNoCount(@Param("code") String code,@Param("pdBatchNo") String pdBatchNo,@Param("list") List<String> list);

    @Select("<script>" +
            " select count(*) from (\n" +
            "  select pd_bill_no from  ops_as_pd_bill_data where pd_bill_type = 1 and pd_batch_no = #{pdBatchNo} and del_flag = 0 and warehouse_code = #{code} " +
            "<if test = 'list != null and  list.size() &gt; 0' >" +
            "  and pd_data_type in " +
            " <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            "  </foreach>" +
            "</if> " +
            " GROUP BY pd_bill_no  "+
            ") a " +
            "</script>")
    int creXpBillCount(@Param("code") String code,@Param("pdBatchNo") String pdBatchNo, @Param("list") List<String> list);

    // @Select("select count(*) from  ops_as_pd_bill_data where pd_bill_type = 2 and pd_batch_no = #{pdBatchNo} and del_flag = 0 and warehouse_code = #{code} ")
    @Select("<script>" +
            " select count(*) from (\n" +
            "  select pd_bill_no from  ops_as_pd_bill_data where pd_bill_type = 2 and pd_batch_no = #{pdBatchNo} and del_flag = 0 and warehouse_code = #{code} " +
            "<if test = 'list != null and  list.size() &gt; 0' >" +
            "  and pd_data_type in  " +
            " <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            "  </foreach>" +
            "</if> " +
            " GROUP BY pd_bill_no  "+
            ") a " +
            "</script>")
    int creDataBillCount(@Param("code") String code,@Param("pdBatchNo") String pdBatchNo, @Param("list") List<String> list);

    // @Select("select count(*) from  ops_as_pd_bill_data where pd_bill_type = 3 and pd_batch_no = #{pdBatchNo} and del_flag = 0 and warehouse_code = #{code} ")
    @Select("<script>" +
            " select count(*) from (\n" +
            "  select pd_bill_no from  ops_as_pd_bill_data where pd_bill_type = 3 and pd_batch_no = #{pdBatchNo} and del_flag = 0 and warehouse_code = #{code} " +
            " <if test = 'list != null and  list.size() &gt; 0' >" +
            "  and pd_data_type in  " +
            " <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'> " +
            " #{item} " +
            "  </foreach>" +
            " </if> " +
            " GROUP BY pd_bill_no "+
            ") a " +
            "</script>")
    int creArriveNotInBillCount(@Param("code") String code,@Param("pdBatchNo") String pdBatchNo, @Param("list") List<String> list);





    @Select("<script>" +
            " select * from ops_as_pd_bill_data " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " pd_batch_no = #{batchNo} and " +
            "<if test='dto.pdType != null and dto.pdType != \"\" and dto.pdDataType != null and dto.pdDataType != \"\"  '> " +
            " (pd_bill_type = #{dto.pdType} or pd_data_type = #{dto.pdDataType}) and  " +
            "</if> " +
            "<if test='dto.pdType != null and dto.pdType != \"\" and (dto.pdDataType == null or dto.pdDataType == \"\") '> " +
            " pd_bill_type = #{dto.pdType} and  " +
            "</if> " +
            "<if test = 'dto.warehouseCodes != null and dto.warehouseCodes.size() &gt; 0 ' >" +
            " warehouse_code in " +
            "   <foreach collection = 'dto.warehouseCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "     #{item}" +
            "   </foreach>" +
            " and " +
            "</if>"+
            "<if test='dto.pdBillNo != null and dto.pdBillNo != \"\" '> " +
            " pd_bill_no like #{dto.pdBillNo} and  " +
            "</if> " +
            "<if test='dto.modelNo != null and dto.modelNo != \"\" '> " +
            " model_no_1 like #{dto.modelNo} and  " +
            "</if> " +
            "<if test='dto.pdInputort != null and dto.pdInputort != \"\" '> " +
            " update_user like #{dto.pdInputort} and  " +
            "</if> " +
            "</trim>"+
            "<if test='dto.pdType != null and dto.pdType == \"4\"' >" +
            " order by pd_bill_no asc, CAST(isnull(pd_no,0) AS INT) asc ,id asc " +
            "</if>" +
            "<if test='dto.pdType != \"4\"' >" +
            " order by pd_bill_no asc, CAST(isnull(pd_no,0) AS INT) asc , shelves_no asc , CAST(isnull(pd_sort,0) AS INT) asc, location_no asc,  model_no_1 asc " +
            "</if>" +
            "</script>")
    List<OpsAsPdBillDataDO> listPdBillDataByXpInput(@Param("dto") PdInputRequestVO dto,@Param("batchNo")String batchNo);



    @Select("<script>" +
            " select * from ops_as_pd_bill_data " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " del_flag = 0 and pd_batch_no = #{batchNo} and " +
            "<if test='dto.pdBillType != null and dto.pdBillType != \"\" '> " +
            " pd_bill_type = #{dto.pdBillType} and  " +
            "</if> " +
            "<if test = 'dto.warehouseCodes != null and dto.warehouseCodes.size() &gt; 0 ' >" +
            " warehouse_code in " +
            "   <foreach collection = 'dto.warehouseCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "     #{item}" +
            "   </foreach>" +
            " and " +
            "</if>"+
            "<if test='dto.modelNo != null and dto.modelNo != \"\" '> " +
            " model_no_1 like #{dto.modelNo} and  " +
            "</if> " +
            "<if test='dto.pdBillNo != null and dto.pdBillNo != \"\" '> " +
            " pd_bill_no like #{dto.pdBillNo} and  " +
            "</if> " +
            "<if test='dto.diffType != null and dto.diffType != \"\" '> " +
            // 型号差异:model2有值 且 票据类型为空白票
            "<if test='dto.diffType == \"1\" '> " +
            " ( pd_bill_type in (4,5) and isnull(model_no_2,'') != '' ) and" +
            "</if>"+
            // 数量差异：isnull(qty2,0)<>isnull(账簿数,0)
            "<if test='dto.diffType == \"2\" '> " +
            " isNull(bill_qty, 0) != isNull(pd_qty_2, 0) and" +
            "</if>"+
            "</if> " +
            "<if test='dto.diffTypes != null and dto.diffTypes.size() == 2 '> " +
            " ( (  pd_bill_type in (4,5) and isnull(model_no_2,'') != '' ) or isNull(bill_qty, 0) != isNull(pd_qty_2, 0) )  " +
            "</if> " +
            "</trim>"+
            // "order by pd_bill_no asc, id asc " +
            " order by pd_bill_no asc, CAST(isnull(pd_no,0) AS INT) asc ,shelves_no asc, CAST(isnull(pd_sort,0) AS INT) asc, location_no asc, model_no_1 asc " +
            "</script>")
    List<OpsAsPdBillDataDO> findPdBillDataListWithDiff(@Param("dto")PdDiffDataRequestVO dto,@Param("batchNo") String batchNo);


    @Select("<script>" +
            " select * from ops_as_pd_bill_data " +
            "<trim prefix='WHERE' suffixOverrides='AND |OR' >" +
            " del_flag = 0 and pd_batch_no = #{batchNo} and " +
            "<if test='dto.pdBillType != null and dto.pdBillType != \"\" '> " +
            " pd_bill_type = #{dto.pdBillType} and  " +
            "</if> " +
            "<if test = 'dto.warehouseCodes != null and dto.warehouseCodes.size() &gt; 0 ' >" +
            " warehouse_code in " +
            "   <foreach collection = 'dto.warehouseCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "     #{item}" +
            "   </foreach>" +
            " and " +
            "</if>"+
            "<if test='dto.modelNo != null and dto.modelNo != \"\" '> " +
            " model_no_1 like #{dto.modelNo} and  " +
            "</if> " +
            "<if test='dto.pdBillNo != null and dto.pdBillNo != \"\" '> " +
            " pd_bill_no like #{dto.pdBillNo} and  " +
            "</if> " +
            "<if test='dto.diffType != null and dto.diffType != \"\" '> " +
            // 型号差异:model2有值 且 票据类型为空白票
            "<if test='dto.diffType == \"1\" '> " +
            " ( pd_bill_type in (4,5) and isnull(model_no_2,'') != '' ) and" +
            "</if>"+
            // 数量差异：isnull(qty2,0)<>isnull(账簿数,0)
            "<if test='dto.diffType == \"2\" '> " +
            " isNull(bill_qty, 0) != isNull(pd_qty_2, 0) and" +
            "</if>"+
            "</if> " +
            "<if test='dto.diffTypes != null and dto.diffTypes.size() == 2 '> " +
            " ( (  pd_bill_type in (4,5) and isnull(model_no_2,'') != '' ) or isNull(bill_qty, 0) != isNull(pd_qty_2, 0) )  " +
            "</if> " +
            "</trim>"+
            "order by pd_bill_no asc, id asc " +
            "</script>")
    List<PdDiffBillDataExportVO> findPdBillDataListWithDiffByExport(@Param("dto")PdDiffDataRequestVO dto, @Param("batchNo") String batchNo);


    @Update(" update ops_as_pd_bill_data set model_no_2 = model_no_1, pd_qty_2 = pd_qty_1,update_user = #{optuser}, update_time = getDate()" +
            " where del_flag = 0 and pd_batch_no = #{batchNo} and warehouse_code = #{warehouseCode} ")
    void updateWithExport(@Param("batchNo")String batchNo, @Param("optuser") String optuser,@Param("warehouseCode")String warehouseCode);


    @Update(" update ops_as_pd_bill_data set model_no_2 = model_no_1, pd_qty_2 = pd_qty_1,update_user = #{optuser}, update_time = getDate()" +
            " where del_flag = 0 and pd_batch_no = #{batchNo} and model_no_2 is null ")
    void updateWithExport2(@Param("batchNo")String batchNo, @Param("optuser") String optuser);


    @Select("select count(*) from ops_as_pd_bill_data where (pd_qty_1 is null or model_no_1 is null) and pd_batch_no = #{batchNo} and pd_bill_type not in ('4','5') ")
    int countIsNullPdQty1(@Param("batchNo")String batchNo);

    @Select("select count(*) from ops_as_pd_bill_data where (pd_qty_1 is null or model_no_1 is null) and warehouse_code = #{warehouseCode} and " +
            " pd_batch_no = #{batchNo} and pd_bill_type not in ('4','5') ")
    int countIsNullPdQty1WithWarehouseCode(@Param("warehouseCode") String warehouseCode,@Param("batchNo")String batchNo);

    @Select("select count(*) from ops_as_pd_bill_data where isNull(pd_bill_type,'') = '' ")
    int selectNullBillType();


    // 批量插入
    @Insert("<script>" +
            " insert into [ops_report].[dbo].[ops_as_pd_bill_data] ( " +
            "[pd_batch_no] " +
            ",[warehouse_code] " +
            ",[shelves_no] " +
            ",[location_no] " +
            ",[invoice_no] " +
            ", order_no " +
            ",[case_no] " +
            ",[barcode] " +
            ",[pd_no] " +
            ",[model_no_1] " +
            ",[bill_qty] " +
            ",[pd_qty_1] " +
            ",[pd_inputort_1] " +
            ",[pd_input_time_1] " +
            ",[pd_data_type] " +
            ",[pd_bill_type] " +
            ",[pd_warehouse_type] " +
            ",[del_flag] " +
            ",[create_user] " +
            ",[create_time] " +
            ",[update_user] " +
            ",[update_time] " +
            ", wms_invoice_no " +
            ", pd_sort " +
            ") values" +
            " <foreach collection='list' item='item' index='index'  separator=',' > " +
            " ( " +
            "  #{item.pdBatchNo}, " +
            "  #{item.warehouseCode}, " +
            "  #{item.shelvesNo}, " +
            "  #{item.locationNo}, " +
            "  #{item.invoiceNo}, " +
            "  #{item.orderNo}, " +
            "  #{item.caseNo}, " +
            "  #{item.barcode}, " +
            "  #{item.pdNo}, " +
            "  #{item.modelNo1}, " +
            "  #{item.billQty}, " +
            "  #{item.pdQty1}, " +
            "  #{item.pdInputort1}, " +
            "  #{item.pdInputTime1}, " +
            "  #{item.pdDataType}, " +
            "  #{item.pdBillType}, " +
            "  #{item.pdWarehouseType}, " +
            "  #{item.delFlag}, " +
            "  #{item.createUser}, " +
            "  #{item.createTime}, " +
            "  #{item.updateUser}, " +
            "  #{item.updateTime}, " +
            "  #{item.wmsInvoiceNo}, " +
            "  #{item.pdSort}" +
            " ) " +
            "  </foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void batchInsertPdPillData(@Param("list") List<OpsAsPdBillDataDO> list);


    // 批量插入
    @Insert("<script>" +
            " insert into [ops_report].[dbo].[ops_as_pd_bill_data] ( " +
            "[pd_batch_no] " +
            ",[pd_data_type] " +
            ",[pd_bill_type] " +
            ",[del_flag] " +
            ",[warehouse_code] " +
            ",[pd_warehouse_type] " +
            ",[invoice_no] " +
            ",[create_time] " +
            ",[update_time] " +
            ",[create_user] " +
            ",[update_user] " +
            ",remark " +
            ") values" +
            " <foreach collection='list' item='item' index='index'  separator=',' > " +
            " ( " +
            "  #{item.pdBatchNo}, " +
            "  #{item.pdDataType}, " +
            "  #{item.pdBillType}, " +
            "  #{item.delFlag}, " +
            "  #{item.warehouseCode}, " +
            "  #{item.pdWarehouseType}, " +
            "  #{item.invoiceNo}, " +
            "  #{item.createTime}, " +
            "  #{item.updateTime}, " +
            "  #{item.createUser}, " +
            "  #{item.updateUser}, " +
            "  #{item.remark} " +
            " ) " +
            "  </foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void batchInsertBlackPdBill(@Param("list") List<OpsAsPdBillDataDO> list);

    @Delete(" delete from ops_report.dbo.ops_as_pd_bill_data where pd_batch_no = #{pdNo} ")
    void clearPdBillData(@Param("pdNo") String pdNo);


    @Select("<script>" +
            "select * from ops_report.dbo.ops_as_pd_bill_data " +
            " where pd_batch_no = #{pdBatchNo} and pd_bill_type = #{billType} and warehouse_code = #{warehouseCode} " +
            "<if test='dataType != null and dataType != \"\" ' >" +
            " and pd_data_type = #{dataType}  " +
            "</if>" +
            " order by id asc " +
            "</script>")
    List<OpsAsPdBillDataDO> findDataByBillType(@Param("billType") String billType,
                                               @Param("pdBatchNo") String pdBatchNo,
                                               @Param("warehouseCode") String warehouseCode,
                                               @Param("dataType") String dataType);

    @Select("select count(*) from ops_as_pd_bill_data where ISNULL(warehouse_code, '')  = '' and pd_batch_no = #{pdBatchNo} ")
    int selectWarehouseCodeIsNull(@Param("pdBatchNo") String pdBatchNo);

    @Select("<script> " +
            "select top(15) pd_bill_no from ops_report.dbo.ops_as_pd_bill_data where isnull(pd_bill_no,'') != '' and pd_batch_no = #{pdBatchNo} " +
            "<if test='remotePdBillNoVo.pdBillNo != null and remotePdBillNoVo.pdBillNo != \"\" ' >" +
            " and pd_bill_no like #{remotePdBillNoVo.pdBillNo}  " +
            "</if>" +
            "<if test = 'remotePdBillNoVo.warehouseCodes != null and remotePdBillNoVo.warehouseCodes.size() &gt; 0 ' >" +
            " and warehouse_code in " +
            "   <foreach collection = 'remotePdBillNoVo.warehouseCodes' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "     #{item}" +
            "   </foreach>" +
            "</if>"+
            "group by pd_bill_no order by pd_bill_no asc " +
            "</script>")
    List<OpsAsPdBillDataDO> getTopTenPdBillNoLike(@Param("remotePdBillNoVo") RemotePdBillNoVo remotePdBillNoVo,@Param("pdBatchNo") String pdBatchNo);


    @Select("<script>" +
            "select * from ops_report.dbo.ops_as_pd_bill_data where pd_bill_type = #{billType} and warehouse_code = #{warehouseCode} and pd_batch_no = #{batchNo}" +
            "<if test='billType != null and billType == \"5\" ' > " +
            " and remark= '到货未入空白票' " +
            "</if>" +
            " order by pd_bill_no asc, CAST(isnull(pd_no,0) AS INT) asc , shelves_no asc , CAST(isnull(pd_sort,0) AS INT) asc, location_no asc,  model_no_1 asc " +
            "</script>")
    List<OpsAsPdBillDataDO> findPdBillDataByBillType(@Param("billType") String billType,@Param("warehouseCode") String warehouseCode, @Param("batchNo")String batchNo);


    // 导出货架号
    @Select("select warehouse_code,model_no_1,bill_qty,CONCAt(shelves_no,location_no) as shelvesNo from ops_as_pd_bill_data where pd_batch_no = #{pdBatchNo} and pd_data_type = '1' " +
            "order by warehouse_code asc, pd_sort asc ,shelves_no asc, model_no_1 asc")
    List<ExportShelvesVO> exportShelves(@Param("pdBatchNo") String pdBatchNo);

}
