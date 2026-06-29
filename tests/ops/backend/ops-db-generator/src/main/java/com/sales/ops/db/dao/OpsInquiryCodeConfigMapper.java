package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInquiryCodeConfig;
import com.sales.ops.db.entity.OpsInquiryCodeConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsInquiryCodeConfigMapper {
    long countByExample(OpsInquiryCodeConfigExample example);

    int deleteByExample(OpsInquiryCodeConfigExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInquiryCodeConfig record);

    int insertSelective(OpsInquiryCodeConfig record);

    List<OpsInquiryCodeConfig> selectByExample(OpsInquiryCodeConfigExample example);

    OpsInquiryCodeConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInquiryCodeConfig record, @Param("example") OpsInquiryCodeConfigExample example);

    int updateByExample(@Param("record") OpsInquiryCodeConfig record, @Param("example") OpsInquiryCodeConfigExample example);

    int updateByPrimaryKeySelective(OpsInquiryCodeConfig record);

    int updateByPrimaryKey(OpsInquiryCodeConfig record);
}