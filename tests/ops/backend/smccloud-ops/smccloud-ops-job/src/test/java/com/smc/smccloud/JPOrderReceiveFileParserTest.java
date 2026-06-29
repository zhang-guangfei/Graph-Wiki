package com.smc.smccloud;

import cn.hutool.json.JSONUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.service.EmailFileParser;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: B90034
 * Date: 2021-12-21 09:25
 * Description:
 */
public class JPOrderReceiveFileParserTest extends OpsJobApplicationTests {

    @Resource(name = "JPOrderReceiveFileParser")
    private EmailFileParser jpOrderReceiveFileParser;

    @Resource(name = "JPShippingDataFileParser")
    private EmailFileParser jpShippingDataFileParser;

    @Test
    public void parserFileTest() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);

        // orderno,itemNo,modelNo,quantity,supplierOrderNo,shikomiNo
        File file = new File("F:\\副本供应商返信-8月14日AP 返信");
        System.out.println("result = " + jpOrderReceiveFileParser.parserFile(file));
    }

    @Test
    public void impJPSHPINGTest()
    {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        File file = new File("F:\\TXQQ\\cache\\2355766573\\FileRecv\\shpinf.txt~\\shpinf.txt");
        File[] files = file.listFiles();
        for (File f : files) {
            ResultVo<String> resultVo = jpShippingDataFileParser.parserFile(f);
            System.out.println("resultVo = " + JSONUtil.toJsonPrettyStr(resultVo));
            f.delete();
        }
    }



}
