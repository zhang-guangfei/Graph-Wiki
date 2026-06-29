package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.ops.delivery.model.poImps.OpsPoDeliveryInvoiceLogFromSupplierDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-02-22
 */
@Mapper
@DS("poadapterdb")
public interface OpsPoDeliveryInvoiceLogFromSupplierMapper extends BaseMapper<OpsPoDeliveryInvoiceLogFromSupplierDO> {

}
