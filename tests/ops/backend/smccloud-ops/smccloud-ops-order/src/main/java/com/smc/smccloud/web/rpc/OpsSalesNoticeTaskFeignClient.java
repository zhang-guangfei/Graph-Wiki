package com.smc.smccloud.web.rpc;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.service.OpsSalesNoticeTaskFeignApi;
import com.smc.smccloud.service.SalesNotickTaskService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2023/7/20 8:23
 * @Descripton TODO
 */
@RestController
public class OpsSalesNoticeTaskFeignClient implements OpsSalesNoticeTaskFeignApi {

    @Resource
    private SalesNotickTaskService salesNotickTaskService;

    @Override
    public ResultVo<String> execInsertOpsSalesNoticeTask(String paramJson) {
        return salesNotickTaskService.execInsertOpsSalesNoticeTask(paramJson);
    }

    @Override
    public ResultVo<String> opsSalesNoticeTask() {
        return salesNotickTaskService.execCallInterface("1");
    }

    @Override
    public ResultVo<String> opsSalesNoticeTask2() {
        return salesNotickTaskService.execCallInterface("2");
    }

    @Override
    public ResultVo<String> opsSalesCallBackNoticeTask() {
        return salesNotickTaskService.execCallBackInterface("1");
    }

    @Override
    public ResultVo<String> opsSalesCallBackNoticeTask2() {
        return salesNotickTaskService.execCallBackInterface("2");
    }

    @Override
    public ResultVo<String> upOpsSalesNoticeTaskInfo(UpTaskInfoVO vo) {
        return salesNotickTaskService.upTaskInfoByBatchNo(vo);
    }
}
