package com.smc.smccloud.core.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: B90034
 * Date: 2022-04-28 09:44
 * Description: 翻译工具类
 */
@Slf4j
@Component
public class FanYiUtil {

    private final static String BAI_DU_TRANSLATE_API = "https://fanyi-api.baidu.com/api/trans/vip/translate";
    private final static String APP_ID = "20220427001193046";
    private final static String SALT = "123456";
    private final static String PASSWORD = "CdBWEvpXyx2SY2ZNocQl";
    private final static String ZH = "zh";
    private final static String EN = "en";

    /**
     * 中文翻译为英文
     *
     * @param chinese 中文
     * @return 英文字符
     */
    public static String converToEN(String chinese) {
        try {
            Thread.sleep(2000); //接口访问频率有限制
        } catch (InterruptedException e) {
           log.error(e.getMessage(),e);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("q", chinese);
        params.put("from", ZH);
        params.put("to", EN);
        params.put("appid", APP_ID);
        params.put("salt", SALT);
        params.put("sign", SecureUtil.md5(APP_ID + chinese + SALT + PASSWORD)); // 拼接 appid+q+salt+密钥 后MD5加密
        log.info("params = {}", JSONUtil.toJsonPrettyStr(params));

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "fanyi-api.baidu.com");
        headers.put("Content-Type", "application/json");

        HttpResponse response = HttpUtil.createGet(BAI_DU_TRANSLATE_API)
                .addHeaders(headers)
                .form(params)
                .execute();
        String body = response.body();
        JSONObject jsonObject = JSONUtil.parseObj(body);

        String english = null;
        if (jsonObject.containsKey("trans_result")) {
            JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
            Map<String, String> result = (Map<String, String>) jsonArray.get(0);
            english = result.getOrDefault("dst", "");
        } else {
            log.error("request fanyi-api.baidu.com error : {}", response);
        }
        return english;
    }

    public static void main(String[] args) {
//        String chinese = "浙江昱荣数码喷印技术有限公司#北京精创通公司";
//        System.out.println("chinese = " + chinese);
//        String s = converToEN(chinese);
//        String[] split = s.split("#");
//        System.out.println("split = " + split.length);
//        System.out.println("split = " + split[0].trim());
//        System.out.println("split = " + split[1].trim());
        // Zhejiang Yurong Digital Jet Printing Technology Co., Ltd
        // Zhejiang Yurong Digital Jet Printing Technology Co., Ltd
        // Beijing Jingchuangtong Company
//        int wordByteLen = PublicUtil.getWordByteLen(chinese);
//        System.out.println("wordByteLen = " + wordByteLen);
//        System.out.println("1" + PublicUtil.getWordByteLen("aa"));
//        System.out.println("2 = " +  PublicUtil.getWordByteLen("w"));
//        System.out.println("3 = " +  PublicUtil.getWordByteLen("我问问"));
        String aa = converToEN("爱在心中");
        System.out.println("result = " + aa);


    }
}
