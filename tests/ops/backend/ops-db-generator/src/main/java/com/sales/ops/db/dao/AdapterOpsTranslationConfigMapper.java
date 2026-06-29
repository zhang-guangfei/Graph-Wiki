package com.sales.ops.db.dao;

import com.sales.ops.db.entity.AdapterOpsTranslationConfig;
import com.sales.ops.db.entity.AdapterOpsTranslationConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdapterOpsTranslationConfigMapper {
    long countByExample(AdapterOpsTranslationConfigExample example);

    int deleteByExample(AdapterOpsTranslationConfigExample example);

    int insert(AdapterOpsTranslationConfig record);

    int insertSelective(AdapterOpsTranslationConfig record);

    List<AdapterOpsTranslationConfig> selectByExample(AdapterOpsTranslationConfigExample example);

    int updateByExampleSelective(@Param("record") AdapterOpsTranslationConfig record, @Param("example") AdapterOpsTranslationConfigExample example);

    int updateByExample(@Param("record") AdapterOpsTranslationConfig record, @Param("example") AdapterOpsTranslationConfigExample example);
}