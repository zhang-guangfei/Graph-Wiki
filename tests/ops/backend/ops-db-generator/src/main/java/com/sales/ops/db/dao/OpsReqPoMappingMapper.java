package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsReqPoMapping;
import com.sales.ops.db.entity.OpsReqPoMappingExample;
import com.sales.ops.db.entity.OpsReqPoMappingKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsReqPoMappingMapper {
    long countByExample(OpsReqPoMappingExample example);

    int deleteByExample(OpsReqPoMappingExample example);

    int deleteByPrimaryKey(OpsReqPoMappingKey key);

    int insert(OpsReqPoMapping record);

    int insertSelective(OpsReqPoMapping record);

    List<OpsReqPoMapping> selectByExample(OpsReqPoMappingExample example);

    OpsReqPoMapping selectByPrimaryKey(OpsReqPoMappingKey key);

    int updateByExampleSelective(@Param("record") OpsReqPoMapping record, @Param("example") OpsReqPoMappingExample example);

    int updateByExample(@Param("record") OpsReqPoMapping record, @Param("example") OpsReqPoMappingExample example);

    int updateByPrimaryKeySelective(OpsReqPoMapping record);

    int updateByPrimaryKey(OpsReqPoMapping record);
}