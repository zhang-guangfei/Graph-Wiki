package com.smc.smccloud.core.rabbitmq;

import cn.hutool.core.date.DateTime;
import com.smc.smccloud.core.utils.BaseException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

@Data
public class RabbitMqMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 随机数,必须唯一
     */
    private String randomNumber;

    /**
     * 发送内容：json格式
     */
    private String content;

    /**
     * 过滤标记
     */
    protected String flag;

    /**
     * 重试次数
     */
    protected int retireNumber = 0;

    /**
     * 延迟秒数
     */
    protected int delaySeconds = 0;

    /**
     * 数据类型：订单、预占库存、分库补库等
     */
    protected String dataType;

    /**
     * 系统标识：ERP（营业业务系统），MDM（主数据管理系统），SMS（销售门户）等
     */
    protected String system;

    /**
     * 状态：00待发送，01发送成功，02发送失败，11接收成功，12接收失败
     */
    private String status = "待发送";

    private String date = (new DateTime(new Date())).toString("yyyy-MM-dd HH:mm:ss");

    public RabbitMqMessage() {
    }

    public RabbitMqMessage(String content, String flag, String dataType, String system) {
        super();
        if(StringUtils.isBlank(dataType)) {
            throw new BaseException("rabbitMqMessage属性dataType不能为空");
        }
        this.content = content;
        this.flag = flag;
        this.dataType = dataType;
        this.system = system;
    }

    public RabbitMqMessage(String content, String flag, int retireNumber, int delaySeconds) {
        super();
        this.content = content;
        this.flag = flag;
        this.retireNumber = retireNumber;
        this.delaySeconds = delaySeconds;
    }

    public RabbitMqMessage(String content, String flag, int retireNumber, int delaySeconds, String dataType, String system) {
        super();
        this.content = content;
        this.flag = flag;
        this.retireNumber = retireNumber;
        this.delaySeconds = delaySeconds;
        this.dataType = dataType;
        this.system = system;
    }

}
