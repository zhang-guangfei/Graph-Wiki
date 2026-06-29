package com.smc.smccloud.mapper.salesInvoice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.salesinvoice.SalesInvoiceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-02-05
 */

@Mapper
@DS("opsdb")
public interface SalesInvoiceMapper extends BaseMapper<SalesInvoiceDO> {

}
