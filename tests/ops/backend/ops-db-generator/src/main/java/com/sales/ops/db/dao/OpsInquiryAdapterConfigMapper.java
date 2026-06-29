package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInquiryAdapterConfig;
import com.sales.ops.db.entity.OpsInquiryAdapterConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsInquiryAdapterConfigMapper {
    long countByExample(OpsInquiryAdapterConfigExample example);

    int deleteByExample(OpsInquiryAdapterConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInquiryAdapterConfig record);

    int insertSelective(OpsInquiryAdapterConfig record);

    List<OpsInquiryAdapterConfig> selectByExample(OpsInquiryAdapterConfigExample example);

    OpsInquiryAdapterConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInquiryAdapterConfig record, @Param("example") OpsInquiryAdapterConfigExample example);

    int updateByExample(@Param("record") OpsInquiryAdapterConfig record, @Param("example") OpsInquiryAdapterConfigExample example);

    int updateByPrimaryKeySelective(OpsInquiryAdapterConfig record);

    int updateByPrimaryKey(OpsInquiryAdapterConfig record);
}