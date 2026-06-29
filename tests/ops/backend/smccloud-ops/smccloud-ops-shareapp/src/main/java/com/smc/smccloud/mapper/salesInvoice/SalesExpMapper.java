package com.smc.smccloud.mapper.salesInvoice;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.salesinvoice.SalesExpDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-07-19
 */
@Mapper
@DS("costdb")
public interface SalesExpMapper extends BaseMapper<SalesExpDO> {

}
