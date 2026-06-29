package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.Manufacture.OpsShareTModelAvailableToSalesDO;
import com.smc.smccloud.model.Manufacture.OpsTModelAvailableToSales;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author edp04
 * @title: OpsShareTModelAvailableToSales
 * @date 2022/04/28 12:08
 */
@Mapper
@DS("sharedb")
public interface OpsShareTModelAvailableToSales extends BaseMapper<OpsShareTModelAvailableToSalesDO> {
}
