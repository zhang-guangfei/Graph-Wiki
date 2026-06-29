package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.ImpDataDO;
import com.smc.smccloud.model.invoice.ImpInvoiceDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 *  * Author: B2829
 *  * Date: 2021-12-13 13:59
 *  * Description:
 */
@Mapper
public interface ImpdataMapper extends BaseMapper<ImpDataDO> {
}
