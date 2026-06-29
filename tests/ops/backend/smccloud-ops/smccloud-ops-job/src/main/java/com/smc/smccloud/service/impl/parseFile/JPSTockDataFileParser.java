package com.smc.smccloud.service.impl.parseFile;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.EmailInfo;
import com.smc.smccloud.model.FileParseType;
import com.smc.smccloud.model.inventory.InventorySupplierVO;
import com.smc.smccloud.service.EmailFileParser;
import com.smc.smccloud.service.InventoryServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/1/25 10:03
 * @Descripton TODO
 */
@Slf4j
@Service
public class JPSTockDataFileParser implements EmailFileParser {

    @Resource
    private InventoryServiceFeignApi inventoryServiceFeignApi;
    private EmailInfo emailInfo;

    @Override
    public String keyword() {
        return FileParseType.JPSTOCK_FILE.keyword();
    }

    @Override
    public ResultVo<String> parserFile(File file) {

        if(emailInfo!=null && emailInfo.getSendTime()!=null)
        {
             if(DateUtil.getDiffDay(emailInfo.getSendTime(),DateUtil.getToday())>2)
             {
                 return ResultVo.failure("发送日期超过2天不导入，需用最新邮件导入"+ emailInfo.getSendTime());
             }
        }

        log.info("文件 => {}, 解析器: {}",file.getName(),"JPShippingDataFileParser");
        MultipartFile multipartFile = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            return inventoryServiceFeignApi.parseJPSTockFile(multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
            return  ResultVo.failure(file.getName() + " JPShippingDataFileParser 解析异常:"+e.getMessage());
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                boolean delete = file.delete();
                log.info("删除JPShippingDataFileParser解析邮件附件结果: {}",delete);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public void setSender(String sender) {

    }

    @Override
    public boolean validEmail(String title) {
        if (PublicUtil.isNotEmpty(title)&& title.indexOf(keyword())!=-1) {
            return true;
        }
        return false;
    }

    @Override
    public void setEmailInfo(EmailInfo info) {
       this.emailInfo=info;
    }


}
