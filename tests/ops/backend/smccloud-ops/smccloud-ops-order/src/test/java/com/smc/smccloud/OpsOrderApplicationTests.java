package com.smc.smccloud;

import com.alibaba.fastjson.JSONArray;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.model.constants.Constants;

import com.smc.smccloud.core.redis.redisson.RedissonUtil;

import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.OrderLogMapper;

import com.smc.smccloud.model.OrderConstants;
import com.smc.smccloud.model.invoice.ImpInvoiceDetailDO;
import com.smc.smccloud.model.invoice.ImpInvoiceReceiveDTO;
import com.smc.smccloud.service.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Author: B90034
 * Date: 2021-12-01 13:27
 * Description:
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class OpsOrderApplicationTests {

    @Autowired
    private RedissonUtil redissonUtil;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Resource
    private ImpInvoiceService impInvoiceService;

    @Resource
    private InvoiceServiceFeignApi invoiceServiceFeignApi;

//    @Resource
//    private ClientAuthFeginConfiguration clientAuthFeginConfiguration;
    @Resource
    private RedisManager redisManager;

    @Resource
    private OpsCommonService opsCommonService;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

    @Test
    public void testaaa() {
        File file = new File("F:\\TXQQ\\cache\\2355766573\\FileRecv\\SHPINF(1).TXT");
        MultipartFile multipartFile = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            ResultVo<String> resultVo = impInvoiceService.importJPShippingFile(multipartFile);
            System.out.println("resultVo = " + resultVo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLock() {
        RLock lock_test = redissonUtil.lock("lock_test");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            redissonUtil.unlock(lock_test);
        }
    }

    @Test
    public void testLock2() {
        boolean test_locak = redissonUtil.tryLock("test_locak", 20, 120);
        if (test_locak) {
            System.out.println("test_locak = ");
        } else {
            System.out.println("aaa = ");
        }
    }



    @Test
    public void tesget(){
        List<String> list = opsCommonService.canDelOrderStatus();
        System.out.println("list = " + JSONArray.toJSONString(list));

    }

}
