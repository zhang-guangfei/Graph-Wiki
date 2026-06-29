package com.smc.smccloud;

import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.service.OrderSalesService;
import com.smc.smccloud.service.ProductServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Author: B90034
 * Date: 2022-07-22 19:15
 * Description:
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderSalesServiceTests {

    @Resource
    private OrderSalesService orderSalesService;
    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private RedisManager redisManager;

    @Before
    public void setUp() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }



    @Test
    public void savedOrderQueryTest() {
        String key = "ops:orderSales:saved:HDD2208240502:FZD2208243984";
        log.info("savedOrderQueryTest = {}", redisManager.get(key));
    }
}
