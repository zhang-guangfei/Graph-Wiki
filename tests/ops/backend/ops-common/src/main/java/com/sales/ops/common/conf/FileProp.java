package com.sales.ops.common.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：服务器文件路径
 * @date ：Created in 2023/1/9 16:27
 */
@Component
@ConfigurationProperties(prefix = "file")
public class FileProp {
    /**
     * 连接url
     */
    private String base;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
