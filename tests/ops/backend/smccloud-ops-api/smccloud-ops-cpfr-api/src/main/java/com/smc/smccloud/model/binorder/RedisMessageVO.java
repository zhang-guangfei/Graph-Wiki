package com.smc.smccloud.model.binorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author edp02 @Date 2022/3/12 9:37
 */
@Data
public class RedisMessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    private String no;

    private String userNo;

    private String msgType;

    private Integer status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date optDate;

    private String content;
}
