package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.Inventory.InventorySupplierDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-01-25
 */
@Mapper
@DS("opsdb")
public interface InventorySupplierMapper extends BaseMapper<InventorySupplierDO> {

}
