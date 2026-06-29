package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInquiryReasonParamConfig;
import com.sales.ops.db.entity.OpsInquiryReasonParamConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsInquiryReasonParamConfigMapper {
    long countByExample(OpsInquiryReasonParamConfigExample example);

    int deleteByExample(OpsInquiryReasonParamConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInquiryReasonParamConfig record);

    int insertSelective(OpsInquiryReasonParamConfig record);

    List<OpsInquiryReasonParamConfig> selectByExample(OpsInquiryReasonParamConfigExample example);

    OpsInquiryReasonParamConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInquiryReasonParamConfig record, @Param("example") OpsInquiryReasonParamConfigExample example);

    int updateByExample(@Param("record") OpsInquiryReasonParamConfig record, @Param("example") OpsInquiryReasonParamConfigExample example);

    int updateByPrimaryKeySelective(OpsInquiryReasonParamConfig record);

    int updateByPrimaryKey(OpsInquiryReasonParamConfig record);
}