package com.sales.ops.nacos;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosWatch;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: B90034
 * Date: 2021-11-20 08:45
 * Description: nacos客户端注册制服务端时,更改服务详情中的元数据
 */
@Configuration
@ConditionalOnNacosDiscoveryEnabled
@AutoConfigureBefore({SimpleDiscoveryClientAutoConfiguration.class, CommonsClientAutoConfiguration.class})
public class NacosDiscoveryClientConfiguration {

    public NacosDiscoveryClientConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            value = {"spring.cloud.nacos.discovery.watch.enabled"},
            matchIfMissing = true
    )
    public NacosWatch nacosWatch(NacosServiceManager nacosServiceManager, NacosDiscoveryProperties nacosDiscoveryProperties) {
        // 更改服务详情中的元数据,增加服务注册时间
        nacosDiscoveryProperties.getMetadata().put("startup.time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        /*Properties props = System.getProperties();
        nacosDiscoveryProperties.getMetadata().put("os", props.getProperty("os.name"));*/
        return new NacosWatch(nacosServiceManager, nacosDiscoveryProperties);
    }
}
