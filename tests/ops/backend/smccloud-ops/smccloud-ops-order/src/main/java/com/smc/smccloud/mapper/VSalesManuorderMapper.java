package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.VSalesManuorder.VSalesManuorderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-02-11
 */

@DS("cmdb")
@Mapper
public interface VSalesManuorderMapper extends BaseMapper<VSalesManuorderDO> {

    @Select("select top(1000)* from OPS_V_ManuOrderToSales where insertDate > '2022-05-17' ")
    List<VSalesManuorderDO> findNoRcvOrderData();

    /**
     *  从关务系统查询每月汇率
     * @param currencyId
     * @param monthBeginDate
     * @param monthEndDate
     * @return
     */
    @Select("select  top 1 rate " +
            " FROM [OPS_V_ExchangeRateToSales] " +
            " where currencyID=#{currencyID} and changeDate>=#{monthBegin} and changedate<=#{monthEndDate}\n" +
            " order by changeDate desc")
    BigDecimal getExchangeRate(@Param("currencyID") String currencyId,
                               @Param("monthBegin")Date monthBeginDate,@Param("monthEndDate") Date monthEndDate);

}
