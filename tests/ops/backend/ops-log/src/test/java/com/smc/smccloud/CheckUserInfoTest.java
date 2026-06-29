package com.smc.smccloud;

import cn.hutool.json.JSONUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2023/5/30 8:46
 * @Descripton TODO
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CheckUserInfoTest.class)
public class CheckUserInfoTest {



    @Test
    public void testMenHuCheckVerifyToken() {
        System.out.println("log = ");

    }



}
