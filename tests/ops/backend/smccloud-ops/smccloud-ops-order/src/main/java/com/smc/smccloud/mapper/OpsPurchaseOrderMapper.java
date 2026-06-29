package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.yulichang.base.MPJBaseMapper;
import com.smc.smccloud.model.Purchase.OpsPurchaseOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-03-17
 */
@Mapper
@DS("opsdb")
public interface OpsPurchaseOrderMapper extends MPJBaseMapper<OpsPurchaseOrderDO> {

}
