package com.sales.ops.db.dao;

import com.sales.ops.db.entity.HrOrganization;
import com.sales.ops.db.entity.HrOrganizationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HrOrganizationMapper {
    long countByExample(HrOrganizationExample example);

    int deleteByExample(HrOrganizationExample example);

    int deleteByPrimaryKey(String id);

    int insert(HrOrganization record);

    int insertSelective(HrOrganization record);

    List<HrOrganization> selectByExample(HrOrganizationExample example);

    HrOrganization selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") HrOrganization record, @Param("example") HrOrganizationExample example);

    int updateByExample(@Param("record") HrOrganization record, @Param("example") HrOrganizationExample example);

    int updateByPrimaryKeySelective(HrOrganization record);

    int updateByPrimaryKey(HrOrganization record);
}