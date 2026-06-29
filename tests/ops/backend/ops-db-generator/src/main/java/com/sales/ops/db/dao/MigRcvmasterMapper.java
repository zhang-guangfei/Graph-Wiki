package com.sales.ops.db.dao;

import com.sales.ops.db.entity.MigRcvmaster;
import com.sales.ops.db.entity.MigRcvmasterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MigRcvmasterMapper {
    long countByExample(MigRcvmasterExample example);

    int deleteByExample(MigRcvmasterExample example);

    int insert(MigRcvmaster record);

    int insertSelective(MigRcvmaster record);

    List<MigRcvmaster> selectByExample(MigRcvmasterExample example);

    int updateByExampleSelective(@Param("record") MigRcvmaster record, @Param("example") MigRcvmasterExample example);

    int updateByExample(@Param("record") MigRcvmaster record, @Param("example") MigRcvmasterExample example);
}