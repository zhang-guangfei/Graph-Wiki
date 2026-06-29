package com.smc.smccloud.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.Adapter.TblWorkdayYearDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-03-16
 */
@DS("opsdb")
@Mapper
public interface TblWorkdayYearMapper extends BaseMapper<TblWorkdayYearDO> {

    // 计算有效货期
    @Select("select case when (select OptFlag from Tbl_WorkDay_Year where " +
            " Workday_date='${deliveryDate}')='0' then '${deliveryDate}' else (select " +
            " top 1 " +
            " Workday_date from Tbl_WorkDay_Year where Workday_date < '${deliveryDate}' " +
            " and OptFlag='0' order by Workday_date DESC) end as deliveryDate")
    Date calDlvDate(@Param("deliveryDate") String deliveryDate);


    @Update("UPDATE Tbl_WorkDay_Year\n" +
            "SET Optdate = GETDATE(), OptFlag_AM = CASE\n" +
            "    WHEN smcdb.dateType = '工作日' THEN 0\n" +
            "    ELSE 1\n" +
            "END\n" +
            "FROM Tbl_WorkDay_Year ops\n" +
            "INNER JOIN [10.116.0.22].smcdb80.dbo.SHR_SaleDate smcdb\n" +
            "ON CONVERT(varchar, ops.Workday_date, 112) = CONVERT(varchar, smcdb.Workdate, 112)")
    void syncWorkDay();

}
