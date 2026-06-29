package com.smc.smccloud.service;

import com.smc.smccloud.log.annotation.SysLog;
import com.smc.smccloud.core.model.ResultVo.ResultVo;



/**
 * @Author lyc
 * @Date 2023/4/21 15:30
 * @Descripton TODO
 */
public interface KettleJobManageService {


    /**
     * mdm对接通知任务接口
     */
    ResultVo<String> mdmNoticeWithOpsBaseData(String jsonStr);

    ResultVo<String> testSaveLog(String str);

    ResultVo<String> tetsSaveLog2(String str2);

}
