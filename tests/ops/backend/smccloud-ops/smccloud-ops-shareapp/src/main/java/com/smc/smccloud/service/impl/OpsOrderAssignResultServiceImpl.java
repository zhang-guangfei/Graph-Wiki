package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.mapper.OpsOrderAssignResultMapper;
import com.smc.smccloud.model.OpsOrderAssignResultDO;
import com.smc.smccloud.service.OpsOrderAssignResultService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lyc
 * @Date 2023/6/25 12:08
 * @Descripton TODO
 */
@Service
public class OpsOrderAssignResultServiceImpl implements OpsOrderAssignResultService {

    @Resource
    private OpsOrderAssignResultMapper opsOrderAssignResultMapper;

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<List<OpsOrderAssignResultDO>> findOrderAssignResult(String orderNo, String itemNo) {
        if (StringUtils.isBlank(orderNo) || StringUtils.isBlank(itemNo)) {
            return ResultVo.failure("入参不可为空.");
        }
        LambdaQueryWrapper<OpsOrderAssignResultDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsOrderAssignResultDO::getOrderNo, orderNo)
                .eq(OpsOrderAssignResultDO::getOrderItem, itemNo)
                .eq(OpsOrderAssignResultDO::getDelflag, 0);
        List<OpsOrderAssignResultDO> opsOrderAssignResultDOS = opsOrderAssignResultMapper.selectList(queryWrapper);
        return ResultVo.success(opsOrderAssignResultDOS);
    }
}
