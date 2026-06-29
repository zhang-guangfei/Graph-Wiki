package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInquiryApply;
import com.sales.ops.db.entity.OpsInquiryApplyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsInquiryApplyMapper {
    long countByExample(OpsInquiryApplyExample example);

    int deleteByExample(OpsInquiryApplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInquiryApply record);

    int insertSelective(OpsInquiryApply record);

    List<OpsInquiryApply> selectByExample(OpsInquiryApplyExample example);

    OpsInquiryApply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInquiryApply record, @Param("example") OpsInquiryApplyExample example);

    int updateByExample(@Param("record") OpsInquiryApply record, @Param("example") OpsInquiryApplyExample example);

    int updateByPrimaryKeySelective(OpsInquiryApply record);

    int updateByPrimaryKey(OpsInquiryApply record);
}