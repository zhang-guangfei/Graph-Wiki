package com.sales.ops.db.dao;

import com.sales.ops.db.entity.HrHolon;
import com.sales.ops.db.entity.HrHolonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HrHolonMapper {
    long countByExample(HrHolonExample example);

    int deleteByExample(HrHolonExample example);

    int insert(HrHolon record);

    int insertSelective(HrHolon record);

    List<HrHolon> selectByExample(HrHolonExample example);

    int updateByExampleSelective(@Param("record") HrHolon record, @Param("example") HrHolonExample example);

    int updateByExample(@Param("record") HrHolon record, @Param("example") HrHolonExample example);
}