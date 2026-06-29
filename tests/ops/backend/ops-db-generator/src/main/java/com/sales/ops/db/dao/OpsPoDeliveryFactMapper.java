package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoDeliveryFact;
import com.sales.ops.db.entity.OpsPoDeliveryFactExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoDeliveryFactMapper {
    long countByExample(OpsPoDeliveryFactExample example);

    int deleteByExample(OpsPoDeliveryFactExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPoDeliveryFact record);

    int insertSelective(OpsPoDeliveryFact record);

    List<OpsPoDeliveryFact> selectByExample(OpsPoDeliveryFactExample example);

    OpsPoDeliveryFact selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPoDeliveryFact record, @Param("example") OpsPoDeliveryFactExample example);

    int updateByExample(@Param("record") OpsPoDeliveryFact record, @Param("example") OpsPoDeliveryFactExample example);

    int updateByPrimaryKeySelective(OpsPoDeliveryFact record);

    int updateByPrimaryKey(OpsPoDeliveryFact record);
}