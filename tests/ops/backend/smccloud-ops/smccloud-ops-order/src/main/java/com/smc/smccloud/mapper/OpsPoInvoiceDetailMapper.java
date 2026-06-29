package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.PoInvoiceDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author edp04
 * @title: OpsPoInvoiceDetailMapper
 * @date 2022/07/16 18:59
 */
@Mapper
@DS("opsdb")
public interface OpsPoInvoiceDetailMapper extends BaseMapper<PoInvoiceDetailDO> {
}
