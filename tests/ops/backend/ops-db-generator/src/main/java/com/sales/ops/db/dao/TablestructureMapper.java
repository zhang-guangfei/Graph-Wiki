package com.sales.ops.db.dao;

import com.sales.ops.db.entity.Tablestructure;
import com.sales.ops.db.entity.TablestructureExample;
import com.sales.ops.db.entity.TablestructureKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TablestructureMapper {
    long countByExample(TablestructureExample example);

    int deleteByExample(TablestructureExample example);

    int deleteByPrimaryKey(TablestructureKey key);

    int insert(Tablestructure record);

    int insertSelective(Tablestructure record);

    List<Tablestructure> selectByExample(TablestructureExample example);

    Tablestructure selectByPrimaryKey(TablestructureKey key);

    int updateByExampleSelective(@Param("record") Tablestructure record, @Param("example") TablestructureExample example);

    int updateByExample(@Param("record") Tablestructure record, @Param("example") TablestructureExample example);

    int updateByPrimaryKeySelective(Tablestructure record);

    int updateByPrimaryKey(Tablestructure record);
}