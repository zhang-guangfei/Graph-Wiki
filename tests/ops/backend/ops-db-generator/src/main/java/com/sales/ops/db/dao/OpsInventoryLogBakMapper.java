package com.sales.ops.db.dao;

import com.sales.ops.db.entity.OpsInventoryLogBak;
import com.sales.ops.db.entity.OpsInventoryLogBakExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpsInventoryLogBakMapper {
    long countByExample(OpsInventoryLogBakExample example);

    int deleteByExample(OpsInventoryLogBakExample example);

    int deleteByPrimaryKey(Long logId);

    int insert(OpsInventoryLogBak record);

    int insertSelective(OpsInventoryLogBak record);

    List<OpsInventoryLogBak> selectByExample(OpsInventoryLogBakExample example);

    OpsInventoryLogBak selectByPrimaryKey(Long logId);

    int updateByExampleSelective(@Param("record") OpsInventoryLogBak record, @Param("example") OpsInventoryLogBakExample example);

    int updateByExample(@Param("record") OpsInventoryLogBak record, @Param("example") OpsInventoryLogBakExample example);

    int updateByPrimaryKeySelective(OpsInventoryLogBak record);

    int updateByPrimaryKey(OpsInventoryLogBak record);
}