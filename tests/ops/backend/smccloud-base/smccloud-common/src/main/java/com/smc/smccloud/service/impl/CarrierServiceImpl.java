package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.mapper.CarrierMapper;
import com.smc.smccloud.model.CarrierDO;
import com.smc.smccloud.model.CarrierVO;
import com.smc.smccloud.service.CarrierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2022/4/7 9:06
 * @Descripton TODO
 */
@Service
public class CarrierServiceImpl implements CarrierService {

    @Resource
    private CarrierMapper carrierMapper;


    @Override
    public ResultVo<CarrierVO> findCarrierInfoById(String carrierId) {
        if (StringUtils.isBlank(carrierId)) {
            return ResultVo.failure("参数为空");
        }

        LambdaQueryWrapper<CarrierDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CarrierDO::getCarrierId, carrierId);
        CarrierDO carrierDO = carrierMapper.selectOne(queryWrapper);
        if (carrierDO == null) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copy(carrierDO, CarrierVO.class));
    }
}
