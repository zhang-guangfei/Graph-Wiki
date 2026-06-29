package com.sales.ops.common.until;

import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：http工具类
 * @date ：Created in 2021/10/29 8:45
 */
@Component
public class OkHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    @Autowired
    private OkHttpClient okHttpClient;

    /**
     * get
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public  String get(String url, Map<String, String> queries) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = new Request.Builder()
                .url(sb.toString())
                .build();

        return execute(request);
    }

    /**
     * post
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return
     */
    public String post(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        return execute(request);
    }

    /**
     * get 请求
     * @param url  请求url地址
     * @param params 请求参数 map
     * @param headers 请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     * */
    public String doGet(String url, Map<String, String> params, String[] headers) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }
        Request.Builder builder = new Request.Builder();
        if (headers != null && headers.length > 0) {
            if (headers.length % 2 == 0) {
                for (int i = 0; i < headers.length; i = i + 2) {
                    builder.addHeader(headers[i], headers[i + 1]);
                }
            } else {
                logger.warn("headers s length[{}] is logger.error.", headers.length);
            }
        }
        Request request = builder.url(sb.toString()).build();
        logger.info("do get request and url[{}]", sb.toString());
        return execute(request);
    }

    /**
     * post 请求, 请求数据为 json 的字符串
     * @param url  请求url地址
     * @param json  请求数据, json 字符串
     * @return string
     */
    public String doPostJson(String url, String json) {
        logger.info("do post request and url[{}]", url);
        return exectePost(JSON ,url, json);
    }
    /**
     * post 请求, 请求数据为 xml 的字符串
     * @param url  请求url地址
     * @param xml  请求数据, xml 字符串
     * @return string
     */
    public String doPostXml(String url, String xml) {
        logger.info("do post request and url[{}]", url);
        return exectePost(XML,url, xml);
    }
    /**
     * Post请求发送数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的body
     * 参数三：mediaType json/xml
     */
    public String exectePost(MediaType mediaType,String url, String params) {
        RequestBody requestBody = RequestBody.create(mediaType, params);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return execute(request);
    }

    //执行
    private String execute(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }else{
                logger.error(response.toString());
                return response.toString();
            }
        } catch (Exception e) {
            logger.error(e.toString());
            //bugid:14677 c14717 20240709
            return e.toString();
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
