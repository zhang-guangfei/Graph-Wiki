package com.sales.ops.db.dao;

import com.sales.ops.db.entity.RcvmaseterGz;
import com.sales.ops.db.entity.RcvmaseterGzExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RcvmaseterGzMapper {
    long countByExample(RcvmaseterGzExample example);

    int deleteByExample(RcvmaseterGzExample example);

    int insert(RcvmaseterGz record);

    int insertSelective(RcvmaseterGz record);

    List<RcvmaseterGz> selectByExampleWithBLOBs(RcvmaseterGzExample example);

    List<RcvmaseterGz> selectByExample(RcvmaseterGzExample example);

    int updateByExampleSelective(@Param("record") RcvmaseterGz record, @Param("example") RcvmaseterGzExample example);

    int updateByExampleWithBLOBs(@Param("record") RcvmaseterGz record, @Param("example") RcvmaseterGzExample example);

    int updateByExample(@Param("record") RcvmaseterGz record, @Param("example") RcvmaseterGzExample example);
}