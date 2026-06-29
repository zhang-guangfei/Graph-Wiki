package com.smc.smccloud.mapper.csstock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.csstock.CsBalanceCalcMasterDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.Date;

/**
 * @author edp04
 * @title: CsBalanceCalcMasterMapper
 * @date 2022/06/14 09:57
 */
@Mapper
@DS("sharedb")
public interface CsBalanceCalcMasterMapper extends BaseMapper<CsBalanceCalcMasterDO> {

    @Select("{call ops_sharedb.dbo.csBalance_calc(#{monthDate,jdbcType=TIMESTAMP,mode=IN}," +
            "#{fromDate,jdbcType=TIMESTAMP,mode=IN},#{toDate,jdbcType=TIMESTAMP,mode=IN})}")
    @Options(statementType = StatementType.CALLABLE)
    void csBalanceCalc(@Param("monthDate")Date monthDate,@Param("fromDate")Date fromDate,@Param("toDate") Date toDate);

    @Select("SELECT TOP 1 toTime FROM cs_balance_master ORDER BY toTime DESC")
    Date getLastBanceDate();
}
