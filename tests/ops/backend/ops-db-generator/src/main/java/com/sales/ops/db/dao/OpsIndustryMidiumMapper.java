package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsIndustryMidium;
import com.sales.ops.db.entity.OpsIndustryMidiumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsIndustryMidiumMapper {
    long countByExample(OpsIndustryMidiumExample example);

    int deleteByExample(OpsIndustryMidiumExample example);

    int deleteByPrimaryKey(String code);

    int insert(OpsIndustryMidium record);

    int insertSelective(OpsIndustryMidium record);

    List<OpsIndustryMidium> selectByExample(OpsIndustryMidiumExample example);

    OpsIndustryMidium selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") OpsIndustryMidium record, @Param("example") OpsIndustryMidiumExample example);

    int updateByExample(@Param("record") OpsIndustryMidium record, @Param("example") OpsIndustryMidiumExample example);

    int updateByPrimaryKeySelective(OpsIndustryMidium record);

    int updateByPrimaryKey(OpsIndustryMidium record);
}