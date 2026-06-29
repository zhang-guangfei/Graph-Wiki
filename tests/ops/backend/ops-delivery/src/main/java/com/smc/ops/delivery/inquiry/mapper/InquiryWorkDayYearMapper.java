package com.smc.ops.delivery.inquiry.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.TblWorkdayYear;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2023-12-26
 */
@Mapper
@DS("opsdb")
public interface InquiryWorkDayYearMapper {

    @Select(" <script> select * from Tbl_WorkDay_Year where Workday_date = #{day} </script>")
    TblWorkdayYear getWorkDay(@Param("day") String day);

    @Select(" <script> WITH RankedWorkdays AS ( SELECT [Workday_date], ROW_NUMBER () OVER (ORDER BY Workday_date) AS rn FROM Tbl_WorkDay_Year WHERE Workday_date &gt; #{day}  AND OptFlag_JP = '0' ) \n" +
            " SELECT MIN ([Workday_date]) AS EarliestWorkdayDate FROM RankedWorkdays WHERE rn = #{diff}  </script>")
    Date getJPWorkDayDiff(@Param("day") String day,@Param("diff") int diff);

    @Select(" <script> WITH RankedWorkdays AS ( SELECT [Workday_date], ROW_NUMBER () OVER (ORDER BY Workday_date) AS rn FROM Tbl_WorkDay_Year WHERE Workday_date &gt; #{day}  AND OptFlag = '0' ) \n" +
            " SELECT MIN ([Workday_date]) AS EarliestWorkdayDate FROM RankedWorkdays WHERE rn = #{diff}  </script>")
    Date getCNWorkDayDiff(@Param("day") String day, @Param("diff") int diff);

    @Select(" <script>  select COUNT(1) from Tbl_WorkDay_Year where OptFlag = '0' and  Workday_date &gt; #{startDate} and  Workday_date &lt;= #{endDate} </script>")
    int getCNWorkDayCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
