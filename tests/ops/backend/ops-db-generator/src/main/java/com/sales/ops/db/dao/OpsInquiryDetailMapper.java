package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInquiryDetail;
import com.sales.ops.db.entity.OpsInquiryDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsInquiryDetailMapper {
    long countByExample(OpsInquiryDetailExample example);

    int deleteByExample(OpsInquiryDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInquiryDetail record);

    int insertSelective(OpsInquiryDetail record);

    List<OpsInquiryDetail> selectByExample(OpsInquiryDetailExample example);

    OpsInquiryDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInquiryDetail record, @Param("example") OpsInquiryDetailExample example);

    int updateByExample(@Param("record") OpsInquiryDetail record, @Param("example") OpsInquiryDetailExample example);

    int updateByPrimaryKeySelective(OpsInquiryDetail record);

    int updateByPrimaryKey(OpsInquiryDetail record);
}