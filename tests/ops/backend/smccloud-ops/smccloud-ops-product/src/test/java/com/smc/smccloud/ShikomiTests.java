package com.smc.smccloud;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.mapper.ShikomiModelMapper;
import com.smc.smccloud.mapper.ShikomiTotalMapper;
import com.smc.smccloud.model.shikomi.*;
import com.smc.smccloud.service.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShikomiTests {

    @Resource
    private ShikomiService productService;
    @Resource
    private ProductService productService1;
    @Resource
    private ShikomiTotalMapper shikomiTotalMapper;
    @Resource
    private ShikomiModelMapper shikomiModelMapper;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Test
    public void contextLoads() {

//        productService.listAllShikomi(1,10,10);
//        productService.listShikomi("","","",1,"JP0",1,1);
    }


    @Test
    public void canUse() {

   productService1.importCNMProductModel();
    }

    @Test
    public void listCustomerShikomiTest() {

        String modelNo="CDU16D-T0956-35";
        String customerno="2";

        List<ShikomiVO> list = productService.listCustomerShikomi(modelNo, customerno);
        for (ShikomiVO shikomiVO : list) {
            log.info(shikomiVO.toString());
        }
    }

    @Test
    public void prepareShikomi(){
//        ShikomiPrepareDTO shikomiPrepareDTO = new ShikomiPrepareDTO();
//        shikomiPrepareDTO.setOrderNo("A1003");
//        shikomiPrepareDTO.setQuantity(3);
//        shikomiPrepareDTO.setShikomiNo("AAAAAA");
//        shikomiPrepareDTO.setModelNo("BBBBB");
//        productService.prepareShikomi(shikomiPrepareDTO);
    }
    @Test
    public void getCanUseShikomiTest( ) {
       log.info("getCanUseShikomi ==>{}",productService.getCanUseShikomi("CKQ50-159BCNB-X1776Z"));
    }
}
