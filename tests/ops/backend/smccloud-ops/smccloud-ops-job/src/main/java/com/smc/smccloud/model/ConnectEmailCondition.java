package com.smc.smccloud.model;

import lombok.Data;

@Data
public class ConnectEmailCondition {
    private String host;
    private String port;
    private String userName;
    private String passWord;
    private String saveDirectory; // 文件保存地址


    /**
     *  code:
     * "host": "mail.smcgz.com.cn",
     * "port": "110",
     * "userName": "invoice",
     *  "passWord": "IT#190117",
     *  "saveDirectory": "E:/Attachment/"
     */
}
