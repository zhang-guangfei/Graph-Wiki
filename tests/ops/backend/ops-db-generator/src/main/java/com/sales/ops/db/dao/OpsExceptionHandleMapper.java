package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsExceptionHandle;
import com.sales.ops.db.entity.OpsExceptionHandleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsExceptionHandleMapper {
    long countByExample(OpsExceptionHandleExample example);

    int deleteByExample(OpsExceptionHandleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OpsExceptionHandle record);

    int insertSelective(OpsExceptionHandle record);

    List<OpsExceptionHandle> selectByExample(OpsExceptionHandleExample example);

    OpsExceptionHandle selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OpsExceptionHandle record, @Param("example") OpsExceptionHandleExample example);

    int updateByExample(@Param("record") OpsExceptionHandle record, @Param("example") OpsExceptionHandleExample example);

    int updateByPrimaryKeySelective(OpsExceptionHandle record);

    int updateByPrimaryKey(OpsExceptionHandle record);
}