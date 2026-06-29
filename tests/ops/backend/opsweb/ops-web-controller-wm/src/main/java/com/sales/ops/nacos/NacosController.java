package com.sales.ops.nacos;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class NacosController {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Autowired
    private NacosServiceManager nacosServiceManager;

    @GetMapping(value = "/api/nacos/deregister")
    public String deregisterInstance() {
        String serviceName = nacosDiscoveryProperties.getService();
        String groupName = nacosDiscoveryProperties.getGroup();
        String clusterName = nacosDiscoveryProperties.getClusterName();
        String ip = nacosDiscoveryProperties.getIp();
        int port = nacosDiscoveryProperties.getPort();
        log.info("deregister from nacos, serviceName:{}, groupName:{}, clusterName:{}, ip:{}, port:{}", serviceName, groupName, clusterName, ip, port);
        try {
            nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties()).deregisterInstance(serviceName, groupName, ip, port, clusterName);
        } catch (NacosException e) {
            log.error("deregister from nacos error", e);
            return "error";
        }
        return "success";
    }


    @GetMapping(value = "/api/nacos/register")
    public String registerInstance() {
        String serviceName = nacosDiscoveryProperties.getService();
        String groupName = nacosDiscoveryProperties.getGroup();
        String clusterName = nacosDiscoveryProperties.getClusterName();
        String ip = nacosDiscoveryProperties.getIp();
        int port = nacosDiscoveryProperties.getPort();
        log.info("register from nacos, serviceName:{}, groupName:{}, clusterName:{}, ip:{}, port:{}", serviceName, groupName, clusterName, ip, port);
        try {
            nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties()).registerInstance(serviceName, groupName, ip, port, clusterName);
        } catch (NacosException e) {
            log.error("register from nacos error", e);
            return "error";
        }
        return "success";
    }
}
