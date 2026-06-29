package com.smc.smccloud;


import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.core.utils.ThreadLocalMapUtil;
import com.smc.smccloud.service.ExpdetailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Author: B90034
 * Date: 2022-02-21 17:12
 * Description:
 */
@Slf4j
public class listExpdetailServiceTests extends OpsOrderApplicationTest {

    @Resource
    private ExpdetailService expdetailService;

    @Before
    public void setUp() {
        LoginUserDTO curUser = new LoginUserDTO();
        ThreadLocalMapUtil.put(GlobalConstant.Sys.LOGIN_USER_DTO, curUser);
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    @Test
    public void listExpdetailTest() {

    }
}
