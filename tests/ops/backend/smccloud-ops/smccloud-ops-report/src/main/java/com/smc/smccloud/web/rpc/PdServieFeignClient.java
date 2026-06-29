package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.MonthPdService;
import com.smc.smccloud.service.PdService;
import com.smc.smccloud.service.PdServiceFeignApi;
import com.smc.smccloud.service.PreOrderHitRateService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2023/6/26 8:22
 * @Descripton TODO
 */
@RestController
public class PdServieFeignClient implements PdServiceFeignApi {

    @Resource
    private PdService pdService;

    @Resource
    private MonthPdService monthPdService;

    @Resource
    private PreOrderHitRateService preOrderHitRateService;

    @Override
    public ResultVo<String> execPdImpDataTask() {
        return pdService.execPdTaskImpData();
    }

    @Override
    public ResultVo<String> execYdPdStepByPlan() {
        return monthPdService.execYdPdStep();
    }

    @Override
    public ResultVo<String> execYdOpsExtractPdStepByPlan() {
        return monthPdService.execYdExtractOpsDataPdStep();
    }

    @Override
    public ResultVo<String> execYdWmsExtractPdStepByPlan() {
        return monthPdService.execYdExtractWmsDataPdStep();
    }

    @Override
    public ResultVo<String> preOrderHitRate(String calDate) {
        return preOrderHitRateService.preOrderHitRate(calDate);
    }
}
