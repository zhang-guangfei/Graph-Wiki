package com.sales.ops.db.dao;

import com.sales.ops.db.entity.RcvdetailGz;
import com.sales.ops.db.entity.RcvdetailGzExample;
import com.sales.ops.db.entity.RcvdetailGzWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RcvdetailGzMapper {
    long countByExample(RcvdetailGzExample example);

    int deleteByExample(RcvdetailGzExample example);

    int insert(RcvdetailGzWithBLOBs record);

    int insertSelective(RcvdetailGzWithBLOBs record);

    List<RcvdetailGzWithBLOBs> selectByExampleWithBLOBs(RcvdetailGzExample example);

    List<RcvdetailGz> selectByExample(RcvdetailGzExample example);

    int updateByExampleSelective(@Param("record") RcvdetailGzWithBLOBs record, @Param("example") RcvdetailGzExample example);

    int updateByExampleWithBLOBs(@Param("record") RcvdetailGzWithBLOBs record, @Param("example") RcvdetailGzExample example);

    int updateByExample(@Param("record") RcvdetailGz record, @Param("example") RcvdetailGzExample example);
}