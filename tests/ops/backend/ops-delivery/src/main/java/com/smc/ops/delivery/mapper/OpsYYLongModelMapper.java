package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.YyLongmodelexchange;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：dxf
 * @date ：Created in 2024/08/08 10:07
 * @description:超长型号转换mapper
 */
@DS("opsdb")
@Mapper
public interface OpsYYLongModelMapper {
    @Select(" select * from YY_LongModelExchange WHERE YYModel = #{model} ")
    List<YyLongmodelexchange> getLongModelExchange(@Param("model") String model);

    @Insert(" insert into YY_LongModelExchange ([YYModel]) values (#{model}) ")
    int insertLongModelExchange(@Param("model") String model);

}
