package com.sales.ops.db.dao;

import com.sales.ops.db.entity.TempDoconfirm;
import com.sales.ops.db.entity.TempDoconfirmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TempDoconfirmMapper {
    long countByExample(TempDoconfirmExample example);

    int deleteByExample(TempDoconfirmExample example);

    int insert(TempDoconfirm record);

    int insertSelective(TempDoconfirm record);

    List<TempDoconfirm> selectByExample(TempDoconfirmExample example);

    int updateByExampleSelective(@Param("record") TempDoconfirm record, @Param("example") TempDoconfirmExample example);

    int updateByExample(@Param("record") TempDoconfirm record, @Param("example") TempDoconfirmExample example);
}