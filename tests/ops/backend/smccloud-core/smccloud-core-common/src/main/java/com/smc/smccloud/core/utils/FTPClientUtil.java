package com.smc.smccloud.core.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class FTPClientUtil {


    public FTPClient ftpConnection(String ip, String port, String username, String password) throws Exception {
            FTPClient ftpClient = new FTPClient();
            // 设置连接超时时间
            // ftpClient.setConnectTimeout(600000);
            log.info("开始连接");
            ftpClient.connect(ip, Integer.parseInt(port));
            log.info("连接成功");
            System.out.println("连接成功");
            ftpClient.login(username, password);
            log.info("登录成功");

            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)) {
                ftpClient.disconnect();
                log.error("--ftp连接失败--");
                System.exit(1);
            }
            ftpClient.enterLocalPassiveMode();//这句最好加告诉对面服务器开一个端口
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

            return ftpClient;
    }


    /**
     * 关闭FTP方法
     * @param ftp
     * @return
     */
    public boolean closeFTP(FTPClient ftp){

        try {
            ftp.logout();
        } catch (Exception e) {
            log.error("FTP关闭失败");
        }finally{
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    log.error("FTP关闭失败");
                }
            }
        }

        return false;

    }

    /*public static void main(String[] args) {
     String ss = "010";
        int i = Integer.parseInt(ss);
        System.out.println("i = " + i);
        String s = String.valueOf(i);
        System.out.println("s = " + s);
    }*/


}