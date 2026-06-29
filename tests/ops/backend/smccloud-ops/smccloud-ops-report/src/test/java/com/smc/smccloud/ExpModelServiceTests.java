package com.smc.smccloud;


import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.BigDecimalUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.CommonMapper;
import com.smc.smccloud.service.ExpModelService;
import com.smc.smccloud.service.PdService;
import com.smc.smccloud.service.SalesDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author edp04
 * @title: ExpModelServiceTests
 * @date 2022/05/11 15:18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExpModelServiceTests {

    @Resource
    private ExpModelService expModelService;

    @Resource
    private SalesDataService salesDataService;

    @Resource
    private PdService pdService;

    @Resource
    private CommonMapper commonMapper;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    @Test
    public void sendTest() {
//        Date date = DateUtil.stringToDate("2021-10-01");
//        Date date2 = DateUtil.stringToDate("2022-03-01");
//        expModelService.onSendAgentOrderFreqReport("XY012");
        BigDecimal mul = new BigDecimal(1372.5);
        BigDecimal div = BigDecimalUtil.div(mul, 10, 2);
        double v = div.doubleValue();
        long l = Math.round(v);
        long l1 = l * 10;
        System.out.println(l1);
    }

    @Test
    public void testSendEmailForEDis() {
        commonMapper.insertSysLog("test", "test", "test");

    }

}
