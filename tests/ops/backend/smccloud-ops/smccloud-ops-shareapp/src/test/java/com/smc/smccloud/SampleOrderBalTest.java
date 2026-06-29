package com.smc.smccloud;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.TaskNoticCommonMapper;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.sampleorder.ZlzsExportRequest;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.SampleBalService;
import com.smc.smccloud.service.SampleOrderApplyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/9/4 12:55
 * @Descripton TODO
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SampleOrderBalTest {

    @Resource
    private SampleOrderApplyService sampleOrderApplyService;

    @Resource
    private SampleBalService sampleBalService;

    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private TaskNoticCommonMapper taskNoticCommonMapper;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        SMCApp.setForSysUser();
    }


    @Test
    public void handSampleBal() {
        ResultVo<String> resultVo = sampleOrderApplyService.autoInsertSales();
        System.out.println("resultVo = " + resultVo.toString());
    }


    @Test
    public void  exportZlzsOrderBalance() {
        ZlzsExportRequest request = new ZlzsExportRequest();
        request.setStartExpDate("2022-01-01");
        request.setEndExpDate("2022-06-01");
        ResultVo<String> resultVo = sampleBalService.pushZlzsOrderBalanceForPdf(request);
        // ResultVo<String> resultVo = sampleOrderApplyService.exportOverdueBalData();
        System.out.println("JSONUtil.toJsonPrettyStr(resultVo) = " + JSONUtil.toJsonPrettyStr(resultVo));
//        MultipartFile multipartFile = null;
//        try {
//            File file = new File("E:\\xxx\\展览展示品销账模板.xlsx");
//            FileInputStream fileInputStream = new FileInputStream(file);
//            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        sampleBalService.importWriteOffData(multipartFile,"C18023");
    }

    @Test
    public void getDictInfoByClassCode() {
        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(com.smc.smccloud.model.constants.Constants.DICT_CODE_SAMPLEORDER_APPTYPE);
        System.out.println("JSONUtil.toJsonPrettyStr(dataCodes) = " + JSONUtil.toJsonPrettyStr(dataCodes));
    }


    @Autowired
    private StringEncryptor encryptor;

    @Autowired
    private ApplicationContext context;




    @Test
    public void encode() {
//        Environment env = context.getBean(Environment.class);
//        String username = env.getProperty("spring.datasource.username");
//        String password = "123456";//env.getProperty("spring.datasource.password");
//        String encrypt = encryptor.encrypt(password);
//        log.info("数据库用户名：{}", username);
//        log.info("未加密的密码：{}", password);
//        log.info("加密的密码：ENC({})", encrypt);
        taskNoticCommonMapper.insertTaskNotice("3","2","3","4","5","6","7","8");
    }

}
