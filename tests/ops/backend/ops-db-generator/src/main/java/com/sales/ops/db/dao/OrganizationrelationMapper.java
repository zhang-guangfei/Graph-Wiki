package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Organizationrelation;
import com.sales.ops.db.entity.OrganizationrelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrganizationrelationMapper {
    long countByExample(OrganizationrelationExample example);

    int deleteByExample(OrganizationrelationExample example);

    int deleteByPrimaryKey(String id);

    int insert(Organizationrelation record);

    int insertSelective(Organizationrelation record);

    List<Organizationrelation> selectByExample(OrganizationrelationExample example);

    Organizationrelation selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Organizationrelation record, @Param("example") OrganizationrelationExample example);

    int updateByExample(@Param("record") Organizationrelation record, @Param("example") OrganizationrelationExample example);

    int updateByPrimaryKeySelective(Organizationrelation record);

    int updateByPrimaryKey(Organizationrelation record);
}