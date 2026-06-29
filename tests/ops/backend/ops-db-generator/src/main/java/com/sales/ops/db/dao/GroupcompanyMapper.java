package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Groupcompany;
import com.sales.ops.db.entity.GroupcompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupcompanyMapper {
    long countByExample(GroupcompanyExample example);

    int deleteByExample(GroupcompanyExample example);

    int insert(Groupcompany record);

    int insertSelective(Groupcompany record);

    List<Groupcompany> selectByExample(GroupcompanyExample example);

    int updateByExampleSelective(@Param("record") Groupcompany record, @Param("example") GroupcompanyExample example);

    int updateByExample(@Param("record") Groupcompany record, @Param("example") GroupcompanyExample example);
}