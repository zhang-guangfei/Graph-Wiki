package com.sales.ops.db.dao;

import com.sales.ops.db.entity.BinOrderApp;
import com.sales.ops.db.entity.BinOrderAppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BinOrderAppMapper {
    long countByExample(BinOrderAppExample example);

    int deleteByExample(BinOrderAppExample example);

    int deleteByPrimaryKey(Long appId);

    int insert(BinOrderApp record);

    int insertSelective(BinOrderApp record);

    List<BinOrderApp> selectByExample(BinOrderAppExample example);

    BinOrderApp selectByPrimaryKey(Long appId);

    int updateByExampleSelective(@Param("record") BinOrderApp record, @Param("example") BinOrderAppExample example);

    int updateByExample(@Param("record") BinOrderApp record, @Param("example") BinOrderAppExample example);

    int updateByPrimaryKeySelective(BinOrderApp record);

    int updateByPrimaryKey(BinOrderApp record);
}