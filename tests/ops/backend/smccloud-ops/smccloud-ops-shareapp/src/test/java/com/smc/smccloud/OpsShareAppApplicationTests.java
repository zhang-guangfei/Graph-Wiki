package com.smc.smccloud;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.sampleorder.SampleOrderApplyMapper;
import com.smc.smccloud.service.ReceiveOrderServiceFeignApi;
import com.smc.smccloud.service.ReturnOrderService;
import com.smc.smccloud.service.SampleOrderApplyFeignApi;
import com.smc.smccloud.service.SampleOrderApplyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Author: B90034
 * Date: 2021-09-27 11:49
 * Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OpsShareAppApplicationTests {

    @Resource
    private SampleOrderApplyMapper sampleOrderApplyMapper;

    @Resource
    private ReceiveOrderServiceFeignApi receiveOrderServiceFeignApi;

    @Resource
    private SampleOrderApplyFeignApi sampleOrderApplyFeignApi;
    @Resource
    private RedisManager redisManager;

    @Resource
    private SampleOrderApplyService sampleOrderApplyService;

    @Resource
    private ReturnOrderService returnOrderService;

    @Before
    public void setAuth() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);

    }

    @Test
    public void testRedis() {
//        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
//        ResultVo<String> stringResultVo = sampleOrderApplyFeignApi.autoGenerateSampleBalData();
//        System.out.println("stringResultVo = " + stringResultVo.toString());
        ResultVo<String> resultVo = sampleOrderApplyService.autoGenerateSampleBalData();
        System.out.println("resultVo = " + resultVo.toString());
    }

    @Test
    public void testaa() {
        // warehouse:name
        // warehouse:type
        Set<String> keys = redisManager.scan("ops:warehouse:name:*");
        for (String key : keys) {
            boolean aa = redisManager.delete(key);
            System.out.println("remove " + key + " = " + aa);
        }

        keys = redisManager.scan("ops:warehouse:type:*");
        for (String key : keys) {
            boolean bb = redisManager.delete(key);
            System.out.println("remove " + key + " = " + bb);
        }
    }

    @Test
    public void testAddData() {

        //Object tttt = redisManager.Lget("tttt", -1);
        //System.out.println("tttt = " + tttt.toString());

    }

}
