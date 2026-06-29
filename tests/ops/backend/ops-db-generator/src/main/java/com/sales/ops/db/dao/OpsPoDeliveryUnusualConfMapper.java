package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoDeliveryUnusualConf;
import com.sales.ops.db.entity.OpsPoDeliveryUnusualConfExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoDeliveryUnusualConfMapper {
    long countByExample(OpsPoDeliveryUnusualConfExample example);

    int deleteByExample(OpsPoDeliveryUnusualConfExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsPoDeliveryUnusualConf record);

    int insertSelective(OpsPoDeliveryUnusualConf record);

    List<OpsPoDeliveryUnusualConf> selectByExample(OpsPoDeliveryUnusualConfExample example);

    OpsPoDeliveryUnusualConf selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsPoDeliveryUnusualConf record, @Param("example") OpsPoDeliveryUnusualConfExample example);

    int updateByExample(@Param("record") OpsPoDeliveryUnusualConf record, @Param("example") OpsPoDeliveryUnusualConfExample example);

    int updateByPrimaryKeySelective(OpsPoDeliveryUnusualConf record);

    int updateByPrimaryKey(OpsPoDeliveryUnusualConf record);
}