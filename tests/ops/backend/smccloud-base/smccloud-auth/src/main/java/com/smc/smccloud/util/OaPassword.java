package com.smc.smccloud.util;

import com.smc.smccloud.core.utils.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

/**
 * 加解密类
 */
@Slf4j
public class OaPassword {
    public OaPassword() {
    }

    /**
     * 加密
     */
    public static String encryptPassword(String password) {
        String epassword = "";
        int j = password.length();
        char[] tempPassword = new char[j];
        tempPassword = password.toCharArray();

        int i;
        for (i = 0; i < j; ++i) {
            tempPassword[i] = (char) (158 - tempPassword[i]);
        }

        for (i = 0; i < j; ++i) {
            epassword = epassword + tempPassword[i];
        }

        return epassword;
    }

    /**
     * 解密
     */
    public static String decryptPassword(String encryptPassword) {
        String password = "";
        int j = encryptPassword.length();
        char[] tempPassword = new char[j];
        tempPassword = encryptPassword.toCharArray();

        int i;
        for (i = 0; i < j; ++i) {
            tempPassword[i] = (char) (158 - tempPassword[i]);
        }

        for (i = 0; i < j; ++i) {
            password = password + tempPassword[i];
        }

        return password;
    }



    public static void main(String[] args) {
        // 6745d36b84db42afa690651361a42df8
        // {bcrypt}$2a$10$J2G17zsK2QjsjKfQQ9Yp5uqfOA8Q2ihoUVn.62V9Be4xyL2biMTme
        // 加密client_secret
        String s = "{bcrypt}" + new BCryptPasswordEncoder().encode("6745d36b84db42afa690651361a42df8");

        System.out.println("args = " + s);

    }
}
