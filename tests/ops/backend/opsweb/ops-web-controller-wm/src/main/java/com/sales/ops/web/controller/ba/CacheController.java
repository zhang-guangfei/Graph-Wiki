package com.sales.ops.web.controller.ba;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.sales.ops.service.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/4/23 10:11
 */
@CrossOrigin
@RestController
@Slf4j
public class CacheController {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Autowired
    private NacosServiceManager nacosServiceManager;

    @Autowired
    private CacheService cacheService;

    @PostConstruct
    public void init() {
        log.info("----------------------加载分配数据到缓存start------------------");
        cacheService.saveBaseCache();
        log.info("----------------------加载分配数据到缓存end------------------");
    }

    /**
     * 保存库存缓存，预计x秒钟
     * @return
     */
    @GetMapping("/saveBaseCache")
    public String saveBaseCache(){
        try {
            String ip = nacosDiscoveryProperties.getIp();
            List<Instance> allInstances = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties()).getAllInstances("wm-service");
            for(Instance instance : allInstances){
                if(ip.equals(instance.getIp())){
                    continue;
                }
                OkHttpClient okHttpClient = new OkHttpClient();
                String url = "http://"+instance.getIp()+":"+instance.getPort()+"/saveBaseCacheLocal";
                Request request = new Request.Builder().url(url)
                        .get().build();
                Response response = okHttpClient.newCall(request).execute();
                String strres = response.body().string();
                log.info(instance.getIp() );
                log.info(strres);
                // 调用另外一个服务清除缓存
            }
            cacheService.saveBaseCache();
            log.info("保存分配缓存");
        } catch (Exception e) {
            log.error("保存分配缓存",e);
        }
        return "success";
    }

    /**
     * 保存基础缓存
     * @return
     */
    @GetMapping("/saveBaseCacheLocal")
    public String saveBaseCacheLocal(){
        try {
            cacheService.saveBaseCache();
            log.info("保存分配数据缓存");
        } catch (Exception e) {
            log.error("保存分配数据缓存",e);
        }
        return "success";
    }
}
