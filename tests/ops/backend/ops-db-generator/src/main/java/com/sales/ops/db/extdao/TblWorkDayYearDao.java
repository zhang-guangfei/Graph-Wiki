package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.TblWorkdayYear;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：工作日表
 * @date ：Created in 2023/6/21 13:53
 */
public interface TblWorkDayYearDao {

    // 13794 c14717 20240729 切换为自动化字段
    //计算给定日期（如果不是工作日，设置工作日）+2个工作日
    @Select("select top 3 Workday_date from Tbl_WorkDay_Year where OptFlag_AM =0 and Workday_date >= #{today} order by Workday_date asc ")
    List<TblWorkdayYear> getWorkDay(@Param("today") String today);

    @Select("select top ${days} Workday_date from Tbl_WorkDay_Year where OptFlag_AM =0 and Workday_date >= #{today} order by Workday_date asc ")
    List<TblWorkdayYear> getAddDaysWorkDay(@Param("today") String today ,@Param("days") Integer days  );

    @Select("select top ${days} Workday_date from Tbl_WorkDay_Year where OptFlag_AM =0 and Workday_date <= #{today} order by Workday_date desc ")
    List<TblWorkdayYear> getSubDaysWorkDay(@Param("today") String today ,@Param("days") Integer days  );


    @Select("select top ${days} Workday_date from Tbl_WorkDay_Year where OptFlag_JP =0 and Workday_date >= #{today} order by Workday_date asc ")
    List<TblWorkdayYear> getAddDaysJPWorkDay(@Param("today") String today ,@Param("days") Integer days );

    @Select("select top ${days} Workday_date from Tbl_WorkDay_Year where OptFlag_JP =0 and Workday_date <= #{today} order by Workday_date desc ")
    List<TblWorkdayYear> getSubDaysJpWorkDay(@Param("today") String today ,@Param("days") Integer days );

    @Select("select top ${days} Workday_date from Tbl_WorkDay_Year where OptFlag =0 and Workday_date >= #{today} order by Workday_date asc ")
    List<TblWorkdayYear> getAddDaysCNWorkDay(@Param("today") String today ,@Param("days") Integer days );

    @Select("select top ${days} Workday_date from Tbl_WorkDay_Year where OptFlag =0 and Workday_date <= #{today} order by Workday_date desc ")
    List<TblWorkdayYear> getSubDaysCNWorkDay(@Param("today") String today ,@Param("days") Integer days );
}
