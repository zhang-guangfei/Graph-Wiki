package com.sales.ops.db.dao;

import com.sales.ops.db.entity.MtWarehousedeptconfig;
import com.sales.ops.db.entity.MtWarehousedeptconfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MtWarehousedeptconfigMapper {
    long countByExample(MtWarehousedeptconfigExample example);

    int deleteByExample(MtWarehousedeptconfigExample example);

    int insert(MtWarehousedeptconfig record);

    int insertSelective(MtWarehousedeptconfig record);

    List<MtWarehousedeptconfig> selectByExample(MtWarehousedeptconfigExample example);

    int updateByExampleSelective(@Param("record") MtWarehousedeptconfig record, @Param("example") MtWarehousedeptconfigExample example);

    int updateByExample(@Param("record") MtWarehousedeptconfig record, @Param("example") MtWarehousedeptconfigExample example);
}