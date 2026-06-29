package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsDrawupinventory;
import com.sales.ops.db.entity.OpsDrawupinventoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsDrawupinventoryMapper {
    long countByExample(OpsDrawupinventoryExample example);

    int deleteByExample(OpsDrawupinventoryExample example);

    int insert(OpsDrawupinventory record);

    int insertSelective(OpsDrawupinventory record);

    List<OpsDrawupinventory> selectByExample(OpsDrawupinventoryExample example);

    int updateByExampleSelective(@Param("record") OpsDrawupinventory record, @Param("example") OpsDrawupinventoryExample example);

    int updateByExample(@Param("record") OpsDrawupinventory record, @Param("example") OpsDrawupinventoryExample example);
}