package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.HandDelErrorOrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/9/8 14:02
 * @Descripton TODO
 */
@Mapper
@DS("opsdb")
public interface HandDelErrorOrderMapper extends BaseMapper<HandDelErrorOrderDO> {


    @Select("select DISTINCT order_no from hand_del_error_order where hand_status = 0 ")
    List<String> handDelErrorOrderDOS();
}
