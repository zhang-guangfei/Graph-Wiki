package com.smc.smccloud.mapper.stockassembly;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.stockassembly.StockAssemblyDetailDO;
import com.smc.smccloud.model.stockassembly.StockAssemblyDetailProperty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: B90034
 * Date: 2021-09-27 12:48
 * Description:
 */
@DS("sharedb")
@Mapper
public interface StockAssemblyDetailMapper extends BaseMapper<StockAssemblyDetailDO> {


    @Select("SELECT company_id ,quantity ,property_quantity  from stock_assembly_detail_property where del_flag =0 and detail_id = #{detailId}")
    List<StockAssemblyDetailProperty> queryStockAssemblyDetailProperty(@Param("detailId") Long detailId);

    @Select("SELECT count(id) from stock_assembly_detail where ApplyId = #{applyId} and branch_flag =1")
    Integer countStockAssemblyDetail(@Param("applyId") Long applyId);


    @DS("opsdb")
    @Select("select Top(1) inventory_id from ops_core.dbo.ops_inventory where modelno=#{modelNo} and warehouse_code=#{warehouseCode} and inventory_property_id=#{inventoryPropertyId}")
    Long getInventoryId(@Param("modelNo") String modelNo, @Param("warehouseCode") String warehouseCode, @Param("inventoryPropertyId") Long inventoryPropertyId);

     @Select("<script> " +
            "  INSERT INTO stock_assembly_detail " +
             "   (ApplyId, ModelNo, IsTransOut, Quantity, optCode, inventory_keys, warehouseCode, Remark, create_time, update_time, create_user, update_user )" +
             "    values" +
            " <if test='detailList !=null and detailList.size() &gt; 0'>" +
            "  <foreach collection='detailList' item='item' index='index'  separator=',' > " +
            "   ( #{item.applyId,jdbcType=BIGINT},#{item.modelNo,jdbcType=VARCHAR},#{item.isTransOut,jdbcType=BIT}," +
             "   #{item.quantity,jdbcType=DECIMAL},#{item.optCode,jdbcType=SMALLINT},#{item.inventoryKeys,jdbcType=VARCHAR}," +
             "   #{item.warehouseCode,jdbcType=VARCHAR},#{item.remark,jdbcType=VARCHAR}," +
             "   #{item.createTime,jdbcType=TIMESTAMP},#{item.updateTime,jdbcType=TIMESTAMP}," +
             "   #{item.createUser,jdbcType=VARCHAR},#{item.updateUser,jdbcType=VARCHAR} " +
             "    ) " +
            "  </foreach>" +
            " </if>" +
            "</script>")
    Integer insertBatch(@Param("detailList")  List<StockAssemblyDetailDO> detailList);
}
