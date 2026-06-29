package com.smc.smccloud.mapper.sampleorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.yulichang.base.MPJBaseMapper;
import com.smc.smccloud.model.sampleorder.SampleOrderApplyDO;
import com.smc.smccloud.model.sampleorder.SampleOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-10-27
 */
@Mapper
@DS("sharedb")
public interface SampleOrderApplyMapper extends MPJBaseMapper<SampleOrderApplyDO> {

    @Select("select sa.*, sd.* from sampleOrder_apply sa LEFT JOIN sampleOrder_detail sd on sa.id = sd.apply_id where sa.status = 3 ORDER BY sd.create_time desc")
    List<SampleOrderVO> listSampleOrderByStatus();

}
