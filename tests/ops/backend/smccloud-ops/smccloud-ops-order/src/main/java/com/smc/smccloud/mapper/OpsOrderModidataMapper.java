package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.order.OpsOrderModidataDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2022-08-19
 */
@Mapper
@DS("opsdb")
public interface OpsOrderModidataMapper extends BaseMapper<OpsOrderModidataDO> {

    @Select("SELECT * FROM ops_order_modidata where type_code='C' and create_time>'2022-08-19'")
    List<OpsOrderModidataDO> listData();
}
