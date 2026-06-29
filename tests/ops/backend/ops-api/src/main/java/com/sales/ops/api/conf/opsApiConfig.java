package com.sales.ops.api.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author C02483
 * @version 1.0
 * @description: API配置文件
 * @date 2021/9/23 10:53
 */
@Configuration
public class opsApiConfig {

    @Value("${wms.url}")
    private String WmsApi;

    public String getWmsApi() {
        return WmsApi;
    }

}
