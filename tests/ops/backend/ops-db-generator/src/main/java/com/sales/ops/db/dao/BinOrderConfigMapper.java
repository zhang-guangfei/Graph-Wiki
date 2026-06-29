package com.sales.ops.db.dao;

import com.sales.ops.db.entity.BinOrderConfig;
import com.sales.ops.db.entity.BinOrderConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BinOrderConfigMapper {
    long countByExample(BinOrderConfigExample example);

    int deleteByExample(BinOrderConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BinOrderConfig record);

    int insertSelective(BinOrderConfig record);

    List<BinOrderConfig> selectByExample(BinOrderConfigExample example);

    BinOrderConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BinOrderConfig record, @Param("example") BinOrderConfigExample example);

    int updateByExample(@Param("record") BinOrderConfig record, @Param("example") BinOrderConfigExample example);

    int updateByPrimaryKeySelective(BinOrderConfig record);

    int updateByPrimaryKey(BinOrderConfig record);
}