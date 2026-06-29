package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TblWorkdayYear;
import com.sales.ops.db.entity.TblWorkdayYearExample;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblWorkdayYearMapper {
    long countByExample(TblWorkdayYearExample example);

    int deleteByExample(TblWorkdayYearExample example);

    int deleteByPrimaryKey(Date workdayDate);

    int insert(TblWorkdayYear record);

    int insertSelective(TblWorkdayYear record);

    List<TblWorkdayYear> selectByExample(TblWorkdayYearExample example);

    TblWorkdayYear selectByPrimaryKey(Date workdayDate);

    int updateByExampleSelective(@Param("record") TblWorkdayYear record, @Param("example") TblWorkdayYearExample example);

    int updateByExample(@Param("record") TblWorkdayYear record, @Param("example") TblWorkdayYearExample example);

    int updateByPrimaryKeySelective(TblWorkdayYear record);

    int updateByPrimaryKey(TblWorkdayYear record);
}