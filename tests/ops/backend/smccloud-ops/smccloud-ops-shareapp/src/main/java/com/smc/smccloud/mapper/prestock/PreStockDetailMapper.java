package com.smc.smccloud.mapper.prestock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.smc.smccloud.model.prestock.PreStockDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: B90034
 * Date: 2021-10-25 10:19
 * Description:
 */
@DS("sharedb")
@Mapper
public interface PreStockDetailMapper extends BaseMapper<PreStockDetailDO> {

    @Select("select Max(item_no) from prestock_detail where apply_id=#{applyId} and status<>9")
    Integer getMaxItemByApplyId(@Param("applyId") Long applyId);

    @Select("<script> " +
            " select  id from prestock_detail with(nolock) where " +
            " <if test='datas != null and datas.size() &gt; 0' > " +
            "  <foreach collection='datas' item='item' index='index' open='(' separator='or' close=')'> " +
            "   order_nos like CONCAT('%', #{item}, '%') " +
            "  </foreach>" +
            "</if> "+
            "</script>")
    List<Long> getPrestockDetailIDByOrders( @Param("datas") List<String> data);

    @Select("<script> " +
            "   SELECT * FROM  ops_core.dbo.ops_inventory_move WHERE  modelno=#{modelNo}  and delflag=0 and inventory_status='P'  and quantity>prepare_quantity " +
            "AND LEFT(orderNo,2) IN ('99') " +
            " <if test='warehouseS != null and warehouseS.size() &gt; 0' > " +
            "  and warehouse_code in " +
            "  <foreach collection='warehouseS' item='item' index='index' open='(' separator='or' close=')'> " +
            "     #{item}  " +
            "  </foreach>" +
            "</if> "+
            "</script>")
    List<OpsInventoryMove> getOpsInventoryMove(@Param("modelNo") String modelNo,@Param("warehouseS") List<String> warehouseS);
}
