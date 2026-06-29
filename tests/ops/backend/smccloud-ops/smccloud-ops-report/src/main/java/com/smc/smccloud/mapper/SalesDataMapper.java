package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.SalesDataDO;
import com.smc.smccloud.model.salesData.EDisvStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-10-28
 */
@Mapper
@DS("smcdb30")
public interface SalesDataMapper extends BaseMapper<SalesDataDO> {


    // 当月
    @Select("select TradeCompanyId,SUM(ntaxamount) as ntaxAmount,SUM(EPRMBAmount) as EAmount,SUM(BNSAmount) as BNSAmount " +
            "from SalesData " +
            "where CONVERT(varchar(7),optdate,121)=#{date} " +
            "group by TradeCompanyId")
    List<EDisvStatisticsVO> getCurMonthDisReport(@Param("date") String date);

    // 当年
    @Select("select TradeCompanyId,SUM(ntaxamount) as ntaxAmount,SUM(EPRMBAmount) as EAmount,SUM(BNSAmount) as BNSAmount " +
            "from SalesData " +
            "where optdate>=#{startDate} and OptDate<#{endDate} " +
            "group by TradeCompanyId")
    List<EDisvStatisticsVO> getCurYearDisReport(@Param("startDate") String startDate,@Param("endDate")String endDate);

    // 当月- 修改前E率
    @Select("select TradeCompanyId,SUM(a.Quantity*isnull(b.EPrice_base_lot,a.EPriceRMB)) as EAmount " +
            "from SalesData a left join Salesdata_modidata b on b.RorderNO=a.RorderNo " +
            "where CONVERT(varchar(7),optdate,121)=#{date} " +
            "group by TradeCompanyId")
    List<EDisvStatisticsVO> getCurMonthBeforeUpDisReport(@Param("date") String date);

    // 当年-修改前E率
    @Select("select TradeCompanyId,SUM(a.Quantity*isnull(b.EPrice_base_lot,a.EPriceRMB)) as EAmount " +
            "from SalesData a left join Salesdata_modidata b on b.RorderNO=a.RorderNo " +
            "where optdate>=#{startDate} and OptDate<#{endDate} " +
            "group by TradeCompanyId")
    List<EDisvStatisticsVO> getCurYearBeforeUpDisReport(@Param("startDate") String startDate,@Param("endDate")String endDate);
}
