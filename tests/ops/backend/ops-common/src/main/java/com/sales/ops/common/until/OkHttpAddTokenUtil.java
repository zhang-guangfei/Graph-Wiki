package com.sales.ops.common.until;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sales.ops.dto.purchase.OrderToManuDto;
import com.sales.ops.dto.purchase.RecordInfo;
import com.sales.ops.enums.PurchaseManuEnum;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description： 调取备案数据查询接口工具类
 * @date ：Created in 2021/11/16 18:51
 */
@Component
public class OkHttpAddTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(OkHttpAddTokenUtil.class);
    private static final MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");
    private static String token = "";

    //获取备案数据查询接口
    @Autowired
    private OkHttpClient okHttpClient;

    /*
     * test地址 http://10.116.1.234:10300/auth/buoath/token   sales/ZTZjZjY0NTM0ZmRhNGRiZmJlZDhhNjZjYjMyNmYzOWU=  (base64)
     * release http://10.116.1.236:10300/auth/buoath/token     sales/YmYyYTEwZjkxN2Q3NDQ1YTg0NDg0NDZiMzgzYjM3NGU=  (base64)
     * */

    /**
     * @param dataUrl      http://10.116.1.234:10100/service/sales/queryRecordInfoByNoBonded
     * @param plantMark    厂别：CN/BJ/AM.. 必填
     * @param materialType 物料类型：I:料件，E:成品，2:设备 非必填
     * @param list         型号集合，最大支持 500 个型
     * @param tokenUrl     http://10.116.1.234:10300/auth/buoath/token
     * @param userName     sales
     * @param password     ZTZjZjY0NTM0ZmRhNGRiZmJlZDhhNjZjYjMyNmYzOWU=
     * @return
     */
    public List<RecordInfo> getData(String dataUrl, String plantMark, String materialType, List<String> list, String tokenUrl, String userName, String password) {

        List<RecordInfo> resultList = new ArrayList<RecordInfo>();
        JSONObject obj = getData(dataUrl, plantMark, materialType, list);
        if (obj.getString("code") != null && obj.getString("code").equals("0")) {//返回数据成功
            resultList = JSONArray.parseArray(obj.getJSONArray("data").toJSONString(), RecordInfo.class);
            return resultList;
        }
        if (obj.getString("code") != null && (obj.getString("code").equals(PurchaseManuEnum.EXPIRED_TOKEN.getType()) || obj.getString("code").equals(PurchaseManuEnum.INVALID_TOKEN.getType()))) {//token失效重新获得token
            boolean tokenFlag = getToken(tokenUrl, userName, password);//获取token成功
            if (tokenFlag) {//再次调用一次数据
                JSONObject objAgain = getData(dataUrl, plantMark, materialType, list);
                if (objAgain.getString("code") != null && objAgain.getString("code").equals("0")) {//返回数据成功
                    resultList = JSONArray.parseArray(objAgain.getJSONArray("data").toJSONString(), RecordInfo.class);
                    return resultList;
                } else {
                    logger.warn(objAgain.toJSONString());
                }

            } else {
                logger.warn(obj.toJSONString());
            }

        }

        logger.warn(obj.toJSONString());
        return resultList;
    }

    private Boolean getToken(String url, String username, String password) {
        JSONObject param = new JSONObject();
        param.put("appId", username);
        param.put("password", password);
        String result = doPostJson(url, param.toString(), "Authorization");
        if (StringUtils.isNotBlank(result)) {
            String[] resArr = result.split("~");
            if (StringUtils.isNotBlank(resArr[0])) {
                JSONObject obj = JSONObject.parseObject(resArr[0]);
                if (resArr.length > 1 && obj.getString("code").equals("0")) {//调用成功
                    if (StringUtils.isNotBlank(resArr[1])) {//获取Token
                        token = resArr[1];
                    }
                    return true;
                } else {
                    logger.warn(resArr[0]);
                }
            }
        }
        return false;
    }

    /**
     * @param dataUrl      http://10.116.1.234:10100/service/sales/queryRecordInfoByNoBonded
     * @param plantMark    厂别：CN/BJ/AM.. 必填
     * @param materialType 物料类型：I:料件，E:成品，2:设备 非必填
     * @param list         型号集合，最大支持 500 个型
     * @return
     */
    private JSONObject getData(String dataUrl, String plantMark, String materialType, List<String> list) {
        String param = com.alibaba.fastjson.JSON.toJSONString(list);
        String result = doPostAddTokenJson(dataUrl + "?plantMark=" + plantMark + "&materialType=" + materialType, param, this.token, "refreshToken");
        if (StringUtils.isNotBlank(result)) {
            String[] resArr = result.split("~");
            if (StringUtils.isNotBlank(resArr[0])) {
                JSONObject obj = JSONObject.parseObject(resArr[0]);
                if (obj.getString("code").equals("0")) {//调用成功
                    if (resArr.length > 1 && StringUtils.isNotBlank(resArr[1])) {//获取refreshToken 如果 Token 有效期过半，该接口回同时返回 refreshToken。可使用 refreshToken 继续请求。
                        token = resArr[1];
                    }
                    return obj;
                }
                return obj;
            }

        }
        return new JSONObject();
    }

    /**
     * post 请求, 请求数据为 json 的字符串
     *
     * @param url  请求url地址
     * @param json 请求数据, json 字符串
     * @return string
     */
    private String doPostAddTokenJson(String url, String json, String token, String headerName) {
        logger.info("do post request and url[{}]", url);
        return execteAddTokenPost(JSONTYPE, url, json, token, headerName);
    }

    /**
     * post 请求, 请求数据为 json 的字符串
     *
     * @param url  请求url地址
     * @param json 请求数据, json 字符串
     * @return string
     */
    private String doPostJson(String url, String json, String headerName) {
        logger.info("do post request and url[{}]", url);
        return exectePost(JSONTYPE, url, json, headerName);
    }

    /**
     * Post请求发送数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的body
     * 参数三：mediaType json/xml
     */
    private String exectePost(MediaType mediaType, String url, String params, String headerName) {
        RequestBody requestBody = RequestBody.create(mediaType, params);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return execute(request, headerName);
    }

    /**
     * Post请求发送数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的body
     * 参数三：mediaType json/xml
     */
    private String execteAddTokenPost(MediaType mediaType, String url, String params, String token, String headerName) {
        RequestBody requestBody = RequestBody.create(mediaType, params);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("Authorization", token)// Bearer 后面有空格 Authorization
                .build();
        return execute(request, headerName);
    }

    //执行
    private String execute(Request request, String headerName) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                if (response.header(headerName) != null) {
                    return response.body().string() + "~" + response.header(headerName);
                } else {
                    return response.body().string() + "~";
                }
            } else if (response.code() == 401) {
                return response.body().string() + "~";
            } else {
                logger.error(response.message());
                return response.toString();
            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }


    /**
     * 工厂订单发送，调用制造接口
     * 测试环境http://10.116.1.234:10100/service/sales/salesOrderAccess
     * 生产环境http://10.116.1.236:10100/service/sales/salesOrderAccess
     *
     * @param dataUrl
     * @param orderToManuDtos
     * @param tokenUrl
     * @param userName
     * @param password
     * @return
     */
    public JSONObject toManuOrder(String dataUrl, List<OrderToManuDto> orderToManuDtos, String tokenUrl, String userName, String password) {
        JSONObject result;
        logger.info(this.token);
        getToken(tokenUrl, userName, password);
        JSONObject obj = toManuOrder(dataUrl, orderToManuDtos);
        result = obj;
//        result = obj.getString("code");
//        result = obj.getJSONArray("code").toJSONString();
        if (obj.getString("code") != null && (obj.getString("code").equals(PurchaseManuEnum.EXPIRED_TOKEN.getType()) || obj.getString("code").equals(PurchaseManuEnum.INVALID_TOKEN.getType()))) {
            //token失效重新获得token
            logger.info(obj.getString("code"));
            boolean tokenFlag = getToken(tokenUrl, userName, password);
            //获取token成功
            if (tokenFlag) {//再次调用一次数据
                JSONObject objAgain = toManuOrder(dataUrl, orderToManuDtos);
                // 先注释掉条件，错误返回结果也要接收 && objAgain.getString("code").equals("0")
                if (objAgain.getString("code") != null) {//返回数据成功
//                    result  = objAgain.getString("code");
                    result = objAgain;
                    logger.info(objAgain.getString("message"));
                    return result;
                } else {
                    logger.warn(objAgain.toJSONString());
                }
            } else {
                logger.warn(obj.toJSONString());
            }

        }
        logger.warn(obj.toJSONString());
        return result;
    }

    private JSONObject toManuOrder(String dataUrl, List<OrderToManuDto> orderToManuDtos) {
        String param = com.alibaba.fastjson.JSON.toJSONString(orderToManuDtos);
        String result = doPostAddTokenJson(dataUrl, param, this.token, "refreshToken");
        if (StringUtils.isNotBlank(result)) {
            String[] resArr = result.split("~");
            if (StringUtils.isNotBlank(resArr[0])) {
                JSONObject obj = JSONObject.parseObject(resArr[0]);
                if (obj.getString("code").equals("0")) {
                    //调用成功
                    if (resArr.length > 1 && StringUtils.isNotBlank(resArr[1])) {//获取refreshToken 如果 Token 有效期过半，该接口回同时返回 refreshToken。可使用 refreshToken 继续请求。
                        token = resArr[1];
                    }
                    return obj;
                }
                return obj;
            }

        }
        return new JSONObject();
    }
}
