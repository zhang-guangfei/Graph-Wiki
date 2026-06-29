package com.smc.smccloud;

import cn.hutool.json.JSONUtil;
import com.smc.smccloud.service.KettleExecService;
import com.smc.smccloud.util.ResultVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class OpsKettleJobApplicationTests {

    @Resource
    private KettleExecService kettleExecService;

    @Test
    void contextLoads() {
        ResultVo<String> stringResultVo = kettleExecService.syncCrmCustomerLimitAll();
        System.out.println("stringResultVo = " + JSONUtil.toJsonStr(stringResultVo));
    }

}
