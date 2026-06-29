package com.sales.ops.api.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ip白名单
 * @date ：Created in 2022/6/3 15:50
 */
@Slf4j
public class IPInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("---------- 判断相关ip");
        //先获取相关需要验证的ip列表
        //过滤ip,若用户在白名单内，则放行
        String ipAddress=getIpAddress(request);
        //所用需要验证的ip,暂时批量验证
        List<String> listIps = getListIps();
        if ( listIps.size() == 0 || !listIps.contains(ipAddress) ){
            log.error("请检查ip白名单配置是否正确");
            return false;
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 获取符合要求的项目
     * @return
     */
    private List<String> getListIps(){
        /**
         * 保存最后结果
         */
        String ips = "1.1.1.1,10.116.1.118";
        List<String> list = splitStr(ips);
        return new ArrayList<>(list);
    }

    /**
     * 吧ip拆分
     */
    private List<String> splitStr(String ips){
        String[] split = ips.split(",");
        return new ArrayList<>(Arrays.asList(split));
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.hasText(ip)) {
            return ip.indexOf(",") > 0 ? ip.substring(0, ip.indexOf(",")) : ip;
        } else {
            ip = request.getHeader("X-Real-IP");
            return StringUtils.hasText(ip) ? ip : request.getRemoteAddr();
        }
    }
}
