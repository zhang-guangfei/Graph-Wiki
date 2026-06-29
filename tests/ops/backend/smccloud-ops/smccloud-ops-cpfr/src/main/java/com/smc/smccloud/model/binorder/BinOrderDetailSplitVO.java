package com.smc.smccloud.model.binorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author edp02 @Date 2022/5/30 16:35
 */
@Data
public class BinOrderDetailSplitVO {

    /**
     * 生成的单号
     */
    private String orderNo;

    private Long fromId;

    private String orderType;

    private String createUser;

    private String modelNo;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    private String transType;

    private Integer delFlag;

    private Long id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    private String updateUser;

    private Integer confirmQty;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dlvDate;

    private String supplierCode;

    private Integer status;

    private Long calcId;

    private Integer appId;

    private Integer itemNo;
}
