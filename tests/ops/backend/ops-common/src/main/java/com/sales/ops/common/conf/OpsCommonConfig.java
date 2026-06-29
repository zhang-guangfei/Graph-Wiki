package com.sales.ops.common.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：读取配置文件公用参数
 * @date ：Created in 2022/11/22 11:06
 */
@Configuration
public class OpsCommonConfig { //common doconfirm-error-frequency: 10

    /**
     * 失败doconfirm重试次数
     */
    @Value("${common.doconfirm-error-frequency}")
    private Integer coConfirmErrorFrequency;
    //库存修改重试此时 inventory-retry-frequency
    @Value("${common.inventory-retry-frequency}")
    private Integer coInvRetryFrequency;

    public Integer getCoConfirmErrorFrequency() {
        return coConfirmErrorFrequency;
    }

    public void setCoConfirmErrorFrequency(Integer coConfirmErrorFrequency) {
        this.coConfirmErrorFrequency = coConfirmErrorFrequency;
    }

    public Integer getCoInvRetryFrequency() {
        return coInvRetryFrequency;
    }

    public void setCoInvRetryFrequency(Integer coInvRetryFrequency) {
        this.coInvRetryFrequency = coInvRetryFrequency;
    }
}
