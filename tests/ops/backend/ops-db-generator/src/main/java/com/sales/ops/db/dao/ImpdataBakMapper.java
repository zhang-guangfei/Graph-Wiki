package com.sales.ops.db.dao;

import com.sales.ops.db.entity.ImpdataBak;
import com.sales.ops.db.entity.ImpdataBakExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImpdataBakMapper {
    long countByExample(ImpdataBakExample example);

    int deleteByExample(ImpdataBakExample example);

    int insert(ImpdataBak record);

    int insertSelective(ImpdataBak record);

    List<ImpdataBak> selectByExample(ImpdataBakExample example);

    int updateByExampleSelective(@Param("record") ImpdataBak record, @Param("example") ImpdataBakExample example);

    int updateByExample(@Param("record") ImpdataBak record, @Param("example") ImpdataBakExample example);
}