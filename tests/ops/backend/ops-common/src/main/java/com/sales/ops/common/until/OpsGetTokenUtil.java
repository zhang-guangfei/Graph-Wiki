package com.sales.ops.common.until;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：通过token获取userName 封装DTO
 * @date ：Created in 2021/12/8 16:36
 */
public class OpsGetTokenUtil {

    public static String BEARER = "bearer";
    private final static  String TOKEN_HEADER="Authorization";

    public static String  getTokenByRequest(){
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  ra.getRequest();
        String headerToken = request.getHeader(TOKEN_HEADER);
        if (headerToken == null) {
            return null;
        }
        String token = getToken(headerToken);       ;
        return token;
    }
    public static String  getClientIpByRequest(){
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  ra.getRequest();
        String ip = getIPAddress(request);
        if (ip == null) {
            return null;
        }
        return ip;
    }
    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

    /**
     * 获取token串
     *
     * @param auth token
     * @return String
     */
    private static String getToken(String auth) {
        if ((auth != null)) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo(BEARER) == 0) {
                auth = auth.substring(7);
            }
            return auth;
        }
        return null;
    }
}


