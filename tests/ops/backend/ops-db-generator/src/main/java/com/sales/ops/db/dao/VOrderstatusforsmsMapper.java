package com.sales.ops.db.dao;

import com.sales.ops.db.entity.VOrderstatusforsms;
import com.sales.ops.db.entity.VOrderstatusforsmsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VOrderstatusforsmsMapper {
    long countByExample(VOrderstatusforsmsExample example);

    int deleteByExample(VOrderstatusforsmsExample example);

    int insert(VOrderstatusforsms record);

    int insertSelective(VOrderstatusforsms record);

    List<VOrderstatusforsms> selectByExample(VOrderstatusforsmsExample example);

    int updateByExampleSelective(@Param("record") VOrderstatusforsms record, @Param("example") VOrderstatusforsmsExample example);

    int updateByExample(@Param("record") VOrderstatusforsms record, @Param("example") VOrderstatusforsmsExample example);
}