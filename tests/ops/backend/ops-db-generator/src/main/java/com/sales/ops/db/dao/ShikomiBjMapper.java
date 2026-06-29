package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ShikomiBj;
import com.sales.ops.db.entity.ShikomiBjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShikomiBjMapper {
    long countByExample(ShikomiBjExample example);

    int deleteByExample(ShikomiBjExample example);

    int insert(ShikomiBj record);

    int insertSelective(ShikomiBj record);

    List<ShikomiBj> selectByExample(ShikomiBjExample example);

    int updateByExampleSelective(@Param("record") ShikomiBj record, @Param("example") ShikomiBjExample example);

    int updateByExample(@Param("record") ShikomiBj record, @Param("example") ShikomiBjExample example);
}