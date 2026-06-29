package com.smc.smccloud;

import com.smc.smccloud.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Author: B90034
 * Date: 2022-06-21 09:19
 * Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests {

    @Resource
    private ProductService productService;

    @Test
    public void checkAndReturnErrorModelTest() {
        List<String> modelNoList = Arrays.asList("ITV2050-312BL");
        log.info("checkModelNosTest = {}", productService.checkAndReturnErrorModel(modelNoList));
    }
}
