package com.sales.ops.common.until;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：调用db30接口
 * @date ：Created in 2022/1/11 14:25
 */
@Component
public class OkHttpDB30ApiUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpAddTokenUtil.class);
    private static final MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");
    private static final MediaType X_WWW = MediaType.parse("application/x-www-form-urlencoded");

    private static String token = "";

    //获取备案数据查询接口
    @Autowired
    private OkHttpClient okHttpClient;

    /*
     *post http://localhost:8080/oauth/token?client_id=client_1&client_secret=123456&grant_type=client_credentials&grant_type=select
     *{
    "access_token": "ca96b042-0953-435e-b5ba-cc6b7913a033",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "select"
}
     *
     *
     *
     * */


    public JSONObject putJsonData(String dataUrl, String JsonParam, String tokenUrl,String clientId,String clientSecret,String grantType){

        Map<String,String> map = getData(dataUrl,JSONTYPE,JsonParam);
        if(map.containsKey("falg")&& map.get("falg").equals("200")){//返回数据成功
            return JSONObject.parseObject(map.get("message"));
        }
        if(map.containsKey("falg")&& map.get("falg").equals("401")){//token失效重新获得token
            boolean tokenFlag = getToken(tokenUrl,clientId,clientSecret,grantType);//获取token成功
            if(tokenFlag){//再次调用一次数据
                Map<String,String> againMap = getData(dataUrl,JSONTYPE,JsonParam);
                if(againMap.containsKey("flag") && map.get("falg").equals("200")){//返回数据成功
                    return JSONObject.parseObject(againMap.get("message"));
                }else{
                    logger.warn(againMap.get("message"));
                }
            }else{
                logger.warn(map.get("message"));
            }
        }
        logger.warn(map.get("message"));
        return null;
    }

    private Boolean getToken(String url,String clientId,String clientSecret,String grantType){
        StringBuffer param = new StringBuffer();
        param.append("client_id=").append(clientId).append("&").append("client_secret=")
                .append(clientSecret).append("&").append("grant_type=").append(grantType);
        Map<String,String> result = exectePost(X_WWW,url,param.toString());
        if(result.containsKey("flag") && result.get("flag").equals("200")) {
            JSONObject obj = JSONObject.parseObject(result.get("message"));
            if(StringUtils.isNotBlank(obj.getString("access_token"))){//调用成功
                //获取Token
                token = new StringBuffer("bearer ").append(obj.getString("access_token")).toString();
                return true;
            }else{
                logger.warn(result.get("message"));
            }
        }
        return false;
    }

    private Map<String,String> getData(String dataUrl,MediaType mediaType, String param){
        Map<String,String> result = exectePostAddToken(mediaType,dataUrl,param,this.token);
        return result;
    }

    /**
     * Post请求发送数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的body
     * 参数三：mediaType json/xml
     */
    private Map<String,String> exectePost(MediaType mediaType,String url, String params) {
        RequestBody requestBody = RequestBody.create(mediaType, params);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return execute(request);
    }

    /**
     * Post请求发送数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的body
     * 参数三：mediaType json/xml
     */
    private Map<String,String> exectePostAddToken(MediaType mediaType,String url, String params,String token) {
        RequestBody requestBody = RequestBody.create(mediaType, params);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("Authorization",token)// Bearer 后面有空格 Authorization
                .build();
        return execute(request);
    }

    private Map<String,String> execteGetAddToken(String url,String token) {
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization",token)// Bearer 后面有空格 Authorization
                .build();
        return execute(request);
    }

    //执行
    private Map<String,String> execute(Request request) {
        Response response = null;
        Map<String,String> result = new HashMap<String,String>();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                result.put("flag","200");
                result.put("message",response.body().string());
                return result;
            } else if(response.code() == 401){
                result.put("flag","401");
                result.put("failure",response.body().string());
                return result;
            }else{
                result.put("flag",response.code()+"");
                result.put("failure",response.body().string());
                return result;
            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return result;
    }
}
