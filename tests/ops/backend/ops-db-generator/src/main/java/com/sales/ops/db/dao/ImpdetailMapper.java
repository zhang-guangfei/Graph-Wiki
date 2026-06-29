package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Impdetail;
import com.sales.ops.db.entity.ImpdetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImpdetailMapper {
    long countByExample(ImpdetailExample example);

    int deleteByExample(ImpdetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Impdetail record);

    int insertSelective(Impdetail record);

    List<Impdetail> selectByExample(ImpdetailExample example);

    Impdetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Impdetail record, @Param("example") ImpdetailExample example);

    int updateByExample(@Param("record") Impdetail record, @Param("example") ImpdetailExample example);

    int updateByPrimaryKeySelective(Impdetail record);

    int updateByPrimaryKey(Impdetail record);
}