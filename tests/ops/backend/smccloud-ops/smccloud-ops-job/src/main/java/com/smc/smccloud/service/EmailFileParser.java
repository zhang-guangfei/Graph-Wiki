package com.smc.smccloud.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.EmailInfo;

import java.io.File;
import java.util.Date;

/***
 * 解析邮件附件接口
 */
public interface EmailFileParser {

   /**
    * 文件名关键字
    * @return
    */
   String keyword();


   /**
    * 解析文件
    * @param file
    * @return
    */
     ResultVo<String> parserFile(File file);

     void setSender(String sender);

     boolean validEmail(String title);


     void  setEmailInfo(EmailInfo info);





}
