package com.smc.smccloud.model.orderstate;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/6/21 10:46
 * @Descripton TODO
 */
@Data
public class CheckOrderStateVO {

    private String rorderNo;
    private String customerNo;
    private String rorderItem;
    private String modelNo;
    private String stockCode;
    private String stockType;
    private Integer status;
    private Date updateTime;
    private Integer orderType;
    private Integer stateCode;

}
