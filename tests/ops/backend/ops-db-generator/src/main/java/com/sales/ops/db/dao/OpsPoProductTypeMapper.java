package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsPoProductType;
import com.sales.ops.db.entity.OpsPoProductTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsPoProductTypeMapper {
    long countByExample(OpsPoProductTypeExample example);

    int deleteByExample(OpsPoProductTypeExample example);

    int insert(OpsPoProductType record);

    int insertSelective(OpsPoProductType record);

    List<OpsPoProductType> selectByExample(OpsPoProductTypeExample example);

    int updateByExampleSelective(@Param("record") OpsPoProductType record, @Param("example") OpsPoProductTypeExample example);

    int updateByExample(@Param("record") OpsPoProductType record, @Param("example") OpsPoProductTypeExample example);
}