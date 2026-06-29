package com.sales.ops.db.dao;

import com.sales.ops.db.entity.BinOrderCalc;
import com.sales.ops.db.entity.BinOrderCalcExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BinOrderCalcMapper {
    long countByExample(BinOrderCalcExample example);

    int deleteByExample(BinOrderCalcExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BinOrderCalc record);

    int insertSelective(BinOrderCalc record);

    List<BinOrderCalc> selectByExample(BinOrderCalcExample example);

    BinOrderCalc selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BinOrderCalc record, @Param("example") BinOrderCalcExample example);

    int updateByExample(@Param("record") BinOrderCalc record, @Param("example") BinOrderCalcExample example);

    int updateByPrimaryKeySelective(BinOrderCalc record);

    int updateByPrimaryKey(BinOrderCalc record);
}