package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInquiryPoHandle;
import com.sales.ops.db.entity.OpsInquiryPoHandleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpsInquiryPoHandleMapper {
    long countByExample(OpsInquiryPoHandleExample example);

    int deleteByExample(OpsInquiryPoHandleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsInquiryPoHandle record);

    int insertSelective(OpsInquiryPoHandle record);

    List<OpsInquiryPoHandle> selectByExample(OpsInquiryPoHandleExample example);

    OpsInquiryPoHandle selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsInquiryPoHandle record, @Param("example") OpsInquiryPoHandleExample example);

    int updateByExample(@Param("record") OpsInquiryPoHandle record, @Param("example") OpsInquiryPoHandleExample example);

    int updateByPrimaryKeySelective(OpsInquiryPoHandle record);

    int updateByPrimaryKey(OpsInquiryPoHandle record);
}