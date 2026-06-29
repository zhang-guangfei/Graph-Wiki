package com.smc.smccloud;

import cn.hutool.json.JSONUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Author: B90034
 * Date: 2022-08-17 10:50
 * Description:
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTests {

    @Resource
    private CustomerService customerService;

    @Resource
    private RedisManager redisManager;


    @Test
    public void getCustomerByCustomerNoTest() {

        ResultVo<String> resultVo = customerService.translateCustomerNameToEnglish();
        System.out.println("JSONUtil.toJsonPrettyStr(resultVo) = " + JSONUtil.toJsonPrettyStr(resultVo));

    }
}
