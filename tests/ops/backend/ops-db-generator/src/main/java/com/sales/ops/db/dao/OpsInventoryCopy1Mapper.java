package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryCopy1;
import com.sales.ops.db.entity.OpsInventoryCopy1Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryCopy1Mapper {
    long countByExample(OpsInventoryCopy1Example example);

    int deleteByExample(OpsInventoryCopy1Example example);

    int deleteByPrimaryKey(Long inventoryId);

    int insert(OpsInventoryCopy1 record);

    int insertSelective(OpsInventoryCopy1 record);

    List<OpsInventoryCopy1> selectByExample(OpsInventoryCopy1Example example);

    OpsInventoryCopy1 selectByPrimaryKey(Long inventoryId);

    int updateByExampleSelective(@Param("record") OpsInventoryCopy1 record, @Param("example") OpsInventoryCopy1Example example);

    int updateByExample(@Param("record") OpsInventoryCopy1 record, @Param("example") OpsInventoryCopy1Example example);

    int updateByPrimaryKeySelective(OpsInventoryCopy1 record);

    int updateByPrimaryKey(OpsInventoryCopy1 record);
}