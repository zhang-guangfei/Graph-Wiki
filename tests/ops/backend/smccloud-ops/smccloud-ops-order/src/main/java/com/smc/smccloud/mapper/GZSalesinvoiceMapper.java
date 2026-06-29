package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.GZSalesinvoiceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author edp02 @Date 2022/6/7 15:25
 */
@Mapper
@DS("gzdb")
public interface GZSalesinvoiceMapper extends BaseMapper<GZSalesinvoiceDO> {

}
