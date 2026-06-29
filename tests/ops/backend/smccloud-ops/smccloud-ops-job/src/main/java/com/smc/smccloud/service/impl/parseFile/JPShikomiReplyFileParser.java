package com.smc.smccloud.service.impl.parseFile;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.EmailInfo;
import com.smc.smccloud.model.FileParseType;
import com.smc.smccloud.service.EmailFileParser;
import com.smc.smccloud.service.ProductServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
public class JPShikomiReplyFileParser implements EmailFileParser {

    @Resource
    ProductServiceFeignApi productServiceFeignApi;
    @Override
    public String keyword() {
        return FileParseType.JPSHIKOMI_REPLY_FILE.keyword();
    }

    @Override
    public ResultVo<String> parserFile(File file) {
        log.info("文件 => {}, 解析器: JPShippingDataFileParser", file.getName());
        MultipartFile multipartFile = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            return productServiceFeignApi.importJPReplyFile(multipartFile);
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
                log.info("删除CNProductShikomiFileParser解析邮件附件结果: {}",delete);
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
        if (PublicUtil.isNotEmpty(title)&& title.contains(keyword())) {
            return true;
        }
        return false;
    }

    @Override
    public void setEmailInfo(EmailInfo info) {

    }


}
