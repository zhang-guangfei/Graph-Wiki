package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.mapper.pd.OpsAsPdBatchMapper;
import com.smc.smccloud.model.pd.OpsAsPdBatchDO;
import com.smc.smccloud.service.PdBatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/6/15 16:04
 * @Descripton TODO
 */
@Service
@Slf4j
public class PdBatchServiceImpl implements PdBatchService {

    @Resource
    private OpsAsPdBatchMapper opsAsPdBatchMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> updatePdStateByPdbatchNo(String pdBatchNo,String pdState,String optUser) {
        LambdaUpdateWrapper<OpsAsPdBatchDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(OpsAsPdBatchDO::getPdBatchNo,pdBatchNo)
                .set(OpsAsPdBatchDO::getPdState,pdState)
                .set(OpsAsPdBatchDO::getUpdateTime,new Date())
                .set(OpsAsPdBatchDO::getUpdateUser,optUser);
        try {
            opsAsPdBatchMapper.update(null, updateWrapper);
        } catch (Exception e) {
            log.error("变更批次表状态异常{}",e.getMessage(),e);
            throw new RuntimeException("变更批次表状态异常:"+e.getMessage());
        }
        return ResultVo.success("变更成功");
    }
}
