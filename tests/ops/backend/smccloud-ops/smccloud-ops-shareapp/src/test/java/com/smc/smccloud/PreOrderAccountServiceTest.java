package com.smc.smccloud;

import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.impl.PreOrderAccountServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * @author wuweidong
 * @create 2024/1/8 17:00
 * @description
 */
@Slf4j
public class PreOrderAccountServiceTest extends OpsShareAppApplicationTests  {

    @Resource
    private CommonServiceFeignApi  commonServiceFeignApi;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        SMCApp.setForSysUser();
    }
    @Value("${spring.datasource.dynamic.datasource.sharedb.url}")
    private String DATABASE;
    @Value("${spring.datasource.dynamic.datasource.sharedb.username}")
    private String USER;
    @Value("${spring.datasource.dynamic.datasource.sharedb.password}")
    private String PASSWORD;






    @Test
    public void getChargerTest(){
        commonServiceFeignApi.getEmployeeInfo("940002");
    }

}
