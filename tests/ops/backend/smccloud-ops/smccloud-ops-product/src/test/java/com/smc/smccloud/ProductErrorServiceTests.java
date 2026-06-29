package com.smc.smccloud;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.InventorySupplierService;
import com.smc.smccloud.service.ProductErrorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Author: B90034
 * Date: 2022-06-21 09:37
 * Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductErrorServiceTests {

    @Resource
    private ProductErrorService productErrorService;

    @Resource
    private InventorySupplierService inventorySupplierService;

    @Test
    public void isErrorModelNoTest() {
        System.out.println("isErrorModelNoTest result = " + productErrorService.isErrorModelNo("CS5080"));
    }

    @Test
    public void testPaseJPInventorySupplier() {
        ResultVo<String> resultVo = inventorySupplierService.paseJPInventorySupplier();
        System.out.println("resultVo = " + resultVo.toString());
    }
}
