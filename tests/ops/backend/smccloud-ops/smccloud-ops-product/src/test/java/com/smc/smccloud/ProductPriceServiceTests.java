package com.smc.smccloud;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.service.ProductPriceService;
import com.smc.smccloud.service.ProductService;
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
 * Date: 2022-05-05 10:17
 * Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductPriceServiceTests {
    @Resource
    private ProductPriceService productPriceService;

    @Resource
    private ProductServiceFeignApi productServiceFeignApi;

    @Resource
    private ProductService productService;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    @Test
    public void getModelEPriceTest() {
        log.info("==> getModelEPriceTest result = {}", productPriceService.getModelEPrice("AS-DPP00093"));
    }

    @Test
    public void getModelEPriceTest2() {
        ResultVo<Boolean> eosModelNoResult = productService.isEosModelNo("VVQC4000-3A-2-X45");
        System.out.println("eosModelNoResult = " + eosModelNoResult.toString());
    }
}
