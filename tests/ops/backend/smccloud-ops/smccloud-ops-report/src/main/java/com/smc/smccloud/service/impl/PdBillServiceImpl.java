package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smc.smccloud.mapper.pd.OpsAsPdBillDataMapper;
import com.smc.smccloud.model.pd.OpsAsPdBillDataDO;
import com.smc.smccloud.service.PdBillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lyc
 * @Date 2023/7/8 11:19
 * @Descripton TODO
 */
@Service
public class PdBillServiceImpl extends ServiceImpl<OpsAsPdBillDataMapper, OpsAsPdBillDataDO> implements PdBillService  {

    @Resource
    private OpsAsPdBillDataMapper opsAsPdBillDataMapper;

    @Override
    // @Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
    public List<OpsAsPdBillDataDO> findPdBillData(List<String> PDbillTypeList,String pdBatchNo) {
        LambdaQueryWrapper<OpsAsPdBillDataDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(OpsAsPdBillDataDO::getPdBillType,PDbillTypeList)
                .eq(OpsAsPdBillDataDO::getPdBatchNo,pdBatchNo);
       return opsAsPdBillDataMapper.selectList(queryWrapper);
    }
}
