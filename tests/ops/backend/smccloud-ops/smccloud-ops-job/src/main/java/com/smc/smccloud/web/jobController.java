package com.smc.smccloud.web;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.FileUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.ConnectEmailCondition;
import com.smc.smccloud.service.EmailFileParser;
import com.smc.smccloud.service.EmailReceiver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;


@RestController
@RequestMapping("/opsjob")
public class jobController {

    @Resource
    private EmailReceiver emailAttachmentReceiver;
    @Resource(name = "JPDeliveryDataFileParser")
    private EmailFileParser jPDeliveryDataFileParser;

    @Resource(name = "JPShippingDataFileParser")
    private EmailFileParser jPShippingDataFileParser;
    @Value("${ops-file-upload-path.url}")
    private String opsFileUploadPath;

//    @RequestMapping(value = "/autoGetEmailAttachment",method = RequestMethod.POST)
//    public ResultVo<String> autoGetEmailAttachment(@RequestBody ConnectEmailCondition connectEmailCondition){
//        ResultVo<String> stringResultVo = emailAttachmentReceiver.receiveEmails(connectEmailCondition);
//        if (stringResultVo.isSuccess()) {
//            return ResultVo.success("邮件解析完毕");
//        }
//        return ResultVo.failure("邮件解析失败");
//    }

    @GetMapping("/testDeliveryFile")
    public ResultVo<String> testDeliveryFile() {
        // SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        String path= opsFileUploadPath + File.separator + "DELIVERY.DAT";
        File file = new File(path);
        return jPDeliveryDataFileParser.parserFile(file);
    }

    @GetMapping("/testShpinfFile")
    public ResultVo<String> testShpinfFile() {
        // SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        String path= opsFileUploadPath + File.separator + "SHPINF.TXT";
        File file = new File(path);
        return jPShippingDataFileParser.parserFile(file);
    }
}
