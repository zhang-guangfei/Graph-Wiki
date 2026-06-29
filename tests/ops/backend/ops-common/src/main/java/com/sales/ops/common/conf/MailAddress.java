package com.sales.ops.common.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：读取邮件地址
 * @date ：Created in 2022/11/21 10:15
 */
@Configuration
public class MailAddress {

    /**
     * 邮件发送地址
     */
    @Value("${opsmail.threec-cc}")
    private String threeCcc;

    @Value("${opsmail.threec-mail-to}")
    private String threecMailTo;

    @Value("${opsmail.exception-handle-cc}")
    private String exceptionHandleCC;

    @Value("${opsmail.exception-handle-mail-to}")
    private String exceptionHandleMailTo;

    public String getThreeCcc() {
        return threeCcc;
    }

    public void setThreeCcc(String threeCcc) {
        this.threeCcc = threeCcc;
    }

    public String getThreecMailTo() {
        return threecMailTo;
    }

    public void setThreecMailTo(String threecMailTo) {
        this.threecMailTo = threecMailTo;
    }

    public String getExceptionHandleCC() {
        return exceptionHandleCC;
    }

    public void setExceptionHandleCC(String exceptionHandleCC) {
        this.exceptionHandleCC = exceptionHandleCC;
    }

    public String getExceptionHandleMailTo() {
        return exceptionHandleMailTo;
    }

    public void setExceptionHandleMailTo(String exceptionHandleMailTo) {
        this.exceptionHandleMailTo = exceptionHandleMailTo;
    }
}
