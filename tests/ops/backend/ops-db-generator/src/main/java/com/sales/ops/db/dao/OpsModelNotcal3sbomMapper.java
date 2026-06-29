package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsModelNotcal3sbom;
import com.sales.ops.db.entity.OpsModelNotcal3sbomExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsModelNotcal3sbomMapper {
    long countByExample(OpsModelNotcal3sbomExample example);

    int deleteByExample(OpsModelNotcal3sbomExample example);

    int insert(OpsModelNotcal3sbom record);

    int insertSelective(OpsModelNotcal3sbom record);

    List<OpsModelNotcal3sbom> selectByExample(OpsModelNotcal3sbomExample example);

    int updateByExampleSelective(@Param("record") OpsModelNotcal3sbom record, @Param("example") OpsModelNotcal3sbomExample example);

    int updateByExample(@Param("record") OpsModelNotcal3sbom record, @Param("example") OpsModelNotcal3sbomExample example);
}