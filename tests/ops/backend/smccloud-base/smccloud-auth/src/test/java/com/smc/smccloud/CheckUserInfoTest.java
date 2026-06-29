package com.smc.smccloud;

import cn.hutool.json.JSONUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2023/2/17 8:46
 * @Descripton TODO
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckUserInfoTest {

    @Resource
    private AuthService authService;


    @Test
    public void testMenHuCheckVerifyToken() {



    }



}
