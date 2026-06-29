package com.smc.smccloud.mapper.prestock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.smc.smccloud.model.inventory.OpsInventoryPropertyVO;
import com.smc.smccloud.model.prestock.PreStockApplyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: B90034
 * Date: 2021-10-25 10:18
 * Description:
 */
@DS("sharedb")
@Mapper
public interface PreStockApplyMapper extends BaseMapper<PreStockApplyDO> {

    @Select("select   *From ops_core.dbo.ops_inventory_property where inventory_type_code=#{inventoryTypeCode}  and customer_no=#{customerNo} and delflag=0 ")
    List<OpsInventoryPropertyVO> getOpsInventoryProperty(@Param("inventoryTypeCode") String inventoryTypeCode, @Param("customerNo") String CustomerNo);

}
