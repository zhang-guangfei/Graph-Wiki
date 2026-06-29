package com.sales.ops.common.until;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：调用sss接口 bom清单
 * @date ：Created in 2022/1/11 14:25
 */
@Component
public class OkHttpSSSUtil {

	private static final Logger logger = LoggerFactory.getLogger(OkHttpAddTokenUtil.class);
	private static final MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");
	private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");
	private static final MediaType X_WWW = MediaType.parse("application/x-www-form-urlencoded");

	@Autowired
	private OPSRedisUtils opsRedisUtils;

	// 获取备案数据查询接口
	@Autowired
	private OkHttpClient okHttpClient;

	/*
	 * post http://10.116.32.63/token client_id:MDM client_secret:a1b2c3d4f5
	 * grant_type client_credentials
	 *
	 * { "access_token":
	 * "lYoGRvqT8IJbycdEEnQN8dByYBUDF9wlC79tZJWYnACRwAEoe5BOcbmlJsBC5ZQuY-1vYjbWrRmBC91rmQ0_8a_w0JdljhG76C9oofMkPGbVFEfKOVZ1jz3EDqChgAPkrHWQp5gLON00u8nzqH0SxW62OOAdpOeOQ0xmaG-HmVEpcy8hu77RuQoW7h4_u5zmg8zan6b0qRjtV5BbIKzJVToPXCZ8br0fuTKxPpX90e-OXmWdvovLVUHuNW6gHXeFANyvibYdY9vLLwyfeLdPVcZdC_nSCXTaTTXlpOQDIjgQFA5hjnrTNUsyfYtQs6IHTr_Wdg1NhuVvydxLdaLGI-P763q0Id74tZQ1uEZKRL7M1-3KPJ3v-wDUBrK2QbMWdzfapUXulAMDSyXQJsMow6UqyVNI1eLJd6r3_fdapK4",
	 * "token_type": "bearer", "expires_in": 2591999 }
	 *
	 *
	 * GET http://10.116.32.63/check/v1?modelNo= 10-AFF2C-01B-JR&fields=64&language=
	 * zh-CHS
	 */

	public JSONObject getDataTOJsonObj(String dataUrl, String modelNo, String fields, String language, String tokenUrl,
			String clientId, String clientSecret, String grantType) {
		JSONObject obj = getData(dataUrl, modelNo, fields, language);
		if (obj.getString("message") != null && obj.getString("message").equals("Serial Product")) {// 返回数据成功
			return obj;
		}
		if (obj.getString("code") != null && obj.getString("code").equals("42")) {
			return null;
		}
		if (obj.getString("message") != null && obj.getString("message").equals("已拒绝为此请求授权。")) {// token失效重新获得token
			boolean tokenFlag = getToken(tokenUrl, clientId, clientSecret, grantType);// 获取token成功
			if (tokenFlag) {// 再次调用一次数据
				JSONObject objAgain = getData(dataUrl, modelNo, fields, language);
				if (objAgain.getString("message") != null && objAgain.getString("message").equals("Serial Product")) {// 返回数据成功
					return objAgain;
				} else if (obj.getString("code") != null && obj.getString("code").equals("42")) {
					return null;
				} else {
					logger.warn(objAgain.toJSONString());
				}
			} else {
				logger.warn(obj.toJSONString());
			}
		}
		logger.warn(obj.toJSONString());
		return new JSONObject();
	}

	// 获取更新的typeid，http://10.116.32.63/ops/get
	public JSONObject getDataTOJsonObjUpdate(String dataUrl, String tokenUrl, String clientId, String clientSecret,
			String grantType) {
		JSONObject obj = getDataUpdate(dataUrl);
		if (obj.getString("message") != null && StringUtils.isBlank(obj.getString("message"))) {// 返回数据成功
			return obj;
		}
		if (obj.getString("message") != null && obj.getString("message").equals("已拒绝为此请求授权。")) {// token失效重新获得token
			boolean tokenFlag = getToken(tokenUrl, clientId, clientSecret, grantType);// 获取token成功
			if (tokenFlag) {// 再次调用一次数据
				JSONObject objAgain = getDataUpdate(dataUrl);
				if (objAgain.getString("message") != null && objAgain.getString("message").equals("Serial Product")) {// 返回数据成功
					return objAgain;
				} else {
					logger.warn(objAgain.toJSONString());
				}
			} else {
				logger.warn(obj.toJSONString());
			}
		}
		logger.warn(obj.toJSONString());
		return new JSONObject();
	}

	public JSONObject postDataTOJsonObjUpdate(String dataUrl, String tokenUrl, String clientId, String clientSecret,
			String grantType, List<Integer> id) {
		JSONObject obj = postDataUpdate(dataUrl, id);
		if (obj.getString("message") != null && StringUtils.isBlank(obj.getString("message"))) {// 返回数据成功
			return obj;
		}
		if (obj.getString("message") != null && obj.getString("message").equals("已拒绝为此请求授权。")) {// token失效重新获得token
			boolean tokenFlag = getToken(tokenUrl, clientId, clientSecret, grantType);// 获取token成功
			if (tokenFlag) {// 再次调用一次数据
				JSONObject objAgain = postDataUpdate(dataUrl, id);
				if (objAgain.getString("data") != null && objAgain.getString("data").equals("Finished.")) {// 返回数据成功
					return objAgain;
				} else {
					logger.warn(objAgain.toJSONString());
				}
			} else {
				logger.warn(obj.toJSONString());
			}
		}
		logger.warn(obj.toJSONString());
		return new JSONObject();
	}

	private Boolean getToken(String url, String clientId, String clientSecret, String grantType) {
		StringBuffer param = new StringBuffer();
		param.append("client_id=").append(clientId).append("&").append("client_secret=").append(clientSecret)
				.append("&").append("grant_type=").append(grantType);
		String result = exectePost(X_WWW, url, param.toString());
		if (StringUtils.isNotBlank(result)) {
			JSONObject obj = JSONObject.parseObject(result);
			if (StringUtils.isNotBlank(obj.getString("access_token"))) {// 调用成功
				// 获取Token
				String token = new StringBuffer("bearer ").append(obj.getString("access_token")).toString();
				opsRedisUtils.setKeyAndTimeoutDays("ops:token:sss_token", token, 10);
				return true;
			} else {
				logger.warn(result);
			}

		}
		return false;
	}

	// http://10.116.32.63/check/v1 ?modelNo= 10-AFF2C-01B-JR&fields=64&language=
	// zh-CHS
	private JSONObject getData(String dataUrl, String modelNo, String fields, String language) {
		String token = "";
		Object tokenObj = opsRedisUtils.get("ops:token:sss_token");
		if (Objects.nonNull(tokenObj)) {
			token = tokenObj.toString();
		}

		String result = execteGetAddToken(
				new StringBuffer(dataUrl).append("?").append("modelNo=").append(modelNo).append("&").append("fields=")
						.append(fields).append("&").append("language=").append(language).toString(),
				token);
		if (StringUtils.isNotBlank(result)) {
			return JSONObject.parseObject(result);
		}
		return new JSONObject();
	}

	// http://10.116.32.63/ops/get
	private JSONObject getDataUpdate(String dataUrl) {
		String token = "";
		Object tokenObj = opsRedisUtils.get("ops:token:sss_token");
		if (Objects.nonNull(tokenObj)) {
			token = tokenObj.toString();
		}
		String result = execteGetAddToken(new StringBuffer(dataUrl).toString(), token);
		if (StringUtils.isNotBlank(result)) {
			return JSONObject.parseObject(result);
		}
		return new JSONObject();
	}

	private JSONObject postDataUpdate(String dataUrl, List<Integer> id) {
		String token = "";
		Object tokenObj = opsRedisUtils.get("ops:token:sss_token");
		if (Objects.nonNull(tokenObj)) {
			token = tokenObj.toString();
		}

		String result = exectePostAddToken(JSONTYPE, new StringBuffer(dataUrl).toString(), JSONArray.toJSONString(id),
				token);
		if (StringUtils.isNotBlank(result)) {
			return JSONObject.parseObject(result);
		}
		return new JSONObject();
	}


	/**
	 * Post请求发送数据....{"name":"zhangsan","pwd":"123456"} 参数一：请求Url 参数二：请求的body
	 * 参数三：mediaType json/xml
	 */
	private String exectePost(MediaType mediaType, String url, String params) {
		RequestBody requestBody = RequestBody.create(mediaType, params);
		Request request = new Request.Builder().url(url).post(requestBody).build();
		return execute(request);
	}

	private String execteGetAddToken(String url, String token) {
		Request request = new Request.Builder().url(url).header("Authorization", token)// Bearer 后面有空格 Authorization
				.build();
		return execute(request);
	}

	private String exectePostAddToken(MediaType mediaType, String url, String params, String token) {
		RequestBody requestBody = RequestBody.create(mediaType, params);
		Request request = new Request.Builder().url(url).header("Authorization", token).post(requestBody).build();
		return execute(request);
	}

	// 执行
	private String execute(Request request) {
		Response response = null;
		try {
			response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			} else if (response.code() == 401) {
				return response.body().string();
			} else if (response.code() == 400) {
				return "";
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
}
