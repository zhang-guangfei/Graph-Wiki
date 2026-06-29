package com.sales.ops.common.conf;

import com.sales.ops.common.interceptor.OkHttpInterceptor;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：okhttpClient
 * @date ：Created in 2021/10/29 8:43
 */
@Configuration
public class OkhttpConfig {


    /*@Bean
    public Encoder encoder() {//解决中文乱码
        return new FormEncoder();
    }*/

    @Bean
    public OkHttpClient okHttpClient() {

        return new OkHttpClient.Builder()
                //.sslSocketFactory(sslSocketFactory(), x509TrustManager())
                .retryOnConnectionFailure(false)//是否自动重连
                .connectionPool(pool())
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90,TimeUnit.SECONDS)
                //.addInterceptor(okHttpInterceptor())//添加拦截器
                .build();
    }
    @Bean
    public X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    @Bean
    public SSLSocketFactory sslSocketFactory() {
        try {
            //信任任何链接
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a new connection pool with tuning parameters appropriate for a single-user application.
     * The tuning parameters in this pool are subject to change in future OkHttp releases. Currently
     */
    @Bean
    public ConnectionPool pool() {
        return new ConnectionPool(200, 5, TimeUnit.MINUTES); //配置连接池 ConnectionPool 默认创建5个线程，保持5分钟长连接
    }
}
