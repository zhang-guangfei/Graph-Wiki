package com.sales.ops.common.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：	订单执行DIY时间 handleid
 * @date ：Created in 2023/8/8 10:27
 */
@Component
@ConfigurationProperties(prefix = "handle")
public class XXLJOBHandleIdProp {

    private int onceid;

    public int getOnceid() {
        return onceid;
    }

    public void setOnceid(int onceid) {
        this.onceid = onceid;
    }
}
