package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoDeliveryUnusualLog;
import com.sales.ops.db.entity.OpsPoDeliveryUnusualLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoDeliveryUnusualLogMapper {
    long countByExample(OpsPoDeliveryUnusualLogExample example);

    int deleteByExample(OpsPoDeliveryUnusualLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPoDeliveryUnusualLog record);

    int insertSelective(OpsPoDeliveryUnusualLog record);

    List<OpsPoDeliveryUnusualLog> selectByExample(OpsPoDeliveryUnusualLogExample example);

    OpsPoDeliveryUnusualLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPoDeliveryUnusualLog record, @Param("example") OpsPoDeliveryUnusualLogExample example);

    int updateByExample(@Param("record") OpsPoDeliveryUnusualLog record, @Param("example") OpsPoDeliveryUnusualLogExample example);

    int updateByPrimaryKeySelective(OpsPoDeliveryUnusualLog record);

    int updateByPrimaryKey(OpsPoDeliveryUnusualLog record);
}