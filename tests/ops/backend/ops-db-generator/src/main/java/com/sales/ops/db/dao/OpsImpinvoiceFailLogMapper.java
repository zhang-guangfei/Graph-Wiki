package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsImpinvoiceFailLog;
import com.sales.ops.db.entity.OpsImpinvoiceFailLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsImpinvoiceFailLogMapper {
    long countByExample(OpsImpinvoiceFailLogExample example);

    int deleteByExample(OpsImpinvoiceFailLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsImpinvoiceFailLog record);

    int insertSelective(OpsImpinvoiceFailLog record);

    List<OpsImpinvoiceFailLog> selectByExample(OpsImpinvoiceFailLogExample example);

    OpsImpinvoiceFailLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsImpinvoiceFailLog record, @Param("example") OpsImpinvoiceFailLogExample example);

    int updateByExample(@Param("record") OpsImpinvoiceFailLog record, @Param("example") OpsImpinvoiceFailLogExample example);

    int updateByPrimaryKeySelective(OpsImpinvoiceFailLog record);

    int updateByPrimaryKey(OpsImpinvoiceFailLog record);
}