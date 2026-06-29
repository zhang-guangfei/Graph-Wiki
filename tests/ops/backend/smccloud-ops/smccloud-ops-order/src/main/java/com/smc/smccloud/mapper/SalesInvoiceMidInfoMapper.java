package com.smc.smccloud.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.invoice.SalesInvoiceMidInfoDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("opsdb")
public interface SalesInvoiceMidInfoMapper extends BaseMapper<SalesInvoiceMidInfoDO> {

}
