package com.sales.ops.common.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2022/9/11 10:26
 */
@Configuration
public class OpsUrlConfig {

    /**
     * 制造接口配置
     */
    @Value("${manu.url}")
    private String ManuApi;

    @Value("${manu.token.url}")
    private String ManuTokenApi;

    @Value("${manu.token.name}")
    private String ManuTokenName;

    @Value("${manu.token.psd}")
    private String ManuTokenPsd;

    public String getManuApi() {
        return ManuApi;
    }

    public void setManuApi(String manuApi) {
        ManuApi = manuApi;
    }

    public String getManuTokenApi() {
        return ManuTokenApi;
    }

    public void setManuTokenApi(String manuTokenApi) {
        ManuTokenApi = manuTokenApi;
    }

    public String getManuTokenName() {
        return ManuTokenName;
    }

    public void setManuTokenName(String manuTokenName) {
        ManuTokenName = manuTokenName;
    }

    public String getManuTokenPsd() {
        return ManuTokenPsd;
    }

    public void setManuTokenPsd(String manuTokenPsd) {
        ManuTokenPsd = manuTokenPsd;
    }
}
