package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.SalesProductSpecialModelExcludeDO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-12-31
 */
@Mapper
@DS("opsdb")
public interface SalesProductSpecialModelExcludeMapper extends BaseMapper<SalesProductSpecialModelExcludeDO> {

    @Select("select model_no from sales_product_special_model_exclude ")
    List<String> getSpecialModelNoList();

}
