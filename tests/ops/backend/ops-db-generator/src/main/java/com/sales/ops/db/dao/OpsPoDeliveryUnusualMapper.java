package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoDeliveryUnusual;
import com.sales.ops.db.entity.OpsPoDeliveryUnusualExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoDeliveryUnusualMapper {
    long countByExample(OpsPoDeliveryUnusualExample example);

    int deleteByExample(OpsPoDeliveryUnusualExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPoDeliveryUnusual record);

    int insertSelective(OpsPoDeliveryUnusual record);

    List<OpsPoDeliveryUnusual> selectByExample(OpsPoDeliveryUnusualExample example);

    OpsPoDeliveryUnusual selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPoDeliveryUnusual record, @Param("example") OpsPoDeliveryUnusualExample example);

    int updateByExample(@Param("record") OpsPoDeliveryUnusual record, @Param("example") OpsPoDeliveryUnusualExample example);

    int updateByPrimaryKeySelective(OpsPoDeliveryUnusual record);

    int updateByPrimaryKey(OpsPoDeliveryUnusual record);
}