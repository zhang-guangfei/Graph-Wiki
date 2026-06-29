package com.smc.smccloud;

import com.smc.smccloud.model.product.ProductInterceptRuleDTO;
import com.smc.smccloud.service.ProductInterceptRuleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.regex.Pattern;

/**
 * Author: B90034
 * Date: 2022-05-23 13:47
 * Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInterceptRuleServiceTests {

    @Resource
    private ProductInterceptRuleService productInterceptRuleService;

    @Test
    public void checkProductInterceptRuleTest() {
        ProductInterceptRuleDTO dto = new ProductInterceptRuleDTO();
        dto.setApplyType(1);
        dto.setModelNo("KQ2L07-01NS");
        dto.setSupplierId("JP");
        dto.setWarehouseCode("KGZ");
        dto.setCustomerNo("B9ZRL");
        dto.setQuantity(750);
        log.info("checkProductInterceptRuleTest result = {}", productInterceptRuleService.checkProductInterceptRule(dto));
    }

    @Test
    public void matchModelNoTest() {
        String modelNo = "KQ2L06-01AS";
        System.out.println(" = " + Pattern.matches("^SY\\S+", modelNo));
    }

}
