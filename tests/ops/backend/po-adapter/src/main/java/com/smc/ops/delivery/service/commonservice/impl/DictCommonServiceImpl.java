package com.smc.ops.delivery.service.commonservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.smc.ops.delivery.config.RedisManager;
import com.smc.ops.delivery.mapper.DictDataMapper;
import com.smc.ops.delivery.model.DataTypeDO;
import com.smc.ops.delivery.model.DataTypeVO;
import com.smc.ops.delivery.service.commonservice.DictCommonService;
import com.smc.ops.delivery.util.BeanCopyUtil;
import com.smc.ops.delivery.util.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2024/2/27 11:53
 * @Descripton TODO
 */
@Service
@Slf4j
public class DictCommonServiceImpl implements DictCommonService {

    @Resource
    private DictDataMapper dictDataMapper;



    @Override
    public DataTypeVO getDataCodesInfo(String classCode, String code) {
        LambdaQueryWrapper<DataTypeDO> query = new LambdaQueryWrapper<>();
        query.eq(DataTypeDO::getClassCode,classCode).eq(DataTypeDO::getCode,code);
        DataTypeDO dataTypeDO = dictDataMapper.selectOne(query);
        if (dataTypeDO == null) {
            return null;
        }
        try {
            return BeanCopyUtil.copy(dataTypeDO, DataTypeVO.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResultVo<Boolean> updateExtNote2(Long id, String extNote2, String curExtNote2) {
        LambdaUpdateWrapper<DataTypeDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(DataTypeDO::getExtNote2, extNote2);
        updateWrapper.eq(DataTypeDO::getId, id).eq(DataTypeDO::getExtNote2, curExtNote2);

        if (1 == dictDataMapper.update(null, updateWrapper)) {
            return ResultVo.success(true);
        }
        return ResultVo.failure("更新失败");
    }
}
