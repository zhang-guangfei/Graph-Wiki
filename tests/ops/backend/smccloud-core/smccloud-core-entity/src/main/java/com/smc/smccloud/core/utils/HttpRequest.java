package com.smc.smccloud.core.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 *  向指定 URL 发送POST方法的请求
 */
@Slf4j
public class HttpRequest
{

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                log.info(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * HttpClient send Post
     */
    public byte[] httpClientPost(final String url,final List<NameValuePair> params){
        // 客户端实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // post实例
        HttpPost httpPost = new HttpPost(url);
        // 返回请求
        CloseableHttpResponse response = null;
        // IO流
        InputStream inputStream = null;
        try {
            // 参数设置
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
            entity.setContentType("application/json;charset=utf-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            // 返回请求
            response = httpClient.execute(httpPost);
            // 获取响应码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                log.error("响应编码:" + response.getStatusLine().getStatusCode());
                log.error("请求失败:" + response.getStatusLine().getReasonPhrase());
            } else {
                log.error("响应编码:" + response.getStatusLine().getStatusCode());
                log.error("请求成功:" + response.getStatusLine().getReasonPhrase());
                // 获取全部的请求头
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    log.error("全部的请求头：" + header);
                }
                // 获取响应消息实体
                HttpEntity httpEntity = response.getEntity();
                // 返回IO流
                inputStream = httpEntity.getContent();
                // 返回bytes
                byte[] buffer = new byte[1024];
                int len = 0;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((len = inputStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.close();
                // 返回响应内容
                return bos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭inputStream和httpResponse
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * 发送 post 请求
     *
     * @param url     请求地址
     * @param jsonStr Form表单json字符串
     * @return 请求结果
     */
    public static String doPost(String url, Map<String, String> urlBuilderMap, String jsonStr) {
        // 创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建post请求方式实例
        HttpPost httpPost = null;

        // 设置参数--设置URL拼接参数
        if (urlBuilderMap != null && !urlBuilderMap.isEmpty()) {
            try {
                URIBuilder newBuilder = new URIBuilder(url);
                for (Map.Entry<String, String> param : urlBuilderMap.entrySet()) {
                    newBuilder.addParameter(param.getKey(), param.getValue());
                }
                httpPost = new HttpPost(newBuilder.build());
            } catch (URISyntaxException e) {
                log.error("--{}", e.getMessage(), e);
            }
        } else {
            httpPost = new HttpPost(url);
        }

        // 设置请求头 发送的是json数据格式
        httpPost.setHeader("Content-type", "application/json;charset=utf-8");
        httpPost.setHeader("Connection", "Close");

        // 设置参数---设置消息实体 也就是携带的数据
        if (jsonStr != null && !"".equals(jsonStr)) {
            StringEntity entity = new StringEntity(jsonStr, Charset.forName("UTF-8"));
            // 设置编码格式
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json;charset=utf-8");
            // 把请求消息实体塞进去
            httpPost.setEntity(entity);
        }

        // 执行http的post请求
        CloseableHttpResponse httpResponse;
        String result = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        } catch (IOException e) {
            log.error("--{}", e.getMessage(), e);
        }

        return result;
    }


    /**
     * post请求封装 参数为{"a":1,"b":2,"c":3}
     * @param path 接口地址
     * @param Info 参数
     * @return
     * @throws IOException
     */
    public static String doPostWithJsonStr(String path, JSONObject Info) throws IOException{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(path);

        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";

        try {
            StringEntity s = new StringEntity(Info.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
            result = strber.toString();
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("请求服务器成功，做相应处理");
            } else {
                System.out.println("请求服务端失败");
            }

        } catch (Exception e) {
            result= null;
            throw new RuntimeException(e);
        }

        return result;
    }

}
