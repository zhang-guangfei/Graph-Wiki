package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.inventory.InventorySupplierDO;
import com.smc.smccloud.model.inventory.InventoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-01-25
 */
@Mapper
@DS("opsdb")
public interface InventorySupplierMapper extends BaseMapper<InventorySupplierDO> {

    /**
     * 查询型号的可订货（可用）供应商库存（bug-11913）
     *
     * @param modelNos 型号列表
     * @return 型号的可订货（可用）供应商库存
     */
    @Select("<script>" +
            " select t1.modelNo, SUM(ISNULL(quantity,0) - ISNULL(quantityPrepare,0)) as avaQty " +
            " from inventory_supplier t1 with(nolock) inner join product_delivery t2 with(nolock) " +
            " on t1.modelNo=t2.ModelNo and t1.supplierId=t2.supplyId " +
            " where t1.modelNo in " +
            " <foreach collection='modelNos' item='item' index='index' open='(' separator=',' close=')'> " +
            "    #{item} " +
            " </foreach> " +
            " group by t1.modelNo" +
            "</script>")
    List<InventoryVO> listModelSupplierCanOrderQty(@Param("modelNos") List<String> modelNos);

}
