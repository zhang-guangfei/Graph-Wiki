package com.sales.ops.db.extdao;

import com.sales.ops.dto.po.PoOriginConfigDto;
import com.sales.ops.dto.po.core.PoCalCoreDto;
import com.sales.ops.dto.po.core.PoTransTypeCoreDto;
import com.sales.ops.dto.po.core.TransTypeDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/4/16 14:46
 */
public interface PoConfigDao {

    @Select("SELECT top 1 * from ops_core.dbo.ops_po_origin_config  WHERE model_no = #{modelNo} and delfalg =0")
    public PoOriginConfigDto getPoOriginConfig(@Param("modelNo") String modelNo);

    // START 20241215
    // 获取运输方式配置表
    @Select("SELECT id as trans_id,name as trans_name from ops_po_transtype")
    public List<TransTypeDto> getPoTransType();

    // 仓库和供应商获取运输方式
    @Select("SELECT DISTINCT trans_id from ops_po_cal_core_config where delflag =0 and supplier =#{supplierId} AND warehouse =#{warehouse}")
    public List<String> getPoCalCoreTransIds(@Param("warehouse") String warehouse, @Param("supplierId") String supplierId);

    // 仓库和供应商获取运输方式
    @Select("SELECT DISTINCT trans_id,usage_type from ops_po_cal_core_config where delflag =0 and supplier =#{supplierId} AND warehouse =#{warehouse}")
    public List<TransTypeDto> getPoCalCoreTrans(@Param("warehouse") String warehouse, @Param("supplierId") String supplierId);

    @Select("SELECT DISTINCT trans_id,usage_type from ops_po_cal_core_config where delflag =0 and warehouse =#{warehouse}")
    public List<TransTypeDto> getPoCalCoreTransByWarehouse(@Param("warehouse") String warehouse);

    @Select("SELECT DISTINCT trans_id,usage_type from ops_po_cal_core_config where delflag =0 and supplier =#{supplierId}")
    public List<TransTypeDto> getPoCalCoreTransBySupplier( @Param("supplierId") String supplierId);

    @Select("SELECT DISTINCT trans_id,usage_type from ops_po_cal_core_config where delflag =0")
    public List<TransTypeDto> getPoCalCoreTransAll();
    // 特殊尺寸
    @Select("SELECT nonstandard_sized_product from product where  is_deleted =0 and ModelNo = #{modelNo}")
    public String getPorductNonStandard(@Param("modelNo") String modelNo);

    // 获取温控器
    @Select("SELECT InspectionsGroupId  FROM ops_sharedb.dbo.MDM_V_InspectionsGroup where  Model = #{modelNo} and  InspectionsGroupId =1")
    public String getInspectionsGroup(@Param("modelNo") String modelNo);

    @Select("SELECT model_no ,trans_id ,compare_greater ,compare_quantity ,usage_type from  ops_po_transtype_core_config " +
            "where  delflag =0 and match_type=0  and model_no =  #{modelNo} ")
    public List<PoTransTypeCoreDto> getTransTypeCore(@Param("modelNo") String modelNo);

    @Select("SELECT model_no ,trans_id ,compare_greater ,compare_quantity ,usage_type from  ops_po_transtype_core_config " +
            "where  delflag =0 and match_type=1")
    public List<PoTransTypeCoreDto> getTransTypeMatchCore();

    //无出库区分无供应商产地的判断
    @Select("<script> SELECT * from ops_po_cal_core_config  WHERE supplier  = #{supplierId} and warehouse = #{warehouse} " +
            "and enable_inventory = #{outStock}   and po_origin ='NON' and inventory_class ='NON'  and delflag=0 AND usage_type =0  " +
            "<if test='transTypeList != null and transTypeList.size() > 0'>"+
            "  AND trans_id IN"+
            "  <foreach collection='transTypeList' item='item' open='(' separator=',' close=')'>"+
            "    #{item.transId}"+
            "  </foreach>"+
            "</if>"+
            " ORDER BY priority  </script>")
    List<PoCalCoreDto> getPoCalCoreByOutStock(@Param("warehouse") String warehouse, @Param("supplierId") String supplierId
            , @Param("outStock") boolean outStock,@Param("transTypeList") List<TransTypeDto> transTypeList);

    //供应商出库区分（JP: WH F1 F2） 先判断库存，注意顺序
    @Select("<script> SELECT * from ops_po_cal_core_config  WHERE supplier  = #{supplierId} and warehouse = #{warehouse} and po_origin = #{poOrigin} " +
            "and delflag=0 AND usage_type =0  " +
            "<if test='transTypeList != null and transTypeList.size() > 0'>"+
            "  AND trans_id IN"+
            "  <foreach collection='transTypeList' item='item' open='(' separator=',' close=')'>"+
            "    #{item.transId}"+
            "  </foreach>"+
            "</if>"+
            " ORDER BY priority  </script>")
    List<PoCalCoreDto> getPoCalCoreByOrigin(@Param("warehouse") String warehouse, @Param("supplierId") String supplierId
            , @Param("poOrigin") String poOrigin,@Param("transTypeList") List<TransTypeDto> transTypeList);

    //供应商产地（JP:VN IN ）
    @Select("<script> SELECT * from ops_po_cal_core_config  WHERE supplier  = #{supplierId} and warehouse = #{warehouse} " +
            "and inventory_class = #{inventoryClass} and delflag=0 AND usage_type =0" +
            "<if test='transTypeList != null and transTypeList.size() > 0'>"+
            "  AND trans_id IN"+
            "  <foreach collection='transTypeList' item='item' open='(' separator=',' close=')'>"+
            "    #{item.transId}"+
            "  </foreach>"+
            "</if>"+
            " ORDER BY priority </script>")
    List<PoCalCoreDto> getPoCalCoreByInvClass(@Param("warehouse") String warehouse, @Param("supplierId") String supplierId
            , @Param("inventoryClass") String inventoryClass ,@Param("transTypeList") List<TransTypeDto> transTypeList);


    @Select("select trans_day  from ops_core.dbo.ops_po_cal_core_config  where warehouse=#{warehouse} and supplier=#{supplierId}" +
            " and enable_inventory =0 and trans_id = #{transId} and usage_type =0 and delflag =0")
    List<PoCalCoreDto> getTransDays(@Param("warehouse") String warehouse, @Param("supplierId") String supplierId
            , @Param("transId") String transId);


}
