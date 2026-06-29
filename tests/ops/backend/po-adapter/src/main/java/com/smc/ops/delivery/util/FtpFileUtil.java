package com.smc.ops.delivery.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/4/17 14:00
 * @Descripton TODO
 */
@Slf4j
@Component
public class FtpFileUtil {

    public static String dowmloadFile(String upFileName,String server,String user,String password,String filePath) {
        int port = 21;
        String remoteFilePath = File.separator+upFileName;
        String localFilePath = filePath+ File.separator+upFileName+File.separator+DateUtil.getYearMonthDay(DateUtil.getToday());
        FTPClient ftpClient = new FTPClient();
        try {
            // 连接到FTP服务器
            ftpClient.connect(server, port);
            ftpClient.login(user, password);
            ftpClient.enterLocalPassiveMode();

            // 设置文件类型为二进制，以防止文件损坏
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            // 检查本地文件夹是否存在，如果不存在则创建
            File localDir = new File(localFilePath);
            if (!localDir.exists()) {
                localDir.mkdirs();
            }
            // 获取文件列表
            FTPFile[] files = ftpClient.mlistDir("/");
            String dateStr = "";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (FTPFile file : files) {
                if(file == null) {
                    continue;
                }
                if (file.getName().contains(upFileName)) {
                    LocalDateTime dateTime = LocalDateTime.ofInstant(file.getTimestamp().toInstant(), ZoneId.systemDefault());
                    String formattedDateTime = dateTime.format(formatter);
                    dateStr = formattedDateTime;
                    log.info("文件上次变更时间: {}", formattedDateTime);
                    log.info("解析 "+upFileName+" 文件,获得文件日期是"+ dateStr);
                    break;
                }
            }
            if (StringUtils.isBlank(dateStr)) {
                throw new BusinessException("解析 "+upFileName+" 文件,未获得文件日期");
            }
            if(!DateUtil.dateToDateString(new Date()).equals(dateStr.substring(0,10))) {
                throw new BusinessException("解析 "+upFileName+" 文件,文件日期是"+ dateStr + ",非当天时间,不进行解析");
            }
            String tmpFileName = upFileName.substring(0, upFileName.length() - 4);
            String fileName =tmpFileName+"-"+DateUtil.getYYYYMMDDHHMMSS(DateUtil.stringToDateTime(dateStr))+".ZIP" ;
            log.info("保存FTP文件url:"+localFilePath+File.separator+fileName);
            // 下载文件
            try (FileOutputStream fos = new FileOutputStream(localFilePath+File.separator+fileName)) {
                boolean success = ftpClient.retrieveFile(remoteFilePath, fos);
                if (success) {
                    log.info(upFileName+"文件下载成功");
                    return localFilePath+File.separator+fileName;
                } else {
                    log.error(upFileName+"文件下载失败");
                }
            }

        } catch (IOException ex) {
            log.error("{} 解析发生错误：{} ",upFileName,ex.getMessage(),ex);
            throw new BusinessException(upFileName+"解析发生错误： " + ex.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;

    }


}
