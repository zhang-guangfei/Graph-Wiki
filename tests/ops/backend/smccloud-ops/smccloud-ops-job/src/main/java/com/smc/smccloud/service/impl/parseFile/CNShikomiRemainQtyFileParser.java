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
import java.io.InputStream;
import java.util.Date;

/**
 * @author edp04
 * @title: CNShikomiRemainQtyFileParser
 * @date 2022/07/04 17:16
 */
@Slf4j
@Service
public class CNShikomiRemainQtyFileParser implements EmailFileParser {

    @Resource
    ProductServiceFeignApi productServiceFeignApi;


    @Override
    public String keyword() {
        return FileParseType.CNPRODUCTSHIKOMI_FILE.keyword();
    }

    @Override
    public ResultVo<String> parserFile(File file) {
        log.info("文件 => {}, 解析器: CNProductShikomiFileParser", file.getName());
        MultipartFile multipartFile = null;
        InputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            return productServiceFeignApi.importCNShikomiData(multipartFile);
        } catch (Exception e) {
            log.error("{}文件解析失败", keyword(), e);
            return ResultVo.failure(e.getMessage());
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
