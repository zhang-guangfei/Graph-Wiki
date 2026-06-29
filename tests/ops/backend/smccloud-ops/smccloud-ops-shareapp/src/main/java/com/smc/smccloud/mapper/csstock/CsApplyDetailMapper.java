package com.smc.smccloud.mapper.csstock;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.core.model.dto.CodeName;
import com.smc.smccloud.model.csstock.CsApplyDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@Mapper
public interface CsApplyDetailMapper extends BaseMapper<CsApplyDetailDO> {

    @Select("update cs_apply_detail set order_no=#{orderNo} where apply_id=#{applyId} AND  model_no=#{modelNo} ")
    Integer updateOrderNoByApplyId(@Param("applyNo") Long applyNo,@Param("modelNo") String modelNo,@Param("orderNo") String orderNo);
}
