package com.sales.ops.db.dao;

import com.sales.ops.db.entity.GzCustdlvdata;
import com.sales.ops.db.entity.GzCustdlvdataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GzCustdlvdataMapper {
    long countByExample(GzCustdlvdataExample example);

    int deleteByExample(GzCustdlvdataExample example);

    int insert(GzCustdlvdata record);

    int insertSelective(GzCustdlvdata record);

    List<GzCustdlvdata> selectByExample(GzCustdlvdataExample example);

    int updateByExampleSelective(@Param("record") GzCustdlvdata record, @Param("example") GzCustdlvdataExample example);

    int updateByExample(@Param("record") GzCustdlvdata record, @Param("example") GzCustdlvdataExample example);
}