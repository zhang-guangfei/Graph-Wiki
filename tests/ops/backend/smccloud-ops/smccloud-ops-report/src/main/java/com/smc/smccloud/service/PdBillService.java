package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smc.smccloud.model.pd.OpsAsPdBillDataDO;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/7/8 11:18
 * @Descripton TODO
 */
public interface PdBillService  extends IService<OpsAsPdBillDataDO> {
    List<OpsAsPdBillDataDO> findPdBillData(List<String> PDbillTypeList,String pdBatchNo);
}
