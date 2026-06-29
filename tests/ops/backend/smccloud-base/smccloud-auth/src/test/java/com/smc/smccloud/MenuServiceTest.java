package com.smc.smccloud;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.security.service.ReadAuthorityCacheService;
import com.smc.smccloud.core.utils.SMCApp;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cms.PasswordRecipientId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Denghui
 * Date: 2021-10-23 09:29
 * Description:
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceTest {
    @Autowired
    private ReadAuthorityCacheService readAuthorityCacheService;

    @Resource
    private RedisManager redisManager;

    @Test
    public void feignTest() {

        List<Map<String,String>> list = readAuthorityCacheService.findRoleAndResource();
        redisManager.set("aaa",JSONObject.toJSONString(list));
        Object aaa = redisManager.get("aaa");
        List<Map<String,String>> list1 = JSONObject.parseObject(aaa.toString(), List.class);
        System.out.println("list1 = " + list1.toString());
//        Map<String, Collection<ConfigAttribute>> requestMap = new ConcurrentHashMap<String, Collection<ConfigAttribute>>();
//        for(Map<String,String> map : list){
//            getMap(requestMap, map.get("NAME"), map.get("PATTERN"));
//        }
//        log.info("date: {}",requestMap);
//
//        redisManager.hPut("testCache", "cache",JSONObject.toJSONString(requestMap));
//        Object testCache = redisManager.hGet("testCache","cache");
//
//        Map<String, Collection<ConfigAttribute>> hashMap = JSONObject.parseObject(JSONObject.toJSONString(testCache), Map.class);
//        System.out.println("JSON = " + hashMap);
    }

    private synchronized void getMap(Map<String, Collection<ConfigAttribute>> requestMap, String roleName, String resource){
        Collection<ConfigAttribute> configAttributes;
        ConfigAttribute configAttribute = new SecurityConfig(roleName);

        //RequestMatcher requestMatcher = new AntPathRequestMatcher(resource);
        /**
         * 如果requestMatcher已经含有pattern，
         * 则requestMap的key值requestMatcher保持不变
         * 仅将pattern对应的角色添加至requestMap的value值Collection<ConfigAttribute>
         * 否则requestMap新添加pattern与configAttributes
         */
        if (requestMap.containsKey(resource)){
            configAttributes = requestMap.get(resource);
            configAttributes.add(configAttribute);
            //requestMap.put(requestMatcher, value);
        }else{
            configAttributes = new LinkedHashSet<>();
            configAttributes.add(configAttribute);
            requestMap.put(resource, configAttributes);
        }
    }
}
