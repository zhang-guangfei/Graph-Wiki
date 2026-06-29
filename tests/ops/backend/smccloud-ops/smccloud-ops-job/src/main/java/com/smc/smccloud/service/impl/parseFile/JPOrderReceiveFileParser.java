package com.smc.smccloud.service.impl.parseFile;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.model.EmailInfo;
import com.smc.smccloud.model.FileParseType;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.service.EmailFileParser;
import com.smc.smccloud.service.OrderStateServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * 解析日本收到订单通知文件 ORDER-95012.TXT
 */
@Slf4j
@Service
public class JPOrderReceiveFileParser implements EmailFileParser {

    @Resource
    private OrderStateServiceFeignApi orderStateServiceFeignApi;


    @Override
    public String keyword() {
        return FileParseType.ORDER_RECEIVE_FILE.keyword();
    }

    @Override
    public ResultVo<String> parserFile(File file) {
        log.info("文件 => {}, 解析器: {}",file.getName(),"JPOrderReceiveFileParser");
        // add by LiYingChao from bugId 8753 in 2022-11-22
        if (!file.getName().toLowerCase().contains("ORDER-95012.TXT".toLowerCase())) {
            return ResultVo.success(file.getName()+"不进行解析");
        }
        long startTime = System.currentTimeMillis();
        MultipartFile multipartFile;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
            ResultVo<String> stringResultVo = orderStateServiceFeignApi.importJPReceiveOrderFile(multipartFile);
            if (!stringResultVo.isSuccess()) {
                return ResultVo.failure(stringResultVo.getMessage());
            }
            long endTime = System.currentTimeMillis();
            log.info("解析文件程序运行时间： " + (endTime - startTime) + "ms");
            return ResultVo.success(stringResultVo.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
            return ResultVo.failure(file.getName() + " JPOrderReceiveFileParser 解析异常:"+e.getMessage());
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
