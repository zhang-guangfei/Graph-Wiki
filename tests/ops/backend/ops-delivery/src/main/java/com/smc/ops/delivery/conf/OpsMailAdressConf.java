package com.smc.ops.delivery.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2024/07/04 16:27
 */
@Configuration
public class OpsMailAdressConf {

    @Value("${opsmail.modify-order-report-cc}")
    private String modifyOrderCC;

    @Value("${opsmail.modify-order-report-to}")
    private String modifyOrderTO;

    public String getModifyOrderCC() {
        return modifyOrderCC;
    }

    public void setModifyOrderCC(String modifyOrderCC) {
        this.modifyOrderCC = modifyOrderCC;
    }

    public String getModifyOrderTO() {
        return modifyOrderTO;
    }

    public void setModifyOrderTO(String modifyOrderTO) {
        this.modifyOrderTO = modifyOrderTO;
    }
}
