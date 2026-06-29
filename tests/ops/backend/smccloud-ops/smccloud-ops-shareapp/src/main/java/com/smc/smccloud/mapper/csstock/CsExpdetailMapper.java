package com.smc.smccloud.mapper.csstock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.csstock.CsApplyDO;
import com.smc.smccloud.model.csstock.CsExpdetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  出库接口
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@Mapper
@DS("sharedb")
public interface CsExpdetailMapper extends BaseMapper<CsExpdetailDO> {


    @Select("SELECT do_id FROM ops_core.dbo.ops_do WHERE order_id =#{orderNo} AND order_item =#{itemNo}")
    CsExpdetailDO getCsReturnDoId(@Param("orderNo") String orderNo,@Param("itemNo") String itemNo);
}
