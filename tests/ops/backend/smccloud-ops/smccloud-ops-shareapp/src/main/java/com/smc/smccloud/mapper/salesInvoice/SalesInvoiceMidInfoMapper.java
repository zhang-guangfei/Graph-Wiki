package com.smc.smccloud.mapper.salesInvoice;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.salesinvoice.SalesInvoiceMidInfoDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("opsdb")
public interface SalesInvoiceMidInfoMapper extends BaseMapper<SalesInvoiceMidInfoDO> {

}
