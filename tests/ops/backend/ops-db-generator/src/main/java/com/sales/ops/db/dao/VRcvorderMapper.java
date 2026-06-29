package com.sales.ops.db.dao;

import com.sales.ops.db.entity.VRcvorder;
import com.sales.ops.db.entity.VRcvorderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VRcvorderMapper {
    long countByExample(VRcvorderExample example);

    int deleteByExample(VRcvorderExample example);

    int insert(VRcvorder record);

    int insertSelective(VRcvorder record);

    List<VRcvorder> selectByExample(VRcvorderExample example);

    int updateByExampleSelective(@Param("record") VRcvorder record, @Param("example") VRcvorderExample example);

    int updateByExample(@Param("record") VRcvorder record, @Param("example") VRcvorderExample example);
}